package com.example.rhcs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.tabs.TabLayout;

public class mixed13 extends AppCompatActivity {
    TabLayout t1;
    ViewPager v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mixed13);
        t1 = findViewById(R.id.tab);
        v = findViewById(R.id.viewpager);
        pageadp p = new pageadp(getSupportFragmentManager(), t1.getTabCount());
        v.setAdapter(p);
        v.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(t1));
        alide();

    }

    private void alide() {
        t1.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                v.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.right, R.anim.up);

    }


}
