package com.cs_final.englishdict;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.cs_final.englishdict.fragments.QuizFragment;
import com.cs_final.englishdict.fragments.SettingsFragment;

import cs_final.com.englishdict.R;

public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);
        navView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener()
                {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item)
                    {
                        Fragment fragment = null;
                        switch (item.getItemId())
                        {
                            case R.id.action_progress:
                                //fragment = ViewPagerFragment.newInstance(null, null);
                                Toast.makeText(MainActivity.this, "1", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.action_learn:
                                fragment = QuizFragment.newInstance(null, null);
                                break;
                            case R.id.action_settings:
                                fragment = new SettingsFragment();
                                break;
                        }
                        if (fragment != null)
                            moveToFragment(fragment);
                        return true;
                    }
                });
        navView.setSelectedItemId(R.id.action_learn);
    }

    public void moveToFragment(Fragment fragment)
    {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_frame, fragment, null)
                .commit();
    }
}