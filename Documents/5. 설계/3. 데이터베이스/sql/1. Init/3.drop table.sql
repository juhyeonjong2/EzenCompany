#모든 테이블을 삭제하기(잘못만든경우 다시만들기 위해)
use ezencompany;

set FOREIGN_KEY_CHECKS = 0;
drop table boardReader;
drop table boardWriter;
drop table chatting;
drop table attribute;
drop table blog;
drop table blogAttach;
drop table blogReply;
drop table board;
drop table boardAttach;
drop table boardType;
drop table category;
drop table employeeOption;
drop table folder;
drop table joinCert;
drop table member;
drop table memberAttach;
drop table notification;
drop table reply;
drop table joinMail;
set FOREIGN_KEY_CHECKS = 1;
