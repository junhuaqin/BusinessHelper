package com.qfg.businesshelper.saveorder.additem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.qfg.businesshelper.BaseFragment;
import com.qfg.businesshelper.R;
import com.qfg.businesshelper.orders.domain.model.Order;
import com.qfg.businesshelper.stores.domain.model.Product;
import com.qfg.businesshelper.utils.Formatter;
import com.qfg.businesshelper.zxing.android.CaptureActivity;

import java.util.List;

/**
 * Created by rbtq on 9/3/16.
 */
public class AddOrderItemFragment extends BaseFragment implements AddOrderItemContract.View {
    public final static String SALE_ITEM = "sale_item";

    private AddOrderItemContract.Presenter mPresenter;
    private AutoCompleteTextView mBarCode;
    private AutoCompleteTextView mTitle;
    private TextView mUnitPrice;
    private TextView mAmount;
    private ArrayAdapter<String> mBarCodeAdapter;
    private ArrayAdapter<String> mTitleAdapter;

    private ProgressBar mPB;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBarCodeAdapter = new ArrayAdapter<>(getContext(), android.R.layout.select_dialog_item);
        mTitleAdapter = new ArrayAdapter<>(getContext(), android.R.layout.select_dialog_item);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.add_order_item_frag, container, false);
        setHasOptionsMenu(true);

        mPB = (ProgressBar) root.findViewById(R.id.progressBar);

        mBarCode = (AutoCompleteTextView) root.findViewById(R.id.barcode);
        mTitle = (AutoCompleteTextView) root.findViewById(R.id.prod_title);
        mUnitPrice = (TextView) root.findViewById(R.id.unit_price);
        mAmount = (TextView) root.findViewById(R.id.prod_amount);

        mBarCode.setAdapter(mBarCodeAdapter);
        mBarCode.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String barCode = (String) adapterView.getItemAtPosition(i);
                mPresenter.loadProduct(barCode);
            }
        });

        mTitle.setAdapter(mTitleAdapter);
        mTitle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String title = (String) adapterView.getItemAtPosition(i);
                mPresenter.loadProductByTitle(title);
            }
        });

        FloatingActionButton fab =
                (FloatingActionButton) root.findViewById(R.id.fab_edit_item_done);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptDone();
            }
        });
        return root;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.add_sale_item_actions, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_scan:
                Intent intent = new Intent(getContext(), CaptureActivity.class);
                startActivityForResult(intent, 0);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            // 扫描二维码/条码回传
            if (data != null) {
                String content = data.getStringExtra(CaptureActivity.CODED_CONTENT_KEY);
                int index = content.lastIndexOf('/');
                if (index <= 0) {
                    showMessage("无效的二维码");
                } else {
                    mPresenter.loadProductByQR(content.substring(index+1));
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
        getActivity().setResult(Activity.RESULT_OK, intent);
        getActivity().finish();
    }

    @Override
    public void setLoadingIndicator(boolean active) {
        mPB.setVisibility(active?View.VISIBLE:View.GONE);
    }

    @Override
    public void showLoadingError(Throwable e) {
        showMessage(e.getMessage());
    }

    @Override
    public void showProduct(Product product) {
        mBarCode.setText(product.getBarCode());
        mBarCode.dismissDropDown();
        mTitle.setText(product.getTitle());
        mTitle.dismissDropDown();
        mUnitPrice.setText(Formatter.fgToShow(Formatter.toFG(product.getUnitPrice())));
    }

    @Override
    public void setProducts(List<Product> products) {
        mBarCodeAdapter.clear();
        mTitleAdapter.clear();
        if (null == products) {
            return;
        }

        for (Product product : products) {
            mBarCodeAdapter.add(product.getBarCode());
            mTitleAdapter.add(product.getTitle());
        }
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void setPresenter(AddOrderItemContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
