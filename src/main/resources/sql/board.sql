CREATE DATABASE sim_plane_db;		-- 데이터베이스 생성
USE sim_plane_db; 		-- MySQL은 여러 개의 데이터베이스를 만들 수 있어 이 테이블을 어디 DB에 넣을 건지 말해줘야해서 기재

-- sim_member 테이블
CREATE TABLE sim_member (
                            memberid INT AUTO_INCREMENT PRIMARY KEY,      -- 회원번호 (PK, 시퀀스)
                            userid VARCHAR(20) NOT NULL UNIQUE,          -- 아이디 (CK, 후보키)
                            password VARCHAR(20) NOT NULL,                 -- 비밀번호
                            birthdate DATETIME NOT NULL,                       -- 생년월일
                            sex INT NOT NULL DEFAULT 0,                 -- 성별 (기본값 0)
                            name VARCHAR(20) NOT NULL                      -- 이름
);

INSERT INTO sim_member (userid, password, birthdate, sex, name) VALUES ('1', '1234', '19981112', 1, '홍길동');

SELECT * FROM sim_member;


-- sim_board 테이블
CREATE TABLE sim_board (
                           boardid INT AUTO_INCREMENT PRIMARY KEY,      -- 게시글번호(pk, 시퀸스)
                           title VARCHAR(50) NOT NULL,          -- 제목
                           content VARCHAR(3000) NOT NULL,                 -- 내용
                           regdate DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,       -- 등록일(날짜, 시간 자동 기재)
                           imagepath VARCHAR(500) NOT NULL           -- 이미지 경로
);
INSERT INTO sim_board (title, content, imagepath) VALUES ('심리테스트', '잘 팔렸음 좋겠다', 'images/sample1.jpg');

SELECT * FROM sim_board;


-- sim_reply 테이블
CREATE TABLE sim_reply (
                           replyid INT AUTO_INCREMENT PRIMARY KEY,      -- 댓글번호(pk, 시퀸스)
                           testid INT NOT NULL,          -- 테스트번호
                           reply VARCHAR(1000) NOT NULL,                 -- 댓글 내용
                           replydate DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,  -- 댓글 등록일(날짜, 시간 자동 기재)

                           FOREIGN KEY (testid) REFERENCES sim_test(testid) 	-- 테스트번호 외래키
);
INSERT INTO sim_reply (testid, reply) VALUES ('1', '와 난 전생에 고라니');

SELECT * FROM sim_reply;


-- sim_test 테이블
CREATE TABLE sim_test (
                          testid INT AUTO_INCREMENT PRIMARY KEY, 	-- 테스트번호
                          testname VARCHAR(100) NOT NULL		-- 테스트 이름
);
INSERT INTO sim_test (testname) VALUES ('나는 전생에 어떤 동물이였을까?');

SELECT * FROM sim_test;