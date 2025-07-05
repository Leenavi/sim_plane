<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="../includes/header.jsp" %>
<link rel="stylesheet" href="<c:url value='/resources/dist/css/main.css' />">


<h1>회원 관리</h1>

<div>
    <table>
        <thead>
        <tr>
            <th>회원 번호</th>
            <th>회원 아이디</th>
            <th>회원 이름</th>
            <th>회원 성별</th>
            <th>회원 권한</th>
        </tr>
        </thead>

        <c:forEach var="member" items="${memberList}">
            <tr>
                <td><c:out value="${member.memberid}" /></td>

                <td><a class="move" href='<c:out value="${member.memberid}"/>'>
                    <c:out value="${member.userid}"/> </a></td>

                <td><c:out value="${member.name}" /></td>
                <td>
                    <c:choose>
                        <c:when test="${member.sex == 0}">남성</c:when>
                        <c:when test="${member.sex == 1}">여성</c:when>
                        <c:otherwise>미상</c:otherwise>
                    </c:choose>
                </td>

                <!-- 권한 수정 form -->
                <td>
                    <form action="/updateAuth" method="post" style="display: flex; gap: 5px;">
                        <input type="hidden" name="memberid" value="${member.memberid}" />
                        <select name="auth">
                            <option value="ROLE_USER"
                                    <c:if test="${not empty member.authList && member.authList[0].auth == 'ROLE_USER'}">selected</c:if>>
                                일반회원
                            </option>
                            <option value="ROLE_ADMIN"
                                    <c:if test="${not empty member.authList && member.authList[0].auth == 'ROLE_ADMIN'}">selected</c:if>>
                                관리자
                            </option>
                        </select>
                        <button type="submit">수정</button>
                    </form>
                </td>
            </tr>
        </c:forEach>

    </table>
</div>


<%@ include file="../includes/footer.jsp" %>