package com.cs_final.englishdict.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.preference.ListPreference;

import com.takisoft.fix.support.v7.preference.PreferenceFragmentCompat;

import cs_final.com.englishdict.R;

public class SettingsFragment extends PreferenceFragmentCompat
{
    @Override
    public void onCreatePreferencesFix(@Nullable Bundle savedInstanceState, String rootKey)
    {
        setPreferencesFromResource(R.xml.settings_preferences, rootKey);
        ListPreference words = (ListPreference) findPreference("words");
        ListPreference level = (ListPreference) findPreference("level");
        ListPreference category = (ListPreference) findPreference("category");
        ListPreference timer = (ListPreference) findPreference("timer");

        if (words.getValue() == null)
            words.setValueIndex(0);
        if (level.getValue() == null)
            level.setValueIndex(0);
        if (category.getValue() == null)
            category.setValueIndex(4);
        if (timer.getValue() == null)
            timer.setValueIndex(0);
    }
}