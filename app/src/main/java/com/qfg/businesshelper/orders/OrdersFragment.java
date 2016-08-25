package com.qfg.businesshelper.orders;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.qfg.businesshelper.BaseFragment;
import com.qfg.businesshelper.R;
import com.qfg.businesshelper.orders.domain.model.Order;
import com.qfg.businesshelper.utils.Formatter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rbtq on 8/25/16.
 */
public class OrdersFragment extends BaseFragment implements OrdersContract.View {
    private OrdersContract.Presenter mPresenter;

    //no order view
    private View mNoOrderView;

    //orders view
    private View mOrdersView;
    private OrdersListAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new OrdersListAdapter(new ArrayList<Order>());
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.orders_frag, container, false);

        //no order view
        mNoOrderView = root.findViewById(R.id.noOrders);

        //orders view
        mOrdersView = root.findViewById(R.id.ordersLL);
        ExpandableListView listView = (ExpandableListView) root.findViewById(R.id.orders_list);
        listView.setAdapter(mAdapter);

        return root;
    }

    @Override
    public void setLoadingIndicator(boolean active) {

    }

    @Override
    public void showOrders(List<Order> orders) {
        mAdapter.replaceData(orders);

        mOrdersView.setVisibility(View.VISIBLE);
        mNoOrderView.setVisibility(View.GONE);
    }

    @Override
    public void showLoadingError(Throwable e) {
        showMessage(e.getMessage());
    }

    @Override
    public void showNoOrders() {
        mNoOrderView.setVisibility(View.VISIBLE);
        mOrdersView.setVisibility(View.GONE);
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void setPresenter(OrdersContract.Presenter presenter) {
        mPresenter = presenter;
    }

    public class OrdersListAdapter extends BaseExpandableListAdapter {
        private List<Order> mOrders;

        public OrdersListAdapter(List<Order> orders) {
            setList(orders);
        }

        public void replaceData(List<Order> orders) {
            setList(orders);
            notifyDataSetChanged();
        }

        private void setList(List<Order> orders) {
            mOrders = orders;
        }

        @Override
        public int getGroupCount() {
            return mOrders.size();
        }

        @Override
        public int getChildrenCount(int i) {
            return mOrders.get(i).getItems().size();
        }

        @Override
        public Object getGroup(int i) {
            return mOrders.get(i);
        }

        @Override
        public Object getChild(int i, int i1) {
            return mOrders.get(i).getItems().get(i1);
        }

        @Override
        public long getGroupId(int i) {
            return i;
        }

        @Override
        public long getChildId(int i, int i1) {
            return i1;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
            Order order = (Order)getGroup(i);
            if (null == view) {
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.order_list_group, null);
            }

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            TextView lblListHeader = (TextView) view.findViewById(R.id.lblListHeader);
            lblListHeader.setText(String.format("%s %s %s", format.format(order.getCreatedAt()),
                    order.getSale(),
                    Formatter.toCurrency(Formatter.toFG(order.getTotalPrice()))));
            return view;
        }

        @Override
        public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
            Order.OrderItem saleItem = (Order.OrderItem)getChild(i, i1);
            if (null == view) {
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.order_list_item, null);
            }
            TextView txtListChild = (TextView) view.findViewById(R.id.lblListItem);

            txtListChild.setText(String.format("%s %s*%d", saleItem.getTitle(),
                    Formatter.toCurrency(Formatter.toFG(saleItem.getUnitPrice())),
                    saleItem.getCount()));

            return view;
        }

        @Override
        public boolean isChildSelectable(int i, int i1) {
            return false;
        }
    }

}
