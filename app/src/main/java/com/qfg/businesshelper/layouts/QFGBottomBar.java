package com.qfg.businesshelper.layouts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.content.ContextCompat;

import com.qfg.businesshelper.R;
import com.qfg.businesshelper.integration.ReportSaleActivity;
import com.qfg.businesshelper.mine.MineActivity;
import com.qfg.businesshelper.stores.StoresActivity;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;

/**
 * Created by rbtq on 8/23/16.
 */
public class QFGBottomBar {
    private BottomBar bottomBar;
    private int selected = 0;

    public QFGBottomBar(final int selected, final Activity activity, Bundle savedInstanceState) {
        this.selected = selected;

        bottomBar = BottomBar.attach(activity, savedInstanceState);
        bottomBar.setItems(R.menu.bottom_navagation);
        bottomBar.setActiveTabColor(ContextCompat.getColor(activity, R.color.colorBlue));
        bottomBar.selectTabAtPosition(this.selected, true);
        bottomBar.setOnMenuTabClickListener(new OnMenuTabClickListener() {
            @Override
            public void onMenuTabSelected(@IdRes int itemId) {
                Intent intent = null;
                switch (itemId) {
                    case R.id.sale_item:
                        if (!(activity instanceof ReportSaleActivity)) {
                            intent = new Intent(activity, ReportSaleActivity.class);
                            activity.startActivity(intent);
                        }
                        break;
                    case R.id.store_item:
                        if (!(activity instanceof StoresActivity)) {
                            intent = new Intent(activity, StoresActivity.class);
                            activity.startActivity(intent);
                        }
                        break;
                    case R.id.my_item:
                        if (!(activity instanceof MineActivity)) {
                            intent = new Intent(activity, MineActivity.class);
                            activity.startActivity(intent);
                        }
                        break;
                }
            }

            @Override
            public void onMenuTabReSelected(@IdRes int menuItemId) {

            }
        });
    }

    public void selectTab() {
        bottomBar.selectTabAtPosition(selected, true);
    }

    public void onSaveInstanceState(Bundle outState) {
        bottomBar.onSaveInstanceState(outState);
    }
}
