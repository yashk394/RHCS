package com.example.rhcs;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CountererAppointmentlist extends ArrayAdapter<String> {
    private Activity context;
    CardView cardView;
    ProgressDialog progressDialog;
    ArrayList<String> userAppIds;
    DatabaseReference databaseReference;
    DatabaseReference databaseReference1;
    DatabaseReference databaseReference2,databaseReference3,db4,db5,db6;
    DatabaseReference db;
    DatabaseReference dbb;
    Drawable buttonColor;
    Date date1;
    String center1, date, a = " cz";
    String key, username, age, clinical, gender, historyof, patientname, refbydoctor, selecteddate, weight, mobile;
    ArrayList<String> ab = new ArrayList<>();
    List<String> abc = new ArrayList<String>();
    String[] mri = new String[]{"1] 3 TESLA MRI BRAIN",
            "-brain plain",
            "-MRI angio/veno",
            "-brain with angio/veno",
            "-brain perfusion",
            "-spectroscopy",
            "-ciss study", "-cisternography", "-epilepsy protocol", "-sella", "-pns", "-tractography", "-orbit",
            "2] SPINE", "-cervical/dorsal/lumbar/neck", "-whole spine screening", "-bracheal plexus",
            "3] JOINT", "-shoulder", "-elbow-R/L", "-wrist", "-si.joint", "-hip joint", "-knee", "-ankle", "-foot", "-t.m.joint",
            "4] ABDOMEN/PELVIS", "-pelvis", "-mrcp", "-fistulography", "-renal", "-peripheral", "-cardic", "-contrast",
            "5] CT SCAN", "-brain", "-neck", "-pns", "-orbit", "-temporal bone", "-chest", "-hrct thorax", "-abdomen", "-pelvis",
            "-abdomen triphasic", "-ct angio:renal/brain/neck", "-ct bronchoscopy", "-ct urography", "-ct colonoscopy",
            "-ct guided biopsy", "-ct dentascan", "-spine:cervical/dorsal/lumbar", "-joint:knee/shoulder/wrist-R/L", "-joint 3D",
            "-contrast"};
    Set<String> set;

    boolean[] checkeditems;

    public CountererAppointmentlist(Activity context, ArrayList<String> userAppIds, String center1, String date) {
        super(context, R.layout.counterer_app_list, userAppIds);
        this.context = context;
        this.userAppIds = userAppIds;
        this.center1 = center1;
        this.date = date;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listviewitem = inflater.inflate(R.layout.counterer_app_list, null, true);
        final TextView textViewName = (TextView) listviewitem.findViewById(R.id.cpname);
        final Button button = (Button) listviewitem.findViewById(R.id.cbtn);
        abc = Arrays.asList(mri);
        cardView = listviewitem.findViewById(R.id.ccc);
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(context.getString(R.string.wait));
        progressDialog.show();
        final String name = userAppIds.get(position);
        textViewName.setText(name);
        db = FirebaseDatabase.getInstance().getReference().child(center1).child("Color").child(date);
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(name)) {
                    a = dataSnapshot.child(name).child("name").getValue().toString();
                    if (a.equals("g")) {
                        button.setBackgroundResource(R.drawable.gback);
                        progressDialog.dismiss();
                    } else {
                        progressDialog.dismiss();

                    }

                } else {
                    progressDialog.dismiss();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        progressDialog.dismiss();


        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, CounterrPatientApp.class);
                notifyDataSetChanged();
                i.putExtra("pname", textViewName.getText().toString().trim());
                context.startActivity(i);
                progressDialog.dismiss();

            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.dismiss();
                ab.clear();
                checkeditems = new boolean[mri.length];

                databaseReference = FirebaseDatabase.getInstance().getReference().child(center1).child("AddAppointment").child(date).child(textViewName.getText().toString().trim());
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            key = snapshot.child("id").getValue().toString();
                            username = snapshot.child("username").getValue().toString();
                            age = snapshot.child("age").getValue().toString();
                            clinical = snapshot.child("clinicaldetail").getValue().toString();
                            gender = snapshot.child("gender").getValue().toString();
                            historyof = snapshot.child("historyof").getValue().toString();
                            patientname = snapshot.child("patientName").getValue().toString();
                            refbydoctor = snapshot.child("refbydocter").getValue().toString();
                            selecteddate = snapshot.child("selectdateforapp").getValue().toString();
                            weight = snapshot.child("weight").getValue().toString();
                            mobile = snapshot.child("mobile").getValue().toString();
                        }
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(context.getString(R.string.testlist));
                builder.setMultiChoiceItems(mri, checkeditems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        String abcd = abc.get(which);
                        if (isChecked) {


                            ab.add(abcd);

                        } else {

                            ab.remove(abcd);

                        }


                    }
                });
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, int which) {
                        String list = "";
                        for (int i = 0; i < ab.size(); i++) {
                            list += ab.get(i) + "\n" + "          ";
                        }
                        databaseReference1 = FirebaseDatabase.getInstance().getReference().child(center1).child("AppHistory").child(username).child(patientname);
                        addAppointment addAppointment = new addAppointment("PatientName:-" + patientname + "\n" + "Age:-" + age + "\n" + "Weight:-" + weight + "\n" + "HistoryOf:-" + historyof + "\n" + "ClinicalDetails:-" + clinical + "\n" + "Gender:-" + gender + "\n" + "RefDoc:-" + refbydoctor + "\n" + "Date:-" + selecteddate + "\n" + "Test:-" + list);
                        databaseReference1.setValue(addAppointment);
                        databaseReference2 = FirebaseDatabase.getInstance().getReference().child(center1).child("Color").child(date).child(textViewName.getText().toString().trim());
                        addAppointment aaaaa = new addAppointment("g");
                        databaseReference2.setValue(aaaaa);
                        SimpleDateFormat timeStampFormat = new SimpleDateFormat("M-yyyy");
                        Date myDate = new Date();
                        String filename = timeStampFormat.format(myDate);
                        SimpleDateFormat timeStampFormat1 = new SimpleDateFormat("d-M-yyyy");
                        Date myDate1 = new Date();
                        String filename1 = timeStampFormat1.format(myDate1);

                        databaseReference3 = FirebaseDatabase.getInstance().getReference().child(center1).child("Report").child(filename).push();
                        addAppointment aa=new addAppointment("PatientName:-"+patientname+"\n"+"Date:-"+filename1+"\n"+"TestDone:-"+list);
                        databaseReference3.setValue(aa);
                        db4 = FirebaseDatabase.getInstance().getReference().child(center1).child("SaveReport").child(filename).child("Pname").push();
                        addAppointment aaa=new addAppointment(patientname);
                        db4.setValue(aaa);
                        db5 = FirebaseDatabase.getInstance().getReference().child(center1).child("SaveReport").child(filename).child("Date").push();
                        addAppointment aaaa=new addAppointment(filename1);
                        db5.setValue(aaaa);
                        db6 = FirebaseDatabase.getInstance().getReference().child(center1).child("SaveReport").child(filename).child("Test").push();
                        addAppointment aaaaaa=new addAppointment(list);
                        db6.setValue(aaaaaa);
                        button.setBackgroundResource(R.color.green);
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                        builder1.setTitle("Test Added Successfully");
                        String list1 = "";
                        for (int i = 0; i < ab.size(); i++) {
                            if (ab.get(i).contains("1] 3 TESLA MRI BRAIN") || ab.get(i).contains("2] SPINE") || ab.get(i).contains("3] JOINT") || ab.get(i).contains("4] ABDOMEN/PELVIS") || ab.get(i).contains("5] CT SCAN")) {
                                list1 += "<p>" + "<b>" + ab.get(i) + "</b>" + "</p>";
                            } else {
                                list1 += "<p>" + ab.get(i) + "</p>";
                            }
                        }


                        dbb = FirebaseDatabase.getInstance().getReference().child(center1).child("Tests").child(date).child(textViewName.getText().toString().trim());
                        addAppointment te = new addAppointment(list);
                        dbb.setValue(te);

                        builder1.setMessage(Html.fromHtml(list1));
                        builder1.setCancelable(false);
                        builder1.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        builder1.show();


                        ab.clear();


                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        return listviewitem;
    }
}
