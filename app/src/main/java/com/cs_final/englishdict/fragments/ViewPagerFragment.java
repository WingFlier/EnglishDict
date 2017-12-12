package com.cs_final.englishdict.fragments;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cs_final.englishdict.CustomViewPager;
import com.cs_final.englishdict.SwipeDirection;
import com.cs_final.englishdict.ViewPagerSlider;

import java.util.ArrayList;
import java.util.HashMap;

import cs_final.com.englishdict.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ViewPagerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewPagerFragment extends Fragment
{
    public static ArrayList<HashMap<String, String>> words;
    private String timer;

    static CustomViewPager pager;
    PagerAdapter pagerAdapter;

    public ViewPagerFragment()
    {
        // Required empty public constructor
    }

    public static ViewPagerSlider viewPagerSlider = new ViewPagerSlider()
    {
        @Override
        public void slide()
        {
            pager.setCurrentItem(pager.getCurrentItem() + 1, true);
        }
    };

    public static ViewPagerFragment newInstance(String param1, String param2)
    {
        return new ViewPagerFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            words = (ArrayList<HashMap<String, String>>) getArguments().getSerializable("words");
            timer = getArguments().getString("timer");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_view_pager, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        pager = getView().findViewById(R.id.pager);

        pagerAdapter = new MyFragmentPagerAdapter(getChildFragmentManager());
        Log.d("logging_tag", "pages " + pagerAdapter.getCount());
        pager.setAdapter(pagerAdapter);
        pager.setOffscreenPageLimit(words.size());

        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            @Override
            public void onPageSelected(int position)
            {
                Log.d("logging_tag", "onPageSelected, position = " + position);
//                to go back one fragment
//                if (position == 5) getFragmentManager().popBackStack();
            }

            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels)
            {
                Log.d("log", "onPageScrolled, position = " + position);
            }

            @Override
            public void onPageScrollStateChanged(int state)
            {

            }
        });

        pager.setAllowedSwipeDirection(SwipeDirection.right);
        final Handler handler = new Handler();
        if (timer != null)
        {

            final int delay = Integer.parseInt(timer) * 1000;
            handler.postDelayed(new Runnable()
            {
                public void run()
                {
                    pager.setCurrentItem(pager.getCurrentItem() + 1, true);
                    handler.postDelayed(this, delay);
                }
            }, delay);
        }
    }


    private class MyFragmentPagerAdapter extends FragmentPagerAdapter
    {
        private final int PAGE_COUNT = words.size();

        public MyFragmentPagerAdapter(FragmentManager fm)
        {
            super(fm);
        }

        @Override
        public Fragment getItem(int position)
        {
            return PageFragment.newInstance(position);
        }

        @Override
        public int getCount()
        {
            return PAGE_COUNT;
        }

    }

    @Override
    public void onStop()
    {
        super.onStop();
        onDestroy();
    }
}