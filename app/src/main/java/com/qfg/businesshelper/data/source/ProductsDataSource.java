package com.qfg.businesshelper.data.source;

import com.qfg.businesshelper.stores.domain.model.Product;

import java.util.List;

import rx.Observable;

/**
 * Created by rbtq on 9/1/16.
 */
public interface ProductsDataSource {
    Observable<List<Product>> getProducts(boolean force);
    Observable<Product> addProduct(Product product);
    Observable<Product> getProductByQR(String qr);
    Observable<Product> getProductByBarCode(String barCode);
    Observable<Product> getProductByTitle(String title);
    Observable<Product> updateProductByBarCode(Product product);
    Observable<Boolean> deleteProductByBarCode(String barCode);
}
