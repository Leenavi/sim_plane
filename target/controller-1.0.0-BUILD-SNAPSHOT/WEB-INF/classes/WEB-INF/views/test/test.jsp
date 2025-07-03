<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="../includes/header.jsp" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<link rel="stylesheet" href="<c:url value='/resources/dist/css/test.css' />" />


<input type="hidden" id="testid" value="${testid}" />
<c:choose>
    <c:when test="${not empty pageContext.request.userPrincipal}">
        <input type="hidden" id="replyer" value="<sec:authentication property='principal.username' />" />
    </c:when>
    <c:otherwise>
        <input type="hidden" id="replyer" value="" />
    </c:otherwise>
</c:choose>



<div class="container">
    <div class="test-wrapper"> <!-- ✅ 카드형 박스 wrapper -->

        <!-- ✅ 테스트 영역 -->
        <div id="test-section">
            <h2>심리테스트</h2>
            <div id="question-box"></div>
            <div id="choices-box"></div>
        </div>

        <!-- ✅ 결과 영역 -->
        <div id="result-section" style="display: none;">
            <h2>테스트 결과</h2>
            <div id="result-box"></div>
            <button onclick="restartTest()">다시 테스트하기</button>
        </div>

        <hr>

        <form action="/test/list" method="get" style="margin-bottom: 20px;">
            <button type="submit">목록</button>
        </form>

        <!-- ✅ 댓글 영역 -->
        <div id="comment-section">
            <h3>댓글</h3>
            <form id="comment-form">
                <textarea id="comment-content" rows="3" cols="50" placeholder="댓글을 입력하세요"></textarea>
                <button type="submit">작성</button>
            </form>
            <div id="comment-list"></div>
            <div id="pagination"></div>
        </div>

    </div> <!-- /.test-wrapper -->
</div>


<!-- ✅ JS -->
<script type="text/javascript">
    $(document).ready(function () {
        let testidValue = $("#testid").val();
        let loggedInUser = $("#replyer").val();
        let replyUL = $("#comment-list");
        let replyPageFooter = $(".panel-footer");
        let pageNum = 1;

        // ✅ 페이지 로드 시 댓글 목록
        showList(1);

        // ✅ 댓글 목록 함수
        function showList(page) {
            pageNum = page;

            replyService.getList({ testid: testidValue, page: page }, function (replyCnt, list) {
                console.log("✅ 댓글 수:", replyCnt);
                console.log("✅ 댓글 목록:", list);

                let str = "";

                for (let i = 0; i < list.length; i++) {
                    let reply = list[i];
                    console.log("💬 reply.replyer:", reply.replyer);
                    console.log("💬 loggedInUser:", loggedInUser);
                    console.log("🔍 reply 객체:", reply);

                    let isOwner = (loggedInUser && reply.replyer.trim() === loggedInUser.trim());

                    // ✅ 모든 템플릿을 한 줄로! 문자열 연결 방식으로 처리
                    str += '<div class="reply-box" data-replyid="' + reply.replyid + '">' +
                        '<p><strong>' + reply.replyer + '</strong> <small>' + replyService.displayTime(reply.replyDate) + '</small></p>' +
                        '<p class="reply-text">' + reply.reply + '</p>' +
                        (isOwner ? '<button class="edit-btn">수정</button><button class="delete-btn">삭제</button>' : '') +
                        '</div>';
                }

                replyUL.html(str);
                showReplyPage(replyCnt);
            });
        }




        // ✅ 댓글 등록
        $("#comment-form").on("submit", function (e) {
            e.preventDefault();

            let content = $("#comment-content").val().trim();
            if (!content) return alert("댓글을 입력하세요.");
            if (!loggedInUser) return alert("로그인 후 댓글 작성이 가능합니다.");

            let reply = {
                reply: content,
                replyer: loggedInUser,
                testid: testidValue
            };

            replyService.add(reply, function (result, sentReplyData) {
                alert("댓글 등록 완료");
                $("#comment-content").val("");
                showList(1); // 마지막 페이지로 이동
            });
        });

        // ✅ 댓글 수정
        $("#comment-list").on("click", ".edit-btn", function () {
            let box = $(this).closest(".reply-box");
            let replyTextEl = box.find(".reply-text");

            if (replyTextEl.length === 0) {
                console.warn("❗ .reply-text 요소가 없습니다.");
                return;
            }

            let text = replyTextEl.text().trim(); // ← 여기 text() 사용
            replyTextEl.replaceWith(`<textarea class="edit-area"></textarea>`);
            box.find(".edit-area").val(text); // ← 값은 .val()로 안전하게 넣음

            $(this).replaceWith(`<button class="save-btn">저장</button>`);
        });

        $("#comment-list").on("click", ".save-btn", function () {
            let box = $(this).closest(".reply-box");
            let replyid = box.data("replyid");
            let newReply = box.find(".edit-area").val();

            replyService.update({ replyid: replyid, reply: newReply }, function () {
                alert("수정 완료");
                showList(pageNum); // ✅ 전체 목록 갱신으로 처리
            });
        });

        // ✅ 댓글 삭제
        $("#comment-list").on("click", ".delete-btn", function () {
            if (!confirm("정말 삭제하시겠습니까?")) return;

            let box = $(this).closest(".reply-box");
            let replyid = box.data("replyid");

            replyService.remove(replyid, function () {
                alert("삭제 완료");
                showList(pageNum);
            });
        });

        // ✅ 페이징 처리
        function showReplyPage(replyCnt) {
            console.log("📌 페이징 함수 진입!");

            let paginationBox = document.querySelector("#pagination");

            if (!paginationBox) {
                console.warn("❌ #pagination 요소를 찾을 수 없습니다.");
                return;
            }

            if (replyCnt <= 10) {
                replyPageFooter.html(""); // 댓글 수가 10개 이하이면 페이징 출력 안 함
                return;
            }
            let endNum = Math.ceil(pageNum / 10.0) * 10;
            let startNum = endNum - 9;
            let realEnd = Math.ceil(replyCnt / 10); // 실제 마지막 페이지 계산
            endNum = Math.min(endNum, realEnd);     // ⭐ 실제 마지막 페이지까지만 표시
            let prev = startNum !== 1;
            let next = endNum * 10 < replyCnt;

            console.log("startNum:", startNum, "endNum:", endNum, "pageNum:", pageNum, "prev:", prev, "next:", next);


            let str = "<ul class='pagination'>";
            if (prev) str += `<li class='page-item'><a class='page-link' href='${startNum - 1}'>이전</a></li>`;
            for (let i = startNum; i <= endNum; i++) {
                let active = pageNum == i ? "active" : "";
                str += '<li class="page-item ' + active + '"><a class="page-link" href="' + i + '">' + i + '</a></li>';

            }
            if (next) str += `<li class='page-item'><a class='page-link' href='${endNum + 1}'>다음</a></li>`;
            str += "</ul>";

            $("#pagination").html(str);

            console.log("페이징 HTML:", str);
        }

        // 🔥 이벤트 위임을 document로 확장 (모든 동적 요소 대응)
        $(document).on("click", "#pagination li a", function (e) {
            e.preventDefault();
            const targetPage = $(this).attr("href");
            const pageNumToLoad = parseInt(targetPage);

            if (!isNaN(pageNumToLoad)) {
                showList(pageNumToLoad);
            } else {
                console.warn("유효하지 않은 페이지 번호:", targetPage);
            }
        });

    });
</script>

<!-- ✅ 외부 JS (replyService 정의되어 있어야 함) -->
<script src="<c:url value='/resources/js/test.js'/>"></script>
<script src="<c:url value='/resources/js/reply.js'/>"></script>

<script>
    $(document).ready(function () {
        testService.getQuestion(1, renderQuestion);
    });
</script>

<%@ include file="../includes/footer.jsp" %>