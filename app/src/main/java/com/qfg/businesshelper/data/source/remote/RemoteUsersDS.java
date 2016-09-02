package com.qfg.businesshelper.data.source.remote;

import android.content.Context;
import android.support.annotation.NonNull;

import com.qfg.businesshelper.data.source.UsersDataSource;
import com.qfg.businesshelper.login.domain.model.User;
import com.qfg.businesshelper.services.clients.UserClient;
import com.qfg.businesshelper.services.generator.ServiceGenerator;

import rx.Observable;

/**
 * Created by rbtq on 9/1/16.
 */
public class RemoteUsersDS implements UsersDataSource {
    @Override
    public Observable<User> login(@NonNull final Context context, @NonNull final String userName, @NonNull final String password) {
        UserClient client = ServiceGenerator.createService(UserClient.class);
        return client.login(new User().setUserName(userName).setPassword(password));
    }

    @Override
    public boolean isLoggedIn(@NonNull final Context context) {
        return false;
    }
}
