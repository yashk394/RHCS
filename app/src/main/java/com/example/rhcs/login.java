package com.example.rhcs;

import android.app.Notification;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.net.URL;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;

import static com.example.rhcs.notification.CHANNEL1;
import static com.example.rhcs.notification.CHANNEL2;

public class login extends AppCompatActivity {
    TextView textView;
    EditText e1, e2;
    Button button;
    FirebaseAuth firebaseAuth;
    LinearLayout linearLayout;
    String nameofcenter;
    FirebaseDatabase firebaseDatabase;
    ProgressDialog progressDialog, progressDialog1;
    String language, pas = "j", pass = "g";
    private NotificationManagerCompat notificationManagerCompat;
    DatabaseReference databaseReference, databaseReference1;
    boolean isConnected;
    boolean baack = false;
    int a = 1;
    RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        LoadLocal();
        notificationManagerCompat = NotificationManagerCompat.from(this);
        textView = findViewById(R.id.register);
        progressDialog1 = new ProgressDialog(this);
        progressDialog1.setMessage(getString(R.string.chnglang));
        progressDialog1.setCanceledOnTouchOutside(false);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        nameofcenter = getIntent().getExtras().get("name").toString();
        databaseReference = firebaseDatabase.getReference(nameofcenter).child("user");
        relativeLayout = findViewById(R.id.relative);
        linearLayout = findViewById(R.id.lin);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.verywait));
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                Intent i = new Intent(getApplicationContext(), mobile.class);
                i.putExtra("pass", "ok");
                i.putExtra("static", "ok");
                startActivity(i);
                overridePendingTransition(R.anim.left, R.anim.up);
                progressDialog.dismiss();
            }
        });
        button = findViewById(R.id.loginbtn);
        e1 = findViewById(R.id.usernamee);
        e2 = findViewById(R.id.pass);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                //    sendchannel2();
                //sendsms();
                progressDialog.show();
                ConnectivityManager cm =
                        (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                isConnected = activeNetwork != null &&
                        activeNetwork.isConnectedOrConnecting();
                if (isConnected) {
                    String s1 = e1.getText().toString().trim();
                    String s2 = e2.getText().toString();
                    if (s1.isEmpty() || s2.isEmpty()) {
                        progressDialog.dismiss();
                        Toast.makeText(login.this, getString(R.string.entfield), Toast.LENGTH_SHORT).show();
                    } else {
                        databaseReference1 = FirebaseDatabase.getInstance().getReference(nameofcenter).child("counterer");
                        databaseReference1.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.hasChild(e1.getText().toString().trim())) {
                                    pas = dataSnapshot.child(e1.getText().toString().trim()).child("password").getValue().toString().trim();
                                    if (e2.getText().toString().trim().equals(pas)) {
                                        progressDialog.dismiss();
                                        a = 0;
                                        Intent i = new Intent(getApplicationContext(), counterer.class);
                                        startActivity(i);
                                        overridePendingTransition(R.anim.left, R.anim.up);
                                        Toast.makeText(login.this, getString(R.string.loginsucces), Toast.LENGTH_SHORT).show();


                                    } else {
                                        e2.getText().clear();
                                        Toast.makeText(login.this, getString(R.string.pasnotfound), Toast.LENGTH_SHORT).show();
                                    }
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                        databaseReference.addValueEventListener(new ValueEventListener() {

                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                if (e1.getText().toString().trim().equals("rhcs") && e2.getText().toString().equals("rhcs123")) {
                                    progressDialog.dismiss();
                                    Toast.makeText(login.this, getString(R.string.loginsucces), Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(login.this, Manager.class);
                                    startActivity(i);
                                    overridePendingTransition(R.anim.left, R.anim.up);

                                } else if (e1.getText().toString().trim().equals("rhcs") && e2.getText().toString().equals("rhcs")) {
                                    progressDialog.dismiss();
                                    Toast.makeText(login.this, getString(R.string.loginsucces), Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(login.this, HeadManager.class);
                                    startActivity(i);
                                    overridePendingTransition(R.anim.left, R.anim.up);
                                } else if (dataSnapshot.hasChild(e1.getText().toString().trim())) {
                                    pass = dataSnapshot.child(e1.getText().toString().trim()).child("password").getValue().toString().trim();
                                    if (e2.getText().toString().equals(pass)) {

                                        progressDialog.dismiss();
                                        Toast.makeText(login.this, getString(R.string.loginsucces), Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(login.this, patientappointment.class);
                                        i.putExtra("selected_topic", e1.getText().toString().trim());
                                        startActivity(i);
                                        overridePendingTransition(R.anim.left, R.anim.up);

                                    } else {

                                        progressDialog.dismiss();
                                        Toast.makeText(login.this, getString(R.string.plsvalidpassword), Toast.LENGTH_SHORT).show();
                                        Snackbar s = Snackbar.make(relativeLayout, getString(R.string.tryagin), Snackbar.LENGTH_SHORT);
                                        e2.getText().clear();
                                        s.show();


                                    }

                                } else {
                                    if (a != 0) {
                                        progressDialog.dismiss();
                                        Toast.makeText(login.this, getString(R.string.usernotfound), Toast.LENGTH_SHORT).show();
                                        e1.getText().clear();
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }
                } else {
                    progressDialog.dismiss();
                    Snackbar s = Snackbar.make(relativeLayout, getString(R.string.youareoff), Snackbar.LENGTH_SHORT);
                    s.show();
                }
            }

        });
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(login.this, forgotpass.class);
                startActivity(i);
                overridePendingTransition(R.anim.left, R.anim.up);

            }
        });
        e2.setLongClickable(false);
    }

    /*private void sendsms() {
        try {
            // Construct data
            String apiKey = "apikey=" +"U1FYtwYDWrQ-Fqn3crKLwU91uFRbrWFgvMMn49GY1b" ;
            String message = "&message=" + "Welcome to RHCS ";
            String sender = "&sender=" + e1.getText().toString();
            String numbers = "&numbers=" + "919588426121";

            // Send data
            HttpURLConnection conn = (HttpURLConnection) new URL("https://api.textlocal.in/send/?").openConnection();
            String data = apiKey + numbers + message + sender;
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
            conn.getOutputStream().write(data.getBytes("UTF-8"));
            final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            final StringBuffer stringBuffer = new StringBuffer();
            String line;
            while ((line = rd.readLine()) != null) {
              //  stringBuffer.append(line);
                Toast.makeText(getApplicationContext(), "the message is "+line, Toast.LENGTH_SHORT).show();
            }
            rd.close();

           // return stringBuffer.toString();
        } catch (Exception e) {
            //return "Error "+e;
            Toast.makeText(getApplicationContext(), "the error message is "+e, Toast.LENGTH_SHORT).show();
        }

    }

    private void sendchannel2() {
        Notification notification = new NotificationCompat.Builder(this, CHANNEL2).setSmallIcon(R.drawable.logoo).setContentTitle("RHCS").setContentText("i am yash").setPriority(NotificationCompat.PRIORITY_HIGH).setCategory(NotificationCompat.CATEGORY_MESSAGE).build();
        notificationManagerCompat.notify(1, notification);
    }
*/

    @Override
    public void onBackPressed() {


        if (baack) {
            baack = false;
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            super.onBackPressed();

            return;
        } else {
            this.baack = true;
            Snackbar s = Snackbar.make(relativeLayout, getString(R.string.press), Snackbar.LENGTH_SHORT);
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
        getMenuInflater().inflate(R.menu.lag, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.eng) {
            setLocal("en");
            progressDialog1.show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    recreate();
                    progressDialog1.dismiss();
                }
            }, 4000);

        } else if (id == R.id.marathi) {
            setLocal("mr");
            progressDialog1.show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    recreate();
                    progressDialog1.dismiss();
                }
            }, 4000);

        }
        return super.onOptionsItemSelected(item);
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
