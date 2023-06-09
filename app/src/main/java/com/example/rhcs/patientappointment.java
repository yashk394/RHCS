package com.example.rhcs;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.google.android.material.snackbar.Snackbar;

public class patientappointment extends AppCompatActivity {
    CardView addappointment1, about, instructions, direction, contact, news, upcoming, history;
    LinearLayout ll;
    boolean isConnected;
    String SelectedTopic, center;
    boolean baack = false;
    LinearLayout linearLayout;
    AlertDialog.Builder builder;
boolean isReleased=true;
int a=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patientappointment);
        addappointment1 = findViewById(R.id.addappointmentt);
        history = findViewById(R.id.hist);
        about = findViewById(R.id.about);
        builder = new AlertDialog.Builder(this);
        upcoming = findViewById(R.id.upcoming);
        instructions = findViewById(R.id.instructions);
        contact = findViewById(R.id.contact);
        direction = findViewById(R.id.direction);
        news = findViewById(R.id.news);
        ll = findViewById(R.id.lin);
        linearLayout = findViewById(R.id.lin);
        SelectedTopic = getIntent().getExtras().get("selected_topic").toString();
        SharedPreferences sharedPreferencess = getSharedPreferences("username", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferencess.edit();
        editor.putString("uname", SelectedTopic);
        editor.apply();
        SharedPreferences sharedPreferences = getSharedPreferences("share", MODE_PRIVATE);
        center = sharedPreferences.getString("name", "");
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


        addappointment1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(patientappointment.this, add_appointment.class);
                intent.putExtra("selected_topic", SelectedTopic.trim());
                startActivity(intent);
                overridePendingTransition(R.anim.right, R.anim.outright);
            }
        });
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(patientappointment.this, mixed13.class);
                startActivity(intent);
                overridePendingTransition(R.anim.left, R.anim.outright);


            }
        });
        instructions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(patientappointment.this, instructions.class);
                startActivity(intent);
                overridePendingTransition(R.anim.left, R.anim.outright);

            }
        });
        upcoming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(patientappointment.this, UpComingApp.class);
                intent.putExtra("selected_topic", SelectedTopic.trim());
                startActivity(intent);
                overridePendingTransition(R.anim.left, R.anim.outright);

            }
        });
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(patientappointment.this, AppHistory.class);
                intent.putExtra("selected_topic", SelectedTopic.trim());
                startActivity(intent);
                overridePendingTransition(R.anim.right, R.anim.outright);

            }
        });
        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(patientappointment.this, contact.class);
                startActivity(intent);
                overridePendingTransition(R.anim.left, R.anim.outright);

            }
        });
        news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), showall.class);
                i.putExtra("man", "user");
                startActivity(i);
                overridePendingTransition(R.anim.right, R.anim.outright);

            }
        });
        direction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager cm =
                        (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                isConnected = activeNetwork != null &&
                        activeNetwork.isConnectedOrConnecting();
                if (isConnected) {
                    Intent i = new Intent(getApplicationContext(), dest.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.right, R.anim.outright);

                } else {
                    Snackbar snackbar = Snackbar
                            .make(ll, getString(R.string.internetno), Snackbar.LENGTH_LONG)
                            .setAction(getString(R.string.retry), new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    ConnectivityManager cm =
                                            (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

                                    NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                                    isConnected = activeNetwork != null &&
                                            activeNetwork.isConnectedOrConnecting();
                                    if (isConnected) {
                                        Snackbar snackbar1 = Snackbar.make(ll, getString(R.string.interon), Snackbar.LENGTH_SHORT);
                                        snackbar1.show();
                                    } else {
                                        Snackbar snackbar1 = Snackbar.make(ll, getString(R.string.youof), Snackbar.LENGTH_SHORT);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.aboutus, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.aboutus) {
            Intent intent = new Intent(patientappointment.this, aboutus.class);
            startActivity(intent);
        }
        if (id == R.id.lo) {
            AlertDialog alert = builder.create();
            alert.show();

        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseRecognizer();
        if (isReleased==true)
        {
            SharedPreferences sharedPreferences = getSharedPreferences("X", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("out", "p");
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
        if (a==1)
        {
            isReleased=false;
        }
        else {
            isReleased = true;

        }
    }
    }
