package com.example.rhcs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class showc extends AppCompatActivity {
    ListView listView;
    ArrayList<String> listConversation;
    FirebaseDatabase firebaseDatabase;
    ArrayAdapter arrayAdpt;
    DatabaseReference databaseReference, databaseReference1;
    String center;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showc);
        SharedPreferences sharedPreferences = getSharedPreferences("share", MODE_PRIVATE);
        center = sharedPreferences.getString("name", "");
        listView = findViewById(R.id.clist);
        listConversation = new ArrayList<>();
        arrayAdpt = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listConversation);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child(center).child("counterer");
        listView.setAdapter(arrayAdpt);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listConversation.clear();
                arrayAdpt.clear();
                Set<String> set = new HashSet<>();
                Iterator i = dataSnapshot.getChildren().iterator();
                while (i.hasNext()) {
                    set.add(((DataSnapshot) i.next()).getKey());
                }
                arrayAdpt.addAll(set);
                arrayAdpt.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String s = (String) arrayAdpt.getItem(i);
                Toast.makeText(showc.this, "counterer" + s, Toast.LENGTH_SHORT).show();

            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (listConversation.size() == 1) {
                    Toast.makeText(showc.this, getString(R.string.countere), Toast.LENGTH_SHORT).show();
                } else {
                    final String name = arrayAdpt.getItem(i).toString();
                    AlertDialog.Builder builder = new AlertDialog.Builder(showc.this);
                    builder.setTitle(getString(R.string.delcount));
                    builder.setMessage(getString(R.string.decount) + name);
                    builder.setCancelable(false);
                    builder.setPositiveButton(getString(R.string.Yes), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            databaseReference1 = FirebaseDatabase.getInstance().getReference().child(center).child("counterer").child(name);
                            databaseReference1.removeValue();

                        }
                    });
                    builder.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }


                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.right, R.anim.up);

    }
}
