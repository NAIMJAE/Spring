<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>User3::modify</title>
</head>
<body>
    <h3>User3 수정</h3>

    <a href="/ch04">메인</a>
    <a href="/ch04/user3/list">목록</a>

    <form action="/ch04/user3/modify" method="post">
        <table border="1">
            <tr>
                <td>아이디</td>
                <td><input type="text" name="uid" readonly value="${user3DTO.uid}"></td>
            </tr>
            <tr>
                <td>이름</td>
                <td><input type="text" name="name" value="${user3DTO.name}"></td>
            </tr>
            <tr>
                <td>생년월일</td>
                <td><input type="date" name="birth" value="${user3DTO.birth}"></td>
            </tr>
            <tr>
                <td>연락처</td>
                <td><input type="text" name="hp" value="${user3DTO.hp}"></td>
            </tr>
            <tr>
                <td>주소</td>
                <td><input type="text" name="addr" value="${user3DTO.addr}"></td>
            </tr>
            <tr>
                <td colspan="2" align="right"><input type="submit" value="등록"></td>
            </tr>
        </table>
    </form>
</body>
</html>
