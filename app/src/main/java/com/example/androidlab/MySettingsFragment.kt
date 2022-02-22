package com.example.androidlab

import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.preference.EditTextPreference
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat

// 설정 화면용 Fragment. 공유된 프리퍼런스를 이용한 이벤트 처리를 위해 인터페이스 구현
class MySettingsFragment : PreferenceFragmentCompat(), 
SharedPreferences.OnSharedPreferenceChangeListener {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        // 설정 화면 xml 파일 지정
        setPreferencesFromResource(R.xml.settings, rootKey)

        // 코드에서 설정 항목 제어
        // SummaryProvider 하위클래스 객체 (익명 클래스)
        val idPreference: EditTextPreference? = findPreference("id")
        idPreference?.summaryProvider =
            Preference.SummaryProvider<EditTextPreference> {
                val text = it.text
                if (TextUtils.isEmpty(text)) {
                    "설정이 되지 않았습니다."
                } else {
                    "설정된 값은 $text 입니다."
                }
            }

        // 설정 항목 클릭 이벤트 핸들러
        idPreference?.setOnPreferenceClickListener {
            true
        }

        /*
        idPreference?.setOnPreferenceChangeListener { preference, newValue ->
            Log.d("kkang", "preference key : ${preference.key}, newValue : $newValue")
            true
        }

         */

        // SimpleSummaryProvider 객체
        val colorPreference: ListPreference? = findPreference("color")
        colorPreference?.summaryProvider = ListPreference.SimpleSummaryProvider.getInstance()
    }

    // 공유된 프리퍼런스 변경 이벤트 핸들러
    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        // 전체 SharedPreference 를 대상으로 하기 때문에 key 구분 필요
        if (key == "id") {
            Log.i("kkang", "newValue : " + sharedPreferences?.getString("id", ""))
        }
    }

    override fun onResume() {
        super.onResume()
        preferenceManager.sharedPreferences?.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceManager.sharedPreferences?.unregisterOnSharedPreferenceChangeListener(this)
    }
}