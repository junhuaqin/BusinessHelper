package com.qfg.businesshelper.saveorder.additem;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.qfg.businesshelper.R;
import com.qfg.businesshelper.data.source.DataSourceFactory;
import com.qfg.businesshelper.saveorder.additem.usecase.LoadProductByQR;
import com.qfg.businesshelper.utils.ActivityUtils;

public class AddOrderItemActivity extends AppCompatActivity {

    private AddOrderItemPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_order_item_act);

        // Set up the toolbar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);

        AddOrderItemFragment addOrderItemFragment =
                (AddOrderItemFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (addOrderItemFragment == null) {
            // Create the fragment
            addOrderItemFragment = new AddOrderItemFragment();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), addOrderItemFragment, R.id.contentFrame);
        }

        mPresenter = new AddOrderItemPresenter(addOrderItemFragment, new LoadProductByQR(DataSourceFactory.getProductsDataSource()));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
