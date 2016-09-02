package com.qfg.businesshelper.login.domain.usecase;

import android.content.Context;
import android.support.annotation.NonNull;

import com.qfg.businesshelper.UseCase;
import com.qfg.businesshelper.data.source.UsersDataSource;
import com.qfg.businesshelper.login.domain.model.User;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by rbtq on 9/1/16.
 */
public class Login extends UseCase<Login.RequestValues, Login.ResponseValue> {
    private final UsersDataSource mDataSource;

    public Login(@NonNull UsersDataSource usersDataSource) {
        mDataSource = usersDataSource;
    }

    @Override
    protected Observable<ResponseValue> executeUseCase(RequestValues requestValues) {
        return mDataSource.login(requestValues.getContext(),
                requestValues.getUserName(),
                requestValues.getPassword())
                .map(new Func1<User, ResponseValue>() {
                    @Override
                    public ResponseValue call(User user) {
                        return new ResponseValue(user);
                    }
                });
    }

    public static final class RequestValues implements UseCase.RequestValues {
        private final String mUserName;
        private final String mPassword;
        private final Context mContext;

        public RequestValues(@NonNull Context context, @NonNull String userName, @NonNull String password) {
            mContext = context;
            mUserName = userName;
            mPassword = password;
        }

        public Context getContext() {
            return mContext;
        }

        public String getUserName() {
            return mUserName;
        }

        public String getPassword() {
            return mPassword;
        }
    }

    public static final class ResponseValue implements UseCase.ResponseValue {
        private final User mUser;

        public ResponseValue(@NonNull User user) {
            mUser = user;
        }

        public User getLoggedUser() {
            return mUser;
        }
    }
}