package com.example.rhcs;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class select_center extends AppCompatActivity {
    ImageView i1, i2, i3, i4, i5, i6, i7;
    TextView t1, t2, t3, t4, t5, t6, t7;
    String center;
    ProgressDialog progressDialog;
    AlertDialog.Builder builder;
    AlertDialog alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_center);
        i1 = findViewById(R.id.myImage);
        i2 = findViewById(R.id.myImage1);
        i3 = findViewById(R.id.myImage2);
        i4 = findViewById(R.id.myImage3);
        i5 = findViewById(R.id.myImage4);
        i6 = findViewById(R.id.myImage5);
        i7 = findViewById(R.id.myImage6);
        t1 = findViewById(R.id.ch1);
        t2 = findViewById(R.id.ch2);
        t3 = findViewById(R.id.ch3);
        t4 = findViewById(R.id.ch4);
        t5 = findViewById(R.id.ch5);
        t6 = findViewById(R.id.ch6);
        t7 = findViewById(R.id.ch7);
        builder = new AlertDialog.Builder(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.move));

        i1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                center = t1.getText().toString().trim();
                builder.setMessage(getString(R.string.continu) + center)
                        .setCancelable(false)
                        .setPositiveButton("continue", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                progressDialog.show();
                                alert.dismiss();
                                SharedPreferences sharedPreferences = getSharedPreferences("share", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("name", center);
                                editor.apply();
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent i = new Intent(getApplicationContext(), login.class);
                                        i.putExtra("name", center);
                                        startActivity(i);
                                        overridePendingTransition(R.anim.left, R.anim.right);
                                        progressDialog.dismiss();
                                    }
                                }, 300);


                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                alert.dismiss();
                            }
                        });
                alert = builder.create();
                alert.setTitle(getString(R.string.makesure));
                alert.show();
            }
        });

        i2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                center = t2.getText().toString().trim();
                builder.setMessage(getString(R.string.continu) + center)
                        .setCancelable(false)
                        .setPositiveButton("continue", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                progressDialog.show();
                                alert.dismiss();
                                SharedPreferences sharedPreferences = getSharedPreferences("share", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("name", center);
                                editor.apply();
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent i = new Intent(getApplicationContext(), login.class);
                                        i.putExtra("name", center);
                                        startActivity(i);
                                        overridePendingTransition(R.anim.left, R.anim.right);
                                        progressDialog.dismiss();
                                    }
                                }, 300);

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                alert.dismiss();
                            }
                        });
                alert = builder.create();
                alert.setTitle(getString(R.string.makesure));
                alert.show();
            }
        });

        i3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                center = t3.getText().toString().trim();
                builder.setMessage(getString(R.string.continu) + center)
                        .setCancelable(false)
                        .setPositiveButton("continue", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                progressDialog.show();
                                alert.dismiss();
                                SharedPreferences sharedPreferences = getSharedPreferences("share", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("name", center);
                                editor.apply();
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent i = new Intent(getApplicationContext(), login.class);
                                        i.putExtra("name", center);
                                        startActivity(i);
                                        overridePendingTransition(R.anim.left, R.anim.right);
                                        progressDialog.dismiss();
                                    }
                                }, 300);

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                alert.dismiss();
                            }
                        });
                alert = builder.create();
                alert.setTitle(getString(R.string.makesure));
                alert.show();

            }
        });

        i4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                center = t4.getText().toString().trim();
                builder.setMessage(getString(R.string.continu) + center)
                        .setCancelable(false)
                        .setPositiveButton("continue", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                progressDialog.show();
                                alert.dismiss();
                                SharedPreferences sharedPreferences = getSharedPreferences("share", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("name", center);
                                editor.apply();
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent i = new Intent(getApplicationContext(), login.class);
                                        i.putExtra("name", center);
                                        startActivity(i);
                                        overridePendingTransition(R.anim.left, R.anim.right);
                                        progressDialog.dismiss();
                                    }
                                }, 300);

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                alert.dismiss();
                            }
                        });
                alert = builder.create();
                alert.setTitle(getString(R.string.makesure));
                alert.show();

            }
        });

        i5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                center = t5.getText().toString().trim();
                builder.setMessage(getString(R.string.continu) + center)
                        .setCancelable(false)
                        .setPositiveButton("continue", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                progressDialog.show();
                                alert.dismiss();
                                SharedPreferences sharedPreferences = getSharedPreferences("share", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("name", center);
                                editor.apply();
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent i = new Intent(getApplicationContext(), login.class);
                                        i.putExtra("name", center);
                                        startActivity(i);
                                        overridePendingTransition(R.anim.left, R.anim.right);
                                        progressDialog.dismiss();
                                    }
                                }, 300);

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                alert.dismiss();
                            }
                        });
                alert = builder.create();
                alert.setTitle(getString(R.string.makesure));
                alert.show();
            }
        });

        i6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                center = t6.getText().toString().trim();
                builder.setMessage(getString(R.string.continu)+ center)
                        .setCancelable(false)
                        .setPositiveButton("continue", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                progressDialog.show();
                                alert.dismiss();
                                SharedPreferences sharedPreferences = getSharedPreferences("share", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("name", center);
                                editor.apply();
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent i = new Intent(getApplicationContext(), login.class);
                                        i.putExtra("name", center);
                                        startActivity(i);
                                        overridePendingTransition(R.anim.left, R.anim.right);
                                        progressDialog.dismiss();
                                    }
                                }, 300);

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                alert.dismiss();
                            }
                        });
                alert = builder.create();
                alert.setTitle(getString(R.string.makesure));
                alert.show();
            }
        });
        i7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                center = t7.getText().toString().trim();
                builder.setMessage(getString(R.string.continu) + center)
                        .setCancelable(false)
                        .setPositiveButton("continue", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                progressDialog.show();
                                alert.dismiss();
                                SharedPreferences sharedPreferences = getSharedPreferences("share", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("name", center);
                                editor.apply();
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent i = new Intent(getApplicationContext(), login.class);
                                        i.putExtra("name", center);
                                        startActivity(i);
                                        overridePendingTransition(R.anim.left, R.anim.right);
                                        progressDialog.dismiss();
                                    }
                                }, 300);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                alert.dismiss();
                            }
                        });
                alert = builder.create();
                alert.setTitle(getString(R.string.makesure));
                alert.show();
            }
        });
    }


    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
