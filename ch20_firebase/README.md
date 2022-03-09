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
* FirebaseAuth : 파이어베이스 인증 클래스
* GoogleSignInOptions, GoogleSignIn : 구글 인증 관련 클래스

<br/>

## 2. 파이어베이스 파이어스토어, 스토리지 기능
파이어베이스의 파이어스토어와 스토리지를 이용해 이미지를 공유하는 앱. 사용자가 애에서 이미지와 글을 입력하면 파이어베이스에 저장해서 다른 사용자도 볼 수 있도록 한다.
|게시글 작성|게시글 목록|
|:-:|:-:|
|<img src="https://user-images.githubusercontent.com/86085387/157418609-cf031b95-a67f-4840-90af-674f75117fa6.png" width="300"/>|<img src="https://user-images.githubusercontent.com/86085387/157418621-dc7207fe-ecfb-41fa-bc24-560e9df04ce8.png" width="300"/>|
* 파이어스토어(FireStore)
  * NoSQL 데이터베이스, 컬렉션으로 정리되는 문서에 키-값 쌍의 데이터를 저장한다.
  * FireStore에 저장된 데이터를 이용할 때의 보안 규칙을 설정할 수 있다. (ex: 인증된 사용자만 데이터 읽고 쓰기 가능)
  * 문서의 데이터로 Map형태 또는 객체의 데이터를 저장할 수 있다.
  * 데이터를 저장, 업데이트, 삭제할 수 있다.
  * 컬렉션의 전체 문서, 단일 문서, 조건에 만족하는 특정 문서를 조회할 수 있다.
  * FirebaseFiresotre : 파이어스토어
  * CollectionReference : 컬렉션
  * DocumentReference, DocumentSanpShot : 문서
* 스토리지(Storage)
  * 앱의 파일을 저장하는 기능을 제공한다. 사용자가 앱에서 파일을 서버에 올린 후 특정 시점에 내려받을 수 있다.
  * StorageReference : 스토리지 자체 또는 특정 경로 파일을 가리키는 스토리지
  * 바이트배열(ByteArray), 파일(Filre), 스트림(Stream) 세 가지 방법으로 스토리지에 파일을 올릴 수 있다.
  * 바이트배열(ByteArray), 파일(Filre) 두 가지 방법으로 스토리지에서 파일을 내려받을 수 있으며, 다운로드없이 파일의 URL만 가져올 수도 있다.
  * firebase-ui-storage 라이브러리 : Glide를 이용해서 파일을 더 쉽게 다운로드 받을 수 있도록 지원한다.

<br/>

## 3. FCM 서버 데이터 전달 기능
파이어베이스 클라우드 메시징(FCM)으로 서버에서 보내는 메시지를 앱이 받아서 알림을 표시하도록 한다.

![fcm 테스트 성공 send ok](https://user-images.githubusercontent.com/86085387/157426349-26f013c8-eeab-42ce-94af-d81a2aa59da3.png)
<img src="https://user-images.githubusercontent.com/86085387/157426652-1983c6ea-58de-43e0-b60c-b448e4ccad54.jpg" width="450"/>

* 클라우드 메시징(Cloud Messaging)
  * 서버에서 특정 상황이나 데이터가 발생할 때 앱에 알림을 전달하는 기능.
  * 서버 푸시(server push) : 서버에서 어떤 상황이 발생할 때 클라이언트(앱)에 데이터를 전달하는 것. 보통 푸시 알림으로 변화를 알려줌.
  * 이때 서버의 데이터를 앱에 직접 전달하지 않고 FCM 서버를 거쳐 전달한다.
  * 토큰(Token) : FCM을 이용하는 앱을 구분하는 식별값. 앱을 최초로 설치하는 시점에 발급되며, 서버에서는 이 토큰을 사용해서 메시지를 전달할 앱을 식별한다.
  * 서버에서 FCM 서버에 전달하는 정보는 토큰(token), 알림(notification), 데이터(data)로 구분된다. 이때 notification에 설정한 내용이 사용자의 폰에 푸시알림으로 나타나게 된다.
  * 앱에서 FCM 토큰과 메시지를 받는 서비스를 개발자가 직접 작성해야 한다.

<br/>

## 키워드  
* Firebase
* Firebase Console
* Authentication
* Firestore Database
* Storage
* FCM(Firebase Cloud Messaging)
* Network Programming
