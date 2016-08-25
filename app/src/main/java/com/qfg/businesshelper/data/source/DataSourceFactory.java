package com.qfg.businesshelper.data.source;

import com.qfg.businesshelper.data.source.remote.RemoteOrdersDS;

/**
 * Created by rbtq on 8/25/16.
 */
public class DataSourceFactory {
    public static OrdersDataSource getOrdersDataSource() {
        return new RemoteOrdersDS();
    }
}
