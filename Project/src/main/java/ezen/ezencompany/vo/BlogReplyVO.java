package ezen.ezencompany.vo;

public class BlogReplyVO {
	private int bgrno;
	private int bgrpno;
	private String bgrcontent;
	private String bgrdate;
	private int mno;
	private int bgno;
	private String delyn;
	
	// addtional
	private String author;
	private boolean isEditable = false;
	private boolean isMaster =false;
	
	public int getBgrno() {
		return bgrno;
	}
	public void setBgrno(int bgrno) {
		this.bgrno = bgrno;
	}
	public int getBgrpno() {
		return bgrpno;
	}
	public void setBgrpno(int bgrpno) {
		this.bgrpno = bgrpno;
	}
	public String getBgrcontent() {
		return bgrcontent;
	}
	public void setBgrcontent(String bgrcontent) {
		this.bgrcontent = bgrcontent;
	}
	public String getBgrdate() {
		return bgrdate;
	}
	public void setBgrdate(String bgrdate) {
		this.bgrdate = bgrdate;
	}
	public int getMno() {
		return mno;
	}
	public void setMno(int mno) {
		this.mno = mno;
	}
	public int getBgno() {
		return bgno;
	}
	public void setBgno(int bgno) {
		this.bgno = bgno;
	}
	public String getDelyn() {
		return delyn;
	}
	public void setDelyn(String delyn) {
		this.delyn = delyn;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public boolean isEditable() {
		return isEditable;
	}
	public void setEditable(boolean isEditable) {
		this.isEditable = isEditable;
	}
	public boolean isMaster() {
		return isMaster;
	}
	public void setMaster(boolean isMaster) {
		this.isMaster = isMaster;
	}
	
	
}
