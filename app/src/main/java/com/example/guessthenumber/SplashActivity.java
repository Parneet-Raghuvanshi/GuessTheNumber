package com.example.guessthenumber;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature( Window.FEATURE_NO_TITLE);
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Intent homeintent = new Intent(SplashActivity.this, Dashboard.class);
                startActivity(homeintent);
                finish();
            }
        };
        Handler handler = new Handler();
        handler.postDelayed(runnable, 3000);

    }
}