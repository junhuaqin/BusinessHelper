package com.qfg.businesshelper.addeditproduct.domain.usecase;

import android.support.annotation.NonNull;

import com.qfg.businesshelper.UseCase;
import com.qfg.businesshelper.data.source.ProductsDataSource;
import com.qfg.businesshelper.stores.domain.model.Product;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by rbtq on 9/2/16.
 */
public class AddProduct extends UseCase<AddProduct.RequestValues, AddProduct.ResponseValue> {
    private final ProductsDataSource mDataSource;

    public AddProduct(@NonNull ProductsDataSource dataSource) {
        mDataSource = dataSource;
    }

    @Override
    protected Observable<ResponseValue> executeUseCase(RequestValues requestValues) {
        return mDataSource.addProduct(requestValues.getProduct()).map(new Func1<Product, ResponseValue>() {
            @Override
            public ResponseValue call(Product product) {
                return new ResponseValue(product);
            }
        });
    }

    public static final class RequestValues implements UseCase.RequestValues {
        private final Product mProduct;

        public RequestValues(@NonNull Product product) {
            mProduct = product;
        }

        public Product getProduct() {
            return mProduct;
        }
    }

    public static final class ResponseValue implements UseCase.ResponseValue {
        private final Product mProduct;

        public ResponseValue(@NonNull Product product) {
            mProduct = product;
        }

        public Product getProduct() {
            return mProduct;
        }
    }
}
