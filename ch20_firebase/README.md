# 이미지 공유 앱
## 파이어베이스(Firebase)
모바일과 웹 애플리케이션을 개발하는 플랫폼이다. 모바일 앱은 사용자의 기기에서 실행되는 프런트엔드 애플리케이션으로, 서버에 있는 백엔드 애플리케이션과 통신하여 데이터를 주고받는 구조로 개발한다. 
이때 파이어베이스를 이용하면 서버를 별도로 구축하지 않고 서버리스 컴퓨팅을 구현할 수 있다.
아래는 이번 앱에서 사용된 파이어베이스의 핵심 기능들이다.

* Authentication : 인증, 회원가입 및 로그인 처리
* Cloud Firestore : 앱 데이터 저장 및 동기화
* Cloud Storage : 파일 저장소
* Cloud Messaaging : 알림 전송

<br/>

## 1. 파이어베이스 인증 기능  
파이어베이스 인증 기능으로 앱에서 회원가입하고 로그인을 할 수 있도록 하며, 로그인 여부에 따라 앱 사용권한(읽기/쓰기)을 부여한다.
|메인|인증|회원가입|로그인|
|:-:|:-:|:-:|:-:|
|<img src="https://user-images.githubusercontent.com/86085387/157418241-c91a2b21-42c8-4216-96eb-50d573567739.png" width="300"/>|<img src="https://user-images.githubusercontent.com/86085387/157418251-1e67a609-5ad8-4a36-8435-f5cd03afb2f3.png" width="300"/>|<img src="https://user-images.githubusercontent.com/86085387/157418023-65fbca7a-4ea8-4eda-9466-d913cd9c3fbb.png" width="300"/>|<img src="https://user-images.githubusercontent.com/86085387/157418149-478fb89e-918a-47e4-ae83-79669f9879b1.png" width="300"/>
* Firebase Authentication
* 회원가입
  * 이메일/비밀번호 인증
  * 구글 인증
* 로그인
* 로그아웃

<br/>

## 2. 파이어베이스 파이어스토어, 스토리지 기능
파이어베이스의 파이어스토어와 스토리지를 이용해 이미지를 공유하는 앱. 사용자가 애에서 이미지와 글을 입력하면 파이어베이스에 저장해서 다른 사용자도 볼 수 있도록 한다.
|게시글 작성|게시글 목록|
|:-:|:-:|
|<img src="https://user-images.githubusercontent.com/86085387/157418609-cf031b95-a67f-4840-90af-674f75117fa6.png" width="300"/>|<img src="https://user-images.githubusercontent.com/86085387/157418621-dc7207fe-ecfb-41fa-bc24-560e9df04ce8.png" width="300"/>|
* FireStore : Map타입(key:value) 데이터 저장
* Firebase Storage : 이미지 파일 저장


<br/>

## 3. FCM 서버 데이터 전달 기능
파이어베이스 클라우드 메시징으로 서버에서 보내는 메시지를 앱이 받아서 알림을 표시하도록 한다.

<br/>

## 키워드  
* Firebase
* Firebase Console
* Authentication
* Firestore Database
* Storage
* FCM(Firebase Cloud Messaging)
* Network Programming
