package com.qfg.businesshelper.services.clients;

import com.qfg.businesshelper.login.domain.model.User;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by rbtq on 9/1/16.
 */
public interface UserClient {
    @POST("users/login")
    Observable<User> login(@Body User user);
}
