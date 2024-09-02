#  🎮 4Gamer (2024.07.12 ~ 2024.08.22)

## ![free-icon-content-2211565 (1)](https://github.com/KangBaekho10/LuckyWiki/assets/166815465/ebf2b813-d195-4c2b-8b36-715daed486e0) 목차
- [프로젝트 소개](#-프로젝트-소개)
- [팀 소개](#-팀-소개)
- [협업 전략](#-협업-전략)
- [프로젝트 기획](#-프로젝트-기획)
- [서비스 아키텍쳐](#-서비스-아키텍쳐)
- [주요 기능](#-주요-기능)
- [환경 설정](#-환경-설정)

## ![free-icon-project-management-6788500](https://github.com/user-attachments/assets/4baecd55-edbd-43e2-bec3-f9bbf97b9d61) 프로젝트 소개

인기 / 비인기 게임을 가리지 않고, 모든 게임의 정보를 공유하기 위한 사이트

배포 URL : https://4gamer.vercel.app <br />
팀 노션 : [4IGHTING](https://hellou8363.notion.site/4IGHTING-7072b1a98191441da932a2514b26b33c?pvs=4)

## ![free-icon-team-1478928 (2)](https://github.com/user-attachments/assets/4f3c1b95-de23-4959-b926-7558888fdc06) 팀 소개

|             <img src ="https://github.com/user-attachments/assets/90cc6d5d-6523-49b4-9c14-25610d832005" width="160px" height="160px">             | <img src ="https://github.com/user-attachments/assets/d39b9072-68ad-4af9-8284-7dd73e555ae6" width="160px" height="160px"> | <img src ="https://github.com/user-attachments/assets/239a4bbe-521a-47cc-8212-1587d8727f34" width="160px" height="160px"> | <img src ="https://github.com/user-attachments/assets/e07ba106-a4d8-487c-9791-89b3afa5a2c2" width="160px" height="160px"> |
|:--------------------------------------------------------------------------------------------------------------------------------------------------:|:------------------------------------------------------------------------------------------------------------------------:|:-------------------------------------------------------------------------------------------------------------------------:|:-------------------------------------------------------------------------------------------------------------------------:|
|                                                                        팀장                                                                        |                                                          부팀장                                                          |                                                            팀원                                                            |                                                            팀원                                                           |
|                                             유채우<br>[@Turbstructor](https://github.com/Turbstructor)                                             |                                   이태경<br>[@taeaeaeae](https://github.com/taeaeaeae)                                   |                                 김태완<br>[@KangBaekho10](https://github.com/KangBaekho10)                                 |                                   한은혜<br>[@hellou8363](https://github.com/hellou8363)                                  |
|                                           - 코드 리포맷팅 <br> - 사용자 반응 기능 <br> - 동시성 제어 구현                                           |    - 로그인 / 회원가입 <br> - 소셜 로그인 (구글) <br> - Amazon EC2 <br> - HTTPS (Nginx) <br> - CI / CD <br> - reCAPTCHA   |                                     - 기본 CRUD <br> - AmazonS3 <br> - 외부 API (IGDB)                                     |                          - 기본 CRUD <br> - 인기글 조회 (Redis) <br> - 쪽지/채팅 및 알림 기능<br>                          |

## ![free-icon-team-collaboration-15762644](https://github.com/user-attachments/assets/7e995f9a-090b-421b-af18-81129d60f0a1) 협업 전략

### Git Rule

#### Git Branch / flow

- `main`
- `release`
- `develop`
- `feature/`
    - ex. `feature/implement-channels`

#### Git Issue

- 구현할 기능 단위로 `Issue` 생성
- 세부 계획들은 체크박스를 사용하여 구현 여부 확인
- `담당자(Assignee)` : 기능을 담당한 사람(들)
- `라벨(Label)` : 기능 구현에 대한 건지(Feat), 버그 해결에 대한 건지(Bugfix) 등

#### PR Convention

- `PR`을 올리기 전 작동 테스트는 필수
- `develop` 브랜치에 `PR`하기 전에 `Reviewers`에게 변경 사항을 확인
- 구현 기능들은 체크박스를 사용하여 구현 여부 확인
- `담당자(Assignee)` - 기능을 담당한 사람(들)
- `라벨(Label)` : 기능 구현에 대한 건지(Feat), 버그 해결에 대한 건지(Bugfix) 등
- 관련 `Issue`를 닫을 수 있게 본문에 명시
    - ex. `close #6`

#### Commit Rule

- `emoji` : 선택사항

- `Type: subject` : Type: 태그와 제목으로 구성태그의 첫 문자는 대문자, ' : ' 뒤에만 빈칸이 있음을 유의, Subject: 최대 50 글자
    - `Feat`: 새로운 기능 추가
    - `Fix`: 버그 수정
    - `Docs`: 문서 수정
    - `Style`: 코드 포맷팅
    - `Refactor`: 코드 리팩토링
    - `Test`: 테스트 코드 추가
    - `Chore`: 빌드 업무 수정, 패키지 매니저 수정
    - `Design`: UI 디자인 변경
    - `Comment`: 주석 추가 및 변경
    - `Rename`: 파일 혹은 폴더명 수정하거나 옮기는 작업
    - `Remove`: 파일 삭제하는 작업

- `Body` : 최대 72 글자, 무엇을 왜 변경했는지 작성 (생략 가능)

## ![rlghlr](https://github.com/KangBaekho10/LuckyWiki/assets/166815465/d133d561-7cf5-4f5a-a736-cb48253705c4) 프로젝트 기획

- [와이어 프레임](https://file.notion.so/f/f/c9379b50-6e43-45e1-8cec-f30f16cfc9d9/1e307a54-3097-4ada-b29c-0343da068dbb/Untitled.png?table=block&id=f0593c6e-d9c1-4fe5-bc42-077ea65974b7&spaceId=c9379b50-6e43-45e1-8cec-f30f16cfc9d9&expirationTimestamp=1723716000000&signature=rrDBLvZHD_bxFwkomjCASpv671Y29Fr1FNlBK1Opvu0&downloadName=Untitled.png)

- [ERD](https://www.notion.so/hellou8363/Entity-Relationship-Diagram-v1-7a9e20a0d725473788a16a33e49b7b52)

- [API 명세서](https://www.notion.so/hellou8363/API-Specification-v1-a1c00de95132432cbbaa9afbbb747daf)

- [버전 계획](https://www.notion.so/hellou8363/v1-1d2536f995494bbc93336c76be1b024e)

## ![free-icon-blueprint-1833020](https://github.com/user-attachments/assets/962a479d-a16b-467e-b5b1-b8240af973bc) 서비스 아키텍쳐

![image](https://github.com/user-attachments/assets/b881bd3c-00fa-4896-b4ae-6a7564d472d5)

## ![free-icon-function-11337201](https://github.com/user-attachments/assets/52cd4b40-3d68-4ee4-b7dd-f9276c617f8f) 주요 기능

- `채널 - 게시판 - 게시물` 로 이어지는 세분화된 정보 분류

- `IGDB API`
    - 현존하는 게임의 채널만 생성할 수 있도록 게임 존재 확인
    - 실시간 게임 평점 순위 및 최다 팔로우 게임 순위

- Web Socket을 이용한 `채팅 / 쪽지 및 알림`
    - 게시물 등에 존재하는 타인 프로필로 1 대 1 `채팅` 가능
    - 상대방이 접속하지 않는 경우를 생각한 `쪽지` 기능
    - `채팅` 또는 `쪽지`가 수신 된 경우 발생하는 `알림`

- Amazon S3를 이용한 `이미지 업로드`
    - 게시물에서 `이미지 업로드` 가능

- reCAPTCHA
    - 컴퓨터 봇에 의한 스팸, 해킹 등을 방지
    - v3을 사용하는 것으로 사용자가 직접 봇인지 증명하는 것이 아닌 웹 사이트 상호작용 기반 확인
 
- Redis를 이용한 `동시성 제어`
    - Pub/Sub 채널을 사용하는 것으로 다른 스레드의 LOCK 여부 확인

## ![ghksrudtjfwjd](https://github.com/KangBaekho10/LuckyWiki/assets/166815465/debe07f2-1467-4f66-b968-73dd3a2ea14c) 환경 설정 

![Kotlin](https://img.shields.io/badge/kotlin-%237F52FF.svg?style=for-the-badge&logo=kotlin&logoColor=white)
![IntelliJ IDEA](https://img.shields.io/badge/IntelliJIDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white) 
![Jdk17](https://img.shields.io/badge/jdk17-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white"/)
![MySQL](https://img.shields.io/badge/mysql-4479A1.svg?style=for-the-badge&logo=mysql&logoColor=white) 
![AWS](https://img.shields.io/badge/AWS-%23FF9900.svg?style=for-the-badge&logo=amazon-aws&logoColor=white)
![Apache](https://img.shields.io/badge/apache-%23D42029.svg?style=for-the-badge&logo=apache&logoColor=white) <br/>
![Postman](https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=postman&logoColor=white)
![Redis](https://img.shields.io/badge/redis-%23DD0031.svg?style=for-the-badge&logo=redis&logoColor=white)
![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)
![Nginx](https://img.shields.io/badge/nginx-%23009639.svg?style=for-the-badge&logo=nginx&logoColor=white)
![Gradle](https://img.shields.io/badge/Gradle-02303A.svg?style=for-the-badge&logo=Gradle&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens)

