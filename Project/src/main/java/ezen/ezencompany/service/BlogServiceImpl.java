package ezen.ezencompany.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ezen.ezencompany.dao.BlogDAO;
import ezen.ezencompany.vo.BlogVO;

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
	
	
}
