package ezen.ezencompany.service;

import java.util.List;

import ezen.ezencompany.vo.AttributeVO;
import ezen.ezencompany.vo.BoardReaderVO;
import ezen.ezencompany.vo.BoardTypeVO;
import ezen.ezencompany.vo.BoardWriterVO;
import ezen.ezencompany.vo.CategoryVO;

public interface ManagementService {
	List<CategoryVO> getCategorys(); // 모든 카테고리 목록 반환.
	CategoryVO getCategory(int cidx);
	CategoryVO getCategory(String code);
	int addCategory(CategoryVO vo);
	int modifyCategory(CategoryVO vo);
	int removeCategory(int cidx);
	
	List<AttributeVO> getAttributes(); // 모든 속성 목록 반환 
	List<AttributeVO> getAttributes(int cidx); // 카테고리에 종속된 속성 목록 반환
	AttributeVO getAttribute(int aidx);
	int addAttribute(AttributeVO vo);
	int modifyAttribute(AttributeVO vo);
	int removeAttribute(int aidx);
	
	
	List<BoardTypeVO> getBoardTypeList();
	BoardTypeVO getBoardType(int btno);
	
	List<BoardReaderVO> getBoardReaderList();
	List<BoardReaderVO> getBoardReaderList(int bindex);
	
	List<BoardWriterVO> getBoardWriterList();
	List<BoardWriterVO> getBoardWriterList(int bindex);
	
	int addBoardType(BoardTypeVO vo);
	int modifyBoardType(BoardTypeVO vo);
	int removeBoardType(int bindex);
	
	int addBoardReader(BoardReaderVO vo);
	int addBoardReaders(List<BoardReaderVO> list);
	int removeBoardReader(int bindex);
	
	int addBoardWriter(BoardWriterVO vo);
	int addBoardWriters(List<BoardWriterVO> list);
	int removeBoardWriter(int bindex);

}
