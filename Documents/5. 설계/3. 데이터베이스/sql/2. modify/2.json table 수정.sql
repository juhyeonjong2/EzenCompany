use ezencompany;

#테이블 삭제(강제로 외래키 제거 후 생성 - 문제발생시 모든 테이블 전체삭제후 재생성 해야함)
set FOREIGN_KEY_CHECKS = 0;
drop table boardtype;
drop table board;
set FOREIGN_KEY_CHECKS = 1;

#게시판 타입
CREATE TABLE boardtype(
	bindex int unsigned not null primary key auto_increment comment '게시판타입번호',
    btname varchar(50) not null comment '게시판 타입 명'
);

#읽기 권한 테이블
CREATE TABLE boardreader(
	bindex int unsigned not null comment '게시판타입번호',
	foreign key(bindex) references boardtype(bindex),
    reader int unsigned not null comment '읽기 권한',
    ridx int unsigned not null comment '읽기관리번호'
);

#쓰기 권한 테이블
CREATE TABLE boardwriter(
	bindex int unsigned not null comment '게시판타입번호',
	foreign key(bindex) references boardtype(bindex),
    writer int unsigned not null comment '쓰기 권한',
    widx int unsigned not null comment '쓰기관리번호'
);

#게시글 테이블
CREATE TABLE board (
	bno int unsigned not null primary key auto_increment comment '글번호',
    bhit int unsigned not null comment '조회수',
    bdate timestamp not null comment '작성일',
    bcontent text comment '게시글 내용',
    brealcontent text comment '게시글 정리내용',
    btitle varchar(100) comment '게시글 제목',
    mno int  unsigned not null comment '회원번호',
    foreign key(mno) references member(mno),
    bindex int unsigned not null comment '게시판타입번호',
    foreign key(bindex) references boardtype(bindex)
); 