# 할 일 목록 앱 만들기
* 할 일 목록과 등록 화면을 Activity로 구성
* Intent로 화면을 전환
* Activity 간의 데이터 전달
* Menu 이벤트 핸들러
* RecyclerView 항목 동적 추가
* 화면 전화 시 데이터가 사라지지 않도록 Bundle에 데이터 저장 및 가져오기


## Screenshots
![Ch13_Activity](https://user-images.githubusercontent.com/86085387/150302746-87c443f3-3cef-4dce-969e-c10878b1f3f3.gif)


## Layout Structure
![ch13_activity](https://user-images.githubusercontent.com/86085387/150302762-09188462-3e53-4646-a3aa-098dccac1e29.jpg)


## Study
* Activity
* Activity life cycle
* Bundle
  * savedInstanceState
  * outState
* Intent
  * startActivity(), startActivityForResult(), finish(), requestCode, resultCode
  * Intent filter
  * Explicit intent, Implicit intent
* Package visibility
* Extra data
  * putExtra()
  * getExtra()
* InputMethodManager
* android:screenOrientation
* Task, android:launchMode
  * standard
  * singleTop
  * singleTask
  * singleInstance


## Add more
본 예제에서 배운 기능들을 활용하여 좀 더 제대로 된 Todo 리스트 앱으로 개선할 예정
* Todo 삭제
* Todo 수정
* Todo 아이콘 선택
* Todo 순서 변경
* Todo 완료 여부 체크박스 + 취소줄 처리
* 배경화면에 Todo 위젯 추가
* Todo 마감일 날짜 설정
