<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>User1::list</title>
</head>
<body>
    <h3>User1 목록</h3>

    <a href="/ch04">메인</a>
    <a href="/ch04/user1/register">등록</a>

    <table border="1">
        <tr>
            <td>아이디</td>
            <td>이름</td>
            <td>생년월일</td>
            <td>휴대폰</td>
            <td>나이</td>
            <td>관리</td>
        </tr>
        <c:forEach var="user" items="${users}">
        <tr>
            <td>${user.uid}</td>
            <td>${user.name}</td>
            <td>${user.birth}</td>
            <td>${user.hp}</td>
            <td>${user.age}</td>
            <td>
                <a href="/ch04/user1/modify?uid=${user.uid}">수정</a>
                <a href="/ch04/user1/delete?uid=${user.uid}">삭제</a>
            </td>
        </tr>
        </c:forEach>
    </table>
</body>
</html>
