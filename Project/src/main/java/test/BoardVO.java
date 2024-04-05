package test;

public class BoardVO<T> {
	private int bno;
	private T btitle;
	private String bcontent;
	
	public int getBno() {
		return bno;
	}
	public void setBno(int bno) {
		this.bno = bno;
	}
	public T getBtitle() {
		return btitle;
	}
	public void setBtitle(T btitle) {
		this.btitle = btitle;
	}
	public String getBcontent() {
		return bcontent;
	}
	public void setBcontent(String bcontent) {
		this.bcontent = bcontent;
	}
	
	
}
