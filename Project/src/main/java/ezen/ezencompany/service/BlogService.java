package ezen.ezencompany.service;

import ezen.ezencompany.vo.BlogVO;

public interface BlogService {

	int insert(BlogVO vo);
	BlogVO getLastOne(int mno);
}
