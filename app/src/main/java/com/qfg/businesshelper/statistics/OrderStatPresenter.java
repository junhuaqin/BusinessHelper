package com.qfg.businesshelper.statistics;

import android.support.annotation.NonNull;

import com.qfg.businesshelper.UseCaseHandler;
import com.qfg.businesshelper.statistics.domain.usecase.GetOrderStatistics;

import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * Created by rbtq on 8/26/16.
 */
public class OrderStatPresenter implements OrderStatContract.Presenter {
    private final OrderStatContract.View mView;
    private final GetOrderStatistics mGetOrderStat;

    private boolean mFirstLoad = true;

    public OrderStatPresenter(@NonNull OrderStatContract.View view,
                              @NonNull GetOrderStatistics getOrderStat) {
        mView = view;
        mGetOrderStat = getOrderStat;

        mView.setPresenter(this);
    }

    @Override
    public void loadStatistics(final boolean force) {
        mView.setLoadingIndicator(true);

        GetOrderStatistics.RequestValues request = new GetOrderStatistics.RequestValues();
        UseCaseHandler.execute(mGetOrderStat, request, Schedulers.io(), new Subscriber<GetOrderStatistics.ResponseValue>() {
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
            public void onNext(GetOrderStatistics.ResponseValue responseValue) {
                if (mView.isActive()) {
                    mView.showStatistics(responseValue.getmStatistics());
                }
            }
        });
    }

    @Override
    public void start() {
        loadStatistics(mFirstLoad);
    }
}
