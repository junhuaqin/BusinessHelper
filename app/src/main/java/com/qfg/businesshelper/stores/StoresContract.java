package com.qfg.businesshelper.stores;

import com.qfg.businesshelper.BasePresenter;
import com.qfg.businesshelper.BaseView;
import com.qfg.businesshelper.stores.domain.model.Product;

import java.util.List;

/**
 * Created by rbtq on 9/1/16.
 */
public class StoresContract {
    interface View extends BaseView<Presenter> {
        void setLoadingIndicator(boolean active);
        void showLoadingError(Throwable e);
        void showStores(List<Product> products);
        void showNoProduct();

        boolean isActive();
    }

    interface Presenter extends BasePresenter {
        void loadStores(final boolean force);
    }
}
