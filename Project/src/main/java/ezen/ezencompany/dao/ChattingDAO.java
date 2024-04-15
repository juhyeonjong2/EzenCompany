package ezen.ezencompany.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ezen.ezencompany.vo.AttributeVO;
import ezen.ezencompany.vo.ChatVO;
import ezen.ezencompany.vo.MemberVO;

@Repository
public class ChattingDAO {

	//sql을 사용하기 위해서 생성(자동주입)
	@Autowired
	SqlSession sqlSession;
	
	//Mapper의 경로를 적어줌
	private final String namespace = "ezen.ezencompany.mapper.chattingMapper";
	
	//사원 목록
	public List<AttributeVO> chattingList(){
		return sqlSession.selectList(namespace+".chattingList");
	};
	
	//모든 사원
	public List<MemberVO> detaleList(){
		return sqlSession.selectList(namespace+".detaleList");
	};
	
	//채팅방 찾기
	public String getRoom(HashMap<String, Object> map){
		return sqlSession.selectOne(namespace+".getRoom", map);
	};
	
	//채팅 내역 찾기
	public List<ChatVO> chattingStart(String chattingroom){
		return sqlSession.selectList(namespace+".chattingStart", chattingroom);
	};
	
	//채팅방 만들기
	public void chattingCreate(HashMap<String, Object> map){
		sqlSession.insert(namespace+".chattingCreate", map);
	};
	
	//채팅방 찾기
	public String getProfile(int anotherMno){
		return sqlSession.selectOne(namespace+".getProfile", anotherMno);
	};
	
	//채팅하기
	public void chatting(HashMap<String, Object> chatting){
		sqlSession.insert(namespace+".chatting", chatting);
	};
	
	//채팅 연결
	public void linkStart(int mno){
		sqlSession.update(namespace+".linkStart", mno);
	};
	
	//채팅 끊기
	public void linkEnd(int mno){
		sqlSession.update(namespace+".linkEnd", mno);
	};
	
	//링크 확인
	public int checkLink(int mno){
		return sqlSession.selectOne(namespace+".checkLink", mno);
	};
	
	//채팅방 찾기
	public String getName(int mno){
		return sqlSession.selectOne(namespace+".getName", mno);
	};
}
