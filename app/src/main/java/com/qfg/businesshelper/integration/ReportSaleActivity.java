package com.qfg.businesshelper.integration;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.qfg.businesshelper.R;
import com.qfg.businesshelper.data.source.DataSourceFactory;
import com.qfg.businesshelper.layouts.QFGBottomBar;
import com.qfg.businesshelper.login.LoginActivity;
import com.qfg.businesshelper.orders.domain.model.Order;
import com.qfg.businesshelper.saveorder.SaveOrderContract;
import com.qfg.businesshelper.saveorder.SaveOrderFragment;
import com.qfg.businesshelper.saveorder.SaveOrderPresenter;
import com.qfg.businesshelper.saveorder.domain.usecase.SaveOrder;
import com.qfg.businesshelper.statistics.OrderStatFragment;
import com.qfg.businesshelper.statistics.OrderStatPresenter;
import com.qfg.businesshelper.statistics.domain.usecase.GetOrderStatistics;
import com.qfg.businesshelper.utils.ActivityUtils;

/**
 * Created by rbtq on 8/26/16.
 */
public class ReportSaleActivity extends AppCompatActivity implements SaveOrderContract.Presenter.OnOrderSavedListener{
    private SaveOrderPresenter mSaveOrderPresenter;
    private OrderStatPresenter mStatOrderPresenter;

    private QFGBottomBar mBottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_sale_act);

        mBottomBar = new QFGBottomBar(0, this, savedInstanceState);

        OrderStatFragment statFragment =
                (OrderStatFragment) getSupportFragmentManager().findFragmentById(R.id.orderStatFrame);
        if (statFragment == null) {
            statFragment = new OrderStatFragment();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), statFragment, R.id.orderStatFrame);
        }

        mStatOrderPresenter = new OrderStatPresenter(statFragment, new GetOrderStatistics(DataSourceFactory.getOrdersDataSource()));

        SaveOrderFragment orderFragment =
                (SaveOrderFragment) getSupportFragmentManager().findFragmentById(R.id.orderFrame);
        if (orderFragment == null) {
            // Create the fragment
            orderFragment = new SaveOrderFragment();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), orderFragment, R.id.orderFrame);
        }

        mSaveOrderPresenter = new SaveOrderPresenter(orderFragment, new SaveOrder(DataSourceFactory.getOrdersDataSource()));
        mSaveOrderPresenter.setOnOrderSavedListener(this);
    }

    @Override
    public void onSaved(Order order) {
        mStatOrderPresenter.loadStatistics(true);
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
        if (!DataSourceFactory.getUsersDataSource().isLoggedIn(getApplicationContext())) {
            gotoLogin();
        } else {
            mBottomBar.selectTab();
        }
    }

    private void gotoLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void onBackPressed() {
        finishAffinity();
    }
}
