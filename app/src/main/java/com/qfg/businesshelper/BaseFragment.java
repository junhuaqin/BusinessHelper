package com.qfg.businesshelper;

import android.support.v4.app.Fragment;
import android.support.design.widget.Snackbar;

/**
 * Created by rbtq on 8/25/16.
 */
public class BaseFragment extends Fragment {
    protected void showMessage(String message) {
        Snackbar.make(getView(), message, Snackbar.LENGTH_LONG).show();
    }
}
