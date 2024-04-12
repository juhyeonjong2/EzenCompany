CREATE TABLE chatting(
	cindex int unsigned not null primary key auto_increment comment '채팅번호',
	chattingroom varchar(100) not null comment '채팅방', 
	chat text comment '채팅로그',
	cdate timestamp not null comment '채팅일자',
	mno int unsigned not null comment '회원번호',
	foreign key(mno) references member(mno)
);