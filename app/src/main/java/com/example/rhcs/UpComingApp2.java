package com.example.rhcs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UpComingApp2 extends AppCompatActivity {
    ListView listView;
    String date,SelectedTopic,center;
    DatabaseReference databaseReference;
    List<addAppointment> staff;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_coming_app2);
        listView=findViewById(R.id.lis1);
        Context context;
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.fetch2));
        progressDialog.show();
        staff=new ArrayList<>();
        date=getIntent().getExtras().get("patientdate").toString();
        SelectedTopic=getIntent().getExtras().get("selected_topic").toString();
        SharedPreferences sharedPreferences = getSharedPreferences("share", MODE_PRIVATE);
        center = sharedPreferences.getString("name", "");
        databaseReference= FirebaseDatabase.getInstance().getReference().child(center).child("ShowApp").child(date).child(SelectedTopic);
    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                staff.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    addAppointment aa = snapshot.getValue(addAppointment.class);
                    staff.add(aa);
                }

                    UserAppointmentlist appointmentlist=new UserAppointmentlist(UpComingApp2.this,staff,date,SelectedTopic,center);
                    listView.setAdapter(appointmentlist);
                    progressDialog.dismiss();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();

    }
}
