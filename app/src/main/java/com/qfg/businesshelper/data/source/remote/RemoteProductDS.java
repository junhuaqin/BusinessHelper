package com.qfg.businesshelper.data.source.remote;

import com.qfg.businesshelper.data.source.ProductsDataSource;
import com.qfg.businesshelper.data.source.local.UserPref;
import com.qfg.businesshelper.services.clients.ProductClient;
import com.qfg.businesshelper.services.generator.ServiceGenerator;
import com.qfg.businesshelper.stores.domain.model.Product;

import java.util.List;

import rx.Observable;

/**
 * Created by rbtq on 9/1/16.
 */
public class RemoteProductDS implements ProductsDataSource {
    @Override
    public Observable<List<Product>> getProducts() {
        ProductClient client = ServiceGenerator.createService(ProductClient.class, UserPref.getToken());
        return client.getProducts();
    }

    @Override
    public Observable<Product> addProduct(Product product) {
        ProductClient client = ServiceGenerator.createService(ProductClient.class, UserPref.getToken());
        return client.addProduct(product);
    }
}
