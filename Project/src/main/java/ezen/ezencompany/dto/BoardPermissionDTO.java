package ezen.ezencompany.dto;

import java.util.ArrayList;
import java.util.List;

import ezen.ezencompany.vo.AttributeVO;
import ezen.ezencompany.vo.CategoryVO;

public class BoardPermissionDTO {
	
	CategoryVO category = new CategoryVO();
	List<AttributeVO> attributes = new ArrayList<AttributeVO>();
	
	public CategoryVO getCategory() {
		return category;
	}
	public void setCategory(CategoryVO category) {
		this.category = category;
	}
	public List<AttributeVO> getAttributes() {
		return attributes;
	}
	public void setAttributes(List<AttributeVO> attributes) {
		this.attributes = attributes;
	}
	
	
}
