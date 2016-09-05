package com.qfg.businesshelper.addeditproduct;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;
import com.qfg.businesshelper.R;
import com.qfg.businesshelper.addeditproduct.domain.usecase.AddProduct;
import com.qfg.businesshelper.addeditproduct.domain.usecase.EditProduct;
import com.qfg.businesshelper.data.source.DataSourceFactory;
import com.qfg.businesshelper.stores.domain.model.Product;
import com.qfg.businesshelper.utils.ActivityUtils;

/**
 * Created by rbtq on 9/2/16.
 */
public class AddEditProductActivity extends AppCompatActivity {
    public final static int ADD_PRODUCT = 0;
    public final static int EDIT_PRODUCT = 1;

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */

    private AddEditProductPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_edit_prod_act);

        AddEditProductFragment addEditProductFragment =
                (AddEditProductFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        Product product = null;
        if (addEditProductFragment == null) {
            // Create the fragment
            addEditProductFragment = new AddEditProductFragment();
            if (getIntent().hasExtra(AddEditProductFragment.ARGUMENT_EDIT_PRODUCT)) {
                String content = getIntent().getStringExtra(
                        AddEditProductFragment.ARGUMENT_EDIT_PRODUCT);
                product = new Gson().fromJson(content, Product.class);
            }

            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), addEditProductFragment, R.id.contentFrame);
        }

        mPresenter = new AddEditProductPresenter(addEditProductFragment,
                product,
                new AddProduct(DataSourceFactory.getProductsDataSource()),
                new EditProduct(DataSourceFactory.getProductsDataSource()));
    }
}
