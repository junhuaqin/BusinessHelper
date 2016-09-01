package com.qfg.businesshelper.orders;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.qfg.businesshelper.R;
import com.qfg.businesshelper.data.source.DataSourceFactory;
import com.qfg.businesshelper.orders.domain.usecase.GetOrders;
import com.qfg.businesshelper.utils.ActivityUtils;

public class OrdersActivity extends AppCompatActivity {

    private OrdersPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orders_act);

        OrdersFragment ordersFragment =
                (OrdersFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (ordersFragment == null) {
            // Create the fragment
            ordersFragment = new OrdersFragment();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), ordersFragment, R.id.contentFrame);
        }

        mPresenter = new OrdersPresenter(ordersFragment, new GetOrders(DataSourceFactory.getOrdersDataSource()));
    }
}
