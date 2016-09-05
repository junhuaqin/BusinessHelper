package com.qfg.businesshelper.stores.domain.usecase;

import android.support.annotation.NonNull;

import com.qfg.businesshelper.UseCase;
import com.qfg.businesshelper.data.source.ProductsDataSource;
import com.qfg.businesshelper.stores.domain.model.Product;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by rbtq on 9/5/16.
 */
public class DeleteProduct extends UseCase<DeleteProduct.RequestValues, DeleteProduct.ResponseValue> {
    private final ProductsDataSource mDataSource;

    public DeleteProduct(@NonNull ProductsDataSource dataSource) {
        mDataSource = dataSource;
    }

    @Override
    protected Observable<ResponseValue> executeUseCase(RequestValues requestValues) {
        return mDataSource.deleteProductByBarCode(requestValues.getProduct().getBarCode()).map(new Func1<Boolean, ResponseValue>() {
            @Override
            public ResponseValue call(Boolean result) {
                return new ResponseValue(result);
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
        private final Boolean mResult;

        public ResponseValue(@NonNull Boolean result) {
            mResult = result;
        }

        public Boolean getResult() {
            return mResult;
        }
    }
}