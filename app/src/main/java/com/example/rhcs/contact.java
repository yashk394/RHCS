package com.example.rhcs;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class contact extends AppCompatActivity {
    ListView listView;
    adapterr a;
    String s, center1;
    String nm[];
    String mo[];
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        listView = findViewById(R.id.list);
        ActivityCompat.requestPermissions(contact.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
        SharedPreferences sharedPreferences = getSharedPreferences("share", MODE_PRIVATE);
        center1 = sharedPreferences.getString("name", "");
        if (center1.equals("satara")) {
            nm = new String[]{getString(R.string.rahul)};
            mo = new String[]{getString(R.string.monum)};
        } else if (center1.equals("karad")) {
            nm = new String[]{getString(R.string.anna)};
            mo = new String[]{getString(R.string.monum1)};
        } else if (center1.equals("indapur")) {
            nm = new String[]{getString(R.string.shende)};
            mo = new String[]{getString(R.string.monum2)};
        } else if (center1.equals("baramati")) {
            nm = new String[]{getString(R.string.Shrinivas)};
            mo = new String[]{getString(R.string.monum3)};
        } else if (center1.equals("phaltan")) {
            nm = new String[]{getString(R.string.vasant)};
            mo = new String[]{getString(R.string.monum4)};
        } else if (center1.equals("belgaum")) {
            nm = new String[]{getString(R.string.vikas)};
            mo = new String[]{getString(R.string.monum5)};
        } else if (center1.equals("chiplun")) {
            nm = new String[]{getString(R.string.akash)};
            mo = new String[]{getString(R.string.monum6)};
        }

        builder = new AlertDialog.Builder(this);
        a = new adapterr(this, nm, mo);
        listView.setAdapter(a);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (checkWriteExternalPermission())
                {
                    AlertDialog alert = builder.create();
                    alert.setTitle("call");
                    alert.show();
                }
                else
                {
                    Toast.makeText(contact.this, "please grant the permission", Toast.LENGTH_SHORT).show();
                    ActivityCompat.requestPermissions(contact.this,new String[]{Manifest.permission.CALL_PHONE},1);
                }


            }
        });

        builder.setMessage(getString(R.string.sure) + nm[0])
                .setCancelable(false)
                .setPositiveButton(getString(R.string.call), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        String telno = mo[0];
                        Intent i = new Intent(Intent.ACTION_CALL);
                        i.setData(Uri.parse("tel:" + telno));
                        startActivity(i);

                    }
                })
                .setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();

                    }
                });


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.right, R.anim.up);


    }
    private boolean checkWriteExternalPermission()
    {
        String permission = Manifest.permission.CALL_PHONE;
        int res = getApplicationContext().checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }
}
