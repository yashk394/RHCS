package com.example.rhcs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class showall extends AppCompatActivity {
    RecyclerView recyclerView;
    Imageadapter imageadapter;
    DatabaseReference databaseReference;
    List<Uploadimage> muploads;
    ProgressDialog progressDialog;
    boolean isConnected;
    RelativeLayout re;
    TextView textView;
    FirebaseStorage firebaseStorage;
    String mn;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showall);
        recyclerView = findViewById(R.id.recycle);
        recyclerView.setHasFixedSize(true);
        progressDialog = new ProgressDialog(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        muploads = new ArrayList<>();
        mn = getIntent().getExtras().get("man").toString();
        databaseReference = FirebaseDatabase.getInstance().getReference("Upload");
        progressDialog.setMessage(getString(R.string.event));
        progressDialog.show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
            }
        }, 4000);
        textView = findViewById(R.id.texttt);
        textView.setVisibility(View.INVISIBLE);
        re = findViewById(R.id.relate);
        firebaseStorage = FirebaseStorage.getInstance();
        imageadapter = new Imageadapter(getApplicationContext(), muploads);
        if (imageadapter.getItemCount() == 0) {
            textView.setText(getString(R.string.notfoun));
            textView.setVisibility(View.VISIBLE);
        }
        recyclerView.setAdapter(imageadapter);
        if (mn.equals("manager")) {
            sharedPreferences = getSharedPreferences("sh", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("mann", "manager");
            editor.apply();
            imageadapter.setOnItemCLickListener(new Imageadapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {

                }

                @Override
                public void onDeleteClick(int position) {
                    Uploadimage selctitem = muploads.get(position);
                    final String s = selctitem.getkey();
                    StorageReference ss = firebaseStorage.getReferenceFromUrl(selctitem.getImgurl());
                    ss.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            databaseReference.child(s).removeValue();
                            Toast.makeText(showall.this, getString(R.string.eventdel), Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(showall.this, getString(R.string.failevent), Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            });
        }
        progressDialog.dismiss();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                muploads.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Uploadimage uploadimage = dataSnapshot1.getValue(Uploadimage.class);
                    uploadimage.setkay(dataSnapshot1.getKey());

                    muploads.add(uploadimage);


                }

                imageadapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(showall.this, "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if (!isConnected) {
            Snackbar s = Snackbar.make(re, getString(R.string.yff), Snackbar.LENGTH_SHORT);
            progressDialog.dismiss();
            textView.setVisibility(View.VISIBLE);
            s.show();
            Toast.makeText(this, getString(R.string.coninternet), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onStop() {
        SharedPreferences sh = getSharedPreferences("sh", MODE_PRIVATE);
        SharedPreferences.Editor editor = sh.edit();
        editor.putString("mann", "managerxs");
        editor.apply();
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        SharedPreferences sh = getSharedPreferences("sh", MODE_PRIVATE);
        SharedPreferences.Editor editor = sh.edit();
        editor.putString("mann", "managerxs");
        editor.apply();
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.right, R.anim.up);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences sh = getSharedPreferences("sh", MODE_PRIVATE);
        SharedPreferences.Editor editor = sh.edit();
        editor.putString("mann", "managerxs");
        editor.apply();
    }
}
