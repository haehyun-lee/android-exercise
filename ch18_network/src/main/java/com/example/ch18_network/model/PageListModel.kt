package com.example.ch18_network.model

/**
 * 모델 클래스
 * Retrofit으로 서버의 데이터를 저장하는 클래스
 * ItemModel로 표현된 기사가 여러 건 담긴 MutableList객체 포함
 */
class PageListModel {
    var articles: MutableList<ItemModel>? = null
}