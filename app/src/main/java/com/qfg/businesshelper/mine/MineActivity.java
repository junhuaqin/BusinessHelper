package com.qfg.businesshelper.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.qfg.businesshelper.R;
import com.qfg.businesshelper.data.source.local.UserPref;
import com.qfg.businesshelper.layouts.QFGBottomBar;
import com.qfg.businesshelper.login.LoginActivity;

public class MineActivity extends AppCompatActivity {

    private QFGBottomBar mBottomBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine);
        mBottomBar = new QFGBottomBar(2, this, savedInstanceState);
    }

    public void onLogout(View view) {
        UserPref.clearLoggedInStatus(getApplicationContext());
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mBottomBar.selectTab();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Necessary to restore the BottomBar's state, otherwise we would
        // lose the current tab on orientation change.
        mBottomBar.onSaveInstanceState(outState);
    }
}
