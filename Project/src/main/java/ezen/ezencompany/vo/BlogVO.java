package ezen.ezencompany.vo;

public class BlogVO{

	private int bgno;
	private String blockyn;
	private int bghit;
	private String bgdate;
	private String bgcontent;
	private String bgrealcontent;
	private String bgtitle;
	private int mno;
	private int fno;
	public int getBgno() {
		return bgno;
	}
	public void setBgno(int bgno) {
		this.bgno = bgno;
	}
	public String getBlockyn() {
		return blockyn;
	}
	public void setBlockyn(String blockyn) {
		this.blockyn = blockyn;
	}
	public int getBghit() {
		return bghit;
	}
	public void setBghit(int bghit) {
		this.bghit = bghit;
	}
	public String getBgdate() {
		return bgdate;
	}
	public void setBgdate(String bgdate) {
		this.bgdate = bgdate;
	}
	public String getBgcontent() {
		return bgcontent;
	}
	public void setBgcontent(String bgcontent) {
		this.bgcontent = bgcontent;
	}
	
	public String getBgrealcontent() {
		return bgrealcontent;
	}
	public void setBgrealcontent(String bgrealcontent) {
		this.bgrealcontent = bgrealcontent;
	}
	public String getBgtitle() {
		return bgtitle;
	}
	public void setBgtitle(String bgtitle) {
		this.bgtitle = bgtitle;
	}
	public int getMno() {
		return mno;
	}
	public void setMno(int mno) {
		this.mno = mno;
	}
	public int getFno() {
		return fno;
	}
	public void setFno(int fno) {
		this.fno = fno;
	}
	

	@Override
	public String toString() {
        return "bgno:" + bgno + ", " +
        		"blockyn:" + blockyn + ", " +
        		"bghit:" + bghit + ", " +
        		"bgdate:" + bgdate + ", " +
        		"bgcontent:" + bgcontent + ", " +
        		"bgrealcontent:" + bgrealcontent + ", " +
        		"bgtitle:" + bgtitle + ", " +
        		"mno:" + mno + ", " +
        		"fno:" + fno ;
	}
}
