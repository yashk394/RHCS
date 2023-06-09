package com.example.rhcs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class aboutus extends AppCompatActivity {
CardView c1,c2,c3,c4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);
        c1=findViewById(R.id.card1);
        c2=findViewById(R.id.card2);
        c3=findViewById(R.id.card3);
        c4=findViewById(R.id.card4);

        c1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent cl=new Intent(Intent.ACTION_DIAL);
                cl.setData(Uri.parse("tel:"+"8788282089"));
                startActivity(cl);

                return true;
            }
        });
        c2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent cl=new Intent(Intent.ACTION_DIAL);
                cl.setData(Uri.parse("tel:"+"9588426121"));
                startActivity(cl);

                return true;
            }
        });

        c3.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent cl=new Intent(Intent.ACTION_DIAL);
                cl.setData(Uri.parse("tel:"+"9527155129"));
                startActivity(cl);

                return true;
            }
        });

        c4.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent cl=new Intent(Intent.ACTION_DIAL);
                cl.setData(Uri.parse("tel:"+"7972884471"));
                startActivity(cl);

                return true;
            }
        });
    }
}
