package com.qfg.businesshelper.stores;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.qfg.businesshelper.UseCaseHandler;
import com.qfg.businesshelper.stores.domain.model.Product;
import com.qfg.businesshelper.stores.domain.usecase.GetProducts;

import java.util.List;

import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * Created by rbtq on 9/1/16.
 */
public class StoresPresenter implements StoresContract.Presenter {
    private boolean mFirstLoad = true;
    private final StoresContract.View mView;
    private final GetProducts mGetProducts;

    public StoresPresenter(@NonNull StoresContract.View view,
                           @NonNull GetProducts getProducts) {
        mView = view;
        mGetProducts = getProducts;

        mView.setPresenter(this);
    }

    @Override
    public void loadStores(boolean force) {
        mView.setLoadingIndicator(true);

        GetProducts.RequestValues request = new GetProducts.RequestValues();
        UseCaseHandler.execute(mGetProducts, request, Schedulers.io(), new Subscriber<GetProducts.ResponseValue>() {
            private void hideLoadingIndicator() {
                if (mView.isActive()) {
                    mView.setLoadingIndicator(false);
                }
            }

            @Override
            public void onCompleted() {
                hideLoadingIndicator();
                mFirstLoad = false;
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
                    processStores(responseValue.getProducts());
                }
            }
        });
    }

    @Override
    public void addNewProduct() {
        mView.showAddNewTask();
    }

    @Override
    public void onResult(int requestCode, int resultCode) {
        if (Activity.RESULT_OK == resultCode) {
            mView.showSuccessfullySavedMessage();
        }
    }

    @Override
    public void start() {
        loadStores(mFirstLoad);
    }

    private void processStores(List<Product> products) {
        if (products.isEmpty()) {
            mView.showNoProduct();
        } else {
            mView.showStores(products);
        }
    }
}
