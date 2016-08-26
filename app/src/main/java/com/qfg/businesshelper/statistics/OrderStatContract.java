package com.qfg.businesshelper.statistics;

import com.qfg.businesshelper.BasePresenter;
import com.qfg.businesshelper.BaseView;
import com.qfg.businesshelper.statistics.domain.model.OrderStatistics;

/**
 * Created by rbtq on 8/26/16.
 */
public interface OrderStatContract {
    interface View extends BaseView<Presenter> {
        void setLoadingIndicator(boolean active);
        void showLoadingError(Throwable e);
        void showStatistics(OrderStatistics statistics);

        boolean isActive();
    }

    interface Presenter extends BasePresenter {
        void loadStatistics(final boolean force);
    }
}
