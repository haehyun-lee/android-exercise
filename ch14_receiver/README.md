# 배터리 정보 앱 만들기
* IntentFilter, registerReceiver()로 배터리 정보 얻기
* 배터리 전원 종류(EXTRA_PLUGGED), 배터리 퍼센트(EXTRA_LEVEL, EXTRA_SCALE) 정보 출력
* Notification 알림을 띄우는 BroadcastReceiver 생성
* 버튼 클릭 시 BroadcastReceiver를 실행하는 Intent 전달


## Screenshots
### 1. Battery Info
|Not Plugged|AC|USB|
|:-:|:-:|:-:|
|<img src="https://user-images.githubusercontent.com/86085387/150493260-25715ca0-3d63-4529-8670-88d4e8b42571.png" width="300"/>|<img src="https://user-images.githubusercontent.com/86085387/150493267-89abd8b9-ffb7-40cc-9523-22a1ab112690.png" width="300"/>|<img src="https://user-images.githubusercontent.com/86085387/150493272-bd67b244-908f-48d8-b733-d786e57bea56.jpg" width="300"/>|


### 2. Notification
<img src="https://user-images.githubusercontent.com/86085387/150493247-1e08c758-57f8-4a68-82ab-aa202f8ee28c.gif" width="300"/>

## Study
* Broadcast Receiver
* Intent
  * Activity Intent
  * Broadcast Receiver Intent
  * IntentFilter
* Action String
* System Event
