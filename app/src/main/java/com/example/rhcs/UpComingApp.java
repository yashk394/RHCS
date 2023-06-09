package com.example.rhcs;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class UpComingApp extends AppCompatActivity {
    ListView listView;
    TextView textView;
    String SelectedTopic, user_msg_key, center;
    ArrayList<String> listConversation = new ArrayList<>();
    ArrayList<String> listConversation2 = new ArrayList<>();
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase, firebaseDatabase2;
    DatabaseReference databaseReference, databaseReference1, dR, drTracks;
    ArrayAdapter arrayAdpt;
    ProgressDialog progressDialog;
    Date date1, date2;
    List<UserAppId> user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_coming_app);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.fetch1));
        progressDialog.setCancelable(true);
        progressDialog.show();
        String date = new SimpleDateFormat("d-M-yyyy").format(new Date());
        try {
            date1 = new SimpleDateFormat("d-M-yyyy").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        listView = findViewById(R.id.upcomingapp);
        textView = findViewById(R.id.textt);
        SelectedTopic = getIntent().getExtras().get("selected_topic").toString();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase2 = FirebaseDatabase.getInstance();
        arrayAdpt = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listConversation);
        SharedPreferences sharedPreferences = getSharedPreferences("share", MODE_PRIVATE);
        center = sharedPreferences.getString("name", "");
        listView.setAdapter(arrayAdpt);


        user = new ArrayList<>();


        databaseReference = FirebaseDatabase.getInstance().getReference().child(center).child("ShowApp");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayAdpt.clear();
                final Iterator i = dataSnapshot.getChildren().iterator();
                while (i.hasNext()) {
                    listConversation2.add(((DataSnapshot) i.next()).getKey());

                }

                for (int j = 0; j < listConversation2.size(); j++) {
                    try {
                        date2 = new SimpleDateFormat("d-M-yyyy").parse(listConversation2.get(j).toString());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    if (date1.compareTo(date2) <= 0) {
                        final String d = new SimpleDateFormat("d-M-yyyy").format(date2);
                        SharedPreferences sharedPreferenc = getSharedPreferences("username", MODE_PRIVATE);
                        final String stp = sharedPreferenc.getString("uname", "");
                        dR=FirebaseDatabase.getInstance().getReference().child(center).child("ShowApp").child(d);
                        dR.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.hasChild(stp))
                                {
                                    arrayAdpt.insert(d, arrayAdpt.getCount());

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }

                }
                if (listConversation.size() == 0) {
                    textView.setVisibility(View.VISIBLE);
                    progressDialog.dismiss();
                } else {
                    textView.setVisibility(View.INVISIBLE);
                    listView.setAdapter(arrayAdpt);
                    progressDialog.dismiss();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(), UpComingApp2.class);
                i.putExtra("patientdate", ((TextView) view).getText().toString());
                i.putExtra("selected_topic", SelectedTopic);
                startActivity(i);
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.right, R.anim.up);

    }

    private void showDelete(final String id) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.delete_dialog, null);
        dialogBuilder.setView(dialogView);

        final Button buttonDelete = (Button) dialogView.findViewById(R.id.delete);
        dialogBuilder.setTitle("");
        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteapp(id);
                b.dismiss();
            }
        });
    }

    private boolean deleteapp(String id) {
        //getting the specified artist reference


        //getting the tracks reference for the specified artist
        drTracks = firebaseDatabase2.getReference().child("AddAppointment").child(SelectedTopic).child(id);

        //removing all tracks
        drTracks.removeValue();
        Toast.makeText(getApplicationContext(), getString(R.string.delappoint), Toast.LENGTH_LONG).show();

        return true;
    }


}
