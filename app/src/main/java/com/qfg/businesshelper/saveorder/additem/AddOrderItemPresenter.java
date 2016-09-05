package com.qfg.businesshelper.saveorder.additem;

import android.support.annotation.NonNull;

import com.qfg.businesshelper.BaseProductResponse;
import com.qfg.businesshelper.UseCaseHandler;
import com.qfg.businesshelper.saveorder.additem.usecase.LoadProductByBarCode;
import com.qfg.businesshelper.saveorder.additem.usecase.LoadProductByQR;
import com.qfg.businesshelper.saveorder.additem.usecase.LoadProductByTitle;
import com.qfg.businesshelper.stores.domain.usecase.GetProducts;

import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * Created by rbtq on 9/2/16.
 */
public class AddOrderItemPresenter implements AddOrderItemContract.Presenter {
    private final AddOrderItemContract.View mView;
    private final LoadProductByQR mLoadByQR;
    private final LoadProductByBarCode mLoadByBarCode;
    private final LoadProductByTitle mLoadByTitle;
    private final GetProducts mGetProducts;

    public AddOrderItemPresenter(@NonNull AddOrderItemContract.View view,
                                 @NonNull LoadProductByQR loadProductByQR,
                                 @NonNull LoadProductByBarCode loadProductByBarCode,
                                 @NonNull LoadProductByTitle loadProductByTitle,
                                 @NonNull GetProducts getProducts) {
        mView = view;
        mLoadByQR = loadProductByQR;
        mLoadByBarCode = loadProductByBarCode;
        mLoadByTitle = loadProductByTitle;
        mGetProducts = getProducts;

        mView.setPresenter(this);
    }

    private <T extends BaseProductResponse> Subscriber<T> getShowProductSubscriber() {
        return new Subscriber<T>() {
            private void hideLoadingIndicator() {
                if (mView.isActive()) {
                    mView.setLoadingIndicator(false);
                }
            }

            @Override
            public void onCompleted() {
                hideLoadingIndicator();
            }

            @Override
            public void onError(Throwable e) {
                hideLoadingIndicator();
                if (mView.isActive()) {
                    mView.showLoadingError(e);
                }
            }

            @Override
            public void onNext(T responseValue) {
                if (mView.isActive()) {
                    mView.showProduct(responseValue.getProduct());
                }
            }
        };
    }

    @Override
    public void loadProductByQR(String qrCode) {
        mView.setLoadingIndicator(true);

        final LoadProductByQR.RequestValues request = new LoadProductByQR.RequestValues(qrCode);
        UseCaseHandler.execute(mLoadByQR, request, Schedulers.io(), this.<LoadProductByQR.ResponseValue>getShowProductSubscriber());
    }

    @Override
    public void loadProduct(String barCode) {
        mView.setLoadingIndicator(true);

        final LoadProductByBarCode.RequestValues request = new LoadProductByBarCode.RequestValues(barCode);
        UseCaseHandler.execute(mLoadByBarCode, request, Schedulers.io(), this.<LoadProductByBarCode.ResponseValue>getShowProductSubscriber());
    }

    @Override
    public void loadProductByTitle(String title) {
        mView.setLoadingIndicator(true);

        final LoadProductByTitle.RequestValues request = new LoadProductByTitle.RequestValues(title);
        UseCaseHandler.execute(mLoadByTitle, request, Schedulers.io(), this.<LoadProductByTitle.ResponseValue>getShowProductSubscriber());
    }

    private void loadProducts() {
        mView.setLoadingIndicator(true);

        GetProducts.RequestValues request = new GetProducts.RequestValues(false);
        UseCaseHandler.execute(mGetProducts, request, Schedulers.io(), new Subscriber<GetProducts.ResponseValue>() {
            private void hideLoadingIndicator() {
                if (mView.isActive()) {
                    mView.setLoadingIndicator(false);
                }
            }

            @Override
            public void onCompleted() {
                hideLoadingIndicator();
            }

            @Override
            public void onError(Throwable e) {
                hideLoadingIndicator();
                if (mView.isActive()) {
                    mView.showLoadingError(e);
                }
            }

            @Override
            public void onNext(GetProducts.ResponseValue responseValue) {
                if (mView.isActive()) {
                    mView.setProducts(responseValue.getProducts());
                }
            }
        });
    }


    @Override
    public void start() {
        loadProducts();
    }
}
