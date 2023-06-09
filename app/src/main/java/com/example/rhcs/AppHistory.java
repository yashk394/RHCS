package com.example.rhcs;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class AppHistory extends AppCompatActivity {
    ListView listView;
    String SelectedTopic, user_msg_key, center;
    ArrayList<String> listConversation = new ArrayList<>();
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ArrayAdapter arrayAdpt;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_history);
        listView = findViewById(R.id.apphist);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.fetch));
        progressDialog.setCancelable(true);
        progressDialog.show();
        SharedPreferences sharedPreferences = getSharedPreferences("share", MODE_PRIVATE);
        center = sharedPreferences.getString("name", "");
        SelectedTopic = getIntent().getExtras().get("selected_topic").toString();
        firebaseAuth = FirebaseAuth.getInstance();
        arrayAdpt = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listConversation);
        listView.setAdapter(arrayAdpt);

        databaseReference = FirebaseDatabase.getInstance().getReference().child(center).child("AppHistory").child(SelectedTopic);
        Map<String, Object> map = new HashMap<String, Object>();
        user_msg_key = databaseReference.push().getKey();
        databaseReference.updateChildren(map);

        DatabaseReference dbr2 = databaseReference.child(user_msg_key);
        Map<String, Object> map2 = new HashMap<String, Object>();
        dbr2.updateChildren(map2);


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                updateCoversation(dataSnapshot);
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String name = arrayAdpt.getItem(i).toString();
                Intent j = new Intent(AppHistory.this, historydetails.class);
                j.putExtra("name", name);
                j.putExtra("selected_topic", SelectedTopic);
                j.putExtra("str","history");
                startActivity(j);
            }
        });

    }

    public void updateCoversation(DataSnapshot dataSnapshot) {
        String msg, conversation;
        Iterator i = dataSnapshot.getChildren().iterator();
        while (i.hasNext()) {
            msg = (String) ((DataSnapshot) i.next()).getKey();
            conversation = msg;
            arrayAdpt.insert(conversation, arrayAdpt.getCount());
            arrayAdpt.notifyDataSetChanged();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.right, R.anim.up);

    }
}
