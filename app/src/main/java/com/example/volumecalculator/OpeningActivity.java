package com.example.volumecalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Path;
import android.os.Bundle;

public class OpeningActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opening);

        getSupportActionBar().hide();

        Thread thread = new Thread() {

            public void run() {
                try {
                    sleep(1500);

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    Intent intent = new Intent(OpeningActivity.this , MainActivity.class);
                    startActivity(intent);
                }
            }

        };
        thread.start();
    }
}