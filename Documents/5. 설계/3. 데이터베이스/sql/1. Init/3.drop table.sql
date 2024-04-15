#모든 테이블을 삭제하기(잘못만든경우 다시만들기 위해)
use ezencompany;

set FOREIGN_KEY_CHECKS = 0;
drop table boardreader;
drop table boardwriter;
drop table chatting;
drop table attribute;
drop table blog;
drop table blogattach;
drop table blogreply;
drop table board;
drop table boardattach;
drop table boardtype;
drop table category;
drop table employeeoption;
drop table folder;
drop table joincert;
drop table member;
drop table memberattach;
drop table notification;
drop table reply;
drop table joinmail;
set FOREIGN_KEY_CHECKS = 1;
