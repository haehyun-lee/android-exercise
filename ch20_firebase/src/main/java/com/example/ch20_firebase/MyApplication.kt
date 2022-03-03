package com.example.ch20_firebase

import androidx.multidex.MultiDexApplication
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

// 앱 전역에서 이용할 객체나 함수
class MyApplication: MultiDexApplication() {
    companion object {
        lateinit var auth: FirebaseAuth     // Firebase 인증 객체
        var email: String? = null           // 이메일
        // 인증 상태 체크
        fun checkAuth(): Boolean {
            val currentUser = auth.currentUser
            return currentUser?.let {
                email = currentUser.email
                if (currentUser.isEmailVerified) {
                    true
                } else {
                    false
                }
            } ?: let {
                false
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        // FirebaseAuth 객체 얻기
        auth = Firebase.auth
    }
}