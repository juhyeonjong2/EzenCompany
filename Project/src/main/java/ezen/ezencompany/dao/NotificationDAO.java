package ezen.ezencompany.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ezen.ezencompany.vo.NotificationVO;

@Repository
public class NotificationDAO {
	
	@Autowired
	SqlSession sqlSession;
	
	//Mapper의 경로를 적어줌
	private final String namespace = "ezen.ezencompany.mapper.notificationMapper";
	
	//읽지 않은 알림을 가져옴
	public List<NotificationVO> getNoti(int mno){
		return sqlSession.selectList(namespace+".getNoti", mno);
	}
	
	//가져온 알림들을 읽음표시함
	public void checkNoti(int nno){
		 sqlSession.update(namespace+".checkNoti", nno);
	}
}
