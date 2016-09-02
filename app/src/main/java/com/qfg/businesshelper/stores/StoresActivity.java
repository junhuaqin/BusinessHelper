package com.qfg.businesshelper.stores;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.qfg.businesshelper.R;
import com.qfg.businesshelper.data.source.DataSourceFactory;
import com.qfg.businesshelper.layouts.QFGBottomBar;
import com.qfg.businesshelper.stores.domain.usecase.GetProducts;
import com.qfg.businesshelper.utils.ActivityUtils;

/**
 * Created by rbtq on 9/1/16.
 */
public class StoresActivity extends AppCompatActivity {

    private StoresPresenter mPresenter;
    private QFGBottomBar mBottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orders_act);

        mBottomBar = new QFGBottomBar(1, this, savedInstanceState);

        StoresFragment storesFragment =
                (StoresFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (storesFragment == null) {
            // Create the fragment
            storesFragment = new StoresFragment();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), storesFragment, R.id.contentFrame);
        }

        mPresenter = new StoresPresenter(storesFragment, new GetProducts(DataSourceFactory.getProductsDataSource()));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Necessary to restore the BottomBar's state, otherwise we would
        // lose the current tab on orientation change.
        mBottomBar.onSaveInstanceState(outState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mBottomBar.selectTab();
    }
}