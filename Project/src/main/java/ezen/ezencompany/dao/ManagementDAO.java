package ezen.ezencompany.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ezen.ezencompany.vo.AttributeVO;
import ezen.ezencompany.vo.BoardReaderVO;
import ezen.ezencompany.vo.BoardTypeVO;
import ezen.ezencompany.vo.BoardWriterVO;
import ezen.ezencompany.vo.CategoryVO;

@Repository
public class ManagementDAO {
	@Autowired
	SqlSession sqlSession;
	
	//Mapper의 경로를 적어줌
	private final String namespace = "ezen.ezencompany.mapper.managementMapper";
	
	// 카테고리 목록
	public List<CategoryVO> selectAllCategory(){
		return sqlSession.selectList(namespace+".selectAllCategory");
	}
	
	public CategoryVO selectCategory(int cidx){
		return sqlSession.selectOne(namespace+".selectCategory", cidx);
	}
	
	public CategoryVO selectCategoryByCode(String code){
		return sqlSession.selectOne(namespace+".selectCategoryByCode", code);
	}
	
	
	
	public int insertCategory(CategoryVO vo){
		return sqlSession.insert(namespace+".insertCategory", vo);
	}
	
	public int updateCategory(CategoryVO vo) {
		return sqlSession.update(namespace+".updateCategory", vo);
	}
	
	public int deleteCategory(int cidx) {
		return sqlSession.delete(namespace+".deleteCategory", cidx);
	}
	
	
	public List<AttributeVO> selectAllAttribute(){
		return sqlSession.selectList(namespace+".selectAllAttribute");
	}
	
	public List<AttributeVO> selectAttributes(int cidx){
		return sqlSession.selectList(namespace+".selectAttributes", cidx);
	}
	

	public AttributeVO selectAttribute(int aidx){
		return sqlSession.selectOne(namespace+".selectAttribute", aidx);
	}
	
	public int insertAttribute(AttributeVO vo){
		return sqlSession.insert(namespace+".insertAttribute", vo);
	}
	
	public int updateAttribute(AttributeVO vo) {
		return sqlSession.update(namespace+".updateAttribute", vo);
	}
	
	public int deleteAttribute(int aidx) {
		return sqlSession.delete(namespace+".deleteAttribute", aidx);
	}
	
	// 게시판 관리
	public List<BoardTypeVO> selectAllBoardType(){
		return sqlSession.selectList(namespace+".selectAllBoardType");
	}
	
	public BoardTypeVO selectBoardType(int btno) {
		return sqlSession.selectOne(namespace+".selectBoardType", btno);
	}
	
	public List<BoardReaderVO> selectAllBoardReader(){
		return sqlSession.selectList(namespace+".selectAllBoardReader");
	}
	
	public List<BoardReaderVO> selectBoardReaders(int bindex){
		return sqlSession.selectList(namespace+".selectBoardReaders", bindex);
	}
	
	public List<BoardWriterVO> selectAllBoardWriter(){
		return sqlSession.selectList(namespace+".selectAllBoardWriter");
	}
	
	public List<BoardWriterVO> selectBoardWriters(int bindex){
		return sqlSession.selectList(namespace+".selectBoardWriters", bindex);
	}
	
	public int insertBoardType(BoardTypeVO vo){
		return sqlSession.insert(namespace+".insertBoardType", vo);
	}
	
	public int updateBoardType(BoardTypeVO vo){
		return sqlSession.update(namespace+".updateBoardType", vo);
	}
	
	public int deleteBoardType(int index ){
		return sqlSession.delete(namespace+".deleteBoardType", index);
	}
	
	
	// erader에 업데이트는 없음. 업데이트 방법은 bindex에 포함하는 모든 목록을 지우고, 다시 추가하는게 업데이트)
	public int insertBoardReader(BoardReaderVO vo){
		return sqlSession.insert(namespace+".insertBoardReader", vo);
	}
	
	public int insertBoardReaders(List<BoardReaderVO> list){
		return sqlSession.insert(namespace+".insertBoardReaders", list);
	}
	
	public int deleteBoardReader(int index ){
		return sqlSession.delete(namespace+".deleteBoardReader", index);
	}
	
	// writer에 업데이트는 없음. 업데이트 방법은 bindex에 포함하는 모든 목록을 지우고, 다시 추가하는게 업데이트)
	public int insertBoardWriter(BoardWriterVO vo){
		return sqlSession.insert(namespace+".insertBoardWriter", vo);
	}
	
	public int insertBoardWriters(List<BoardWriterVO> list){
		return sqlSession.insert(namespace+".insertBoardWriters", list);
	}
	
	public int deleteBoardWriter(int index ){
		return sqlSession.delete(namespace+".deleteBoardWriter", index);
	}

	
	
	
}
