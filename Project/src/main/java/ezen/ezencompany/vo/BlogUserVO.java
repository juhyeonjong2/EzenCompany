package ezen.ezencompany.vo;

public class BlogUserVO extends MemberVO{
	// 블로그 Home URL이 포함
	private String blogHome;

	public String getBlogHome() {
		return blogHome;
	}

	public void setBlogHome(String blogHome) {
		this.blogHome = blogHome;
	}
	
}
