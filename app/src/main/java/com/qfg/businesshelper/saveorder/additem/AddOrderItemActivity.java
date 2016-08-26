package com.qfg.businesshelper.saveorder.additem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.qfg.businesshelper.R;
import com.qfg.businesshelper.orders.domain.model.Order;
import com.qfg.businesshelper.utils.Formatter;

public class AddOrderItemActivity extends AppCompatActivity {

    public final static String SALE_ITEM = "sale_item";

    private TextView mBarCode;
    private TextView mTitle;
    private TextView mUnitPrice;
    private TextView mAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_order_item_act);

        mBarCode = (TextView) findViewById(R.id.barcode);
        mTitle = (TextView) findViewById(R.id.prod_title);
        mUnitPrice = (TextView) findViewById(R.id.unit_price);
        mAmount = (TextView) findViewById(R.id.prod_amount);

        FloatingActionButton fab =
                (FloatingActionButton) findViewById(R.id.fab_edit_item_done);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptDone();
            }
        });
    }

    private void attemptDone() {
        mBarCode.setError(null);
        mTitle.setError(null);
        mUnitPrice.setError(null);
        mAmount.setError(null);

        String barCode = mBarCode.getText().toString();
        String title = mTitle.getText().toString();
        String unitPrice = mUnitPrice.getText().toString();
        String amount = mAmount.getText().toString();

        Integer bgPrice = 0;
        Integer count = 0;

        if (TextUtils.isEmpty(barCode)) {
            mBarCode.setError(getString(R.string.invalid_barcode));
            mBarCode.requestFocus();
            return;
        } else if (TextUtils.isEmpty(title)) {
            mTitle.setError(getString(R.string.invalid_title));
            mTitle.requestFocus();
            return;
        } else if (TextUtils.isEmpty(unitPrice) || (bgPrice = Formatter.toBG(Float.valueOf(unitPrice))) <= 0) {
            mUnitPrice.setError(getString(R.string.invalid_unit_price));
            mUnitPrice.requestFocus();
            return;
        } else if (TextUtils.isEmpty(amount) || (count = Integer.valueOf(amount)) <= 0) {
            mAmount.setError(getString(R.string.invalid_amount));
            mAmount.requestFocus();
            return;
        }

        Order.OrderItem item = new Order.OrderItem()
                .setBarCode(Integer.valueOf(barCode))
                .setTitle(title)
                .setUnitPrice(bgPrice)
                .setCount(count);

        done(item);
    }

    private void done(Order.OrderItem item) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString(SALE_ITEM, new Gson().toJson(item));
        intent.putExtra(SALE_ITEM, bundle);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}
