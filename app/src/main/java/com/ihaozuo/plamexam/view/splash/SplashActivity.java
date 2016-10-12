package com.ihaozuo.plamexam.view.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.WindowManager;

import com.ihaozuo.plamexam.R;
import com.ihaozuo.plamexam.manager.UserManager;
import com.ihaozuo.plamexam.view.base.BaseActivity;
import com.ihaozuo.plamexam.view.login.LoginActivity;
import com.ihaozuo.plamexam.view.main.MainActivity;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_act);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                turnAction();
            }
        }, 2500);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void turnAction(){
        if (UserManager.getInstance().exist()) {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
        }else {
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
        }
        finish();
    }
}
