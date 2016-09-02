package com.qfg.businesshelper.addeditproduct;

import android.support.annotation.NonNull;

import com.qfg.businesshelper.UseCaseHandler;
import com.qfg.businesshelper.addeditproduct.domain.usecase.AddProduct;
import com.qfg.businesshelper.stores.domain.model.Product;

import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * Created by rbtq on 9/2/16.
 */
public class AddEditProductPresenter implements AddEditProductContract.Presenter {
    private final AddEditProductContract.View mView;
    private final AddProduct mAddProduct;

    public AddEditProductPresenter(@NonNull AddEditProductContract.View view,
                                   @NonNull AddProduct addProduct) {
        mView = view;
        mAddProduct = addProduct;

        mView.setPresenter(this);
    }

    @Override
    public void addProduct(Product product) {
        mView.setSavingIndicator(true);

        final AddProduct.RequestValues request = new AddProduct.RequestValues(product);
        UseCaseHandler.execute(mAddProduct, request, Schedulers.io(), new Subscriber<AddProduct.ResponseValue>() {
            private void hideLoggingIndicator() {
                if (mView.isActive()) {
                    mView.setSavingIndicator(false);
                }
            }

            @Override
            public void onCompleted() {
                hideLoggingIndicator();
                if (mView.isActive()) {
                    mView.showSavingSuccess();
                }
            }

            @Override
            public void onError(Throwable e) {
                hideLoggingIndicator();
                if (mView.isActive()) {
                    mView.showSavingError(e);
                }
            }

            @Override
            public void onNext(AddProduct.ResponseValue responseValue) {
            }
        });
    }

    @Override
    public void modifyProduct(Product product) {

    }

    @Override
    public void deleteProduct(Product product) {

    }

    @Override
    public void start() {

    }
}
