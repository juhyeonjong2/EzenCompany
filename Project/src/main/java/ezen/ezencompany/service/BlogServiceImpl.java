package ezen.ezencompany.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ezen.ezencompany.dao.BlogDAO;
import ezen.ezencompany.vo.AttributeVO;
import ezen.ezencompany.vo.BlogAttachVO;
import ezen.ezencompany.vo.BlogReplyVO;
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
	public BlogVO getLastOne(int mno, boolean force) {
		if(force) {
			return blogDAO.selectLastForce(mno);
		}
		return blogDAO.selectLast(mno);
	}

	// 
	@Override
	public List<MemberVO> blogUserListByRetired() {
			return blogDAO.selectRetirEmployees();
	}
	
	@Override
	public List<MemberVO> blogUserListByOption(int category, int attribute) {
		return blogDAO.selectOptionEmployees(category, attribute);
	}
	
	@Override
	public List<MemberVO> blogUserListByActive(int excludeMno) {
		return blogDAO.selectActiveEmployees(excludeMno);
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
				vo.setAttributeKey(aVo.getOtkey());
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
		List<AttributeVO> attributes = blogDAO.getAttributes();
		for(AttributeVO vo : attributes) {
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

	@Override
	public List<BlogVO> blogList(int mno, boolean isAll) {
		return blogDAO.selectBlogList(mno, isAll);
	}

	@Override
	public BlogVO selectOne(int bgno, boolean force) {
		return blogDAO.selectOne(bgno,  force);
	}

	@Override
	public List<BlogReplyVO> blogReplyList(int bgno) {
		return blogDAO.selectReplyList(bgno);
	}

	@Override
	public BlogReplyVO getReply(int bgrno) {
		return blogDAO.selectReply(bgrno);
	}

	@Override
	public int insertReply(BlogReplyVO vo) {
		return blogDAO.insertReply(vo);
	}

	@Override
	public int removeReply(int bgrno) {
		return blogDAO.removeReply(bgrno);
	}

	@Override
	public int modifyReply(BlogReplyVO vo) {
		return blogDAO.modifyReply(vo);
	}
	
	
	@Override
	public int insertFile(BlogAttachVO vo){
		return blogDAO.insertfile(vo);
	}

	@Override
	public List<BlogAttachVO> getFiles(int bgno) {
 
		return blogDAO.selectFiles(bgno);
	}

	@Override
	public BlogAttachVO getFile(int bgfno) {
		return blogDAO.selectFile(bgfno);
	}

	@Override
	public int modifyOne(BlogVO vo) {
		return blogDAO.modifyOne(vo);
	}

	@Override
	public int removeFile(int bgfno) {

		return blogDAO.deleteFile(bgfno);
	}

	@Override
	public int removeOne(int bgno) {
		return blogDAO.removeOne(bgno);
	}

	@Override
	public int removeFiles(int bgno) {
		return blogDAO.deleteFiles(bgno);
	}

	

}
