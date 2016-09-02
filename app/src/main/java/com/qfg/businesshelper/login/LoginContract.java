package com.qfg.businesshelper.login;

import android.content.Context;

import com.qfg.businesshelper.BasePresenter;
import com.qfg.businesshelper.BaseView;
import com.qfg.businesshelper.login.domain.model.User;

/**
 * Created by rbtq on 9/1/16.
 */
public class LoginContract {
    interface View extends BaseView<Presenter> {
        void setLoggingIndicator(boolean active);
        void showLoginSuccess(User user);
        void showLoginError(Throwable e);

        boolean isActive();
    }

    interface Presenter extends BasePresenter {
        void login(Context context, String userName, String password);
    }
}
