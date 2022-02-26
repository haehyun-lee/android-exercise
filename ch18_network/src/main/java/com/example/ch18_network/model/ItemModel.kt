package com.example.ch18_network.model

/**
 * 모델 클래스
 * PageListModel 모델의 구성요소
 * 서버에서 넘어오는 기사 내용 저장
 */
class ItemModel {
    var id: Long = 0
    var author: String? = null
    var title: String? = null
    var description: String? = null
    var urlToImage: String? = null      // 이미지를 가져올 서버 경로
    var publishedAt: String? = null
}