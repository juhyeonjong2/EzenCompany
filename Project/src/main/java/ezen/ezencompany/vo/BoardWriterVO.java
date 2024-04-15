package ezen.ezencompany.vo;

public class BoardWriterVO {
	private int bindex;
	private int writer; // cidx;
	private int widx; // aidx;
	public int getBindex() {
		return bindex;
	}
	public void setBindex(int bindex) {
		this.bindex = bindex;
	}
	
	public int getWidx() {
		return widx;
	}
	public void setWidx(int widx) {
		this.widx = widx;
	}
	
	public int getWriter() {
		return writer;
	}
	public void setWriter(int writer) {
		this.writer = writer;
	}
	// custom
	public void setCidx(int cidx) {
		this.writer = cidx;
	}
	public int getCidx() {
		return writer;
	}
	public int getAidx() {
		return widx;
	}
	public void setAidx(int aidx) {
		this.widx = aidx;
	}
	
	
}
