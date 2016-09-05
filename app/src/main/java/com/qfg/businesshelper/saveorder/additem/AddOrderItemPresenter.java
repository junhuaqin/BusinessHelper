package com.qfg.businesshelper.saveorder.additem;

import android.support.annotation.NonNull;

import com.qfg.businesshelper.UseCaseHandler;
import com.qfg.businesshelper.saveorder.additem.usecase.LoadProductByQR;

import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * Created by rbtq on 9/2/16.
 */
public class AddOrderItemPresenter implements AddOrderItemContract.Presenter {
    private final AddOrderItemContract.View mView;
    private final LoadProductByQR mLoadByQR;

    public AddOrderItemPresenter(@NonNull AddOrderItemContract.View view,
                                 @NonNull LoadProductByQR loadProductByQR) {
        mView = view;
        mLoadByQR = loadProductByQR;

        mView.setPresenter(this);
    }
    @Override
    public void loadProductByQR(String qrCode) {
        mView.setLoadingIndicator(true);

        final LoadProductByQR.RequestValues request = new LoadProductByQR.RequestValues(qrCode);
        UseCaseHandler.execute(mLoadByQR, request, Schedulers.io(), new Subscriber<LoadProductByQR.ResponseValue>() {
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
            public void onNext(LoadProductByQR.ResponseValue responseValue) {
                if (mView.isActive()) {
                    mView.showProduct(responseValue.getProduct());
                }
            }
        });
    }

    @Override
    public void loadProduct(String barCode) {

    }


    @Override
    public void start() {
    }
}
