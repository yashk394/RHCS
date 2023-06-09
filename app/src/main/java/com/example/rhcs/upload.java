package com.example.rhcs;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class upload extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    Button uploadd;
    EditText name;
    ImageView imageView;
    Uri uri, u;
    boolean isConnected;
    StorageTask storageTask;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    AlertDialog.Builder builder;
    int a = 0, b = 0;
    ProgressDialog progressDialog;
    String center, cen = "select_center", secenter = "select_center";
    Spinner spinner;
    String[] allc = new String[]{"select_center", "satara", "indapur", "baramati", "phaltan", "chiplun", "karad", "belgaum"};
RequestQueue requestQueue;
private String URL="https://fcm.googleapis.com/fcm/send";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        uploadd = findViewById(R.id.buttonup);
        name = findViewById(R.id.name);
        cen = getIntent().getExtras().get("hmanager").toString();
        imageView = findViewById(R.id.imgg);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.waitplz));
        progressDialog.setCancelable(false);
        spinner = findViewById(R.id.spin);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, allc);
        spinner.setAdapter(arrayAdapter);
        builder = new AlertDialog.Builder(this);
        storageReference = FirebaseStorage.getInstance().getReference("uploads");
        databaseReference = FirebaseDatabase.getInstance().getReference("uploads");
        SharedPreferences sharedPreferences = getSharedPreferences("share", MODE_PRIVATE);
        center = sharedPreferences.getString("name", "");
        spinner.setVisibility(View.INVISIBLE);
        requestQueue= Volley.newRequestQueue(this);
        FirebaseMessaging.getInstance().subscribeToTopic("news");
        if (cen.equals("hmanager")) {
            spinner.setVisibility(View.VISIBLE);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    secenter = arrayAdapter.getItem(i);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }
        builder.setMessage(getString(R.string.dosure))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.Yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ConnectivityManager cm =
                                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

                        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                        isConnected = activeNetwork != null &&
                                activeNetwork.isConnectedOrConnecting();
                        if (!isConnected) {
                            Toast.makeText(getApplicationContext(), getString(R.string.plzcon), Toast.LENGTH_SHORT).show();
                        } else {
                            a = 1;
                            if (u != null) {
                                if (cen.equals("hmanager")) {

                                    if (secenter.equals("select_center")) {
                                        Toast.makeText(upload.this, getString(R.string.selcent), Toast.LENGTH_SHORT).show();
                                    } else {
                                        caall();
                                    }

                                } else {
                                    call();
                                }
                            } else {
                                Toast.makeText(upload.this, getString(R.string.selectimg), Toast.LENGTH_SHORT).show();
                            }
                        }


                    }
                })
                .setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        a = 0;

                    }
                });

        uploadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ConnectivityManager cm =
                        (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                isConnected = activeNetwork != null &&
                        activeNetwork.isConnectedOrConnecting();
                if (!isConnected) {
                    Toast.makeText(getApplicationContext(), getString(R.string.plzcon), Toast.LENGTH_SHORT).show();
                } else {
                    if (storageTask != null && storageTask.isInProgress()) {
                        Toast.makeText(upload.this, getString(R.string.progressupload), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    } else {
                        if (name.getText().toString().trim().isEmpty()) {
                            AlertDialog alert = builder.create();
                            alert.setTitle(getString(R.string.makesure));
                            alert.show();
                            progressDialog.dismiss();
                        } else {
                            if (u != null) {
                                a = 1;
                                if (cen.equals("hmanager")) {
                                    if (secenter.equals("select_center")) {
                                        Toast.makeText(upload.this, getString(R.string.selcent), Toast.LENGTH_SHORT).show();
                                    } else {
                                        caall();
                                    }

                                } else {
                                    call();
                                }
                            } else {
                                Toast.makeText(upload.this, getString(R.string.selectimg), Toast.LENGTH_SHORT).show();
                            }

                        }


                    }
                }


            }
        });

    }

    private String getfilextension(Uri urii) {

        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(urii));


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.cam, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.ad) {
            choose();
            b = 2;
            return true;
        }
        if (id == R.id.shoo) {

            Intent i = new Intent(getApplicationContext(), showall.class);
            i.putExtra("man", "manager");
            startActivity(i);


        }
        return super.onOptionsItemSelected(item);
    }


    public void choose() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST) {
            if (resultCode == RESULT_OK) {
                uri = data.getData();
                Picasso.get().load(uri).into(imageView);
                if (uri != null) {
                    a = 0;
                    u = uri;
                } else {
                    a = 0;
                    Toast.makeText(getApplicationContext(), getString(R.string.noselect), Toast.LENGTH_SHORT).show();
                    Toast.makeText(this, getString(R.string.selectimg), Toast.LENGTH_SHORT).show();
                }
            }

        }
    }

    public void call() {
        if (a == 1) {
            progressDialog.show();

            final StorageReference storageReference1 = storageReference.child(System.currentTimeMillis() + "." + getfilextension(uri));
            storageReference1.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    storageReference1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                }
                            }, 400);
                            Toast.makeText(upload.this, getString(R.string.uploadsuccess), Toast.LENGTH_SHORT).show();

                            databaseReference = FirebaseDatabase.getInstance().getReference().child("Upload");
                            String uploadid = databaseReference.push().getKey();
                            String s = "@" + center + " " + name.getText().toString().trim();
                            Uploadimage uploadimage = new Uploadimage(s, String.valueOf(uri));
                            databaseReference.child(uploadid).setValue(uploadimage);
                            progressDialog.dismiss();
                            name.getText().clear();

                        }
                    });

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(upload.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {

                }
            });
        }

    }



    public void caall() {
        if (a == 1) {
            progressDialog.show();

            final StorageReference storageReference1 = storageReference.child(System.currentTimeMillis() + "." + getfilextension(uri));
            storageReference1.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    storageReference1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                }
                            }, 400);
                            Toast.makeText(upload.this, getString(R.string.uploadsuccess), Toast.LENGTH_SHORT).show();

                            databaseReference = FirebaseDatabase.getInstance().getReference().child("Upload");
                            String uploadid = databaseReference.push().getKey();
                            String s = "@" + secenter + " " + name.getText().toString().trim();
                            Uploadimage uploadimage = new Uploadimage(s, String.valueOf(uri));
                            databaseReference.child(uploadid).setValue(uploadimage);
                            progressDialog.dismiss();
                            name.getText().clear();

                        }
                    });

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(upload.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {

                }
            });
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.right, R.anim.up);

    }
}
