package com.qfg.businesshelper.saveorder.domain.usecase;

import android.support.annotation.NonNull;

import com.qfg.businesshelper.UseCase;
import com.qfg.businesshelper.data.source.OrdersDataSource;
import com.qfg.businesshelper.orders.domain.model.Order;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by rbtq on 8/26/16.
 */
public class SaveOrder extends UseCase<SaveOrder.RequestValues, SaveOrder.ResponseValue> {
    private final OrdersDataSource mDataSource;

    public SaveOrder(@NonNull OrdersDataSource dataSource) {
        mDataSource = dataSource;
    }

    @Override
    protected Observable<ResponseValue> executeUseCase(RequestValues requestValues) {
        return mDataSource.saveOrder(requestValues.getOrder()).map(new Func1<Order, ResponseValue>() {
            @Override
            public ResponseValue call(Order order) {
                return new ResponseValue(order);
            }
        });
    }

    public static final class RequestValues implements UseCase.RequestValues {
        private final Order mOrder;

        public RequestValues(@NonNull Order order) {
            mOrder = order;
        }

        public Order getOrder() {
            return mOrder;
        }
    }

    public static final class ResponseValue implements UseCase.ResponseValue {
        private final Order mOrder;

        public ResponseValue(@NonNull Order order) {
            mOrder = order;
        }

        public Order getOrder() {
            return mOrder;
        }
    }
}
