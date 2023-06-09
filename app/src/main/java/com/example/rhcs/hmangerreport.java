package com.example.rhcs;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Locale;

public class hmangerreport extends AppCompatActivity {
    DatabaseReference databaseReference;
    String center;
    ListView listView;
    ArrayList<String> list=new ArrayList<>();
    ArrayList<String> pname=new ArrayList<>();
    ArrayList<String> date1=new ArrayList<>();
    ArrayList<String> test=new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    ProgressDialog progressDialog;
    final Calendar calendar=Calendar.getInstance();
    Spinner spinner;
    TextView e1;
    Button button;
    String[] cent=new String[]{"select_center", "satara", "indapur", "baramati", "phaltan", "chiplun", "karad", "belgaum"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hmangerreport);
        e1=findViewById(R.id.date11);
        button=findViewById(R.id.generate1);
     spinner=findViewById(R.id.spih);
     listView=findViewById(R.id.ls);
        final ArrayAdapter<String> adapter =new ArrayAdapter<String>(this , android.R.layout.simple_spinner_item,cent);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        center=adapter.getItem(i);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
});
        arrayAdapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,list);
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Fetching");



        final DatePickerDialog.OnDateSetListener date=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar.set(Calendar.YEAR,i);
                calendar.set(Calendar.MONTH,i1);
                calendar.set(Calendar.DAY_OF_MONTH,i2);
                updatelabel();
            }
        };
        e1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(hmangerreport.this,date,calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                list.clear();
                date1.clear();
                pname.clear();
                test.clear();
                if (center.equals("select_center"))
                {
                    Toast.makeText(hmangerreport.this, "please select center to view report", Toast.LENGTH_SHORT).show();
                }
                else{


                    databaseReference = FirebaseDatabase.getInstance().getReference().child(center).child("Report").child(e1.getText().toString().trim());

                    databaseReference.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                            String msg,con;
                            Iterator i=dataSnapshot.getChildren().iterator();
                            while (i.hasNext())
                            {
                                msg=(String)((DataSnapshot) i.next()).getValue();
                                con=msg;
                                arrayAdapter.insert(con,arrayAdapter.getCount());
                                arrayAdapter.notifyDataSetChanged();
                            }
                            listView.setAdapter(arrayAdapter);
                            progressDialog.dismiss();
                        }

                        @Override
                        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                            String msg,con;
                            Iterator i=dataSnapshot.getChildren().iterator();
                            while (i.hasNext())
                            {
                                msg=(String)((DataSnapshot) i.next()).getValue();
                                con=msg;
                                arrayAdapter.insert(con,arrayAdapter.getCount());
                                arrayAdapter.notifyDataSetChanged();
                            }
                            listView.setAdapter(arrayAdapter);
                            progressDialog.dismiss();
                        }

                        @Override
                        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                        }

                        @Override
                        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                }



            }
        });
    }
    private void updatelabel()
    {
        String myFormat="M-yyyy";
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat(myFormat, Locale.US);
        e1.setText(simpleDateFormat.format(calendar.getTime()));
    }
}