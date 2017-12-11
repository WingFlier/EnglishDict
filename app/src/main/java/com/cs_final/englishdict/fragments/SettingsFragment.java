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
        ((ListPreference) findPreference("words")).setValueIndex(0);
        ((ListPreference) findPreference("level")).setValueIndex(0);
        ((ListPreference) findPreference("category")).setValueIndex(4);
        ((ListPreference) findPreference("timer")).setValueIndex(0);
    }
}