package com.qfg.businesshelper.stores;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.qfg.businesshelper.BaseFragment;
import com.qfg.businesshelper.R;
import com.qfg.businesshelper.addeditproduct.AddEditProductActivity;
import com.qfg.businesshelper.layouts.ScrollChildSwipeRefreshLayout;
import com.qfg.businesshelper.stores.domain.model.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rbtq on 9/1/16.
 */
public class StoresFragment extends BaseFragment implements StoresContract.View {
    private StoresContract.Presenter mPresenter;

    //no product view
    private View mNoProductView;

    // products view
    private View mProdctsView;
    private StoresAdapter mListAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mListAdapter = new StoresAdapter(new ArrayList<Product>());
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.stores_frag, container, false);

        //no store view
        mNoProductView = root.findViewById(R.id.noProducts);

        //products view
        ListView listView = (ListView) root.findViewById(R.id.stores_list);
        listView.setAdapter(mListAdapter);
        mProdctsView = root.findViewById(R.id.storesLL);

        // Set up progress indicator
        final ScrollChildSwipeRefreshLayout swipeRefreshLayout =
                (ScrollChildSwipeRefreshLayout) root.findViewById(R.id.refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(getActivity(), R.color.colorPrimary),
                ContextCompat.getColor(getActivity(), R.color.colorAccent),
                ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark)
        );
        // Set the scrolling view in the custom SwipeRefreshLayout.
        swipeRefreshLayout.setScrollUpChild(listView);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.loadStores(false);
            }
        });

        FloatingActionButton fab =
                (FloatingActionButton) root.findViewById(R.id.fab_add_item);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.addNewProduct();
            }
        });

        return root;
    }

    @Override
    public void setLoadingIndicator(final boolean active) {
        if (getView() == null) {
            return;
        }
        final SwipeRefreshLayout srl =
                (SwipeRefreshLayout) getView().findViewById(R.id.refresh_layout);

        // Make sure setRefreshing() is called after the layout is done with everything else.
        srl.post(new Runnable() {
            @Override
            public void run() {
                srl.setRefreshing(active);
            }
        });
    }

    @Override
    public void showLoadingError(Throwable e) {
        showMessage(e.getMessage());
    }

    @Override
    public void showStores(List<Product> products) {
        mNoProductView.setVisibility(View.GONE);
        mProdctsView.setVisibility(View.VISIBLE);

        mListAdapter.replaceData(products);
    }

    @Override
    public void showNoProduct() {
        mNoProductView.setVisibility(View.VISIBLE);
        mProdctsView.setVisibility(View.GONE);
    }

    @Override
    public void showAddNewTask() {
        Intent intent = new Intent(getContext(), AddEditProductActivity.class);
        startActivityForResult(intent, AddEditProductActivity.ADD_PRODUCT);
    }

    @Override
    public void showSuccessfullySavedMessage() {
        showMessage(getString(R.string.submitSuccess));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mPresenter.onResult(requestCode, resultCode);
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void setPresenter(StoresContract.Presenter presenter) {
        mPresenter = presenter;
    }

    private static class StoresAdapter extends BaseAdapter {

        private List<Product> mProducts;

        public StoresAdapter(List<Product> products) {
            setList(products);
        }

        public void replaceData(List<Product> products) {
            setList(products);
            notifyDataSetChanged();
        }

        private void setList(List<Product> products) {
            mProducts = products;
        }

        @Override
        public int getCount() {
            return mProducts.size();
        }

        @Override
        public Product getItem(int i) {
            return mProducts.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View rowView = view;
            if (rowView == null) {
                LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                rowView = inflater.inflate(android.R.layout.simple_list_item_1, viewGroup, false);
            }

            Product product = getItem(i);
            TextView textView = (TextView) rowView.findViewById(android.R.id.text1);
            textView.setText(String.format("%s %s %d", product.getBarCode(), product.getTitle(), product.getLeft()));

            return rowView;
        }
    }

    public interface ProductItemListener {

    }
}
