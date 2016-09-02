package com.qfg.businesshelper.addeditproduct;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.qfg.businesshelper.R;
import com.qfg.businesshelper.addeditproduct.domain.usecase.AddProduct;
import com.qfg.businesshelper.data.source.DataSourceFactory;
import com.qfg.businesshelper.utils.ActivityUtils;

/**
 * Created by rbtq on 9/2/16.
 */
public class AddEditProductActivity extends AppCompatActivity {
    public final static int ADD_PRODUCT = 0;

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
        if (addEditProductFragment == null) {
            // Create the fragment
            addEditProductFragment = new AddEditProductFragment();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), addEditProductFragment, R.id.contentFrame);
        }

        mPresenter = new AddEditProductPresenter(addEditProductFragment, new AddProduct(DataSourceFactory.getProductsDataSource()));
    }
}
