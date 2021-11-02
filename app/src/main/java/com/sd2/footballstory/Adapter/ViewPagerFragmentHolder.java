package com.sd2.footballstory.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.sd2.footballstory.Fragments.HighlightsPage;
import com.sd2.footballstory.Fragments.ListMatch;

public class ViewPagerFragmentHolder extends FragmentStateAdapter {


    public ViewPagerFragmentHolder(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment;
        switch (position){
            case 0:
                fragment = ListMatch.newInstance();
                break;
            case 1:
                fragment = HighlightsPage.newInstance();
                break;
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
