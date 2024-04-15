package ezen.ezencompany.dao;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AdminDAO {
	
	//sql을 사용하기 위해서 생성(자동주입)
	@Autowired
	SqlSession sqlSession;
	
	//Mapper의 경로를 적어줌
	private final String namespace = "ezen.ezencompany.mapper.adminMapper";
	
	//1차로 member에 등록
	public int memberRegistration(Map<String, Object> member){
		return sqlSession.insert(namespace+".memberRegistration", member);
	}
	
	//2차로 mno를가져옴 (email은 고유함)
	public int getLastMno(Map<String, Object> member) {
		return sqlSession.selectOne(namespace+".getLastMno", member);
	}
	
	//3차로 사원옵션에 등록
	public int employeeRegistration(HashMap<String, Object> map){
		return sqlSession.insert(namespace+".employeeRegistration", map);
	}
	
	//짧은 경로 생성
	public int insertShortUrl(HashMap<String, Object> shortUrl){
		return sqlSession.insert(namespace+".insertShortUrl", shortUrl);
	}
	
	//email로 mno 구하기
	public int getMno(String email) {
		return sqlSession.selectOne(namespace+".getMno", email);
	}
	
	//프로필 이미지가 존재하는지 확인
	public int searchMno(int mno) {
		return sqlSession.selectOne(namespace+".searchMno", mno);
	}
	
	//프로필 이미지를 insert
	public void insertImg(Map<String, Object> map) {
		sqlSession.insert(namespace+".insertImg", map);
	}
	
	//프로필 이미지를 update
	public void updateImg(Map<String, Object> map) {
		sqlSession.update(namespace+".updateImg", map);
	}
	
	//cidx 찾기
	public int searchOption(Map<String, Object> opmap) {
		return sqlSession.selectOne(namespace+".searchOption", opmap);
	}
	
	//사원 옵션 업데이트
	public void employeeUpdate(Map<String, Object> opmap) {
		sqlSession.update(namespace+".employeeUpdate", opmap);
	}
	
	//사원 옵션 인서트
	public void employeeInsert(Map<String, Object> opmap) {
		sqlSession.insert(namespace+".employeeInsert", opmap);
	}
	
	//멤버 업데이트
	public void memberUpdate(Map<String, Object> memberMap) {
		sqlSession.update(namespace+".memberUpdate", memberMap);
	}
	
	//회원탈퇴시 멤버 업데이트
	public void deleteMember(int mno) {
		sqlSession.update(namespace+".deleteMember", mno);
	}
	
	//회원탈퇴 확인
	public int checkDelete(int mno) {
		return sqlSession.selectOne(namespace+".checkDelete", mno);
	}
	
	//해당인물의 짧은 경로 구하기
	public String getUrl(int mno) {
		return sqlSession.selectOne(namespace+".getUrl", mno);
	}
}
