package com.ihaozuo.plamexam.common.jpush;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class WakeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_wake);
    }

    @Override
    protected void onResume() {
        super.onResume();
        finish();
    }
}
