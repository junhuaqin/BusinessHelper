package com.qfg.businesshelper.statistics;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.qfg.businesshelper.BaseFragment;
import com.qfg.businesshelper.R;
import com.qfg.businesshelper.statistics.domain.model.OrderStatistics;
import com.qfg.businesshelper.utils.Formatter;

/**
 * Created by rbtq on 8/26/16.
 */
public class OrderStatFragment extends BaseFragment implements OrderStatContract.View {
    private OrderStatContract.Presenter mPresenter;

    private ProgressBar mPB;
    private View mContentView;
    private TextView mCurMonth;
    private TextView mCurDay;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.order_stat_frag, container, false);

        mPB = (ProgressBar) root.findViewById(R.id.staticsProgressBar);
        mContentView = root.findViewById(R.id.contentFrame);

        mCurDay = (TextView) root.findViewById(R.id.curDay);
        mCurMonth = (TextView) root.findViewById(R.id.curMon);

        return root;
    }

    @Override
    public void setLoadingIndicator(boolean active) {
        if (active) {
            mPB.setVisibility(View.VISIBLE);
            mContentView.setVisibility(View.GONE);
        } else {
            mPB.setVisibility(View.GONE);
            mContentView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showLoadingError(Throwable e) {
        showMessage(e.getMessage());
        mCurDay.setText(getString(R.string.curDayTotalSale, Formatter.bgToShow(0)));
        mCurMonth.setText(Formatter.bgToShow(0));
    }

    @Override
    public void showStatistics(OrderStatistics statistics) {
        mCurDay.setText(getString(R.string.curDayTotalSale, Formatter.bgToShow(statistics.getCurDay())));
        mCurMonth.setText(Formatter.bgToShow(statistics.getCurMonth()));
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void setPresenter(OrderStatContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
