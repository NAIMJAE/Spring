<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>User1::modify</title>
</head>
<body>
    <h3>User1 수정</h3>

    <a href="/ch05">메인</a>
    <a href="/ch05/user1/list">목록</a>

    <form action="/ch05/user1/modify" method="post">
        <table border="1">
            <tr>
                <td>아이디</td>
                <td><input type="text" name="uid" readonly value="${user1DTO.uid}"></td>
            </tr>
            <tr>
                <td>이름</td>
                <td><input type="text" name="name" value="${user1DTO.name}"></td>
            </tr>
            <tr>
                <td>생년월일</td>
                <td><input type="date" name="birth" value="${user1DTO.birth}"></td>
            </tr>
            <tr>
                <td>연락처</td>
                <td><input type="text" name="hp" value="${user1DTO.hp}"></td>
            </tr>
            <tr>
                <td>나이</td>
                <td><input type="number" name="age" value="${user1DTO.age}"></td>
            </tr>
            <tr>
                <td colspan="2" align="right"><input type="submit" value="등록"></td>
            </tr>
        </table>
    </form>
</body>
</html>
