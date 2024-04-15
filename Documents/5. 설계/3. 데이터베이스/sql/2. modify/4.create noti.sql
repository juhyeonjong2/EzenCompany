
set FOREIGN_KEY_CHECKS = 0;
drop table notification;
set FOREIGN_KEY_CHECKS = 1;

#알림 테이블
CREATE TABLE notification (
	nno int unsigned not null primary key auto_increment comment '알림번호',
	mno int unsigned not null comment '회원번호',
	foreign key(mno) references member(mno),
    code char(2) not null comment '알림코드',
    targetname varchar(10) not null comment '대상이름',
    nconfirm char(1) not null comment '알림 확인'
);