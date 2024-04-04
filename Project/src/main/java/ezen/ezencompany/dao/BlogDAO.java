package ezen.ezencompany.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ezen.ezencompany.vo.BlogVO;

@Repository
public class BlogDAO {
	//sql을 사용하기 위해서 생성(자동주입)
		@Autowired
		SqlSession sqlSession;
		
		//Mapper의 경로를 적어줌
		private final String namespace = "ezen.ezencompany.mapper.blogMapper";
		
		// 블로그 포스트
		public int insert(ezen.ezencompany.vo.BlogVO vo){
			return sqlSession.insert(namespace+".postBlog", vo);
		}
		
		public BlogVO selectLast(int mno) {
			return sqlSession.selectOne(namespace+".selectLast", mno);
		}
}
