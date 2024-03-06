<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>User4::list</title>
</head>
<body>
    <h3>User4 목록</h3>

    <a href="/ch05">메인</a>
    <a href="/ch05/user4/register">등록</a>

    <table border="1">
        <tr>
            <td>아이디</td>
            <td>이름</td>
            <td>성별</td>
            <td>나이</td>
            <td>연락처</td>
            <td>주소</td>
            <td>관리</td>
        </tr>
        <c:forEach var="user" items="${users}">
        <tr>
            <td>${user.uid}</td>
            <td>${user.name}</td>
            <td>${user.gender}</td>
            <td>${user.age}</td>
            <td>${user.hp}</td>
            <td>${user.addr}</td>
            <td>
                <a href="/ch05/user4/modify?uid=${user.uid}">수정</a>
                <a href="/ch05/user4/delete?uid=${user.uid}">삭제</a>
            </td>
        </tr>
        </c:forEach>
    </table>
</body>
</html>
