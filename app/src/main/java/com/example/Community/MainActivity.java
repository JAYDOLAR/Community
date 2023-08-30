package com.example.Community;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import Authentication.Authentication;

public class MainActivity extends AppCompatActivity {
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        onLoadPage();
    }

    public void onLoadPage() {
        progressBar = findViewById(R.id.welcomeProgressBar);
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            Intent intent = new Intent(MainActivity.this, Authentication.class);
            startActivity(intent);
            finish();
        },1000);

/*
        Thread welcomeThread = new Thread(){
            @Override
            public void run() {
                try{
                    super.run();
                    sleep(10000);
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }finally {
                    progressBar.
                }

            }
        };
*/
    }
}