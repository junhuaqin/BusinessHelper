package com.qfg.businesshelper.saveorder;

import com.qfg.businesshelper.BasePresenter;
import com.qfg.businesshelper.BaseView;
import com.qfg.businesshelper.orders.domain.model.Order;

/**
 * Created by rbtq on 8/26/16.
 */
public interface SaveOrderContract {
    interface View extends BaseView<Presenter> {
        void setSavingIndicator(boolean active);
        void showSavingError(Throwable e);
        void showSavingSuccess();

        void showAddItem();
        void showOrder(Order order);

        boolean isActive();
    }

    interface Presenter extends BasePresenter {
        void saveOrder();
        void addNewItem();
        void addItem(Order.OrderItem item);
        void clearItems();
        void deleteItem(Order.OrderItem item);

        void setOnOrderSavedListener(OnOrderSavedListener listener);

        interface OnOrderSavedListener {
            void onSaved(Order order);
        }
    }
}
