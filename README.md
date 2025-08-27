</br>

<div align="center">
  <!-- logo -->
  <img width="350" height="64" alt="Forest - README" src="https://github.com/user-attachments/assets/3dabf50e-1fa8-41e0-b91b-ba94ccd9a640" />
</div>

<div align="center">
  
### 당신의 책장을 세상과 연결하는 중고 서적 플랫폼

</div>

</br>
</br>

<div align="center">
  <img width="600" alt="Forest Intro" src="https://github.com/user-attachments/assets/bd1d929d-3f7e-4643-9fad-c91aaebf1f85" />
</div>

</br>
</br>

### 📍 프로젝트 개요

온라인에서 중고 도서를 구매할 수 있는 전용 플랫폼은 많지 않습니다. 중고 도서를 쉽게 구매할 수 있는 온라인 사이트를 제작하였습니다. 'Forest'에서는 중고 도서의 판매 및 구매뿐만 아니라, 각종 도서에 대한 정보를 제공하여 사용자들이 다양한 책을 쉽게 찾고 구매할 수 있습니다.

</br>
</br>

### 👨‍💻 개발 인원 및 기간

개발인원: 1명 </br>
개발기간: 2024/07/30 ~ 2024/09/02

</br>
</br>

### 🛠️ 사용된 기술 스택

| 구분 | 사용 기술 |
| :--- | :--- |
| **Frontend** | `HTML` `CSS` `JavaScript` `jQuery` `Thymeleaf` |
| **Backend** | `Java` `Spring Boot` |
| **Database** | `MySQL` `JPA` `MyBatis` |
| **배포/형상 관리** | `AWS EC2` `GitHub` |

</br>
</br>

### ✨ 핵심 개발 내용

#### 로그인 & 회원가입
| 일반 로그인 | 카카오 로그인 |
| :---: | :---: |
| <img width="500" alt="login-basic" src="https://github.com/user-attachments/assets/3dbd3bba-2a2c-45b0-bc58-3c8ba742e76a" /> | <img width="500" alt="login-kakao" src="https://github.com/user-attachments/assets/f3fe7930-3593-40bf-ba57-9c99704eca5e" /> |
| 일반 회원을 위한 사용자 신규 회원가입 및 로그인 절차입니다. | 카카오 계정을 통해 로그인을 할 수 있습니다. |

</br>
</br>

#### 도서 정보 🔗 알라딘 API

<div align="center">

| 베스트셀러/시간 도서 |
| :---: |
| <img width="700" alt="aladin-0" src="https://github.com/user-attachments/assets/097379fb-f3a2-4ae6-8778-91bdb3d467cf" /> |
| 베스트셀러와 신간 도서 목록을 알라딘 Open API를 통해 불러옵니다. </br> 상세 도서 페이지에서는 책의 개요를 보여주고 '신품도서' 항목을 </br> 클릭하면 알라딘 사이트의 해당 도서의 판매 링크로 연결됩니다. |

| 중고 도서 목록 & 좋아요/장바구니 |
| :---: |
| <img width="700" alt="aladin-1" src="https://github.com/user-attachments/assets/3a86b66d-8ac0-47eb-9502-c261cc0cd90c" /> |
| (검색창에서 제목/저자/출판사 검색어로 도서를 찾을 수 있습니다.) </br> 중고 도서가 있는 제품은 해당 상품의 구매 링크로 이동할 수 있습니다. </br> 좋아요/장바구니 기능을 이용해 상품을 저장해두고 구매를 할 수 있습니다. |

</div>


</br>
</br>

#### 결제 시스템

<div align="center">

| 결제 프로세스 |
| :---: |
| <img width="800" alt="payment" src="https://github.com/user-attachments/assets/b19b276b-2d0a-4f72-8581-6bcbc45d5774" /> |
| PortOne API를 통해 결제를 진행합니다. 사용자의 배송 정보를 받고 주문 정보를 데이터베이스에 저장합니다. </br> 주문된 상품은 자동으로 판매상태가 변경되고 구매불가 상태로 변경됩니다. |

</div>
