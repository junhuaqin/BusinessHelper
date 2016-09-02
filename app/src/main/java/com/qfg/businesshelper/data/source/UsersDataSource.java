package com.qfg.businesshelper.data.source;

import android.content.Context;
import android.support.annotation.NonNull;

import com.qfg.businesshelper.login.domain.model.User;

import rx.Observable;

/**
 * Created by rbtq on 9/1/16.
 */
public interface UsersDataSource {
    Observable<User> login(@NonNull final Context context, @NonNull final String userName, @NonNull final String password);
    boolean isLoggedIn(@NonNull final Context context);
}
