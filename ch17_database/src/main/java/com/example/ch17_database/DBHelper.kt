package com.example.ch17_database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, "testdb", null, 1) {
    // 앱이 설치된 후 SQLiteOpenHelper 클래스가 이용되는 순간 한 번 호출
    override fun onCreate(db: SQLiteDatabase?) {
        // 데이터베이스 테이블 생성
        db?.execSQL("create table TODO_DB (" +
                "_id integer primary key autoincrement, " +
                "todo not null)"
        )
    }

    // DB 버전이 변경될 때 마다 호출
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

}