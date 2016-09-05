package com.qfg.businesshelper.addeditproduct;

import android.support.annotation.NonNull;

import com.qfg.businesshelper.UseCaseHandler;
import com.qfg.businesshelper.addeditproduct.domain.usecase.AddProduct;
import com.qfg.businesshelper.addeditproduct.domain.usecase.EditProduct;
import com.qfg.businesshelper.stores.domain.model.Product;

import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * Created by rbtq on 9/2/16.
 */
public class AddEditProductPresenter implements AddEditProductContract.Presenter {
    private final AddEditProductContract.View mView;
    private final AddProduct mAddProduct;
    private final EditProduct mEditProduct;

    private final Product mProduct;

    public AddEditProductPresenter(@NonNull AddEditProductContract.View view,
                                   @NonNull Product product,
                                   @NonNull AddProduct addProduct,
                                   @NonNull EditProduct editProduct) {
        mView = view;
        mAddProduct = addProduct;
        mEditProduct = editProduct;
        mProduct = product;

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
        mView.setSavingIndicator(true);

        final EditProduct.RequestValues request = new EditProduct.RequestValues(product);
        UseCaseHandler.execute(mEditProduct, request, Schedulers.io(), new Subscriber<EditProduct.ResponseValue>() {
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
            public void onNext(EditProduct.ResponseValue responseValue) {
            }
        });
    }

    @Override
    public void start() {
        if (null != mProduct) {
            mView.editProduct(mProduct);
        }
    }
}
