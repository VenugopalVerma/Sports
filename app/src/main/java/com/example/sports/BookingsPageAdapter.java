package com.example.sports;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class BookingsPageAdapter extends FragmentStateAdapter {
    public BookingsPageAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new UpcomingFragment();
            default:
                return new PastFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
