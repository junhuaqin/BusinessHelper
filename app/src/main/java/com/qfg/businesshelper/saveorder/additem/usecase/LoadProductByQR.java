package com.qfg.businesshelper.saveorder.additem.usecase;

import android.support.annotation.NonNull;

import com.qfg.businesshelper.BaseProductResponse;
import com.qfg.businesshelper.UseCase;
import com.qfg.businesshelper.data.source.ProductsDataSource;
import com.qfg.businesshelper.stores.domain.model.Product;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by rbtq on 9/2/16.
 */
public class LoadProductByQR  extends UseCase<LoadProductByQR.RequestValues, LoadProductByQR.ResponseValue> {
    private final ProductsDataSource mDataSource;

    public LoadProductByQR(@NonNull ProductsDataSource dataSource) {
        mDataSource = dataSource;
    }

    @Override
    protected Observable<ResponseValue> executeUseCase(RequestValues requestValues) {
        return mDataSource.getProductByQR(requestValues.getQR())
                .map(new Func1<Product, ResponseValue>() {
                    @Override
                    public ResponseValue call(Product product) {
                        return new ResponseValue(product);
                    }
                });
    }

    public static final class RequestValues implements UseCase.RequestValues {
        private final String mQR;
        public RequestValues(@NonNull String qr) {
            mQR = qr;
        }

        public String getQR() {
            return mQR;
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
