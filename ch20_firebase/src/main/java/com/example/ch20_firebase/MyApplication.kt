package com.example.ch20_firebase

import androidx.multidex.MultiDexApplication
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage

// 앱 전역에서 이용할 객체나 함수
class MyApplication: MultiDexApplication() {
    companion object {
        lateinit var auth: FirebaseAuth     // Firebase 인증
        
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

        lateinit var db: FirebaseFirestore      // Firebase 파이어스토어
        lateinit var storage: FirebaseStorage   // Firebase 스토리지
    }

    override fun onCreate() {
        super.onCreate()
        // FirebaseAuth 객체 얻기
        auth = Firebase.auth

        // FirebaseFireStore, FirebaseStorage 객체 얻기
        db = FirebaseFirestore.getInstance()
        storage = Firebase.storage
    }
}