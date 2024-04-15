# 관리자 아이디생성 #admin - aaaa
INSERT INTO member (mid, mpassword, mname, email, authority,  joindate, enabled, mphone)
 VALUES('admin', '$2a$10$BO8HGFguQDhvUN8N8W7kE.GQJ.1U9XJk9L7SqsKCUHkgEEw6ELo1m' ,'관리자', 'aasdfwq@ezencompany.com', 'ROLE_ADMIN', now(), 1, '010-1154-1118');
 
 INSERT INTO employeeoption (mno, cidx, aidx) VALUES
(1, 1, 1),
(1, 2, 5),
(1, 3, 19),
(1, 4, 32);