package com.qfg.businesshelper.orders;

import com.qfg.businesshelper.BasePresenter;
import com.qfg.businesshelper.BaseView;
import com.qfg.businesshelper.orders.domain.model.Order;

import java.util.List;

/**
 * Created by rbtq on 8/25/16.
 */
public interface OrdersContract {
    interface View extends BaseView<Presenter> {
        void setLoadingIndicator(boolean active);
        void showOrders(List<Order> orders);
        void showLoadingError(Throwable e);

        void showNoOrders();
        boolean isActive();
    }

    interface Presenter extends BasePresenter {
        void loadOrders();
    }
}
