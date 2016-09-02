package com.qfg.businesshelper.data.source;

import com.qfg.businesshelper.stores.domain.model.Product;

import java.util.List;

import rx.Observable;

/**
 * Created by rbtq on 9/1/16.
 */
public interface ProductsDataSource {
    Observable<List<Product>> getProducts();
    Observable<Product> addProduct(Product product);
}
