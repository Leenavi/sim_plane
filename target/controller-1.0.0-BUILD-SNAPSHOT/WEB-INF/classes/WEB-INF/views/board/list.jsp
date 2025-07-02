<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="../includes/header.jsp" %>
<link rel="stylesheet" href="<c:url value='/resources/dist/css/main.css' />">
<style>
    .panel-heading {height: 40px; align-items: center; }
</style>
<div class="row">
    <div class="col-lg-12">
        <h1 class="page-header">문의게시판</h1>
    </div>
    <!-- /.col-lg-12 -->
</div>
<!-- /.row -->
<div class="row">
    <div class="col-lg-12">
        <div class="panel panel-default">
            <div class="panel-heading">

                <button id="regBtn" type="button" class="btn btn-xs btn-info pull-right">새글 등록</button>
            </div>
            <!-- /.panel-heading -->
            <div class="panel-body">
                <table width="100%" class="table table-striped table-bordered table-hover">
                    <thead>
                    <tr>
                        <th>#번호</th>
                        <th>제목</th>
                        <th>작성자</th>
                        <th>작성일</th>
                    </tr>
                    </thead>

                    <c:forEach var="board" items="${list}">
                        <tr>
                            <td><c:out value="${board.boardid}" /></td>

                            <td><a class="move" href='<c:out value="${board.boardid}"/>'>
                                <c:out value="${board.title}"/> </a></td>

                            <td><c:out value="${board.writer}" /></td>
                            <td><fmt:formatDate pattern="yyyy-MM-dd"
                                                value="${board.regDate}"/></td>
                        </tr>
                    </c:forEach>
                </table>

                <!-- 검색조건 -->
                <div class="row">
                    <div class="col-lg-12">
                        <form action="/board/list" id="searchForm">
                            <select name="type">
                                <option value=""
                                        <c:out value="${pageMaker.cri.type == null ? 'selected' : '' }" />>--</option>
                                <option value="T"
                                        <c:out value="${pageMaker.cri.type == 'T' ? 'selected' : '' }" />>제목</option>
                                <option value="C"
                                        <c:out value="${pageMaker.cri.type == 'C' ? 'selected' : '' }" />>내용</option>
                                <option value="W"
                                        <c:out value="${pageMaker.cri.type == 'W' ? 'selected' : '' }" />>작성자</option>
                                <option value="TC"
                                        <c:out value="${pageMaker.cri.type == 'TC' ? 'selected' : '' }" />>제목or내용</option>
                                <option value="TW"
                                        <c:out value="${pageMaker.cri.type == 'TW' ? 'selected' : '' }" />>제목or작성자</option>
                                <option value="TCW"
                                        <c:out value="${pageMaker.cri.type == 'TCW' ? 'selected' : '' }" />>제목or내용or작성자</option>
                            </select>

                            <input type="text" name="keyword"
                                   value= <c:out value="${pageMaker.cri.keyword}" />>

                            <input type="hidden" name="pageNum" value='${pageMaker.cri.pageNum}'>
                            <input type="hidden" name="amount" value='${pageMaker.cri.amount}'>
                            <button class="btn btn-default">검색</button>
                        </form>
                    </div>
                </div>
                <!-- end 검색조건 -->

                <!-- 페이징 처리 -->
                <div class="container">
                    <div class='pull-right'>
                        <ul class="pagination">

                            <c:if test="${pageMaker.prev}">
                                <li class="page-item"><a class="page-link"
                                                         href="${pageMaker.startPage - 1}">이전</a></li>
                            </c:if>

                            <c:forEach var="num" begin="${pageMaker.startPage}"
                                       end="${pageMaker.endPage}">
                                <li class="page-item ${pageMaker.cri.pageNum == num ? "active" : ""} "><a class="page-link"
                                                                                                          href="${num}">${num}</a></li>
                            </c:forEach>

                            <c:if test="${pageMaker.next}">
                                <li class="page-item"><a class="page-link"
                                                         href="${pageMaker.endPage + 1}">다음</a></li>
                            </c:if>
                        </ul>
                    </div>
                </div>
                <!-- 페이징 종료 -->


                <form id="actionForm" action="/board/list" method="get">
                    <input type="hidden" name="pageNum" value= "${pageMaker.cri.pageNum}">
                    <input type="hidden" name="amount" value= "${pageMaker.cri.amount}">
                    <input type="hidden" name="keyword"
                           value= <c:out value="${pageMaker.cri.keyword}" />>
                    <input type="hidden" name="type"
                           value= <c:out value="${pageMaker.cri.type}" />>
                </form>


            </div>
            <!-- end panel-body -->
        </div>
        <!-- end panel -->
    </div>
    <!-- /.col-lg-12 -->
</div>
<!-- /.row -->

<!-- The Modal -->
<div class="modal" id="myModal">
    <div class="modal-dialog">
        <div class="modal-content">

            <!-- Modal Header -->
            <div class="modal-header">
                <h4 class="modal-title">Modal title</h4>
                <button type="button" class="close" data-dismiss="modal">&times;</button>
            </div>

            <!-- Modal body -->
            <div class="modal-body">
                처리가 완료되었습니다.
            </div>

            <!-- Modal footer -->
            <div class="modal-footer">
                <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary">Save Changes</button>
            </div>

        </div>
    </div>
</div> <!-- end The Modal -->



<%@ include file="../includes/footer.jsp" %>

<script type="text/javascript">
    $(document).ready(function(){

        let result = "<c:out value='${result}' />";

        checkModal(result);

        //브라우저의 현재 히스토리 항목을 새로운 상태로 대체.
        // 페이지를 새로고침 하거나 뒤로 가기 했을 때 모달창이 다시 표시되지 않도록 한다.
        history.replaceState({}, null, null);

        function checkModal(result){

            if(result == '' || history.state) return ;

            if(parseInt(result)>0){
                $(".modal-body").html("게시글 " + result + "번이 등록되었습니다.");
            }else{
                $(".modal-body").html("게시글 " + result);
            }

            $("#myModal").modal("show");
        } //end checkModal

        $("#regBtn").on("click", function(){
            self.location = "/board/register";
        });


        // 페이지 번호 이벤트 처리
        let actionForm = $("#actionForm");

        $(".page-item a").on("click", function(e){

            e.preventDefault();

            //console.log("click");

            actionForm.find("input[name='pageNum']").val($(this).attr("href"))
            actionForm.submit();
        }); // end 페이지 번호 이벤트 처리


        <%--상세페이지 이동시 pageNum, amount값 전달   <td><a class="move" href='<c:out value="${board.boardid}"/>'> --%>
        $(".move").on("click", function(e){

            e.preventDefault();

            // 기존 boardid 필드 제거
            actionForm.find("input[name='boardid']").remove();

            actionForm.append("<input type='hidden' name='boardid' value='" + $(this).attr("href") + "' >");

            actionForm.attr("action", "/board/get").submit();
            //actionForm.submit();

        }); //  end 상세페이지 이동시 pageNum, amout값 전달


        //검색 버튼 이벤트 처리
        let searchForm = $("#searchForm");

        $("#searchForm button").on("click", function(e){

            if(!searchForm.find("option:selected").val()){
                alert("검색 종류를 선택하세요");
                return false;
            }

            if(!searchForm.find("input[name='keyword']").val()){
                alert("키워드 입력하세요");
                return false;
            }

            //검색 조건 필터링된 상태에서 1페이지로 이동
            searchForm.find("input[name='pageNum']").val("1");

            e.preventDefault();

            searchForm.submit();

        });

        //end 검색 버튼 이벤트 처리

    });
</script>