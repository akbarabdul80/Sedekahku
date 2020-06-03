package com.sragen.sedekahku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.wang.avi.AVLoadingIndicatorView;

public class SplashScreen extends AppCompatActivity {

    private LinearLayout lv_loading;
    private AVLoadingIndicatorView avi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        lv_loading = findViewById(R.id.lv_loading);
        avi= findViewById(R.id.av);
        avi.setIndicator("BallScaleMultiple");

        //membuat sebuah proses yang ter delay
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //mendefenisikan Intent activity
                Intent i = new Intent(getApplicationContext(),IntroActivity.class);
                startActivity(i);                //finish berguna untuk mengakhiri activity
                //disini saya menggunakan finish,supaya ketika menekan tombol back
                //tidak kembali pada activity SplashScreen nya
                finish();
            }
            //disini maksud 3000 nya itu adalah lama screen ini terdelay 3 detik,dalam satuan mili second
        }, 2000);
    }
}

