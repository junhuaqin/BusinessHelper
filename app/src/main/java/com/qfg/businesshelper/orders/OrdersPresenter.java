package com.qfg.businesshelper.orders;

import android.support.annotation.NonNull;

import com.qfg.businesshelper.UseCaseHandler;
import com.qfg.businesshelper.orders.domain.model.Order;
import com.qfg.businesshelper.orders.domain.usecase.GetOrders;

import java.util.List;

import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * Created by rbtq on 8/25/16.
 */
public class OrdersPresenter implements OrdersContract.Presenter {
    private final OrdersContract.View mOrdersView;
    private final GetOrders mGetOrders;

    private boolean mFirstLoad = true;

    public OrdersPresenter(@NonNull OrdersContract.View view,
                           @NonNull GetOrders getOrders) {
        mOrdersView = view;
        mGetOrders = getOrders;

        mOrdersView.setPresenter(this);
    }

    @Override
    public void loadOrders() {
        loadOrders(true);
    }

    public void loadOrders(final boolean showLoadingUI) {
        if (showLoadingUI) {
            mOrdersView.setLoadingIndicator(true);
        }

        GetOrders.RequestValues request = new GetOrders.RequestValues(0, System.currentTimeMillis());
        UseCaseHandler.execute(mGetOrders, request, Schedulers.io(), new Subscriber<GetOrders.ResponseValue>() {

            private void hideLoadingIndicator() {
                if (mOrdersView.isActive() && showLoadingUI) {
                    mOrdersView.setLoadingIndicator(false);
                }
            }

            @Override
            public void onCompleted() {
                hideLoadingIndicator();
            }

            @Override
            public void onError(Throwable e) {
                hideLoadingIndicator();

                if (mOrdersView.isActive()) {
                    mOrdersView.showLoadingError(e);
                }
            }

            @Override
            public void onNext(GetOrders.ResponseValue responseValue) {
                if (mOrdersView.isActive()) {
                    processOrders(responseValue.getOrders());
                }
            }
        });
    }

    @Override
    public void start() {
        loadOrders();
    }

    private void processOrders(List<Order> orders) {
        if (orders.isEmpty()) {
            // Show a message indicating there are no orders for that filter type.
            mOrdersView.showNoOrders();
        } else {
            // Show the list of orders
            mOrdersView.showOrders(orders);
        }
    }
}
