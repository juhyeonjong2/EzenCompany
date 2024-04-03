package ezen.ezencompany.dao;

import java.util.HashMap;

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
	
	//인증번호받기를 누른경우 mno를 이메일로 가공
	public String getEmail2(int mno) {
		return sqlSession.selectOne(namespace+".getEmail2", mno);
	}
	
	//인증번호가 존재하는지 확인
	public int selectNum(String email) {
		return sqlSession.selectOne(namespace+".selectNum", email);
	}
	
	//인증번호를 insert
	public int insertNum(HashMap<String, Object> map) {
		return sqlSession.insert(namespace+".insertNum", map);
	}
	
	//인증번호를 update
	public int updateNum(HashMap<String, Object> map) {
		return sqlSession.update(namespace+".updateNum", map);
	}
	
	//인증번호가 일치하는지 확인
	public int checkCert(HashMap<String, Object> map) {
		return sqlSession.selectOne(namespace+".checkCert", map);
	}
	
	//비밀번호 재설정 클릭 시 아이디를 이메일로 가공
	public String getEmail(String mid) {
		return sqlSession.selectOne(namespace+".getEmail", mid);
	}
	
	//사원목록을 가져온다
	public List<MemberVO> employeeList(){
		return sqlSession.selectList(namespace+".employeeList");
	}
	
	//이메일로 그 사람의 정보를 MemberVO안에 집어넣는다
	public MemberVO getMember(String email) {
		return sqlSession.selectOne(namespace+".getMember", email);
	}
	
	//이메일로 그 사람의 정보를 MemberVO안에 집어넣는다
	public String getImg(int mno) {
		return sqlSession.selectOne(namespace+".getImg", mno);
	}
	
}
