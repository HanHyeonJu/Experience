# Experience
개인프로젝트

## Tools&Languages
<img src="https://img.shields.io/badge/Spring%20Boot-6DB33F.svg?style=for-the-badge&logo=Spring%20Boot&logoColor=white"/></a>
<img src="https://img.shields.io/badge/Spring%20Security-6DB33F.svg?style=for-the-badge&logo=Spring%20Security&logoColor=white"/></a>
<img src="https://img.shields.io/badge/Java-007396?style=flat-square&logo=Java&logoColor=white"/></a>
<img src="https://img.shields.io/badge/JavaScript-F7DF1E?style=flat-square&logo=JavaScript&logoColor=white"/></a>
<img src="https://img.shields.io/badge/MySQL-4479A1?style=flat-square&logo=MySQL&logoColor=white"/></a>
<img src="https://img.shields.io/badge/html5-%23E34F26.svg?style=for-the-badge&logo=html5&logoColor=white"/></a>
<img src="https://img.shields.io/badge/Visual%20Studio%20Code-007ACC.svg?style=for-the-badge&logo=Visual%20Studio%20Code&logoColor=white"/></a>
<img src="https://img.shields.io/badge/css-%231572B6.svg?style=for-the-badge&logo=css3&logoColor=white"/></a>
<img src="https://img.shields.io/badge/bootstrap-%23563D7C.svg?style=for-the-badge&logo=bootstrap&logoColor=white"/></a>
<img src="https://img.shields.io/badge/github-%23121011.svg?style=for-the-badge&logo=github&logoColor=white"/></a>
<img src="https://img.shields.io/badge/Tomcat-caa01a?style=flat-square&logo=APACHE-TOMCAT&logoColor=white"/></a>

## Experience프로그램 소개
본인만의 소중한 경험을 계획하고 기록하고 공유할 수 있는 프로그램
- TodoList를 통해 본인만의 경험을 쌓을 수 있도록 계획할 수 있음
- Diary를 통해 하루 동안 본인에게 일어난 일들을 기록하면 경험을 쌓을 수 있음
- Share 게시판을 통해 본인만의 경험을 공유할 수 있음

## Experience프로그램 기능구현
### 전체
- 스프링 시큐리티를 이용한 회원가입, 로그인, 로그아웃 기능
### TodoList(JPA)
- Todo 작성, 수정, 삭제, 조회 기능 구현(로그인 성공 시 본인의 Todo만)
- 네이티브쿼리를 이용한 키워드 검색
- JPA 페이징 기능 구현
- Todo 시작전, 진행중, 완료 비율을 쉽게 볼 수 있도록 차트 보여지게 하기
### Diary(JPA)
- Diary 작성, 수정, 삭제, 조회 기능 구현(로그인 성공 시 본인의 Diary만)
  - Diary 작성, 수정 시 ck에디터 사용
- jpg,png 파일에 한하여 이미지 파일 업로드 가능
- 네이티브쿼리를 이용한 키워드 검색
- JPA 페이징 기능 구현
### Share(MyBatis)
- 로그아웃 상태에도 게시글, 댓글 조회가능(작성, 수정, 삭제, 댓글 기능 사용못함)
- 게시글 작성, 수정, 삭제 가능(로그인 성공 시)
  - 작성, 수정 시 Diary와 같이 ck에디터 사용
  - 수정, 삭제의 경우 게시글 작성자와 로그인 한 사용자가 같은 경우에 가능
- LIMIT방식을 이용한 쿼리로 페이징 기능 구현
- 주제별 타입을 정하여 키워드를 검색할 수 있는 검색 기능 구현
- 게시글 조회수 확인 가능
- Fetch API를 이용하여 댓글 작성, 수정, 삭제기능 구현
  - 댓글도 게시글과 마찬가지로 댓글 작성자와 로그인 한 사용자가 같은 경우에 가능

