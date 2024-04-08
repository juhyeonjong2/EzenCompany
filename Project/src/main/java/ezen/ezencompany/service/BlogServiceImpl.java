package ezen.ezencompany.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ezen.ezencompany.dao.BlogDAO;
import ezen.ezencompany.vo.AttributeVO;
import ezen.ezencompany.vo.BlogUserVO;
import ezen.ezencompany.vo.BlogVO;
import ezen.ezencompany.vo.CategoryVO;
import ezen.ezencompany.vo.EmployeeOptionVO;
import ezen.ezencompany.vo.FolderVO;
import ezen.ezencompany.vo.MemberVO;

@Service
public class BlogServiceImpl implements BlogService {

	@Autowired 
	BlogDAO blogDAO;
	
	@Override
	public int insert(BlogVO vo) {
		return blogDAO.insert(vo);
	}

	@Override
	public BlogVO getLastOne(int mno) {
		return blogDAO.selectLast(mno);
	}

	// 
	@Override
	public List<BlogUserVO> blogUserListByRetired() {
			return blogDAO.selectRetirEmployees();
	}
	
	@Override
	public List<BlogUserVO> blogUserListByOption(int category, int attribute) {
		return blogDAO.selectOptionEmployees(category, attribute);
	}
	
	@Override
	public List<EmployeeOptionVO> getAdditionalOptions(int mno) {
		// mno에 해당하는 옵션 목록을 받고
		// 해시맵으로 변경해서 돌려줌.
		
		// 카테고리 데이터 받아오기
		List<CategoryVO> categorys = blogDAO.getCategorys();
		HashMap<Integer, CategoryVO> categoryMap = new HashMap<Integer, CategoryVO>();
		for(CategoryVO vo : categorys) {
			categoryMap.put(vo.getCidx(), vo);
		}
		
		// 속성 데이터 받아오기
		List<AttributeVO> attributes = blogDAO.getAttributes();
		HashMap<Integer, AttributeVO> attributeMap = new HashMap<Integer, AttributeVO>();
		for(AttributeVO vo : attributes) {
			attributeMap.put(vo.getAidx(), vo);
		}
		
		List<EmployeeOptionVO> employeeOptions = blogDAO.getAdditionalOptions(mno);
		// 순회하면서 aidx cidx에 해당하는 정보 채우기.
		for(EmployeeOptionVO vo : employeeOptions) {
			
			int cidx = vo.getCidx();
			if(categoryMap.containsKey(cidx))
			{
				CategoryVO cVo = categoryMap.get(cidx);
				vo.setCategoryCode(cVo.getCode());
				vo.setCategoryValue(cVo.getValue());
			}
			
			int aidx = vo.getAidx();
			if(attributeMap.containsKey(aidx))
			{
				AttributeVO aVo = attributeMap.get(aidx);
				vo.setAttributeKey(aVo.getKey());
				vo.setAttributeValue(aVo.getValue());
			}
		}
		
		return employeeOptions;
	}

	@Override
	public List<Integer> blogViewCategorys() {

		// 카테고리 데이터 받아오기
		List<CategoryVO> categorys = blogDAO.getCategorys();
		List<Integer> blogViewList = new ArrayList<Integer>();
		for(CategoryVO vo : categorys) {
			if(vo.getBlogView() == 1) {
				blogViewList.add(vo.getCidx());
			}
		}
		
		return blogViewList;
	}

	@Override
	public String getCategoryName(int category_pk) {
		// 카테고리 데이터 받아오기
		List<CategoryVO> categorys = blogDAO.getCategorys();
		for(CategoryVO vo : categorys) {
			if(vo.getCidx() == category_pk) {
				return vo.getValue();
			}
		}
		return "None";
	}
	@Override
	public String getAttributeName(int attribute_pk) {
		System.out.println(attribute_pk);
		List<AttributeVO> attributes = blogDAO.getAttributes();
		System.out.println("cnt:" + attributes.size());
		for(AttributeVO vo : attributes) {
			System.out.println(vo.getAidx());
			if(vo.getAidx()== attribute_pk) {
				return vo.getValue();
			}
		}
		return "None";
	}

	@Override
	public List<FolderVO> getFolders(int mno){
		return blogDAO.selectFolders(mno);
	}
	
	@Override
	public int makeFolder(FolderVO vo)
	{
		return blogDAO.insertFolder(vo);
	}
	
	
}
