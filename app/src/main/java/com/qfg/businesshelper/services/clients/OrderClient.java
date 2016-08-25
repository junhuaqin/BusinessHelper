package com.qfg.businesshelper.services.clients;

import com.qfg.businesshelper.orders.domain.model.Order;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by rbtq on 8/25/16.
 */
public interface OrderClient {
    @GET("orders/{from}/{to}")
    Observable<List<Order>> getOrders(
            @Path("from") long from,
            @Path("to") long to
    );
}
