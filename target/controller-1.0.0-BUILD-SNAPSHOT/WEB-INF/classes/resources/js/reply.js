console.log("Reply Module............");

let replyService = (function() {

    // 댓글 등록
    function add(reply, callback, error) {
        $.ajax({
            type: 'post',
            url: '/replies/new',
            data: JSON.stringify(reply),
            contentType: 'application/json; charset=utf-8',
            success: function(result) {
                if (callback) callback(result, reply);
            },
            error: function(xhr, status, err) {
                if (error) error(err);
            }
        });
    }

    // 댓글 목록 불러오기 (페이징 포함)
    function getList(param, callback, error) {
        let testid = param.testid;
        let page = param.page || 1;

        $.ajax({
            type: 'get',
            url: '/replies/pages/' + testid + '/' + page,
            success: function(result) {
                console.log("🔥 서버 응답:", result);  // ← 이거 꼭 추가
                if (callback) callback(result.replyCnt, result.list);
            },
            error: function(xhr, status, err) {
                if (error) error(err);
            }
        });
    }

    // 댓글 삭제
    function remove(replyid, callback, error) {
        $.ajax({
            type: 'delete',
            url: '/replies/' + replyid,
            success: function(result) {
                if (callback) callback(result);
            },
            error: function(xhr, status, err) {
                if (error) error(err);
            }
        });
    }

    // 댓글 단건 조회
    function get(replyid, callback, error) {
        $.ajax({
            type: 'get',
            url: '/replies/' + replyid,
            success: function(result) {
                if (callback) callback(result);
            },
            error: function(xhr, status, err) {
                if (error) error(err);
            }
        });
    }

    // 댓글 수정
    function update(reply, callback, error) {
        $.ajax({
            type: 'put',
            url: '/replies/' + reply.replyid,
            data: JSON.stringify(reply),
            contentType: 'application/json; charset=utf-8',
            success: function(result) {
                if (callback) callback(result);
            },
            error: function(xhr, status, err) {
                if (error) error(err);
            }
        });
    }

    // 시간 포맷 변환
    function displayTime(timeValue) {
        let today = new Date();
        let timeGap = today.getTime() - timeValue;
        let dateObj = new Date(timeValue);

        if (timeGap < 1000 * 60 * 60 * 24) {
            let hh = dateObj.getHours();
            let mi = dateObj.getMinutes();
            let ss = dateObj.getSeconds();
            return [(hh > 9 ? '' : '0') + hh, ":", (mi > 9 ? '' : '0') + mi, ":", (ss > 9 ? '' : '0') + ss].join('');
        } else {
            let yy = dateObj.getFullYear();
            let mm = dateObj.getMonth() + 1;
            let dd = dateObj.getDate();
            return [yy, '/', (mm > 9 ? '' : '0') + mm, '/', (dd > 9 ? '' : '0') + dd].join('');
        }
    }

    // 공개 API
    return {
        add: add,
        getList: getList,
        remove: remove,
        get: get,
        update: update,
        displayTime: displayTime
    };

})();
