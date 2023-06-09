package com.example.rhcs;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
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
import java.util.Iterator;

public class historydetails extends AppCompatActivity {
    ListView listView;
    String SelectedTopic, his, center, name;
    ArrayList<String> listConversation = new ArrayList<>();
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference,databaseReference1;
    ArrayAdapter arrayAdpt;
    ProgressDialog progressDialog;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historydetails);
        listView = findViewById(R.id.lishis);
        textView = findViewById(R.id.of);
        SharedPreferences sharedPreferences = getSharedPreferences("share", MODE_PRIVATE);
        center = sharedPreferences.getString("name", "");
        SelectedTopic = getIntent().getExtras().get("selected_topic").toString();
        his=getIntent().getExtras().get("str").toString();
        name = getIntent().getExtras().get("name").toString();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.fetch));
        progressDialog.setCancelable(true);
        progressDialog.show();
        arrayAdpt = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listConversation);
        listView.setAdapter(arrayAdpt);
        if (his.equals("history"))
        {
            databaseReference = FirebaseDatabase.getInstance().getReference().child(center).child("AppHistory").child(SelectedTopic).child(name);
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
        }


    }

    public void updateCoversation(DataSnapshot dataSnapshot) {
        String msg, conversation;
        Iterator i = dataSnapshot.getChildren().iterator();
        while (i.hasNext()) {
            msg = (String) ((DataSnapshot) i.next()).getValue();
            conversation = msg;
            arrayAdpt.insert(conversation, arrayAdpt.getCount());
            arrayAdpt.notifyDataSetChanged();
        }
        if (arrayAdpt.getCount() == 0) {
            textView.setVisibility(View.VISIBLE);
            progressDialog.dismiss();
        } else {
            textView.setVisibility(View.INVISIBLE);
            progressDialog.dismiss();
        }
    }
}
