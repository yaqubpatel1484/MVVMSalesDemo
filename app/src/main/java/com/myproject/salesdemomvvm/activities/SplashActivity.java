package com.myproject.salesdemomvvm.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.myproject.salesdemomvvm.R;
import com.myproject.salesdemomvvm.login.LoginActivity;
import com.myproject.salesdemomvvm.utils.SessionManager;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);

        int DELAY_TIME = 2000;
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                SessionManager sessionManager = new SessionManager();
               if(sessionManager.isExecutiveLogin()) {
                   startActivity(new Intent(SplashActivity.this, BaseActivity.class));
               }else {
                   startActivity(new Intent(SplashActivity.this, LoginActivity.class));
               }
                finish();
            }
        },DELAY_TIME);
    }
}
