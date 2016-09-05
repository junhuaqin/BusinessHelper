package com.qfg.businesshelper.saveorder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.qfg.businesshelper.BaseFragment;
import com.qfg.businesshelper.R;
import com.qfg.businesshelper.orders.domain.model.Order;
import com.qfg.businesshelper.saveorder.additem.AddOrderItemActivity;
import com.qfg.businesshelper.saveorder.additem.AddOrderItemFragment;
import com.qfg.businesshelper.utils.Formatter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rbtq on 8/26/16.
 */
public class SaveOrderFragment extends BaseFragment implements SaveOrderContract.View {
    private SaveOrderContract.Presenter mPresenter;

    private ProgressBar mPB;
    private RecyclerView mOrderItems;
    private OrderItemAdapter mAdapter;

    private TextView mTotalPrice;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new OrderItemAdapter();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.save_order_frag, container, false);

        mPB = (ProgressBar) root.findViewById(R.id.progressBar);
        mTotalPrice = (TextView) root.findViewById(R.id.total_price);

        mOrderItems = (RecyclerView) root.findViewById(R.id.order_items);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mOrderItems.setHasFixedSize(true);

        // use a linear layout manager
        mOrderItems.setLayoutManager(new LinearLayoutManager(getActivity()));
        mOrderItems.setAdapter(mAdapter);

        FloatingActionButton fab =
                (FloatingActionButton) root.findViewById(R.id.fab_add_item);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.addNewItem();
            }
        });

        root.findViewById(R.id.clear_items).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.clearItems();
            }
        });

        root.findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.saveOrder();
            }
        });

        return root;
    }

    @Override
    public void setSavingIndicator(boolean active) {
        if (active) {
            mPB.setVisibility(View.VISIBLE);
        } else {
            mPB.setVisibility(View.GONE);
        }
    }

    @Override
    public void showSavingError(Throwable e) {
        showMessage(e.getMessage());
    }

    @Override
    public void showSavingSuccess() {
        showMessage(getString(R.string.submitSuccess));
    }

    @Override
    public void showAddItem() {
        Intent intent = new Intent(getContext(), AddOrderItemActivity.class);
        startActivityForResult(intent, 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Activity.RESULT_OK == resultCode) {
            Order.OrderItem item = new Gson().fromJson(
                    data.getBundleExtra(AddOrderItemFragment.SALE_ITEM).getString(AddOrderItemFragment.SALE_ITEM),
                    Order.OrderItem.class);

            mPresenter.addItem(item);
        }
    }

    @Override
    public void showOrder(Order order) {
        mTotalPrice.setText(getString(R.string.totalPrice, Formatter.bgToShow(order.getTotalPrice())));
        mAdapter.replace(order.getItems());
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void setPresenter(SaveOrderContract.Presenter presenter) {
        mPresenter = presenter;
    }

    private class OrderItemAdapter extends RecyclerView.Adapter<OrderItemAdapter.ViewHolder> {
        private List<Order.OrderItem> mDataset = new ArrayList<>();

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        public class ViewHolder extends RecyclerView.ViewHolder {
            // each data item is just a string in this case
            public TextView mTextView;
            public ViewHolder(View v) {
                super(v);
                mTextView = (TextView) v.findViewById(android.R.id.text1);
            }
        }

        public void replace(List<Order.OrderItem> items) {
            mDataset = items;
            notifyDataSetChanged();
        }

        // Create new views (invoked by the layout manager)
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
            // create a new view
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(android.R.layout.simple_list_item_1, parent, false);
            // set the view's size, margins, paddings and layout parameters
            return new ViewHolder(v);
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            Order.OrderItem item = mDataset.get(position);
            holder.mTextView.setText(String.format("%s %s %s*%d",
                    item.getBarCode(),
                    item.getTitle(),
                    Formatter.bgToShow(item.getUnitPrice()),
                    item.getCount()));
        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return mDataset.size();
        }
    }

}
