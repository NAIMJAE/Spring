<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>member::modify</title>
</head>
<body>
    <h3>member 수정</h3>

    <a href="/ch05">메인</a>
    <a href="/ch05/member/list">목록</a>

    <form action="/ch05/member/modify" method="post">
        <table border="1">
            <tr>
                <td>아이디</td>
                <td><input type="text" name="uid" readonly value="${memberDTO.uid}"></td>
            </tr>
            <tr>
                <td>이름</td>
                <td><input type="text" name="name" value="${memberDTO.name}"></td>
            </tr>
            <tr>
                <td>연락처</td>
                <td><input type="text" name="hp" value="${memberDTO.hp}"></td>
            </tr>
            <tr>
                <td>직급</td>
                <td><input type="text" name="pos" value="${memberDTO.pos}"></td>
            </tr>
            <tr>
                <td>부서</td>
                <td><input type="number" name="dep" value="${memberDTO.dep}"></td>
            </tr>
            <tr>
                <td colspan="2" align="right"><input type="submit" value="등록"></td>
            </tr>
        </table>
    </form>
</body>
</html>
