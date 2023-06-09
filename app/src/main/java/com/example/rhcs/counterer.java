package com.example.rhcs;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class counterer extends AppCompatActivity {
    CardView addappointment, showapp, news, report;
    boolean baack = false;
    LinearLayout linearLayout;
    String center;
    AlertDialog.Builder builder;
boolean isReleased=true;
int a=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.counterer);
        addappointment = findViewById(R.id.addappointment);
        news = findViewById(R.id.news);
        showapp = findViewById(R.id.applist);
        linearLayout = findViewById(R.id.li);
        builder = new AlertDialog.Builder(this);
        report = findViewById(R.id.reportt);
        SharedPreferences sharedPreferences = getSharedPreferences("share", MODE_PRIVATE);
        center = sharedPreferences.getString("name", "");
        addappointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(counterer.this, add_appointment.class);
                intent.putExtra("selected_topic", "counterer");
                startActivity(intent);
                overridePendingTransition(R.anim.right, R.anim.up);

            }
        });

        showapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(counterer.this, ViewappointmentList.class);
                startActivity(intent);
                overridePendingTransition(R.anim.right, R.anim.up);

            }
        });
        news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(counterer.this, upload.class);
                intent.putExtra("hmanager", "hgkad");
                startActivity(intent);
                overridePendingTransition(R.anim.right, R.anim.up);

            }
        });

        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(counterer.this, reportgeneration.class);
                startActivity(intent);
            }
        });
        builder.setTitle(getString(R.string.Leave));
        builder.setMessage(getString(R.string.want))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.Yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                       a=1;
                        Intent i = new Intent(getApplicationContext(), login.class);
                        i.putExtra("name", center);
                        startActivity(i);
                        overridePendingTransition(R.anim.left, R.anim.outright);
                    }
                })
                .setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();

                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mcm, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.aboutu) {
            Intent intent = new Intent(counterer.this, aboutus.class);
            startActivity(intent);        }
        else if (id == R.id.logout) {
            AlertDialog alert = builder.create();
            alert.show();
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onBackPressed() {
        if (baack) {
            baack = false;
            super.onBackPressed();

            return;
        } else {
            this.baack = true;
            Snackbar s = Snackbar.make(linearLayout, getString(R.string.press), Snackbar.LENGTH_SHORT);
            s.show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    baack = false;

                }
            }, 3000);

        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        releaseRecognizer();
        if (isReleased==true)
        {
            SharedPreferences sharedPreferences = getSharedPreferences("X", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("out", "c");
            editor.apply();
        }
        else {
            SharedPreferences sharedPreferences = getSharedPreferences("X", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("out", "mxcv");
            editor.apply();
        }
    }
    private void releaseRecognizer() {
        if (a == 1) {
            isReleased = false;
        } else {
            isReleased = true;

        }
    }
}
