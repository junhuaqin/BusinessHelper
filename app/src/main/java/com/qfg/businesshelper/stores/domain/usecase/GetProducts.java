package com.qfg.businesshelper.stores.domain.usecase;

import android.support.annotation.NonNull;

import com.qfg.businesshelper.UseCase;
import com.qfg.businesshelper.data.source.ProductsDataSource;
import com.qfg.businesshelper.stores.domain.model.Product;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by rbtq on 9/1/16.
 */
public class GetProducts extends UseCase<GetProducts.RequestValues, GetProducts.ResponseValue> {
    private final ProductsDataSource mDataSource;
    public GetProducts(@NonNull ProductsDataSource dataSource) {
        mDataSource = dataSource;
    }

    @Override
    protected Observable<ResponseValue> executeUseCase(RequestValues requestValues) {
        return mDataSource.getProducts(requestValues.isForce()).map(new Func1<List<Product>, ResponseValue>() {
            @Override
            public ResponseValue call(List<Product> products) {
                return new ResponseValue(products);
            }
        });
    }

    public static final class RequestValues implements UseCase.RequestValues {
        private final boolean mForce;
        public RequestValues(boolean force) {
            mForce = force;
        }

        public boolean isForce() {
            return mForce;
        }
    }

    public static final class ResponseValue implements UseCase.ResponseValue {
        private final List<Product> mProducts;

        public ResponseValue(@NonNull List<Product> products) {
            mProducts = products;
        }

        public List<Product> getProducts() {
            return mProducts;
        }
    }
}
