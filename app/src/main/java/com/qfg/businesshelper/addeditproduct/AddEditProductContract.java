package com.qfg.businesshelper.addeditproduct;

import com.qfg.businesshelper.BasePresenter;
import com.qfg.businesshelper.BaseView;
import com.qfg.businesshelper.stores.domain.model.Product;

/**
 * Created by rbtq on 9/2/16.
 */
public interface AddEditProductContract {
    interface View extends BaseView<Presenter> {
        void setSavingIndicator(boolean active);
        void showSavingSuccess();
        void showSavingError(Throwable e);
        void editProduct(Product product);
        boolean isActive();
    }

    interface Presenter extends BasePresenter {
        void addProduct(Product product);
        void modifyProduct(Product product);
    }
}
