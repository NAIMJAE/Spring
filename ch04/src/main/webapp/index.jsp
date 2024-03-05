<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">

</head>
<!--
    날짜 : 2024/03/05
    이름 : 박임재
    내용 : Spring MVC 실습

    <Spring MVC 라이브러리>
     - Spring-context 6.1.4
     - Spring-webmvc 6.1.4
     - jakarta.servlet-api 6.0.0
     - jakarta.servlet-jsp.jstl-api 3.0.0
     - jakarta.servlet-jsp.jstl 3.0.1

    * WebApplicationInitializer
     - Spring 웹 어플리케이션 초기화를 위한 인터페이스
     - DispatcherServlet을 생성하고 컨텍스트 등록
     - 웹 어플리케이션 컨텍스트(컨테이너)를 설정하고 필요한 서블릿, 필터, 리스너 등

    * WebMvcConfigurer
     - Spring 웹 MVC 구성 컴포넌트 설정을 위한 인터페이스
     - 뷰리졸버(ViewResolver) 설정 및 ResourceHandler 설정 등 애플리케이션 전반에 걸친 자원 설정

    @EnableWebMvc
     - Spring MVC를 구성하고 MVC 관련 기능들을 활성화하는 어노테이션

    Tomcat 관련 설정
     - apache-tomcat-10.1.19





-->
<body>
    <h2>ch04::Spring MVC</h2>

    <h3>MVC 기본</h3>
    <a href="/ch04/hello">hello</a>
    <a href="/ch04/welcome">welcome</a>
    <a href="/ch04/greeting">greeting</a>

    <h3>User1 관리</h3>
    <a href="/ch04/user1/list">목록</a>
    <a href="/ch04/user1/register">등록</a>

    <h3>User2 관리</h3>
    <a href="/ch04/user2/list">목록</a>
    <a href="/ch04/user2/register">등록</a>

    <h3>User3 관리</h3>
    <a href="/ch04/user3/list">목록</a>
    <a href="/ch04/user3/register">등록</a>

    <h3>User4 관리</h3>
    <a href="/ch04/user4/list">목록</a>
    <a href="/ch04/user4/register">등록</a>

    <h3>User5 관리</h3>
    <a href="/ch04/user5/list">목록</a>
    <a href="/ch04/user5/register">등록</a>
</body>
</html>
