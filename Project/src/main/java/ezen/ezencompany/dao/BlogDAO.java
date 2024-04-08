package ezen.ezencompany.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ezen.ezencompany.vo.AttributeVO;
import ezen.ezencompany.vo.BlogUserVO;
import ezen.ezencompany.vo.BlogVO;
import ezen.ezencompany.vo.CategoryVO;
import ezen.ezencompany.vo.EmployeeOptionVO;
import ezen.ezencompany.vo.FolderVO;

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
		
		public List<BlogUserVO> selectRetirEmployees(){
			return sqlSession.selectList(namespace+".selectRetireEmployees");
		}
		
		public List<BlogUserVO> selectOptionEmployees(int cidx, int aidx){
			EmployeeOptionVO paramVO = new EmployeeOptionVO();
			paramVO.setCidx(cidx);
			paramVO.setAidx(aidx);
			
			return sqlSession.selectList(namespace+".selectOptionEmployees", paramVO);
		}
		
		
		public List<EmployeeOptionVO> getAdditionalOptions(int mno){
			return sqlSession.selectList(namespace+".selectAdditionalOptions", mno);
		}
		
		public List<CategoryVO> getCategorys(){
			return sqlSession.selectList(namespace+".selectCategorys");
		}
		
		public List<AttributeVO> getAttributes(){
			return sqlSession.selectList(namespace+".selectAttributes");
		}
		
		public int insertFolder(FolderVO vo){
			return sqlSession.insert(namespace+".insertFolder", vo);
		}
		
		public List<FolderVO> selectFolders(int mno){
			return sqlSession.selectList(namespace+".selectFolders", mno);
		}

						
}