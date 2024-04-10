package ezen.ezencompany.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ezen.ezencompany.vo.AttributeVO;
import ezen.ezencompany.vo.BlogAttachVO;
import ezen.ezencompany.vo.BlogReplyVO;
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
		
		public List<BlogVO> selectBlogList(int mno, boolean isAll) {
			if(isAll) {
				return sqlSession.selectList(namespace+".selectBlogListAll", mno);
			}
			return sqlSession.selectList(namespace+".selectBlogList", mno);
		}
		
		public BlogVO selectOne(int bgno, boolean force) {
			if(force) {
				return sqlSession.selectOne(namespace+".selectOneForce", bgno);
			}
			return sqlSession.selectOne(namespace+".selectOne", bgno);
		}

		// 댓글
		public List<BlogReplyVO> selectReplyList(int bgno) {
			return sqlSession.selectList(namespace+".selectReplyList", bgno);
		}
		
		public BlogReplyVO selectReply(int bgrno) {
			return sqlSession.selectOne(namespace+".selectReply", bgrno);
		}
		
		public int insertReply(BlogReplyVO vo){
			return sqlSession.insert(namespace+".insertReply", vo);
		}
		
		public int removeReply(int bgrno) {
			return sqlSession.update(namespace+".removeReply", bgrno);
		}
		
		public int modifyReply(BlogReplyVO vo) {
			return sqlSession.update(namespace+".modifyReply", vo);
		}
		
		// 첨부파일
		public int insertfile(BlogAttachVO vo){
			return sqlSession.insert(namespace+".insertfile", vo);
		}
		
		public List<BlogAttachVO> selectFiles(int bgno){
			return sqlSession.selectList(namespace+".selectFiles", bgno);
		}
		
		public BlogAttachVO selectFile(int bgfno){
			return sqlSession.selectOne(namespace+".selectFile", bgfno);
		}
		
}
