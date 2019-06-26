package com.example.oasiscab_driver;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {

    int progress = 0;
    ProgressBar loaderProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        loaderProgressBar = findViewById(R.id.loader_progress_bar);

        setProgressValue(progress);


        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(10000);
                    Intent intent = new Intent(SplashScreen.this, WelcomeScreen.class);
                    startActivity(intent);
                    finish();

                } catch (InterruptedException e) {

                }

            }
        };

        thread.start();
    }

    private void setProgressValue(final int progress) {

        // set the progress
        loaderProgressBar.setProgress(progress);
        // thread is used to change the progress value
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                setProgressValue(progress + 10);
            }
        });
        thread.start();
    }
}
