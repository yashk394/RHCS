package com.example.rhcs;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileObserver;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

public class reportgeneration extends AppCompatActivity {
    TextView e1;
    Button button,b2;
    DatabaseReference databaseReference,db1,db2,db3;
    String center;
    String timeStampTime;
    ListView listView;
    ArrayList<String> list=new ArrayList<>();
    ArrayList<String> pname=new ArrayList<>();
    ArrayList<String> date1=new ArrayList<>();
    ArrayList<String> test=new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    ProgressDialog progressDialog;
    final Calendar calendar=Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportgeneration);
        button = findViewById(R.id.generate);
        b2 = findViewById(R.id.save);
        e1 = findViewById(R.id.date1);
        listView=findViewById(R.id.lissst);
        arrayAdapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,list);
        b2.setEnabled(false);
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Fetching");
        progressDialog.setCancelable(false);

        SharedPreferences sharedPreferences = getSharedPreferences("share", MODE_PRIVATE);
        center = sharedPreferences.getString("name", "");
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
               new DatePickerDialog(reportgeneration.this,date,calendar.get(Calendar.YEAR),
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
                db1 = FirebaseDatabase.getInstance().getReference().child(center).child("SaveReport").child(e1.getText().toString().trim()).child("Date");
                db1.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        String msg,con;
                        Iterator i=dataSnapshot.getChildren().iterator();
                        while (i.hasNext())
                        {
                            msg=(String)((DataSnapshot) i.next()).getValue();
                            con=msg;
                            date1.add(con);
                        }
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        String msg,con;
                        Iterator i=dataSnapshot.getChildren().iterator();
                        while (i.hasNext())
                        {
                            msg=(String)((DataSnapshot) i.next()).getValue();
                            con=msg;
                            date1.add(con);
                        }
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
                db2 = FirebaseDatabase.getInstance().getReference().child(center).child("SaveReport").child(e1.getText().toString().trim()).child("Pname");
                db2.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        String msg,con;
                        Iterator i=dataSnapshot.getChildren().iterator();
                        while (i.hasNext())
                        {
                            msg=(String)((DataSnapshot) i.next()).getValue();
                            con=msg;
                            pname.add(con);
                        }
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        String msg,con;
                        Iterator i=dataSnapshot.getChildren().iterator();
                        while (i.hasNext())
                        {
                            msg=(String)((DataSnapshot) i.next()).getValue();
                            con=msg;
                            pname.add(con);
                        }
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
                db3 = FirebaseDatabase.getInstance().getReference().child(center).child("SaveReport").child(e1.getText().toString().trim()).child("Test");
                db3.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        String msg,con;
                        Iterator i=dataSnapshot.getChildren().iterator();
                        while (i.hasNext())
                        {
                            msg=(String)((DataSnapshot) i.next()).getValue();
                            con=msg;
                            test.add(con);
                        }
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
                            test.add(con);
                        }
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
                b2.setEnabled(true);



            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions((Activity) reportgeneration.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

                    return;
                }

                Workbook wb=new HSSFWorkbook();
                Sheet sheet=wb.createSheet("name of sheet");
                PrintSetup ps = sheet.getPrintSetup();
                sheet.setAutobreaks(true);

                ps.setFitHeight((short) 1);
                ps.setFitWidth((short) 1);
                Cell cell=null;
                CellStyle cellStyle=wb.createCellStyle();
                cellStyle.setFillBackgroundColor(HSSFColor.LIGHT_BLUE.index);
                cellStyle.setWrapText(true);
                cellStyle.setAlignment(HorizontalAlignment.CENTER);
                cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);


                Row row1=sheet.createRow(0);
                cell=row1.createCell(0);
                cell.setCellValue("Name");
                cell.setCellStyle(cellStyle);
                cell=row1.createCell(1);
                cell.setCellValue("Date");
                cell.setCellStyle(cellStyle);
                cell=row1.createCell(2);
                cell.setCellValue("Test");
                cell.setCellStyle(cellStyle);
                for (int i=0;i<list.size();i++)
                {
                    String name=pname.get(i);
                    String dates=date1.get(i);
                    String tests=test.get(i);
                    i=i+1;
                    Row row=sheet.createRow(i);
                    cell=row.createCell(0);
                    cell.setCellValue(name);
                    cell.setCellStyle(cellStyle);
                    sheet.setColumnWidth(0,(20*200));
                    cell=row.createCell(1);
                    cell.setCellValue(dates);
                    cell.setCellStyle(cellStyle);
                    sheet.setColumnWidth(0,(20*200));
                    cell=row.createCell(2);
                    cell.setCellValue(tests);
                    cell.setCellStyle(cellStyle);
                    sheet.setColumnWidth(0,(20*200));
                    i=i-1;
                }

                 timeStampTime = new SimpleDateFormat("HHmmss").format(new Date());
                String name = "patient" + timeStampTime + "History";
                String fileName = name + ".xlsx";
                String extStorageDirectory = Environment.getExternalStorageDirectory()
                        .toString();
                File folder = new File(extStorageDirectory, "demo1");
                folder.mkdir();
                File file = new File(folder, fileName);
                try {
                    file.createNewFile(); // creating the file inside the folder
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                try {
                    FileOutputStream fileOut = new FileOutputStream(file); //Opening the file
                    wb.write(fileOut); //Writing all your row column inside the file
                    fileOut.close(); //closing the file and done
                    Toast.makeText(getApplicationContext(), "Excel file saved in your device....", Toast.LENGTH_SHORT).show();
                } catch (FileNotFoundException e) {
                    Toast.makeText(reportgeneration.this, "er", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                } catch (IOException e) {
                    Toast.makeText(reportgeneration.this, "ioer", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
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
