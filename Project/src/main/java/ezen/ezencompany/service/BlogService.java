package ezen.ezencompany.service;


import java.util.List;

import ezen.ezencompany.vo.BlogAttachVO;
import ezen.ezencompany.vo.BlogReplyVO;
import ezen.ezencompany.vo.BlogVO;
import ezen.ezencompany.vo.EmployeeOptionVO;
import ezen.ezencompany.vo.FolderVO;
import ezen.ezencompany.vo.MemberVO;

public interface BlogService {

	int insert(BlogVO vo);
	BlogVO getLastOne(int mno, boolean force);
	List<EmployeeOptionVO> getAdditionalOptions(int mno); // 직종등의 추가 정보 반환
	List<Integer> blogViewCategorys(); // 블로그에 표시할 카테고리 반환.
	String getCategoryName(int category_pk); // 카테고리의 이름을 반환.
	String getAttributeName(int attribute_pk); // 속성의 이름을 반환.
	List<MemberVO> blogUserListByOption(int categoroy, int attribute); // 특정 분류중 속성이 같은 유저를 모두 반환(ex 직군-프로그래밍)
	List<MemberVO> blogUserListByRetired();	// 퇴사한 사원 목록
	List<MemberVO> blogUserListByActive();

	List<FolderVO> getFolders(int mno);
	int makeFolder(FolderVO vo);
	List<BlogVO> blogList(int mno, boolean isAll); // isAll이 true이면 비공개 블로그도 가지고온다. 
	BlogVO selectOne(int bgno, boolean force);
	
	List<BlogReplyVO> blogReplyList(int bgno);
	BlogReplyVO getReply(int bgrno);
	int insertReply(BlogReplyVO vo);
	int removeReply(int bgrno);
	int modifyReply(BlogReplyVO vo);
	int insertfile(BlogAttachVO vo);
	List<BlogAttachVO> getFiles(int bgno);
	BlogAttachVO getFile(int bgfno);

}
