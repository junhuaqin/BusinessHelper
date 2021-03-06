package com.qfg.businesshelper.data.source.remote;

import com.qfg.businesshelper.data.source.OrdersDataSource;
import com.qfg.businesshelper.data.source.local.UserPref;
import com.qfg.businesshelper.orders.domain.model.Order;
import com.qfg.businesshelper.services.clients.OrderClient;
import com.qfg.businesshelper.services.generator.ServiceGenerator;
import com.qfg.businesshelper.statistics.domain.model.OrderStatistics;

import java.util.List;

import rx.Observable;

/**
 * Created by rbtq on 8/25/16.
 */
public class RemoteOrdersDS implements OrdersDataSource {
    @Override
    public Observable<List<Order>> getOrders(long from, long to) {
        OrderClient client = ServiceGenerator.createService(OrderClient.class, UserPref.getToken());
        return client.getOrders(from, to);
    }

    @Override
    public Observable<Order> saveOrder(Order order) {
        OrderClient client = ServiceGenerator.createService(OrderClient.class, UserPref.getToken());
        return client.saveOrder(order);
    }

    @Override
    public Observable<OrderStatistics> getStatistics() {
        OrderClient client = ServiceGenerator.createService(OrderClient.class, UserPref.getToken());
        return client.getStatistics();
    }
}
