package ezen.ezencompany.dto;

public class BoardTypeViewDTO {
	private int bindex;
	private String btname;
	BoardPermissionDTO reader = new BoardPermissionDTO();
	BoardPermissionDTO writer =  new BoardPermissionDTO();
	public int getBindex() {
		return bindex;
	}
	public void setBindex(int bindex) {
		this.bindex = bindex;
	}
	public String getBtname() {
		return btname;
	}
	public void setBtname(String btname) {
		this.btname = btname;
	}
	public BoardPermissionDTO getReader() {
		return reader;
	}
	public void setReader(BoardPermissionDTO reader) {
		this.reader = reader;
	}
	public BoardPermissionDTO getWriter() {
		return writer;
	}
	public void setWriter(BoardPermissionDTO writer) {
		this.writer = writer;
	}
	
	
	
}
