use ezencompany;

CREATE TABLE member (
	mno int unsigned not null primary key auto_increment comment '회원번호',
    mid varchar(20) unique comment '아이디',
    mpassword varchar(300) comment '비밀번호',
    mname text not null comment '이름',
    email varchar(100) not null unique comment '이메일',
    authority varchar(10) not null comment '권한',
    joindate timestamp comment '가입일',
    enabled int comment '탈퇴 여부',
    mphone varchar(13) comment '연락처'
);

CREATE TABLE boardType(
	bindex int unsigned not null primary key auto_increment comment '게시판타입번호',
    btname varchar(50) unique not null comment '게시판 타입 명',
    reader JSON comment '읽기 권한',
    writer JSON comment '쓰기 권한'
);

CREATE TABLE board (
	bno int unsigned not null primary key auto_increment comment '글번호',
    bhit int unsigned not null comment '조회수',
    bdate timestamp not null comment '작성일',
    bcontent text comment '게시글 내용',
    btitle varchar(100) comment '게시글 제목',
    mno int  unsigned not null comment '회원번호',
    foreign key(mno) references member(mno),
    bindex int unsigned not null comment '게시판타입번호',
    foreign key(bindex) references boardType(bindex)
); 

CREATE TABLE reply(
	rno int unsigned not null primary key auto_increment comment '댓글번호',
    rdate timestamp not null comment '작성일',
    rcontent varchar(500) not null comment '내용',
    rpno int unsigned comment '부모댓글',
    delyn char(1) comment '삭제여부',
    bno int unsigned not null comment '글번호',
	foreign key(bno) references board(bno),
	mno int unsigned not null comment '회원번호',
	foreign key(mno) references member(mno)
);

CREATE TABLE boardAttach(
	bfno int unsigned not null primary key auto_increment comment '게시글파일번호',
	bfrealname varchar(100) not null comment '실제이름',
    bforeignname varchar(100) not null comment '외부이름',
	bno int unsigned not null comment '글번호',
	foreign key(bno) references board(bno)
);

CREATE TABLE folder(
	fno int unsigned not null primary key auto_increment comment '폴더 번호',
    pfno int unsigned comment '부모 폴더',
	fname varchar(50) not null comment '폴더명',
	mno int unsigned not null comment '회원번호',
	foreign key(mno) references member(mno)
);

CREATE TABLE blog(
	bgno int unsigned not null primary key auto_increment comment '블로그번호',
	blockyn char(1) comment '비공개 여부',
    bghit int unsigned not null comment '조회수',
    bgdate timestamp not null comment '작성일',
    bgcontent text comment '블로그 내용',
    bgtitle varchar(100) comment '블로그 제목',
    mno int  unsigned not null comment '회원번호',
    foreign key(mno) references member(mno),
    fno int unsigned not null comment '폴더번호',
    foreign key(fno) references folder(fno)
);

CREATE TABLE blogAttach(
	bgfno int unsigned not null primary key auto_increment comment '블로그파일번호',
	bgfrealname varchar(100) not null comment '실제이름',
    bgforeignname varchar(100) not null comment '외부이름',
	bgno int unsigned not null comment '블로그번호',
	foreign key(bgno) references blog(bgno)
);

CREATE TABLE blogReply(
	bgrno int unsigned not null primary key auto_increment comment '블로그댓글번호',
	bgrdate timestamp not null comment '작성일',
    bgrcontent varchar(500) not null comment '댓글내용',
    bgrpno int unsigned comment '부모댓글',
    delyn char(1) comment '삭제여부',
    bgno int unsigned not null comment '블로그번호',
	foreign key(bgno) references blog(bgno),
	mno int unsigned not null comment '회원번호',
	foreign key(mno) references member(mno)
);

# 회원가입 인증번호 테이블 생성
CREATE TABLE joinCert (
	email varchar(100) not null primary key comment '이메일',
    cert char(8) not null comment '인증번호',
	expiretime timestamp not null comment '만료일',
	verifyyn char(1) comment '인증유무'
);

#분류 테이블
CREATE TABLE category (
	code varchar(100) not null unique primary key comment '코드',
    value varchar(100) not null unique comment '분류',
    blogview boolean not null comment '블로그리스트 뷰'
);

#속성 테이블
CREATE TABLE attribute (
	code varchar(100) not null comment '코드',
    foreign key(code) references category(code),
    value varchar(100) comment '값',
    otkey varchar(100) not null comment '키'
);

#사원 옵션
CREATE TABLE employeeOption (
	mno int unsigned not null comment '회원번호',
	foreign key(mno) references member(mno),
	code varchar(100) not null comment '코드',
    foreign key(code) references category(code),
    value varchar(100) comment '옵션값'
);

#알림 테이블
CREATE TABLE notification (
	nno int unsigned not null primary key auto_increment comment '알림번호',
	mno int unsigned not null comment '회원번호',
	foreign key(mno) references member(mno),
    code char(2) not null comment '알림코드',
    targetmno int not null comment '대상회원 번호',
    nconfirm char(1) not null comment '알림 확인'
);

#프로필 사진
CREATE TABLE memberAttach (
	mfno int unsigned not null primary key auto_increment comment '프로필파일번호',
	mfrealname varchar(100) not null comment '실제이름',
    mforeignname varchar(100) not null comment '외부이름',
    rdate timestamp not null comment '등록일',
	mno int unsigned not null comment '회원번호',
	foreign key(mno) references member(mno)
);

#메일발송 테이블
CREATE TABLE joinMail (
	shortUrl varchar(100) not null unique comment '짧은 경로',
	mno int unsigned not null comment '회원번호',
	foreign key(mno) references member(mno)
);



