package com.example.cancelling;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private Button progressButton;

    private int i = 0;
    private Handler handler;
    private Runnable runnable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progress_bar);
        progressButton = findViewById(R.id.progress_button);
/*
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                if (i < 100) {
                    i += 10;
                    progressBar.setProgress(i);
                    progressButton.setText( " %"+i  );
                }
                handler.postDelayed(this, 1000);
                if(i==40){
                  // changeAppIcon(R.mipmap.ic_launcher_suspicious_foreground);
                }
            }
        };*/

        progressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.post(runnable); //
                progressButton.setEnabled(false);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }

    private void changeAppIcon(int iconResourceId) {
     //  ImageView iconImageView = findViewById(R.id.suspicious);
       //iconImageView.setImageResource(R.mipmap.ic_launcher_redsad_foreground);
    }



    private void setupActivityListener() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
                @Override
                public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                    activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
                }

                @Override
                public void onActivityStarted(Activity activity) {
                }

                @Override
                public void onActivityResumed(Activity activity) {

                }

                @Override
                public void onActivityPaused(Activity activity) {
                    if (isScreenshotAttempted(activity)) {
                        Toast.makeText(activity, "Ekran görüntüsü alamazsınız.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onActivityStopped(Activity activity) {
                }

                @Override
                public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
                }

                @Override
                public void onActivityDestroyed(Activity activity) {
                }
            });
        }
    }

    private boolean isScreenshotAttempted(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            return activity.isInPictureInPictureMode();
        }
        return false;
    }
}
