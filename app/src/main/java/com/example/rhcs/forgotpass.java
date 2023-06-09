package com.example.rhcs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class forgotpass extends AppCompatActivity {
    TextView textView;
    Button button;
    EditText e1, e2, e3, e4;
    String center;
    boolean isConnected;
    LinearLayout linearLayout;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpass);
        textView = findViewById(R.id.phonee);
        button = findViewById(R.id.change);
        e1 = findViewById(R.id.namee);
        e2 = findViewById(R.id.passs);
        e3 = findViewById(R.id.npass);
        e4 = findViewById(R.id.cpass);
        linearLayout = findViewById(R.id.linear);
        SharedPreferences sharedPreferences = getSharedPreferences("share", MODE_PRIVATE);
        center = sharedPreferences.getString("name", "");
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("please wait..");
        e2.setLongClickable(false);
        e3.setLongClickable(false);
        e4.setLongClickable(false);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                if (e1.getText().toString().trim().isEmpty()) {
                    e1.setError(getString(R.string.username2));
                    Toast.makeText(forgotpass.this, getString(R.string.change1), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                } else {
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.child(center).child("user").hasChild(e1.getText().toString().trim())) {
                                progressDialog.dismiss();
                                Intent i = new Intent(getApplicationContext(), mobile.class);
                                i.putExtra("pass", e1.getText().toString().trim());
                                i.putExtra("static", "static");
                                startActivity(i);
                                overridePendingTransition(R.anim.left, R.anim.right);
                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(forgotpass.this, getString(R.string.uservalid), Toast.LENGTH_SHORT).show();
                                e1.getText().clear();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }

            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                ConnectivityManager cm =
                        (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                isConnected = activeNetwork != null &&
                        activeNetwork.isConnectedOrConnecting();
                if (isConnected) {
                    if (e1.getText().toString().isEmpty() || e2.getText().toString().isEmpty() || e3.getText().toString().isEmpty() || e4.getText().toString().isEmpty()) {
                        progressDialog.dismiss();
                        Toast.makeText(forgotpass.this, getString(R.string.allfield1), Toast.LENGTH_SHORT).show();
                    } else {
                        if (e3.getText().toString().trim().equals(e4.getText().toString().trim())) {
                            if (e3.getText().length() < 6) {
                                progressDialog.dismiss();
                                Toast.makeText(forgotpass.this, getString(R.string.longchar), Toast.LENGTH_SHORT).show();
                                e3.getText().clear();
                                e4.getText().clear();
                            } else {
                                databaseReference.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                        if (dataSnapshot.child(center).child("user").hasChild(e1.getText().toString().trim())) {
                                            String pass = dataSnapshot.child(center).child("user").child(e1.getText().toString().trim()).child("password").getValue().toString();
                                            if (e2.getText().toString().trim().equals(pass)) {
                                                progressDialog.dismiss();
                                                databaseReference.child(center).child("user").child(e1.getText().toString().trim()).child("password").setValue(e3.getText().toString().trim());
                                                Toast.makeText(forgotpass.this, getString(R.string.passchang), Toast.LENGTH_SHORT).show();
                                                Intent i = new Intent(forgotpass.this, login.class);
                                                i.putExtra("name", center);
                                                startActivity(i);
                                                overridePendingTransition(R.anim.left, R.anim.right);

                                            } else if (!e2.getText().toString().trim().equals(pass)) {
                                                progressDialog.dismiss();
                                                Toast.makeText(forgotpass.this, getString(R.string.notfound), Toast.LENGTH_SHORT).show();
                                                e2.getText().clear();
                                            }
                                        } else {
                                            progressDialog.dismiss();
                                            Toast.makeText(forgotpass.this, getString(R.string.usernot), Toast.LENGTH_SHORT).show();
                                            e1.getText().clear();
                                        }

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });

                            }
                        } else {
                            progressDialog.dismiss();
                            e3.getText().clear();
                            e4.getText().clear();
                            Toast.makeText(forgotpass.this, getString(R.string.nomatch), Toast.LENGTH_SHORT).show();

                        }
                    }
                } else {
                    progressDialog.dismiss();
                    Snackbar s = Snackbar.make(linearLayout, getString(R.string.youoff), Snackbar.LENGTH_SHORT);
                    s.show();
                }
            }
        });


    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
        overridePendingTransition(R.anim.right, R.anim.up);

    }
}
