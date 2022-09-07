package com.WAppz.PassSafe;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class splashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        View s = findViewById(R.id.splashS);
        s.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        Handler handler = new Handler();
        final Runnable r = new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void run() {
                start();
            }
        };
        handler.postDelayed(r,2000);
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void start(){
        Intent i = new Intent(splashScreen.this,mainpage.class);
        startActivity(i);
        finish();
    }
}