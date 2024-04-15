use EzenCompany;
# 더미 사원 추가 (완전한 버전)
INSERT INTO member (mid, mpassword, mname, email, authority, joindate, enabled, mphone) VALUES 
('test1', '$2a$10$BO8HGFguQDhvUN8N8W7kE.GQJ.1U9XJk9L7SqsKCUHkgEEw6ELo1m', '이수현', 'sdfw@gmail.com', 'ROLE_USER', now(), 1, 010-5546-7784),
('test2', '$2a$10$BO8HGFguQDhvUN8N8W7kE.GQJ.1U9XJk9L7SqsKCUHkgEEw6ELo1m', '이현우', 'sd14fw@gmail.com', 'ROLE_USER', now(), 1, '010-9916-7624'),
('test3', '$2a$10$BO8HGFguQDhvUN8N8W7kE.GQJ.1U9XJk9L7SqsKCUHkgEEw6ELo1m', '김진우', 's14dfw@gmail.com', 'ROLE_USER', now(), 1, '010-1146-7424'),
('test4', '$2a$10$BO8HGFguQDhvUN8N8W7kE.GQJ.1U9XJk9L7SqsKCUHkgEEw6ELo1m', '박신영', 'fqwqw@gmail.com', 'ROLE_USER', now(), 1, '010-4236-7684');

#이사람들 사원정보 등록해줘야함
INSERT INTO employeeOption (mno, cidx, aidx) VALUES
(2, 1, 1),
(2, 2, 5),
(2, 3, 19),
(2, 4, 32),
(3, 1, 2),
(3, 2, 6),
(3, 3, 20),
(3, 4, 33),
(4, 1, 3),
(4, 2, 7),
(4, 3, 21),
(4, 4, 34),
(5, 1, 4),
(5, 2, 8),
(5, 3, 22),
(5, 4, 35);
