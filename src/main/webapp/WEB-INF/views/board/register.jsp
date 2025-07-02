<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../includes/header.jsp" %>
<link rel="stylesheet" href="<c:url value='/resources/dist/css/main.css' />" />

<div class="container">
    <h2>문의하기</h2>
    <form role="form" action="/board/register" method="post">

        <table class="table table-bordered">


            <tr>
                <th>제목</th>
                <td><input type="text" name="title" style="width: 100%;"></td>
            </tr>
            <tr>
                <th>작성자</th>
                <td>
                    <input type="text" name="writer" style="width: 100%;">
                </td>
            </tr>
            <tr>
                <th>내용</th>
                <td colspan="5">
                    <textarea name="content" class="form-control" rows="10"></textarea>
                </td>
            </tr>
        </table>

        <button data-oper='register' class="btn btn-info">등록</button>
        <button data-oper='list' class="btn btn-default">목록</button>

    </form>
</div>

<%@ include file="../includes/footer.jsp" %>