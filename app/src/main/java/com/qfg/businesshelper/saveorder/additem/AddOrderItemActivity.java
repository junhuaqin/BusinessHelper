package com.qfg.businesshelper.saveorder.additem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.qfg.businesshelper.R;
import com.qfg.businesshelper.orders.domain.model.Order;
import com.qfg.businesshelper.utils.Formatter;
import com.qfg.businesshelper.zxing.android.CaptureActivity;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_sale_item_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_scan:
                Intent intent = new Intent(this, CaptureActivity.class);
                startActivityForResult(intent, 0);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            // 扫描二维码/条码回传
            if (data != null) {
                String content = data.getStringExtra(CaptureActivity.CODED_CONTENT_KEY);
                int index = content.lastIndexOf('/');
                if (index <= 0) {
                    Toast.makeText(this, "无效的二维码", Toast.LENGTH_SHORT).show();
                } else {

                }
            }
        }
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
                .setBarCode(barCode)
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
