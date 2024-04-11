package ezen.ezencompany.service;

import java.util.List;

import ezen.ezencompany.vo.AttributeVO;
import ezen.ezencompany.vo.CategoryVO;

public interface ManagementService {
	List<CategoryVO> getCategorys(); // 모든 카테고리 목록 반환.
	CategoryVO getCategory(int cidx);
	int addCategory(CategoryVO vo);
	int modifyCategory(CategoryVO vo);
	int removeCategory(int cidx);
	
	List<AttributeVO> getAttributes(); // 모든 속성 목록 반환 
	List<AttributeVO> getAttributes(int cidx); // 카테고리에 종속된 속성 목록 반환
	AttributeVO getAttribute(int aidx);
	int addAttribute(AttributeVO vo);
	int modifyAttribute(AttributeVO vo);
	int removeAttribute(int aidx);
	
}
