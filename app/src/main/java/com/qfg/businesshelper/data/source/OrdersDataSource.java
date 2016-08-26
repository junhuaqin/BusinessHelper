package com.qfg.businesshelper.data.source;

import com.qfg.businesshelper.orders.domain.model.Order;

import java.util.List;

import rx.Observable;

/**
 * Created by rbtq on 8/25/16.
 */
public interface OrdersDataSource {

    Observable<List<Order>> getOrders(long from, long to);
    Observable<Order> saveOrder(Order order);
}
