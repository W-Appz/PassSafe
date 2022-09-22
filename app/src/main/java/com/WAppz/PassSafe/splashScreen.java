package com.WAppz.PassSafe;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Build;
import android.os.Handler;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallState;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;

public class splashScreen extends AppCompatActivity {

    private AppUpdateManager mAppUpdateManager;
    private static final int RC_APP_UPDATE = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        View s = findViewById(R.id.splashS);
        s.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        mAppUpdateManager = AppUpdateManagerFactory.create(this);
        mAppUpdateManager.getAppUpdateInfo().addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
            @Override
            public void onSuccess(AppUpdateInfo result) {
                if (result.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                && result.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                    try {
                        mAppUpdateManager.startUpdateFlowForResult(result,AppUpdateType.IMMEDIATE, splashScreen.this, RC_APP_UPDATE);
                    } catch (IntentSender.SendIntentException e) {
                        e.printStackTrace();
                    }

                }
            }
        });

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

    private InstallStateUpdatedListener installStateUpdatedListener = new InstallStateUpdatedListener() {
        @Override
        public void onStateUpdate(@NonNull InstallState installState) {
            if (installState.installStatus() == InstallStatus.DOWNLOADED) {
                showCompletedUpdate();
            }
        }
    };

    private void showCompletedUpdate() {
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "New update has been downloaded!", Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("Install", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAppUpdateManager.completeUpdate();
            }
        });
        snackbar.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == RC_APP_UPDATE && resultCode != RESULT_OK) {
            Toast.makeText(this, "Error! Cannot update!", Toast.LENGTH_SHORT).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();

        mAppUpdateManager.getAppUpdateInfo().addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
            @Override
            public void onSuccess(AppUpdateInfo result) {
                if (result.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                    try {
                        mAppUpdateManager.startUpdateFlowForResult(result,AppUpdateType.IMMEDIATE, splashScreen.this, RC_APP_UPDATE);
                    } catch (IntentSender.SendIntentException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }
}