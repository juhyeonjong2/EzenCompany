package ezen.ezencompany.vo;

public class BoardWriterVO {
	private int bindex;
	private int wirter; // cidx;
	private int widx; // aidx;
	public int getBindex() {
		return bindex;
	}
	public void setBindex(int bindex) {
		this.bindex = bindex;
	}
	public int getWirter() {
		return wirter;
	}
	public void setWirter(int wirter) {
		this.wirter = wirter;
	}
	public int getWidx() {
		return widx;
	}
	public void setWidx(int widx) {
		this.widx = widx;
	}
	
	// custom
	public void setCidx(int cidx) {
		this.wirter = cidx;
	}
	public int getCidx() {
		return wirter;
	}
	public int getAidx() {
		return widx;
	}
	public void setAidx(int aidx) {
		this.widx = aidx;
	}
	
	
}
