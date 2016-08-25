package com.qfg.businesshelper.orders.domain.usecase;

import android.support.annotation.NonNull;

import com.qfg.businesshelper.UseCase;
import com.qfg.businesshelper.data.source.OrdersDataSource;
import com.qfg.businesshelper.orders.domain.model.Order;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by rbtq on 8/25/16.
 */
public class GetOrders extends UseCase<GetOrders.RequestValues, GetOrders.ResponseValue> {

    private final OrdersDataSource mDataSource;

    public GetOrders(@NonNull OrdersDataSource dataSource) {
        mDataSource = dataSource;
    }

    @Override
    protected Observable<ResponseValue> executeUseCase(RequestValues requestValues) {
        return mDataSource.getOrders(requestValues.getFrom(), requestValues.getTo()).map(new Func1<List<Order>, ResponseValue>() {
            @Override
            public ResponseValue call(List<Order> orders) {
                return new ResponseValue(orders);
            }
        });
    }

    public static final class RequestValues implements UseCase.RequestValues {
        private final long mFrom;
        private final long mTo;

        public RequestValues(long from , long to) {
            mFrom = from;
            mTo = to;
        }

        public long getFrom() {
            return mFrom;
        }

        public long getTo() {
            return mTo;
        }
    }

    public static final class ResponseValue implements UseCase.ResponseValue {
        private final List<Order> mOrders;

        public ResponseValue(@NonNull List<Order> orders) {
            mOrders = orders;
        }

        public List<Order> getOrders() {
            return mOrders;
        }
    }
}
