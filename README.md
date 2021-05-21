# README
- Spring Boot + MyBatis + MySQL
- 2021년 5월 기준 버전 

## Environment setting

### Language : Java
#### java 14.0.2 2020-07-14

### Framework : Spring Boot
#### [Spring boot]<https://start.spring.io/>
- Project : Gradle Project
- Language : Java
- Spring Boot : 2.4.5
- Project Metadata 
	- Group : example
	- Artifact : board
	- Name : board
	- Description : Demo project for Spring Boot
	- Package name : example.board
	- Package : jar
	- Java : 11
- Dependencies : 
	- Spring Web
	- Spring Boot DevTools
	- Lombok 
	- Thymeleaf		  
	- Spring Data JPA
	- H2 Database
	- Mybatis 
	- mysql
- download : click the "GENERATE" button or use a shortcut key "CTRL+enter"

### Tool
- IDE : [IntelliJ Community Version](https://www.jetbrains.com/)
- DB : [MySQL Community Server](https://dev.mysql.com/downloads/mysql/)
- DB Editor : [DBeaver Community Edition](https://dbeaver.io/download/)

## Opne the project
- `bundle.gradle` 파일을 통해 프로젝트 열기 

## Cofiguration
- `build.gradle`에서 필요한 라이브러리들을 주입하고 설정은 `applicaton.yml` 에서 해줌 
- 자바로 설정파일을 만들어 준다거나 다른 설정 파일들이 전혀 필요 없음 (매우 간단)
- mybatis-spring-boot-starter가 이젠 다 알아서 해 줌 (자세한 설명은 마이바티스 홈페이지에서 확인)

### 라이브러리  build.gradle
- start.spring.io 에서 프로젝트 생성시 Dependencies에 추가를 했다면 따로 수정할 것은 없고 필요한 것은 추가해서 사용 하면 됨

```
plugins {
	id 'org.springframework.boot' version '2.4.5'
	id 'io.spring.dependency-management' version '1.0.11.RELEASE'
	id 'java'
}

group = 'jpa'
version = '0.0.1-SNAPSHOT'
sourceCompatibility = '11'

configurations {
	compileOnly {
		extendsFrom annotationProcessor
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation 'org.springframework.boot:spring-boot-starter-thymeleaf'
	implementation 'org.springframework.boot:spring-boot-starter-web'
	implementation 'org.springframework.boot:spring-boot-devtools'
	implementation 'org.mybatis.spring.boot:mybatis-spring-boot-starter:2.1.4'
	compileOnly 'org.projectlombok:lombok'
	runtimeOnly 'mysql:mysql-connector-java'
	annotationProcessor 'org.projectlombok:lombok'
	testImplementation 'org.springframework.boot:spring-boot-starter-test'
}

test {
	useJUnitPlatform()
}

```

### 환경설정 applicaton.yml
- 위치 : src/main/resources/application.yml
- db, mybatis 를 설정
- 설정 옵션에 대한 내용은 해당 홈페이지에서 확인하면서 자유롭게 추가, 변경 가능  

```
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/board?serverTimezone=UTC&characterEncoding=UTF-8
    username: 
    password:
    driver-class-name: com.mysql.cj.jdbc.Driver

mybatis:
  type-aliases-package: example.board    #매퍼용 클래스가 위치한 경로
  mapper-locations: mapper/**/*.xml      #xml파일이위치한 경로/*.xml
  configuration:
    map-underscore-to-camel-case: true
    multiple-result-sets-enabled: false
    call-setters-on-nulls: true
    jdbc-type-for-null: varchar
    default-fetch-size: 500
``` 

#### 이것으로 설정 끝. 더이상의 설정으로 시간낭비하지 않길 

## 개발 

1. 데이터베이스 테이블 생성 (DATA  만들기)
- schema :  board 
```mysql
create table board(  
	board_idx int(11) not null auto_increment comment '글 번호',
	title varchar(300) not null comment '제목',
	contents text not null comment '내용',
	hit_cnt smallint(10) not null default '0' comment '조회수',
	created_datetime datetime not null comment '작성시간',
	creator_id varchar(50) not null comment '작성자',
	updated_datetime datetime default null comment '수정시간',
	updater_id varchar(50) default null comment '수정자',
	deleted_yn char(1) not null default 'N' comment '삭제여부',
	primary key(board_idx)
	);
```


2. VO/ DTO :  도메인객체를 위한 클래스 설계
- `example.board.dto` 패키지내 `BoardDto` 생성

```java

@Data
@Getter @Setter
public class BoardDto {
    private int boardIdx;
    private String title;
    private String contents;
    private int hitCnt;
    private String creatorId;
    private String createdDateTime;
    private String updaterId;
    private String updatedDateTime;
    private String deletedYn;
}

```

3. Mapper/ DAO
- 실제로 실행해야 하는 작업을 인터페이스 정의로 하도록 함  
- `example.mapper` 또는 패키지내 `BoardMapper` 인터페이스 생성 
- 현 프로젝트에서는 맵퍼를 이용하였으나 DAO를 사용하고 싶은 경우, `example.dao` 패키지로 생성해서 `BoardDao` 인터페이스 생성하여 사용하여도 됨
  
```java
@Mapper
public interface BoardMapper {
    List<BoardDto> selectBoardList() throws Exception;
}
```

4. xml mapper작성
- `resources` 하단에 `mapper` 폴더를 생성해줘서 필요한 쿼리문들을  *.xml 에  생성 

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="example.board.mapper. ">
    <select id="selectBoardList" resultType="BoardDto">
        <![CDATA[
            SELECT board_idx
                 , title
                 , hit_cnt
                 , creator_id
                 , date_format(created_datetime, '%Y.%m.%d %H:%i:%s') as created_datetime
              FROM board
             WHERE deleted_yn = 'N'
             order by board_idx desc
        ]]>
    </select>
</mapper>
```

5. Contorller 생성
- `example.board.controller` 패키지내 `BoardController` 클래스 생성
- `@RequestMapping` 매핑과 같은 url로 들어온 클라이언트의 요청을 서비스로직/비즈니스 로직을 호출하여 처리 후  `ModelAndView`에 반환할 화면을 받고 반환 

```java
@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @RequestMapping("/board/boardList")
    public ModelAndView boardList() throws Exception{
        ModelAndView mv = new ModelAndView("board/boardList");//호출되는 html 화면
        List<BoardDto> list = boardService.selectBoardList();
        mv.addObject("list", list);
        return mv;
    }
}
```

6. Service 생성
- `example.board.service` 패키지내 `BoardService` 인터페이스 생성

```java
public interface BoardService {
    List<BoardDto> selectBoardList() throws Exception;
}
```

- 같은 패키지내 서비스를 상속받아 구현해주는 서비스 작성
```java
@Service
public class BoardServiceImpl implements BoardService{

    @Autowired
    private BoardMapper boardMapper;

    @Override
    public List<BoardDto> selectBoardList() throws Exception {
        return boardMapper.selectBoardList();
    }
}
```

7. 화면생성
- `resources` 하단에 `templates` 폴더에 화면 .html 파일들을 생성
- `board`폴더생성 후 `boardList.html`을 만들기 


```html
<!DOCTYPE html>
<html lang="ko" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>board</title>
    <link rel="stylesheet" th:href="@{/css/style.css}"/>
</head>
<body>
<div class="container">
    <h2>게시글 목록</h2>
    <table class="board_list">
        <colgroup>
            <col width="15%"/>
            <col width="*"/>
            <col width="15%"/>
            <col width="20%"/>
        </colgroup>
        <thead>
        <tr>
            <th scope="col">글번호</th>
            <th scope="col">제목</th>
            <th scope="col">조회수</th>
            <th scope="col">작성자</th>
            <th scope="col">작성일</th>
        </tr>
        </thead>
        <tbody>
        <tr th:each="list : ${list}">
            <td th:text="${list.boardIdx}"></td>
            <td class="title" th:text="${list.title}"></td>
            <td th:text="${list.hitCnt}"></td>
            <td th:text="${list.creatorId}"></td>
            <td th:text="${list.createdDateTime}"></td>
        </tr>
        </tbody>
    </table>
    <a href="/board/openBoardWrite.do" class="btn">글 쓰기</a>
</div> <!-- /container -->
</body>
</html>
```

