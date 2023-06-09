package com.example.rhcs;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
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
import androidx.core.app.ActivityCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class Appointmentlist extends ArrayAdapter<addAppointment> {
    private Activity context;
    List<addAppointment> userAppIds;
    Button imageButton;
    DatabaseReference databaseReferencee, db;
    String centerr, uu, testss;
    ProgressDialog progressDialog;

    public Appointmentlist(Activity context, List<addAppointment> userAppIds) {
        super(context, R.layout.patientdetails, userAppIds);
        this.context = context;
        this.userAppIds = userAppIds;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listviewitem = inflater.inflate(R.layout.patientdetails, null, true);
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("fetching details....!");
        progressDialog.show();
        final TextView textViewName = (TextView) listviewitem.findViewById(R.id.pname);
        TextView weight = (TextView) listviewitem.findViewById(R.id.ewight);
        TextView age = (TextView) listviewitem.findViewById(R.id.age);
        TextView gender = (TextView) listviewitem.findViewById(R.id.gender);
        TextView refd = (TextView) listviewitem.findViewById(R.id.refbydoc);
        TextView cd = (TextView) listviewitem.findViewById(R.id.cdetail);
        TextView h = (TextView) listviewitem.findViewById(R.id.his);
        TextView e = (TextView) listviewitem.findViewById(R.id.email);
        final TextView m = (TextView) listviewitem.findViewById(R.id.mobilee);
        TextView test = (TextView) listviewitem.findViewById(R.id.mobilee);
        final TextView tests = (TextView) listviewitem.findViewById(R.id.tesst);
        Button button = (Button) listviewitem.findViewById(R.id.cbtn);
        imageButton = listviewitem.findViewById(R.id.call);

        final addAppointment user = userAppIds.get(position);

        textViewName.setText(user.getPatientName());
        weight.setText(user.getWeight());
        age.setText(user.getAge());
        gender.setText(user.getGender());
        refd.setText(user.getRefbydocter());
        cd.setText(user.getClinicaldetail());
        h.setText(user.getHistoryof());
        e.setText(user.getEmail());
        m.setText(user.getMobile());
        uu = user.getUsername();

        SharedPreferences sharedPreferences = context.getSharedPreferences("share", MODE_PRIVATE);
        centerr = sharedPreferences.getString("name", "");


        databaseReferencee = FirebaseDatabase.getInstance().getReference().child(centerr).child("Tests").child(user.getSelectdateforapp()).child(user.getPatientName());
        databaseReferencee.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                testss = dataSnapshot.getValue().toString();
                tests.setText(testss);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        progressDialog.dismiss();
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage(context.getString(R.string.makcall))
                        .setCancelable(false)
                        .setPositiveButton("call", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                    Toast.makeText(context, context.getString(R.string.permissiongrant), Toast.LENGTH_SHORT).show();
                                } else {

                                    if (m.getText().toString().isEmpty()) {
                                        Toast.makeText(context, context.getString(R.string.trycall), Toast.LENGTH_SHORT).show();
                                    }
                                    Intent i = new Intent(Intent.ACTION_CALL);
                                    i.setData(Uri.parse("tel:" + m.getText().toString()));
                                    context.startActivity(i);
                                }
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();

                            }
                        });
                AlertDialog alert = builder.create();
                alert.setTitle("call");
                alert.show();
            }
        });
        return listviewitem;
    }
}
