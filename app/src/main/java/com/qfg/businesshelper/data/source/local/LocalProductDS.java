package com.qfg.businesshelper.data.source.local;

import com.qfg.businesshelper.data.source.ProductsDataSource;
import com.qfg.businesshelper.stores.domain.model.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * Created by rbtq on 9/5/16.
 */
public class LocalProductDS  implements ProductsDataSource {
    private final Map<String, Product> mProducts = new HashMap<>();

    public void clearCache() {
        mProducts.clear();
    }

    public boolean isEmpty() {
        return mProducts.isEmpty();
    }

    @Override
    public Observable<List<Product>> getProducts(boolean force) {
        List<Product> products = new ArrayList<Product>(mProducts.values());
        return Observable.just(products);
    }

    @Override
    public Observable<Product> addProduct(Product product) {
        mProducts.put(product.getBarCode(), product);
        return Observable.just(product);
    }

    @Override
    public Observable<Product> getProductByQR(String qr) {
        return Observable.empty();
    }

    @Override
    public Observable<Product> getProductByBarCode(String barCode) {
        return Observable.just(mProducts.get(barCode));
    }

    @Override
    public Observable<Product> getProductByTitle(String title) {
        for (Product product : mProducts.values()) {
            if (product.getTitle().equals(title)) {
                return Observable.just(product);
            }
        }

        return Observable.empty();
    }

    @Override
    public Observable<Product> updateProductByBarCode(Product product) {
        mProducts.put(product.getBarCode(), product);
        return Observable.just(product);
    }

    @Override
    public Observable<Boolean> deleteProductByBarCode(String barCode) {
        Product product = mProducts.remove(barCode);
        return Observable.just((null != product));
    }
}
