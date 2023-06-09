package com.example.rhcs;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;


public class frag1 extends Fragment {
    TextView textView;
    private int[] slider = new int[]{
            R.drawable.teslamachine, R.drawable.tesla1
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_frag1, container, false);
        textView = view.findViewById(R.id.txtt);
        CarouselView carouselview = view.findViewById(R.id.machine1);
        carouselview.setPageCount(slider.length);
        carouselview.setImageListener(new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                imageView.setImageResource(slider[position]);
            }
        });
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browse = new Intent(Intent.ACTION_VIEW);
                browse.setData(Uri.parse("https://www.siemens-healthineers.com"));
                startActivity(browse);
            }
        });
        return view;

    }

}
