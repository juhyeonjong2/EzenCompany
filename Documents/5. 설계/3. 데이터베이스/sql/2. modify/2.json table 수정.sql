use ezencompany;

#테이블 삭제(강제로 외래키 제거 후 생성 - 문제발생시 모든 테이블 전체삭제후 재생성 해야함)
set FOREIGN_KEY_CHECKS = 0;
drop table boardtype;
drop table board;
set FOREIGN_KEY_CHECKS = 1;


#읽기 권한 테이블
CREATE TABLE boardReader(
	bindex int unsigned not null comment '게시판타입번호',
    btname varchar(50) not null comment '게시판 타입 명',
    reader int unsigned not null comment '읽기 권한',
    ridx int unsigned not null comment '읽기관리번호'
);

#쓰기 권한 테이블
CREATE TABLE boardWriter(
	bindex int unsigned not null comment '게시판타입번호',
    btname varchar(50) not null comment '게시판 타입 명',
    writer int unsigned not null comment '쓰기 권한',
    widx int unsigned not null comment '쓰기관리번호'
);

#게시글 테이블(게시판타입번호를 외래키로 만들지 않은 이유는 외래키로 만들면 코드가 더 복잡해져서 시간이 더 걸릴거 같아 제외함 (없어도 inner join은 사용가능하고 게시판타입번호는 어차피 똑같으니 문제는 없음))
#(코드가 더 복잡해지지만 논리관계를 정확하게 하려면 토탈테이블을 1개 더 만들어서 관리 -> 회의 후 정확히 결정)
CREATE TABLE board (
	bno int unsigned not null primary key auto_increment comment '글번호',
    bhit int unsigned not null comment '조회수',
    bdate timestamp not null comment '작성일',
    bcontent text comment '게시글 내용',
    btitle varchar(100) comment '게시글 제목',
    mno int  unsigned not null comment '회원번호',
    foreign key(mno) references member(mno),
    bindex int unsigned not null comment '게시판타입번호'
); 