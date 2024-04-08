package ezen.ezencompany.vo;

public class FolderVO {
	private int fno;  // 폴더 번호
	private int pfno =0 ; // 부모 폴더 번호 : 0이면 최상위폴더.
	private String fname; // 폴더명
	private int mno; // 소유한 사원 번호
	public int getFno() {
		return fno;
	}
	public void setFno(int fno) {
		this.fno = fno;
	}
	
	public int getPfno() {
		return pfno;
	}
	public void setPfno(int pfno) {
		this.pfno = pfno;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public int getMno() {
		return mno;
	}
	public void setMno(int mno) {
		this.mno = mno;
	}
	
}
