# 뉴스 앱
## Overview
* 네트워크 프로그래밍 구현
* 서버에서 데이터를 가져와 화면에 출력하기
* API 사용법 (뉴스 데이터)
* HTTP 통신 라이브러리 활용하기
  * Volley
  * Retrofit
* 이미지 처리 라이브러리 활용하기
  * Glide
* JSON 데이터를 객체 타입으로 파싱하기
* 모델 클래스(VO 클래스)

## Screenshots
Volley와 Retrofit 라이브러리의 차이를 확인하고자 두 가지 방식으로 서버와 연동하고, 서버에서 받은 데이터를 Adapter를 활용해 RecyclerView에 출력한다.
* VOLLEY : Volley를 이용해 서버와 연동  
* RETROFIT : Retrofit을 이용해 서버와 연동  

![ch18_network](https://user-images.githubusercontent.com/86085387/155841127-a98206ae-be28-4690-aed2-155adab01e56.gif)


## Layout
![ch18](https://user-images.githubusercontent.com/86085387/155844836-fface18e-4a88-490c-970f-fd62b365afb7.jpg)

## Process
Volley 라이브러리 통신
(관련 파일 : MainActivity, MyAdapter, MyApplication, VolleyFragment, ItemModel)
* VolleyFragment 생성
* Fragment.onCreateView() 함수 실행
* 서버에 요청을 보내는 역할의 RequestQueue 객체 생성
* 서버 URL과 결과를 가져오는 콜백 등 다양한 정보를 JsonObjectRequest 객체에 담기
  * HTTP 메서드 설정 (GET, POST)
  * 서버 URL 지정. 경로에 ?를 이용해 서버에 전달한 데이터를 지정 가능.
  * 서버로부터 결과를 받을 때 호출할 콜백 Listener 정의
  * 서버 연동에 실패할 때 호출할 콜백 ErrorListener 정의
* RequestQueue 객체로 서버에 데이터 요청 (JsonObjectRequest 객체 전달)
* 서버 연동 성공 시 JsonObjectRequest 객체에 정의된 콜백 실행
  * 서버로부터 받은 JSON 데이터를 모델 클래스 타입 객체로 파싱
  * 파싱된 객체를 리스트에 저장
  * 리스트를 RecyclerView Adapter 매개변수(datas)로 전달
  * 리스트에 담긴 객체를 하나씩 꺼내서 RecyclerView의 각 요소로 출력
* RecyclerView 뷰 객체 반환, 화면 출력

Retrofit 라이브러리 통신
(관련 파일 : MainActivity, MyAdapter, MyApplication, RetrofitFragment, NetworkService, ItemModel, PageListModel)
* RetrofitFragment 생성
* Fragment.onCreateView() 함수 실행
* Retrofit 객체 생성
  * baseUrl, Converter 지정
* Retrofit 객체를 이용해 서비스 인터페이스를 구현한 클래스의 객체(=서비스 객체) 얻기
* 서비스 객체에서 인터페이스 함수를 호출하여 Call 객체 얻기
  * URL 뒤에 전달할 데이터를 함수 매개변수로 넘김
* Call 객체의 enqueue() 함수를 호출하여 네트워크 통신 수행
  * 데이터를 담을 모델 클래스 정보를 지정
  * 통신 성공 콜백 함수 onResponse() 정의
  * 통신 실패 콜백 함수 onFailure() 정의
* 서버 연동 성공 
* Call 객체 내 선언된 콜백 함수의 매개변수로 모델클래스 객체(JSON 데이터가 파싱된 결과) 받기
  * 모델클래스 객체를 RecyclerView Adapter 매개변수(datas)로 전달
  * 리스트에 담긴 객체를 하나씩 꺼내서 RecyclerView의 각 요소로 출력
* RecyclerView 뷰 객체 반환, 화면 출력

RecyclerView가 datas 매개변수로 받는 값은 Volley, Retrofit 두 방법에 상관없이 무조건 MutableList<ItemModel> 타입이다.
 
Volley를 사용한 방법에서는 서버로부터 JSONObject 를 받고, 여기서 JsonArray 배열을 추출하고, 배열에서 다시 문자열 등을 추출한 뒤 해당 값을 멤버 필드로 가지는 ItemModel 객체를 하나씩 만들어준 뒤 MutableList<ItemModel> 리스트에 추가해서 전달했다.
 
반면, Retrofit을 사용한 방법에서는 서버로부터 Response<PageListModel>을 받고, 이 객체의 body() 함수를 호출해 PageListModel 클래스의 멤버 필드인 MutableList<ItemModel> 타입의 값을 바로 전달했다.
원래는 JSON 데이터를 코드에서 직접 파싱해서 이용해야 하는데, 데이터를 담을 모델 클래스를 선언하고 클래스 정보를 알려주는 것만으로 Retrofit에서는 모델 클래스의 객체를 알아서 생성하고 그 객체에 데이터를 담아주는 것이다.
