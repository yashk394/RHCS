package com.example.rhcs;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class UserAppointmentlist extends ArrayAdapter<addAppointment> {
    private Activity context;
    List<addAppointment> userAppIds;
    DatabaseReference databaseReference, databaseReference1,data;
    String date, puser, center1;
    CardView cardView;

    public UserAppointmentlist(Activity context, List<addAppointment> userAppIds, String date, String puser, String center1) {
        super(context, R.layout.upcoming3, userAppIds);
        this.context = context;
        this.userAppIds = userAppIds;
        this.date = date;
        this.puser = puser;
        this.center1 = center1;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listviewitem = inflater.inflate(R.layout.upcoming3, null, true);

        final TextView textViewName = (TextView) listviewitem.findViewById(R.id.name1);
        final TextView date = (TextView) listviewitem.findViewById(R.id.type1);
        Button button = listviewitem.findViewById(R.id.delet);
cardView=listviewitem.findViewById(R.id.car);

        addAppointment user = userAppIds.get(position);

        textViewName.setText(user.getPatientName());
        date.setText(user.getSelectdateforapp());

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAppointment user1 = userAppIds.get(position);
                showDelete(user1.getId(), user1.getPatientName());
            }
        });

cardView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

    }
});
        return listviewitem;
    }

    private void showDelete(final String id, final String name) {
        android.app.AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getString(R.string.dd));
        builder.setMessage(context.getString(R.string.aresure));
        builder.setCancelable(false);
        builder.setPositiveButton(context.getString(R.string.Yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteapp(id, name);
                Intent i = new Intent(context, patientappointment.class);
                i.putExtra("selected_topic", puser);
                context.startActivity(i);
            }
        });
        builder.setNegativeButton(context.getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    private boolean deleteapp(String id, String name) {
        //getting the specified artist reference


        //getting the tracks reference for the specified artist
        databaseReference = FirebaseDatabase.getInstance().getReference().child(center1).child("ShowApp").child(date).child(puser).child(id);
        databaseReference1 = FirebaseDatabase.getInstance().getReference().child(center1).child("AddAppointment").child(date).child(name).child(id);

        //removing all tracks
        databaseReference.removeValue();
        databaseReference1.removeValue();
        Toast.makeText(context, context.getString(R.string.appdel), Toast.LENGTH_LONG).show();

        return true;
    }
}
