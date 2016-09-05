package com.qfg.businesshelper.saveorder.additem;

import com.qfg.businesshelper.BasePresenter;
import com.qfg.businesshelper.BaseView;
import com.qfg.businesshelper.stores.domain.model.Product;

import java.util.List;

/**
 * Created by rbtq on 9/2/16.
 */
public interface AddOrderItemContract {
    interface View extends BaseView<Presenter> {
        void setLoadingIndicator(boolean active);
        void showLoadingError(Throwable e);
        void showProduct(Product product);

        void setProducts(List<Product> products);

        boolean isActive();
    }

    interface Presenter extends BasePresenter {
        void loadProductByQR(String qrCode);
        void loadProduct(String barCode);
        void loadProductByTitle(String title);
    }
}
