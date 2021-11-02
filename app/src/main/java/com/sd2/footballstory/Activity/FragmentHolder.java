package com.sd2.footballstory.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.sd2.footballstory.Adapter.ViewPagerFragmentHolder;
import com.sd2.footballstory.R;

public class FragmentHolder extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager2 viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_holder);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPagerFragmentHolder);

        ViewPagerFragmentHolder viewPagerFragmentHolder = new ViewPagerFragmentHolder(getSupportFragmentManager(),getLifecycle());
        viewPager.setAdapter(viewPagerFragmentHolder);

        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager, true, true, (tab, position) -> {
            switch (position){
                case 0:
                    tab.setText("Live");
                    tab.setIcon(R.drawable.live);
                    break;
                case 1:
                    tab.setText("Highlights");
                    tab.setIcon(R.drawable.highlights);
            }
        });
        tabLayoutMediator.attach();
    }
}