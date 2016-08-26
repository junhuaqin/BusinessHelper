package com.qfg.businesshelper.statistics.domain.usecase;

import android.support.annotation.NonNull;

import com.qfg.businesshelper.UseCase;
import com.qfg.businesshelper.data.source.OrdersDataSource;
import com.qfg.businesshelper.statistics.domain.model.OrderStatistics;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by rbtq on 8/26/16.
 */
public class GetOrderStatistics extends UseCase<GetOrderStatistics.RequestValues, GetOrderStatistics.ResponseValue> {
    private final OrdersDataSource mDataSource;

    public GetOrderStatistics(@NonNull OrdersDataSource dataSource) {
        mDataSource = dataSource;
    }

    @Override
    protected Observable<ResponseValue> executeUseCase(RequestValues requestValues) {
        return mDataSource.getStatistics().map(new Func1<OrderStatistics, ResponseValue>() {
            @Override
            public ResponseValue call(OrderStatistics statistics) {
                return new ResponseValue(statistics);
            }
        });
    }

    public static final class RequestValues implements UseCase.RequestValues {

    }

    public static final class ResponseValue implements UseCase.ResponseValue {
        private final OrderStatistics mStat;

        public ResponseValue(@NonNull OrderStatistics statistics) {
            mStat = statistics;
        }

        public OrderStatistics getmStatistics() {
            return mStat;
        }
    }
}
