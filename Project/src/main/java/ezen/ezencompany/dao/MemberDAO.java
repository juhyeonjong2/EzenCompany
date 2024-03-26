package ezen.ezencompany.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ezen.ezencompany.vo.MemberVO;

@Repository
public class MemberDAO {
	
	//sql을 사용하기 위해서 생성(자동주입)
	@Autowired
	SqlSession sqlSession;
	
	//Mapper의 경로를 적어줌
	private final String namespace = "ezen.ezencompany.mapper.memberMapper";
	
	//사용할 sql문을 호출함
	//중복체크용 sql호출
	public int checkID(String text){
		return sqlSession.selectOne(namespace+".checkID", text);
	}
	
	//mno변환용 sql호출
	public int requestMno(String url) {
		return sqlSession.selectOne(namespace+".requestMno", url);
	}
	
	//관리자에 의해 만들어진 계정에 업데이트
	public int joinOk(MemberVO vo) {
		return sqlSession.update(namespace+".joinOk", vo);
	}
	
	//비밀번호 재설정 클릭 시 아이디를 이메일로 가공
	public String getEmail(String mid) {
		return sqlSession.selectOne(namespace+".getEmail", mid);
	}
	
	//사원목록을 가져온다
	public List<MemberVO> employeeList(){
		return sqlSession.selectList(namespace+".employeeList");
	}
}
