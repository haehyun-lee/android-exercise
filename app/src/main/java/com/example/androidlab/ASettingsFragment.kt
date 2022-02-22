package com.example.androidlab

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat

class ASettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.setting_a, rootKey)
    }
}