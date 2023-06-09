package com.example.rhcs;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class ViewAppointmentDetails extends AppCompatActivity {
    ListView listView;
    ArrayList<String> listConversation = new ArrayList<>();
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;

    DatabaseReference databaseReference;
    ArrayAdapter arrayAdpt;
    String SelectedTopic, user_msg_key, center, date;
    List<Countererkey> countererkeys;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_appointment_details);
        listView = findViewById(R.id.listq);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("fetching patients..");
        progressDialog.show();
        SharedPreferences sharedPreferences = getSharedPreferences("share", MODE_PRIVATE);
        center = sharedPreferences.getString("name", "");
        SharedPreferences sharedPreferences1 = getSharedPreferences("date", MODE_PRIVATE);
        date = sharedPreferences1.getString("date", "");
        firebaseAuth = FirebaseAuth.getInstance();
        //arrayAdpt=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,listConversation);
        //listView.setAdapter(arrayAdpt);

        databaseReference = FirebaseDatabase.getInstance().getReference().child(center).child("AddAppointment").child(date);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String name = snapshot.getKey().toString();
                    listConversation.add(name);
                    //set.add((snapshot).getKey());
                }
                CountererAppointmentlist countererAppointmentlist = new CountererAppointmentlist(ViewAppointmentDetails.this, listConversation, center, date);
                listView.setAdapter(countererAppointmentlist);
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


}
