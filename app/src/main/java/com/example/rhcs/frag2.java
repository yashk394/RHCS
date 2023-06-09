package com.example.rhcs;


import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Layout;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;


public class frag2 extends Fragment {
    TextView t, txt;
    private int[] slider = new int[]{
            R.drawable.tesla4, R.drawable.tesla5
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_frag2, container, false);
        t = (TextView) view.findViewById(R.id.text2);
        txt = view.findViewById(R.id.txt);
        txt.setPaintFlags(txt.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            t.setJustificationMode(Layout.JUSTIFICATION_MODE_INTER_WORD);
        }
        CarouselView carouselview = view.findViewById(R.id.machine);
        carouselview.setPageCount(slider.length);
        carouselview.setImageListener(new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                imageView.setImageResource(slider[position]);
            }
        });
        txt.setMovementMethod(LinkMovementMethod.getInstance());
        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browse = new Intent(Intent.ACTION_VIEW);
                browse.setData(Uri.parse("https://www.scanmed.com/single-post/2017/04/27/15T-versus-3T-MRI"));
                startActivity(browse);
            }
        });
        return view;
    }
}
