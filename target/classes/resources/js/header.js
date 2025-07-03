 const fortuneCookieModalWrapper = document.getElementById('fortuneCookieModalWrapper');

    // 부모 창(header.jsp가 포함된 home.jsp)에서 모달을 닫는 함수를 전역으로 정의해요.
    // cookie.jsp의 스크립트에서 window.parent.closeFortuneCookieModal()로 호출할 거예요.
    function closeFortuneCookieModal() {
    fortuneCookieModalWrapper.classList.add('hidden'); // 모달 틀 숨기기
    document.body.style.overflow = ''; // 뒷 배경 스크롤 허용
    fortuneCookieModalWrapper.innerHTML = ''; // 모달 내용 비우기 (다음 번에 다시 불러올 때 깨끗하게)
}

    // 1. "포춘쿠키" 메뉴 링크를 클릭했을 때 모달을 열고 cookie.jsp 내용을 불러와요.
    document.getElementById('openFortuneCookieModalLink').addEventListener('click', async function(event) {
    event.preventDefault(); // 기본 링크 동작(페이지 이동)을 막아요.

    // 모달 열기 전에 이전 내용이 있다면 비워줘요.
    fortuneCookieModalWrapper.innerHTML = '';

    // 모달 틀을 보이게 해요.
    fortuneCookieModalWrapper.classList.remove('hidden');
    document.body.style.overflow = 'hidden'; // 뒷 배경 스크롤 방지

    try {
    // '/cookie/modalContent' 주소로 cookie.jsp의 HTML 내용을 요청해요.
    const response = await fetch('/cookie/modalContent');
    if (!response.ok) {
    const errorResponseText = await response.text(); // 오류 응답 텍스트 확인
    console.error('모달 내용을 불러오는데 실패했습니다. 서버 응답:', response.status, errorResponseText);
    throw new Error('모달 내용을 불러오는데 실패했습니다.');
}
    const modalHtml = await response.text(); // HTML 내용을 텍스트로 받아와요.

    // 받아온 HTML 내용을 모달 틀 안에 삽입해요.
    fortuneCookieModalWrapper.innerHTML = modalHtml;

    const closeBtn = fortuneCookieModalWrapper.querySelector('#closeButtonInModal');
    if (closeBtn) {
    closeBtn.addEventListener('click', () => {
    closeFortuneCookieModal();
});
}

    let modalClickCount = 0;
    const cookieImg = fortuneCookieModalWrapper.querySelector('#cookieImageInModal');
    const fortuneMsg = fortuneCookieModalWrapper.querySelector('#fortuneMessageInModal');

    if (cookieImg && fortuneMsg) {
    cookieImg.addEventListener('click', function () {
        cookieImg.src = '/resources/images/fortune_cookie2.png';

    fetch('/cookie/random')
    .then(response => {
    if (!response.ok) throw new Error("서버 오류");
    return response.json();
})
    .then(data => {
    fortuneMsg.textContent = data.content;
})
    .catch(err => {
    console.error("명언 불러오기 실패:", err);
    fortuneMsg.textContent = "명언을 불러올 수 없습니다.";
});
});
}

    // 모달 어두운 배경 클릭 시 닫기
    // 이 이벤트 리스너는 모달이 열릴 때마다 다시 등록되므로,
    // 이전 onclick을 덮어쓰거나 addEventListener/removeEventListener 쌍으로 관리해야 안전해요.
    // 현재는 onclick으로 덮어쓰는 방식을 사용해요.
    fortuneCookieModalWrapper.onclick = function(event) {
    // 클릭된 요소가 모달 콘텐츠 영역이 아닌 경우에만 닫기
    // 즉, 어두운 배경을 클릭했을 때만 닫히게 합니다.
    if (event.target === fortuneCookieModalWrapper) {
    closeFortuneCookieModal();
}
};

} catch (error) {
    console.error('모달 내용을 불러오는 중 오류 발생:', error);
    fortuneCookieModalWrapper.innerHTML = '<div class="popup-content"><p>모달 내용을 불러올 수 없습니다.</p><button class="close-button" onclick="closeFortuneCookieModal()">닫기</button></div>';
}
});