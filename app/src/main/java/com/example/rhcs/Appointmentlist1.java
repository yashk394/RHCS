package com.example.rhcs;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class Appointmentlist1 extends ArrayAdapter<addAppointment> {
    private Activity context;
    List<addAppointment> userAppIds;
    Button imageButton;
    DatabaseReference databaseReferencee, db;
    String centerr, uu, testss;
    ProgressDialog progressDialog;

    public Appointmentlist1(Activity context, List<addAppointment> userAppIds) {
        super(context, R.layout.patientdetails, userAppIds);
        this.context = context;
        this.userAppIds = userAppIds;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listviewitem = inflater.inflate(R.layout.patientdetails1, null, true);

        final TextView textViewName = (TextView) listviewitem.findViewById(R.id.pnamee);
        TextView weight = (TextView) listviewitem.findViewById(R.id.ewightt);
        TextView age = (TextView) listviewitem.findViewById(R.id.agee);
        TextView gender = (TextView) listviewitem.findViewById(R.id.genderr);
        TextView refd = (TextView) listviewitem.findViewById(R.id.refbydocc);
        TextView cd = (TextView) listviewitem.findViewById(R.id.cdetaill);
        TextView h = (TextView) listviewitem.findViewById(R.id.hiss);
        TextView e = (TextView) listviewitem.findViewById(R.id.emaill);
        final TextView m = (TextView) listviewitem.findViewById(R.id.mobileee);
        TextView test = (TextView) listviewitem.findViewById(R.id.mobileee);
        imageButton = listviewitem.findViewById(R.id.calll);
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("fetching details..!");
        progressDialog.show();
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
