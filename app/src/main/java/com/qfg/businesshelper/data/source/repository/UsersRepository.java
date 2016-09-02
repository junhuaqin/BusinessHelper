package com.qfg.businesshelper.data.source.repository;

import android.content.Context;
import android.support.annotation.NonNull;

import com.qfg.businesshelper.data.source.UsersDataSource;
import com.qfg.businesshelper.data.source.local.UserPref;
import com.qfg.businesshelper.data.source.remote.RemoteUsersDS;
import com.qfg.businesshelper.login.domain.model.User;

import rx.Observable;
import rx.functions.Action0;

/**
 * Created by rbtq on 9/1/16.
 */
public class UsersRepository implements UsersDataSource {
    private final RemoteUsersDS mRemoteUsersDS;

    public UsersRepository(@NonNull RemoteUsersDS remoteUsersDS) {
        mRemoteUsersDS = remoteUsersDS;
    }

    @Override
    public Observable<User> login(@NonNull final Context context, @NonNull final String userName, @NonNull final String password) {
        return mRemoteUsersDS.login(context, userName, password).doOnCompleted(new Action0() {
            @Override
            public void call() {
                UserPref.setLoggedInUserToken(context, userName, password);
            }
        });
    }

    @Override
    public boolean isLoggedIn(@NonNull final Context context) {
        return UserPref.isLoggedIn(context);
    }
}
