package com.qfg.businesshelper.saveorder;

import android.support.annotation.NonNull;

import com.qfg.businesshelper.UseCaseHandler;
import com.qfg.businesshelper.orders.domain.model.Order;
import com.qfg.businesshelper.saveorder.domain.usecase.SaveOrder;

import java.util.ArrayList;

import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * Created by rbtq on 8/26/16.
 */
public class SaveOrderPresenter implements SaveOrderContract.Presenter {
    private final SaveOrderContract.View mView;
    private final SaveOrder mSaveOrder;
    private final Order mOrder = new Order();
    private OnOrderSavedListener mSavedListener;

    public SaveOrderPresenter(@NonNull SaveOrderContract.View view,
                             @NonNull SaveOrder saveOrder) {
        mView = view;
        mSaveOrder = saveOrder;

        mView.setPresenter(this);
    }

    @Override
    public void setOnOrderSavedListener(OnOrderSavedListener listener){
        mSavedListener = listener;
    }

    @Override
    public void saveOrder() {
        mView.setSavingIndicator(true);

        SaveOrder.RequestValues request = new SaveOrder.RequestValues(mOrder);
        UseCaseHandler.execute(mSaveOrder, request, Schedulers.io(), new Subscriber<SaveOrder.ResponseValue>() {
            private void hideSavingIndicator() {
                if (mView.isActive()) {
                    mView.setSavingIndicator(false);
                }
            }

            @Override
            public void onCompleted() {
                hideSavingIndicator();
                clearItems();
            }

            @Override
            public void onError(Throwable e) {
                hideSavingIndicator();

                if (mView.isActive()) {
                    mView.showSavingError(e);
                }
            }

            @Override
            public void onNext(SaveOrder.ResponseValue responseValue) {
                if (mView.isActive()) {
                    mView.showSavingSuccess();
                }

                if (null != mSavedListener) {
                    mSavedListener.onSaved(responseValue.getOrder());
                }
            }
        });
    }

    @Override
    public void start() {
        mView.showOrder(mOrder);
    }

    @Override
    public void addNewItem() {
        mView.showAddItem();
    }

    @Override
    public void addItem(Order.OrderItem item) {
        mOrder.addItem(item);
        mView.showOrder(mOrder);
    }

    @Override
    public void clearItems() {
        mOrder.setItems(new ArrayList<Order.OrderItem>());
        if (mView.isActive()) {
            mView.showOrder(mOrder);
        }
    }

    @Override
    public void deleteItem(Order.OrderItem item) {
        mOrder.deleteItem(item);
        mView.showOrder(mOrder);
    }
}
