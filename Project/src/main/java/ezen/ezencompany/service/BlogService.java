package ezen.ezencompany.service;


import java.util.List;

import ezen.ezencompany.vo.BlogUserVO;
import ezen.ezencompany.vo.BlogVO;
import ezen.ezencompany.vo.EmployeeOptionVO;
import ezen.ezencompany.vo.FolderVO;

public interface BlogService {

	int insert(BlogVO vo);
	BlogVO getLastOne(int mno);
	List<EmployeeOptionVO> getAdditionalOptions(int mno); // 직종등의 추가 정보 반환
	List<Integer> blogViewCategorys(); // 블로그에 표시할 카테고리 반환.
	String getCategoryName(int category_pk); // 카테고리의 이름을 반환.
	String getAttributeName(int attribute_pk); // 속성의 이름을 반환.
	List<BlogUserVO> blogUserListByOption(int categoroy, int attribute); // 특정 분류중 속성이 같은 유저를 모두 반환(ex 직군-프로그래밍)
	List<BlogUserVO> blogUserListByRetired();	// 퇴사한 사원 목록
	List<FolderVO> getFolders(int mno);
	int makeFolder(FolderVO vo);
	
	
	
	
	
	
}
