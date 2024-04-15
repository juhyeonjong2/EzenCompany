package ezen.ezencompany.vo;

public class BoardReaderVO {
	private int bindex;
	private int reader; // cidx
	private int ridx; // aidx
	public int getBindex() {
		return bindex;
	}
	public void setBindex(int bindex) {
		this.bindex = bindex;
	}
	public int getReader() {
		return reader;
	}
	public void setReader(int reader) {
		this.reader = reader;
	}
	public int getRidx() {
		return ridx;
	}
	public void setRidx(int ridx) {
		this.ridx = ridx;
	}
	
	// custom
	public void setCidx(int cidx) {
		this.reader = cidx;
	}
	public int getCidx() {
		return reader;
	}
	public int getAidx() {
		return ridx;
	}
	public void setAidx(int aidx) {
		this.ridx = aidx;
	}
	
	
}
