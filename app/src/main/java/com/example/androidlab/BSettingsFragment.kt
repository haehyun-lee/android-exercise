package com.example.androidlab

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat

class BSettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.setting_b, rootKey)
    }
}