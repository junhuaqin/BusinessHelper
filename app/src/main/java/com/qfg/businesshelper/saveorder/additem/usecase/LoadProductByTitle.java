package com.qfg.businesshelper.saveorder.additem.usecase;

import android.support.annotation.NonNull;

import com.qfg.businesshelper.BaseProductResponse;
import com.qfg.businesshelper.UseCase;
import com.qfg.businesshelper.data.source.ProductsDataSource;
import com.qfg.businesshelper.stores.domain.model.Product;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by rbtq on 9/5/16.
 */
public class LoadProductByTitle extends UseCase<LoadProductByTitle.RequestValues, LoadProductByTitle.ResponseValue> {
    private final ProductsDataSource mDataSource;

    public LoadProductByTitle(@NonNull ProductsDataSource dataSource) {
        mDataSource = dataSource;
    }

    @Override
    protected Observable<ResponseValue> executeUseCase(RequestValues requestValues) {
        return mDataSource.getProductByTitle(requestValues.getTitle())
                .map(new Func1<Product, ResponseValue>() {
                    @Override
                    public ResponseValue call(Product product) {
                        return new ResponseValue(product);
                    }
                });
    }

    public static final class RequestValues implements UseCase.RequestValues {
        private final String mTitle;
        public RequestValues(@NonNull String title) {
            mTitle = title;
        }

        public String getTitle() {
            return mTitle;
        }
    }

    public static final class ResponseValue implements BaseProductResponse {
        private final Product mProduct;

        public ResponseValue(@NonNull Product product) {
            mProduct = product;
        }

        @Override
        public Product getProduct() {
            return mProduct;
        }
    }
}