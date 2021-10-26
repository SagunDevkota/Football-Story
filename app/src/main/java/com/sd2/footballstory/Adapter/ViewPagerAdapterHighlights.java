package com.sd2.footballstory.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.sd2.footballstory.Fragments.HighlightsPage;

public class ViewPagerAdapterHighlights extends FragmentStateAdapter {
    public ViewPagerAdapterHighlights(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment;
        switch (position){
            case 0:
                fragment = HighlightsPage.newInstance();
                break;
            case 1:
                //fragment = HighlightsPage.newInstance();
                return null;
            default:
                return null;
        }
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
