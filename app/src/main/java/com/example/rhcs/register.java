package com.example.rhcs;

import android.app.Notification;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Bundle;

import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.rhcs.notification.CHANNEL1;

public class register extends AppCompatActivity {
    EditText e1, e2, e3, e4;
    Button b1, b2;
    String SelectedTopic;
    private NotificationManagerCompat notificationManagerCompat;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase, firebaseDatabase1;
    DatabaseReference databaseReference, databaseReference1;
    addUser addUser;
    String center;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        e2 = findViewById(R.id.Username);
        e1 = findViewById(R.id.name);
        e3 = findViewById(R.id.email);
        e4 = findViewById(R.id.Password);
        b1 = findViewById(R.id.btnLogin);
        b2 = findViewById(R.id.can);
        notificationManagerCompat = NotificationManagerCompat.from(this);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase1 = FirebaseDatabase.getInstance();
       SelectedTopic = getIntent().getExtras().get("selected_topic").toString();
        SharedPreferences sharedPreferences = getSharedPreferences("share", MODE_PRIVATE);
        center = sharedPreferences.getString("name", "");
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.registering));
        e3.setLongClickable(false);
        e4.setLongClickable(false);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                if (e1.getText().toString().isEmpty() || e2.getText().toString().isEmpty() || e3.getText().toString().isEmpty() || e4.getText().toString().isEmpty()) {
                    Toast.makeText(register.this, getString(R.string.pleaseent), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();

                } else {

                    if (e3.getText().toString().length() != e4.getText().toString().length()) {
                        Toast.makeText(register.this, getString(R.string.passnotmatch), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        e3.getText().clear();
                        e4.getText().clear();
                    } else {
                        databaseReference1 = firebaseDatabase1.getReference(center).child("user");
                        databaseReference1.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.hasChild(e2.getText().toString().trim())) {
                                    Toast.makeText(register.this, getString(R.string.userall), Toast.LENGTH_SHORT).show();
                                    e2.getText().clear();
                                    progressDialog.dismiss();
                                } else {
                                    if (e2.getText().length() < 6) {
                                        e2.getText().clear();
                                        Toast.makeText(register.this, getString(R.string.mustbe6), Toast.LENGTH_SHORT).show();
                                        progressDialog.dismiss();
                                    } else {
                                        if (e4.getText().toString().length() < 6) {
                                            Toast.makeText(register.this, getString(R.string.password6), Toast.LENGTH_SHORT).show();
                                            progressDialog.dismiss();
                                            e4.getText().clear();
                                        } else {

                                            sendData();
                                            Toast.makeText(register.this, getString(R.string.regsuc), Toast.LENGTH_SHORT).show();
                                            Notification notification = new NotificationCompat.Builder(getApplicationContext(), CHANNEL1).setSmallIcon(R.drawable.info).setContentTitle("Welcome..!").setContentText("hello " + e1.getText().toString().trim()).setPriority(NotificationCompat.PRIORITY_HIGH).setCategory(NotificationCompat.CATEGORY_MESSAGE).build();
                                            notificationManagerCompat.notify(1, notification);
                                            Toast.makeText(register.this, getString(R.string.thankyou), Toast.LENGTH_SHORT).show();
                                            progressDialog.dismiss();
//                                            String messageToSend = e1.getText().toString().trim()+"Welcome to RHCS for more details contact to +91";
//                                            SmsManager.getDefault().sendTextMessage(SelectedTopic, null, messageToSend, null,null);
                                            Intent i = new Intent(getApplicationContext(), login.class);
                                            i.putExtra("name", center);
                                            startActivity(i);
                                            overridePendingTransition(R.anim.right, R.anim.left);


                                        }
                                    }


                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }


                }

            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                e1.getText().clear();
                e2.getText().clear();
                e3.getText().clear();
                e4.getText().clear();
                progressDialog.dismiss();
                Intent i = new Intent(getApplicationContext(), login.class);
                i.putExtra("name", center);
                startActivity(i);
                overridePendingTransition(R.anim.right, R.anim.left);

            }
        });
    }


    public void sendData() {
        databaseReference = firebaseDatabase.getReference().child(center).child("user").child(e2.getText().toString().trim());
        addUser = new addUser(e1.getText().toString().trim(), e2.getText().toString().trim(), e3.getText().toString().trim(), SelectedTopic, center);
        databaseReference.setValue(addUser);


    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
        overridePendingTransition(R.anim.bottom, R.anim.left);

    }


}
