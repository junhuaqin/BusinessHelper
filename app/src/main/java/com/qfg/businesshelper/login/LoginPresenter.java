package com.qfg.businesshelper.login;

import android.content.Context;
import android.support.annotation.NonNull;

import com.qfg.businesshelper.UseCaseHandler;
import com.qfg.businesshelper.login.domain.usecase.Login;

import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * Created by rbtq on 9/1/16.
 */
public class LoginPresenter implements LoginContract.Presenter {
    private final Login mLogin;
    private final LoginContract.View mView;

    public LoginPresenter(@NonNull LoginContract.View view,
                          @NonNull Login login) {
        mView = view;
        mLogin = login;

        mView.setPresenter(this);
    }

    @Override
    public void login(Context context, String userName, String password) {
        mView.setLoggingIndicator(true);

        Login.RequestValues request = new Login.RequestValues(context, userName, password);
        UseCaseHandler.execute(mLogin, request, Schedulers.io(), new Subscriber<Login.ResponseValue>() {

            private void hideLoggingIndicator() {
                if (mView.isActive()) {
                    mView.setLoggingIndicator(false);
                }
            }

            @Override
            public void onCompleted() {
                hideLoggingIndicator();
            }

            @Override
            public void onError(Throwable e) {
                hideLoggingIndicator();

                if (mView.isActive()) {
                    mView.showLoginError(e);
                }
            }

            @Override
            public void onNext(Login.ResponseValue responseValue) {
                if (mView.isActive()) {
                    mView.showLoginSuccess(responseValue.getLoggedUser());
                }
            }
        });
    }

    @Override
    public void start() {

    }
}
