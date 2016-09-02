package com.qfg.businesshelper.addeditproduct;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.qfg.businesshelper.BaseFragment;
import com.qfg.businesshelper.R;
import com.qfg.businesshelper.stores.domain.model.Product;
import com.qfg.businesshelper.utils.Formatter;

/**
 * Created by rbtq on 9/2/16.
 */
public class AddEditProductFragment extends BaseFragment implements AddEditProductContract.View{
    private AddEditProductContract.Presenter mPresenter;
    private ProgressBar mPB;

    private TextView mBarCode;
    private TextView mTitle;
    private TextView mUnitPrice;
    private TextView mAmount;

    private View mContent;

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.add_edit_prod_frag, container, false);

        mPB = (ProgressBar) root.findViewById(R.id.progressBar);
        mContent = root.findViewById(R.id.contentFrame);

        mBarCode = (TextView) root.findViewById(R.id.barcode);
        mTitle = (TextView) root.findViewById(R.id.prod_title);
        mUnitPrice = (TextView) root.findViewById(R.id.unit_price);
        mAmount = (TextView) root.findViewById(R.id.prod_amount);

        FloatingActionButton fab =
                (FloatingActionButton) root.findViewById(R.id.fab_edit_item_done);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptAddProduct();
            }
        });

        return root;
    }

    private void attemptAddProduct() {
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

        Product product = new Product()
                .setBarCode(barCode)
                .setTitle(title)
                .setUnitPrice(bgPrice)
                .setLeft(count);

        mPresenter.addProduct(product);
    }

    @Override
    public void setSavingIndicator(boolean active) {
        if (active) {
            mPB.setVisibility(View.VISIBLE);
            mContent.setVisibility(View.GONE);
        } else {
            mPB.setVisibility(View.GONE);
            mContent.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showSavingSuccess() {
        getActivity().setResult(Activity.RESULT_OK);
        getActivity().finish();
    }

    @Override
    public void showSavingError(Throwable e) {
        showMessage(e.getMessage());
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void setPresenter(AddEditProductContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
