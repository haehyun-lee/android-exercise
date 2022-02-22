# 개선된 할 일 목록 앱 만들기
## Overview
* 사용자가 입력한 데이터를 내부 데이터베이스에 저장하기
  * SQLiteDatabase, Cursor 객체
    * exeSQL(), rawQuery() 메서드
    * insert(), update(), deleter(), query() 메서드
  * SQLiteOpenHelper 클래스
* Preference를 활용한 앱 설정 기능
  * SharedPreference
  * Preference에 데이터 저장 및 가져오기
  * 설정 XML 파일 작성
    * PreferenceScreen, Preference, SwitchPreferenceCompat, EditTextPreference, ListPreference
  * 설정 XML 파일 적용
    * PreferenceFragmentCompat
  * 코드에서 Preference 설정 제어, 이벤트 처리

## Screenshots
|할 일 목록|할 일 추가|설정|
|:-:|:-:|:-:|
|<img src="https://user-images.githubusercontent.com/86085387/155158151-46a85ea2-5292-4ae4-b229-4b0005e954e6.png" width="300" />|<img src="https://user-images.githubusercontent.com/86085387/155162229-6adc4f8b-90bc-4e01-a844-80ee8287bb76.png" width="300" />|<img src="https://user-images.githubusercontent.com/86085387/155162218-d2f24d1f-f479-491c-a089-b63bc904c11a.png" width="300" /> |

<img src="https://user-images.githubusercontent.com/86085387/155162214-a6be21e7-ab1b-4cd2-993b-451ea1c9b662.gif" width="350" />

