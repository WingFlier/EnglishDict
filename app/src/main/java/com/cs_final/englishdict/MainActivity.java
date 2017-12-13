package com.cs_final.englishdict;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.cs_final.englishdict.fragments.ChartFragment;
import com.cs_final.englishdict.fragments.QuizFragment;
import com.cs_final.englishdict.fragments.SettingsFragment;

import cs_final.com.englishdict.R;

public class MainActivity extends AppCompatActivity
{
    public static final String APP_PREFERENCES = "app_pref";

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.taskBar));

        BottomNavigationView navView =
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
                                fragment = ChartFragment.newInstance();
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

    @Override
    public void onBackPressed()
    {
        getFragmentManager().popBackStack();
    }
}