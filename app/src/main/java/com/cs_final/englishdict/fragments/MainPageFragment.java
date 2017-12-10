package com.cs_final.englishdict.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.cs_final.englishdict.sample.ViewPagerFragment;

import cs_final.com.englishdict.R;

public class MainPageFragment extends Fragment
{
    String data = "";
    FragmentActivity activity;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MainPageFragment()
    {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainPageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainPageFragment newInstance(String param1, String param2)
    {
        MainPageFragment fragment = new MainPageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_page, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        /*ViewPagerFragment fragment = new ViewPagerFragment();
        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_frame, fragment, null)
                .addToBackStack(null)
                .commit();*/
        activity = getActivity();

        ((BottomNavigationView) activity
                .findViewById(R.id.bottom_navigation)).setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener()
                {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item)
                    {
                        activity.findViewById(R.id.txtCool).setVisibility(View.GONE);
                        activity.findViewById(R.id.inner_main_page).setVisibility(View.VISIBLE);
                        Fragment fragment = null;
                        switch (item.getItemId())
                        {
                            case R.id.action_progress:
                                fragment = ViewPagerFragment.newInstance(null, null);
                                break;
                            case R.id.action_learn:
                                fragment = LearnFragment.newInstance(null, null);
                                break;
                            case R.id.action_settings:
                                fragment = new SettingsFragment();
                                break;
                        }
                        if (fragment != null)
                            getFragmentManager().beginTransaction()
                                    .replace(R.id.inner_main_page, fragment, null)
                                    .commit();
                        return true;
                    }
                });
    }


    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
    }
}