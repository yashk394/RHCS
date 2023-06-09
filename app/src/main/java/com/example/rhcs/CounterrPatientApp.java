package com.example.rhcs;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CounterrPatientApp extends AppCompatActivity {
    ListView lvDiscussion;
    List<addAppointment> staff;
    String center, date, name, a = "zdc";
    DatabaseReference databaseReference1, db;
    AlertDialog.Builder builder;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counterr_patient_app);
        lvDiscussion = findViewById(R.id.cpl);
        staff = new ArrayList<>();
        SharedPreferences sharedPreferences = getSharedPreferences("share", MODE_PRIVATE);
        center = sharedPreferences.getString("name", "");
        SharedPreferences sharedPreferences1 = getSharedPreferences("date", MODE_PRIVATE);
        date = sharedPreferences1.getString("date", "");
        name = getIntent().getExtras().get("pname").toString();
        //a=getIntent().getExtras().get("color").toString();
        databaseReference1 = FirebaseDatabase.getInstance().getReference().child(center).child("AddAppointment").child(date).child(name);
        textView = findViewById(R.id.mobilee);
        ActivityCompat.requestPermissions(CounterrPatientApp.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
    }

    @Override
    protected void onStart() {


        super.onStart();
        db = FirebaseDatabase.getInstance().getReference().child(center).child("Color").child(date);
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(name)) {
                    a = dataSnapshot.child(name).child("name").getValue().toString();
                    if (a.equals("g")) {
                        a = "g";
                    }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    staff.clear();
                    addAppointment aa = snapshot.getValue(addAppointment.class);
                    staff.add(aa);
                }

                if (a.equals("g")) {
                    Appointmentlist appointmentlist = new Appointmentlist(CounterrPatientApp.this, staff);


                    lvDiscussion.setAdapter(appointmentlist);

                } else {

                    Appointmentlist1 appointmentlist = new Appointmentlist1(CounterrPatientApp.this, staff);

                    lvDiscussion.setAdapter(appointmentlist);


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
