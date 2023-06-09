package com.example.rhcs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

public class dest extends AppCompatActivity {
    ImageView imageView;
    String text;
    RelativeLayout ll;
    boolean isConnected;
    String uri;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dest);
        imageView = findViewById(R.id.img);
        ll = findViewById(R.id.ret);
        SharedPreferences sharedPreferences = getSharedPreferences("share", MODE_PRIVATE);
        text = sharedPreferences.getString("name", "");

        if (text.equals("satara")) {
            uri = "geo:0,0?q=SUSHRUT BUILDING,PARANGE CHOWK, Sadar Bazar, Satara, Maharashtra 415001india";
        }
        if (text.equals("indapur")) {
            uri = "geo:0,0?q=Ruby Medical Services Unnamed Road, Indapur, Maharashtra 413106";
        }
        if (text.equals("baramati")) {
            uri = "geo:0,0?q=Ruby Hall Clinic Services Pvt Ltd, 109 ,A ,kanchan tej building, Bhigwan Rd, near union bank, Baramati, Maharashtra 413102";
        }
        if (text.equals("karad")) {
            uri = "geo:0,0?q=RUBY MEDICAL SERVICES KARAD MRI CENTER, Ruby medical services, Konyanpur, Karad, Maharashtra";
        }
        if (text.equals("chiplun")) {
            uri = "geo:0,0?q=ruby hall clinic near Chiplunkar Building, Tara Temple Lane, Grant Road East, Shapur Baug, Grant Road, Mumbai, Maharashtra";
        }
        if (text.equals("phaltan")) {
            uri = "geo:0,0?q=40, Sasoon Rd, Sangamvadi, Pune, Maharashtra 411001";
        }


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager cm =
                        (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                isConnected = activeNetwork != null &&
                        activeNetwork.isConnectedOrConnecting();
                if (isConnected) {
                    Uri gmmIntentUri = Uri.parse(uri);
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    if (mapIntent.resolveActivity(getPackageManager()) != null) {
                        startActivity(mapIntent);

                    }


                } else {
                    Snackbar snackbar = Snackbar
                            .make(ll, getString(R.string.internet), Snackbar.LENGTH_LONG)
                            .setAction(getString(R.string.retry), new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    ConnectivityManager cm =
                                            (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

                                    NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                                    isConnected = activeNetwork != null &&
                                            activeNetwork.isConnectedOrConnecting();
                                    if (isConnected) {
                                        Snackbar snackbar1 = Snackbar.make(ll, getString(R.string.on), Snackbar.LENGTH_SHORT);
                                        snackbar1.show();
                                    } else {
                                        Snackbar snackbar1 = Snackbar.make(ll, getString(R.string.offline1), Snackbar.LENGTH_SHORT);
                                        snackbar1.show();
                                    }
                                }
                            });

                    snackbar.show();
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.right, R.anim.up);

    }
}
