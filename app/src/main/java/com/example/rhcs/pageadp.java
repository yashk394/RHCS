package com.example.rhcs;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class pageadp extends FragmentStatePagerAdapter {
    int count;


    public pageadp(FragmentManager supportFragmentManager, int count) {
        super(supportFragmentManager);
        this.count = count;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                frag1 f1 = new frag1();
                return f1;
            case 1:
                frag2 f2 = new frag2();
                return f2;

            case 2:
                frag3 f3 = new frag3();
                return f3;
            default:
                return null;

        }
    }

    @Override
    public int getCount() {
        return count;
    }
}
