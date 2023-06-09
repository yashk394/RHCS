package com.example.rhcs;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;

import java.util.concurrent.TimeUnit;

public class mobile extends AppCompatActivity {
    CountryCodePicker countryCodePicker;
    EditText editText, edittextcode;
    Button button, verify, resend;
    String mVerificationId = "ja";
    FirebaseAuth mAuth;
    ProgressDialog progressDialog, pp;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference, databaseReference1;
    String s1, s2;
    String center;
    final Context context = this;
    Dialog dialog;
    boolean isConnected;
    RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile);
        pp = new ProgressDialog(this);
        pp.setMessage(getString(R.string.analysing));
        editText = findViewById(R.id.phoneText);
        edittextcode = findViewById(R.id.codeText);
        button = findViewById(R.id.sendButton);
        resend = findViewById(R.id.resendButton);
        verify = findViewById(R.id.verifyButton);
        countryCodePicker = findViewById(R.id.ccp);
        mAuth = FirebaseAuth.getInstance();
        ActivityCompat.requestPermissions(mobile.this, new String[]{Manifest.permission.RECEIVE_SMS}, 1);

        progressDialog = new ProgressDialog(this);
        dialog = new Dialog(context);
        relativeLayout = findViewById(R.id.re);
        dialog.setContentView(R.layout.popup);
        final Button b1 = (Button) dialog.findViewById(R.id.changes);
        final Button b2 = (Button) dialog.findViewById(R.id.cancle);
        final EditText e1 = (EditText) dialog.findViewById(R.id.npasss);
        final EditText e2 = (EditText) dialog.findViewById(R.id.cpasss);
        e1.setLongClickable(false);
        e2.setLongClickable(false);
        dialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage(getString(R.string.wait2));
        editText.setText(countryCodePicker.getSelectedCountryCodeWithPlus());
        editText.setSelection(editText.getText().length());
        SharedPreferences sharedPreferences = getSharedPreferences("share", MODE_PRIVATE);
        center = sharedPreferences.getString("name", "");
        s1 = getIntent().getExtras().get("pass").toString();
        s2 = getIntent().getExtras().get("static").toString();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference1 = firebaseDatabase.getReference();

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (e1.getText().toString().equals(e2.getText().toString())) {
                    databaseReference1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.child(center).child("user").hasChild(s1)) {
                                databaseReference1.child(center).child("user").child(s1).child("password").setValue(e2.getText().toString().trim());
                                Toast.makeText(getApplicationContext(), getString(R.string.paschn), Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(getApplicationContext(), login.class);
                                i.putExtra("name", center.trim());
                                startActivity(i);
                                dialog.dismiss();
                            } else {
                                Toast.makeText(context, getString(R.string.wrng), Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                } else {
                    Toast.makeText(mobile.this, getString(R.string.pasw), Toast.LENGTH_SHORT).show();
                    e1.getText().clear();
                    e2.getText().clear();
                }

            }
        });


        countryCodePicker.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                editText.setText(countryCodePicker.getSelectedCountryCodeWithPlus());
                editText.setSelection(editText.getText().length());
                Selection.setSelection(editText.getText(), editText.getText().length());

            }
        });
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().startsWith(countryCodePicker.getSelectedCountryCodeWithPlus())) {
                    editText.setText(countryCodePicker.getSelectedCountryCodeWithPlus());
                    Selection.setSelection(editText.getText(), editText.getText().length());

                }
            }
        });
        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager cm =
                        (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                isConnected = activeNetwork != null &&
                        activeNetwork.isConnectedOrConnecting();
                if (!isConnected) {
                    Snackbar s = Snackbar.make(relativeLayout, getString(R.string.youof), Snackbar.LENGTH_SHORT);
                    s.show();
                } else {

                    if (checkWriteExternalPermission())
                    {
                        if (s2.equals("static")) {
                            databaseReference = FirebaseDatabase.getInstance().getReference().child(center).child("user").child(s1);
                            databaseReference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    String phone = dataSnapshot.child("phonenumber").getValue().toString().trim();
                                    if (editText.getText().toString().equals(phone)) {
                                        if (countryCodePicker.getSelectedCountryName().equals("India")) {
                                            if (editText.getText().length() == 13) {
                                                String kk = editText.getText().toString().trim();
                                                sendVerificationCode(kk);
                                                progressDialog.show();
                                            } else {
                                                editText.setError(getString(R.string.validno));
                                            }
                                        }
                                    } else {
                                        Toast.makeText(mobile.this, getString(R.string.nomatch1), Toast.LENGTH_SHORT).show();
                                        editText.setError(getString(R.string.regno));
                                    }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        } else {
                            if (countryCodePicker.getSelectedCountryName().equals("India")) {
                                if (editText.getText().length() == 13) {
                                    String kk = editText.getText().toString().trim();
                                    sendVerificationCode(kk);
                                    progressDialog.show();
                                } else {
                                    editText.setError(getString(R.string.validno));
                                }
                            }
                        }
                    }
                    else {
                        Toast.makeText(context, "please grant permission", Toast.LENGTH_SHORT).show();
                        ActivityCompat.requestPermissions(mobile.this, new String[]{Manifest.permission.RECEIVE_SMS}, 1);

                    }
                                    }


            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager cm =
                        (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                isConnected = activeNetwork != null &&
                        activeNetwork.isConnectedOrConnecting();
                if (!isConnected) {
                    Snackbar s = Snackbar.make(relativeLayout, getString(R.string.youof), Snackbar.LENGTH_SHORT);
                    s.show();
                } else {
                    if (checkWriteExternalPermission())
                    {

                        if (s2.equals("static")) {
                            databaseReference = FirebaseDatabase.getInstance().getReference().child(center).child("user").child(s1);
                            databaseReference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    String phone = dataSnapshot.child("phonenumber").getValue().toString().trim();
                                    if (editText.getText().toString().equals(phone)) {
                                        if (countryCodePicker.getSelectedCountryName().equals("India")) {
                                            if (editText.getText().length() == 13) {
                                                String kk = editText.getText().toString().trim();
                                                sendVerificationCode(kk);
                                                progressDialog.show();
                                            } else {
                                                editText.setError(getString(R.string.validno));
                                            }
                                        }
                                    } else {
                                        Toast.makeText(mobile.this, getString(R.string.nomatch1), Toast.LENGTH_SHORT).show();
                                        editText.setError(getString(R.string.regno));
                                    }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        } else {
                            if (countryCodePicker.getSelectedCountryName().equals("India")) {
                                if (editText.getText().length() == 13) {
                                    String kk = editText.getText().toString().trim();
                                    sendVerificationCode(kk);
                                    progressDialog.show();
                                } else {
                                    editText.setError(getString(R.string.validno));
                                }
                            }
                        }

                    }
                    else {
                        Toast.makeText(context, "please grant permission", Toast.LENGTH_SHORT).show();
                        ActivityCompat.requestPermissions(mobile.this, new String[]{Manifest.permission.RECEIVE_SMS}, 1);
                    }


                }

            }
        });


        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edittextcode.getText().toString().isEmpty() || edittextcode.getText().length() < 6) {
                    edittextcode.setError(getString(R.string.validcode));
                    edittextcode.requestFocus();

                } else {
                    if (editText.getText().toString().trim().length() <= 3) {
                        editText.setError(getString(R.string.mobilef));

                    } else {
                        String code = edittextcode.getText().toString().trim();
                        verifyVerificationCode(code);
                        progressDialog.setMessage(getString(R.string.verifying));
                        progressDialog.show();

                    }
                }
            }
        });

    }

    private void sendVerificationCode(String mobile) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                mobile,
                120,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallbacks);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if (code != null) {
                progressDialog.dismiss();
                edittextcode.setText(code);
                pp.dismiss();
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(mobile.this, e.getMessage(), Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            mVerificationId = s;
            Toast.makeText(mobile.this, getString(R.string.otpsend), Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
            pp.show();

        }
    };

    private void verifyVerificationCode(String otp) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, otp);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential).addOnCompleteListener(mobile.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    progressDialog.dismiss();
                    if (s2.equals("static")) {
                        progressDialog.dismiss();
                        dialog.show();
                    } else {
                        Toast.makeText(mobile.this, getString(R.string.authentication), Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(), register.class);
                        i.putExtra("selected_topic", editText.getText().toString());
                        startActivity(i);
                        overridePendingTransition(R.anim.left, R.anim.up);

                    }


                } else {
                    Snackbar s = Snackbar.make(getCurrentFocus(), getString(R.string.something), Snackbar.LENGTH_SHORT);
                    s.show();
                    progressDialog.dismiss();
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

    private boolean checkWriteExternalPermission()
    {
        String permission = Manifest.permission.RECEIVE_SMS;
        int res = getApplicationContext().checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }
}