package com.qfg.businesshelper.data.source.repository;

import android.support.annotation.NonNull;

import com.qfg.businesshelper.data.source.ProductsDataSource;
import com.qfg.businesshelper.data.source.local.LocalProductDS;
import com.qfg.businesshelper.data.source.remote.RemoteProductDS;
import com.qfg.businesshelper.stores.domain.model.Product;

import java.util.List;

import rx.Observable;
import rx.functions.Action1;

/**
 * Created by rbtq on 9/5/16.
 */
public class ProductRepository implements ProductsDataSource {
    private final LocalProductDS mLocalDS;
    private final RemoteProductDS mRemoteDS;

    public ProductRepository(@NonNull LocalProductDS localProductDS,
                             @NonNull RemoteProductDS remoteProductDS) {
        mLocalDS = localProductDS;
        mRemoteDS = remoteProductDS;
    }

    @Override
    public Observable<List<Product>> getProducts(boolean force) {
        if (force || mLocalDS.isEmpty()) {
            mLocalDS.clearCache();
            return mRemoteDS.getProducts(force).doOnNext(new Action1<List<Product>>() {
                @Override
                public void call(List<Product> products) {
                    if (products != null) {
                        for (Product product : products) {
                            mLocalDS.addProduct(product);
                        }
                    }
                }
            });
        } else {
            return mLocalDS.getProducts(force);
        }
    }

    @Override
    public Observable<Product> addProduct(Product product) {
        return mRemoteDS.addProduct(product).doOnNext(new Action1<Product>() {
            @Override
            public void call(Product product) {
                mLocalDS.addProduct(product);
            }
        });
    }

    @Override
    public Observable<Product> getProductByQR(String qr) {
        return mRemoteDS.getProductByQR(qr);
    }

    @Override
    public Observable<Product> getProductByBarCode(String barCode) {
        return mLocalDS.getProductByBarCode(barCode);
    }

    @Override
    public Observable<Product> getProductByTitle(String title) {
        return mLocalDS.getProductByTitle(title);
    }

    @Override
    public Observable<Product> updateProductByBarCode(Product product) {
        return mRemoteDS.updateProductByBarCode(product).doOnNext(new Action1<Product>() {
            @Override
            public void call(Product product) {
                mLocalDS.updateProductByBarCode(product);
            }
        });
    }

    @Override
    public Observable<Boolean> deleteProductByBarCode(final String barCode) {
        return mRemoteDS.deleteProductByBarCode(barCode).doOnNext(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                mLocalDS.deleteProductByBarCode(barCode);
            }
        });
    }
}
