<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>member::list</title>
</head>
<body>
    <h3>member 목록</h3>

    <a href="/ch05">메인</a>
    <a href="/ch05/member/register">등록</a>
    <a href="/ch05/member/list">전체목록</a>

    <form action="/ch05/member/search">
        <select name="type">
            <option value="uid">아이디</option>
            <option value="name">이름</option>
            <option value="hp">휴대폰</option>
        </select>
        <input type="text" name="value">
        <p>
            <label><input type="checkbox" name="pos" value="사원">사원</label>
            <label><input type="checkbox" name="pos" value="대리">대리</label>
            <label><input type="checkbox" name="pos" value="과장">과장</label>
            <label><input type="checkbox" name="pos" value="차장">차장</label>
            <label><input type="checkbox" name="pos" value="부장">부장</label>
        </p>
        <input type="submit" value="검색">
    </form>

    <table border="1">
        <tr>
            <td>아이디</td>
            <td>이름</td>
            <td>연락처</td>
            <td>직급</td>
            <td>부서</td>
            <td>가입일</td>
            <td>관리</td>
        </tr>
        <c:forEach var="member" items="${members}">
        <tr>
            <td>${member.uid}</td>
            <td>${member.name}</td>
            <td>${member.hp}</td>
            <td>${member.pos}</td>
            <td>${member.dep}</td>
            <td>${member.rdate}</td>
            <td>
                <a href="/ch05/member/modify?uid=${member.uid}">수정</a>
                <a href="/ch05/member/delete?uid=${member.uid}">삭제</a>
            </td>
        </tr>
        </c:forEach>
    </table>
</body>
</html>
