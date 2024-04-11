package ezen.ezencompany.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ezen.ezencompany.dao.ManagementDAO;
import ezen.ezencompany.vo.AttributeVO;
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

	
	
	

}
