package com.qfg.businesshelper.data.source;

import com.qfg.businesshelper.data.source.local.LocalProductDS;
import com.qfg.businesshelper.data.source.remote.RemoteOrdersDS;
import com.qfg.businesshelper.data.source.remote.RemoteProductDS;
import com.qfg.businesshelper.data.source.remote.RemoteUsersDS;
import com.qfg.businesshelper.data.source.repository.ProductRepository;
import com.qfg.businesshelper.data.source.repository.UsersRepository;

/**
 * Created by rbtq on 8/25/16.
 */
public class DataSourceFactory {
    private static ProductsDataSource mProductDS = new ProductRepository(
            new LocalProductDS(),
            new RemoteProductDS()
    );

    public static OrdersDataSource getOrdersDataSource() {
        return new RemoteOrdersDS();
    }

    public static ProductsDataSource getProductsDataSource() {
        return mProductDS;
    }

    public static UsersDataSource getUsersDataSource() {
        return new UsersRepository(new RemoteUsersDS());
    }
}
