let modalClickCount = 0;
const modalCookieImage = document.getElementById('cookieImageInModal');
const modalFortuneMessage = document.getElementById('fortuneMessageInModal');
const modalCloseButton = document.getElementById('closeButtonInModal');

// 포춘쿠키 그림을 클릭했을 때 실행되는 동작을 정의해요.
// 요소가 null이 아닐 때만 이벤트 리스너를 추가하도록 안전하게 체크해요.
if (modalCookieImage) {
    modalCookieImage.addEventListener('click', function() {
        console.log("포춘쿠키 그림 클릭됨! (모달 내용물 내부)"); // 로그 추가
        modalClickCount++;

        if (modalClickCount === 1) {
            console.log("두 번째 클릭! 명언 요청 시작... (모달 내용물 내부)"); // 로그 추가
            getFortuneMessageForModal();
        }
    });
}

// 닫기 버튼을 클릭했을 때 실행될 함수예요.
if (modalCloseButton) {
    modalCloseButton.addEventListener('click', function() {
        console.log("닫기 버튼 클릭됨! (모달 내용물 내부)"); // 로그 추가
        // 부모 창(header.jsp에 있는)의 closeFortuneCookieModal 함수를 호출해야 해요!
        if (typeof closeFortuneCookieModal === 'function') {
            closeFortuneCookieModal();
        }
    });
}

// 서버(스프링)에서 명언을 가져오는 함수예요. (이전과 동일)
async function getFortuneMessageForModal() {
    try {
        const response = await fetch('/cookie/random');

        if (!response.ok) {
            const errorText = await response.text(); // 오류 메시지 확인
            console.error('명언을 불러오는데 실패했어요. 서버 응답:', response.status, errorText);
            throw new Error('명언을 불러오는데 실패했어요. 서버에 문제가 있을 수도 있어요.');
        }

        const data = await response.json();
        console.log("명언 불러오기 성공:", data.content); // 로그 추가
        modalFortuneMessage.textContent = data.content;

    } catch (error) {
        console.error('명언을 불러오는 중 오류 발생:', error);
        modalFortuneMessage.textContent = '명언을 불러올 수 없습니다. 다시 시도해주세요.';
    }
}