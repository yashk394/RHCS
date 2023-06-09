package com.example.rhcs;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.util.Locale;

import pl.droidsonroids.gif.GifImageView;


public class splash extends AppCompatActivity {
    String center;
    GifImageView gifImageView;
    Window window;
String language;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        gifImageView = findViewById(R.id.gif);
LoadLocal();



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.bb));
        }


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPreferences = getSharedPreferences("share", MODE_PRIVATE);
                center = sharedPreferences.getString("name", "");
                if (center.isEmpty()) {

                    Intent i = new Intent(getApplicationContext(), select_center.class);
                    startActivity(i);
                } else {
                    SharedPreferences sharedPreferencess = getSharedPreferences("X", MODE_PRIVATE);
                    String s = sharedPreferencess.getString("out", "");


                     if (s.equals("hm"))
                    {
                        Intent i = new Intent(splash.this, HeadManager.class);
                        startActivity(i);
                    }else if (s.equals("m"))
                    {
                        Intent i = new Intent(splash.this, Manager.class);
                        startActivity(i);
                    }
                     else if (s.equals("p"))
                     {
                         Intent i = new Intent(splash.this, patientappointment.class);
                         SharedPreferences sharedPreferenc = getSharedPreferences("username", MODE_PRIVATE);
                         String stp = sharedPreferenc.getString("uname", "");
                         i.putExtra("selected_topic",stp);
                         startActivity(i);
                     }
                     else if (s.equals("c"))
                     {
                         Intent i = new Intent(splash.this, counterer.class);
                         startActivity(i);
                     }
                     else
                     {
                         Intent i = new Intent(splash.this, login.class);
                         i.putExtra("name", center);
                         startActivity(i);
                     }


                }
            }
        }, 8000);


    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
        super.onBackPressed();

    }

    private void setLocal(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());
        SharedPreferences sharedPreferences = getSharedPreferences("setting", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("MYLANG", lang);
        editor.apply();

    }

    public void LoadLocal() {
        SharedPreferences sharedPreferences = getSharedPreferences("setting", MODE_PRIVATE);
        language = sharedPreferences.getString("MYLANG", "");
        setLocal(language);
    }
}
