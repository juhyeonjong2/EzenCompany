ALTER TABLE board ADD COLUMN  brealcontent text comment '게시글 정리내용';
ALTER TABLE blog ADD COLUMN  bgrealcontent text comment '블로그 정리내용';
ALTER TABLE member ADD COLUMN  link int unsigned not null DEFAULT 1 comment '채팅접속여부';