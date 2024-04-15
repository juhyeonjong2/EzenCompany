use EzenCompany;

# 분류 추가
INSERT INTO category (code, value, blogview) VALUES 
('JOB', '직무', true),
('POSITION', '직위', false),
('OCCUPATION', '직종', false),
('DEPARTMENT', '부서', false);

# 직무 -속성 추가
INSERT INTO attribute (cidx, otkey, value) VALUES
(1, 'Development', '개발'),
(1, 'Management Support', '경영지원'),
(1, 'Service', '서비스'),
(1, 'Business', '사업');

# 직위 -속성 추가
INSERT INTO attribute (cidx, otkey, value) VALUES
(2, 'Intern'                 , '인턴'),
(2, 'Staff'                  , '사원'),
(2, 'Associate'              , '주임'),
(2, 'Associate Manager'      , '대리'),
(2, 'General Manager'        , '과장'),
(2, 'Deputy General Manager' , '차장'),
(2, 'General Manager'        , '부장'),
(2, 'Director'               , '이사'),
(2, 'Managing Director'      , '상무'),
(2, 'Deputy Vice President'  , '전무'),
(2, 'Vice President'         , '부사장'),
(2, 'President'              , '사장'),
(2, 'Vice Chairman'          , '부회장'),
(2, 'Chairman'               , '회장');


#직종 -속성 추가
INSERT INTO attribute (cidx, otkey, value) VALUES
(3, 'System Design'      , '시스템 기획'),
(3, 'Level Design'       , '레벨 기획'),
(3, 'Screenwriter'       , '시나리오 작가'),
(3, 'Client Programmer'  , '클라이언트 프로그래머'),
(3, 'Server Programmer'  , '서버 프로그래머'),
(3, 'Concept Art'        , '콘셉 그래픽 디자이너'),
(3, '3D Modeler'         , '3D 모델러'),
(3, 'Animator'           , '에니메이터'),
(3, 'Effecter'           , '비주얼 이펙터'),
(3, 'Sound'              , '사운드 제작'),
(3, 'Business'           , '영업'),
(3, 'Technical Support'  , '기술지원'),
(3, 'Management Support' , '경영지원');


#부서 -속성 추가
INSERT INTO attribute (cidx, otkey, value) VALUES
(4, 'Management Support'        , '경영지원'),
(4, 'Technical Support'         , '기술지원'),
(4, 'Technical Research Center' , '기술연구소'),
(4, 'DevelopmentA'              , '개발1팀'),
(4, 'DevelopmentB'              , '개발2팀');