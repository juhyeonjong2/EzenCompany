package ezen.ezencompany.vo;



/*
id:1, pId:0, name:"스프링 일지", open:true},
{ id:11, pId:1, name:"스프링 일지 1" },
{ id:12, pId:1, name:"스프링 일지 2" },
{ id:2, pId:0, name:"임시", open:true},
{ id:21, pId:2, name:"임시 폴더" ,open:true},
    { id:221, pId:21, name:"[임시]임시로만든 게시물" }];
  */
    


public class BlogNodeVO {
	private int id;
	private int pid;
	private String name;
	private boolean isNode = true; // 폴더가 아님
	private boolean isOpen = false;
	private int blogNo;
	
	public BlogNodeVO() {
		super();
	}
	
	public BlogNodeVO(int id, int pid, String name) {
		this.id = id;
		this.pid = pid;
		this.name = name;
	}
	
	public int getBlogNo() {
		return blogNo;
	}

	public void setBlogNo(int blogNo) {
		this.blogNo = blogNo;
	}

	public boolean isNode() {
		return isNode;
	}

	public void setNode(boolean isNode) {
		this.isNode = isNode;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isOpen() {
		return isOpen;
	}
	public void setOpen(boolean isOpen) {
		
		setNode(false); // open을 조절한다는건 폴더임
		
		this.isOpen = isOpen;
	}
	
	
	
}
