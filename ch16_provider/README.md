# 프로필 사진 교체 기능 구현하기
## Overview
* 콘텐츠 프로바이더(Content Provider)
* 갤러리, 카메라 앱과 연동하기
* 사진 목록에서 선택한 사진을 프로필에 출력하기
* 카메라로 촬영한 사진을 프로필에 출력하기
* 비트맵(Bitmap) 생성 및 사이즈 최적화
* BitmapFactory, Options 객체

## Screenshots
갤러리 앱 연동
|GALLERY APP 클릭|사진 선택|사진 출력|
|:-:|:-:|:-:|
|<img src="https://user-images.githubusercontent.com/86085387/155158151-46a85ea2-5292-4ae4-b229-4b0005e954e6.png" width="300" />|<img src="https://user-images.githubusercontent.com/86085387/155158166-10506f9a-7027-4756-9c3a-5b4ff2ae963e.png" width="300" />|<img src="https://user-images.githubusercontent.com/86085387/155158246-d2ec081b-3ad6-4e16-b8d3-cc180b83e668.png" width="300" /> |

카메라 앱 연동
|CAMERA APP 클릭|사진 촬영|사진 출력|
|:-:|:-:|:-:|
|<img src="https://user-images.githubusercontent.com/86085387/155158151-46a85ea2-5292-4ae4-b229-4b0005e954e6.png" width="300" />|<img src="https://user-images.githubusercontent.com/86085387/155158170-a7903ace-57ba-4344-823f-499f3301bad6.png" width="300" />|<img src="https://user-images.githubusercontent.com/86085387/155158254-4fa3fa4d-12ce-490e-8f0b-1e4c86e8fbb5.png" width="300" /> |


