package com.qfg.businesshelper;

import com.qfg.businesshelper.stores.domain.model.Product;

/**
 * Created by rbtq on 9/5/16.
 */
public interface BaseProductResponse extends UseCase.ResponseValue{
    Product getProduct();
}
