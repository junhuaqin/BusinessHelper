package com.qfg.businesshelper.integration;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.qfg.businesshelper.R;
import com.qfg.businesshelper.data.source.DataSourceFactory;
import com.qfg.businesshelper.saveorder.SaveOrderFragment;
import com.qfg.businesshelper.saveorder.SaveOrderPresenter;
import com.qfg.businesshelper.saveorder.domain.usecase.SaveOrder;
import com.qfg.businesshelper.utils.ActivityUtils;

/**
 * Created by rbtq on 8/26/16.
 */
public class ReportSaleActivity extends AppCompatActivity {
    private SaveOrderPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_sale_act);

        SaveOrderFragment orderFragment =
                (SaveOrderFragment) getSupportFragmentManager().findFragmentById(R.id.orderFrame);
        if (orderFragment == null) {
            // Create the fragment
            orderFragment = new SaveOrderFragment();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), orderFragment, R.id.orderFrame);
        }

        mPresenter = new SaveOrderPresenter(orderFragment, new SaveOrder(DataSourceFactory.getOrdersDataSource()));
    }
}
