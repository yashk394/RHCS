package com.example.rhcs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class ViewappointmentList extends AppCompatActivity {
    ListView listView;
    ArrayList<String> myList = new ArrayList<>();
    ArrayList<String> a = new ArrayList<>();
    ArrayList<String> data = new ArrayList<>();
    FirebaseAuth firebaseAuth;
    EditText editText;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ArrayAdapter<String> myAdapter;
    String center;
    DatePickerDialog datePickerDialog;
    String last;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewappointment_list);
        listView = findViewById(R.id.listt);
        editText = findViewById(R.id.search);
        SharedPreferences sharedPreferences = getSharedPreferences("share", MODE_PRIVATE);
        center = sharedPreferences.getString("name", "");
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child(center).child("AddAppointment");
        myAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, myList);
        listView.setAdapter(myAdapter);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("please wait..!");
        progressDialog.show();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Set<String> set = new HashSet<>();
                data.clear();
                Iterator i = dataSnapshot.getChildren().iterator();

                while (i.hasNext()) {
                    data.add(((DataSnapshot) i.next()).getKey());
                }
                Collections.sort(data, Collections.<String>reverseOrder());
                myAdapter.clear();
                myAdapter.addAll(data);
                myAdapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplication(), ViewAppointmentDetails.class);
                SharedPreferences sharedPreferences = getSharedPreferences("date", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("date", ((TextView) view).getText().toString());
                editor.apply();
                startActivity(i);
                progressDialog.dismiss();

            }
        });
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.dismiss();
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(ViewappointmentList.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                monthOfYear = monthOfYear + 1;
                                String d = String.valueOf(dayOfMonth);
                                String m = String.valueOf(monthOfYear);
                                String y = String.valueOf(year);
                                last = d + "-" + m + "-" + y;
                                editText.setText(last);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                (ViewappointmentList.this).myAdapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.right, R.anim.up);
        progressDialog.dismiss();
    }
}
