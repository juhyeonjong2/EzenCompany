package ezen.ezencompany.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ezen.ezencompany.dao.ManagementDAO;
import ezen.ezencompany.vo.AttributeVO;
import ezen.ezencompany.vo.BoardReaderVO;
import ezen.ezencompany.vo.BoardTypeVO;
import ezen.ezencompany.vo.BoardWriterVO;
import ezen.ezencompany.vo.CategoryVO;

@Service
public class ManagementServiceImpl implements ManagementService {

	@Autowired
	ManagementDAO managementDAO;
	
	@Override
	public List<CategoryVO> getCategorys() {
		return managementDAO.selectAllCategory();
	}
	
	@Override
	public CategoryVO getCategory(int cidx) {
		return managementDAO.selectCategory(cidx);
	}
	
	@Override
	public CategoryVO getCategory(String code) {
		return managementDAO.selectCategoryByCode(code);
	}
	

	@Override
	public int addCategory(CategoryVO vo) {
		return managementDAO.insertCategory(vo);
	}

	@Override
	public int modifyCategory(CategoryVO vo) {
		return managementDAO.updateCategory(vo);
	}

	@Override
	public int removeCategory(int cidx) {
		return managementDAO.deleteCategory(cidx);
	}

	@Override
	public List<AttributeVO> getAttributes() {
		return managementDAO.selectAllAttribute();
	}

	@Override
	public List<AttributeVO> getAttributes(int cidx) {
		return managementDAO.selectAttributes(cidx);
	}

	@Override
	public AttributeVO getAttribute(int aidx) {
		return managementDAO.selectAttribute(aidx);
	}

	@Override
	public int addAttribute(AttributeVO vo) {
		return managementDAO.insertAttribute(vo);
	}

	@Override
	public int modifyAttribute(AttributeVO vo) {
		return managementDAO.updateAttribute(vo);
	}

	@Override
	public int removeAttribute(int aidx) {
		return managementDAO.deleteAttribute(aidx);
	}

	@Override
	public List<BoardTypeVO> getBoardTypeList() {
		return managementDAO.selectAllBoardType();
	}
	
	@Override
	public BoardTypeVO getBoardType(int btno) {
		return managementDAO.selectBoardType(btno);
	}

	@Override
	public List<BoardReaderVO> getBoardReaderList() {
		return managementDAO.selectAllBoardReader();
	}

	@Override
	public List<BoardWriterVO> getBoardWriterList() {
		return managementDAO.selectAllBoardWriter();
	}

	@Override
	public List<BoardReaderVO> getBoardReaderList(int bindex) {
		return managementDAO.selectBoardReaders(bindex);
	}

	@Override
	public List<BoardWriterVO> getBoardWriterList(int bindex) {
		return managementDAO.selectBoardWriters(bindex);
	}

	@Override
	public int addBoardType(BoardTypeVO vo) {
		return managementDAO.insertBoardType(vo);
	}

	@Override
	public int modifyBoardType(BoardTypeVO vo) {
		return managementDAO.updateBoardType(vo);
	}

	@Override
	public int removeBoardType(int bindex) {
		return managementDAO.deleteBoardType(bindex);
	}

	@Override
	public int addBoardReader(BoardReaderVO vo) {
		return managementDAO.insertBoardReader(vo);
	}
	
	@Override
	public int addBoardReaders(List<BoardReaderVO> list) {
		return managementDAO.insertBoardReaders(list);
	}

	@Override
	public int removeBoardReader(int bindex) {
		return managementDAO.deleteBoardReader(bindex);
	}

	@Override
	public int addBoardWriter(BoardWriterVO vo) {
		return managementDAO.insertBoardWriter(vo);
	}

	@Override
	public int removeBoardWriter(int bindex) {
		return managementDAO.deleteBoardWriter(bindex);
	}
	
	@Override
	public int addBoardWriters(List<BoardWriterVO> list) {
		return managementDAO.insertBoardWriters(list);
	}
	

}
