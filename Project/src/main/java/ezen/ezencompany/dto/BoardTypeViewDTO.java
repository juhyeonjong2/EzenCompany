package ezen.ezencompany.dto;

import java.util.ArrayList;
import java.util.List;

public class BoardTypeViewDTO {
	private int bindex;
	private String btname;
	
	List<BoardPermissionDTO> readers = new ArrayList<BoardPermissionDTO>();
	List<BoardPermissionDTO> writers = new ArrayList<BoardPermissionDTO>();
	
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
	public List<BoardPermissionDTO> getReaders() {
		return readers;
	}
	public void setReaders(List<BoardPermissionDTO> readers) {
		this.readers = readers;
	}
	public List<BoardPermissionDTO> getWriters() {
		return writers;
	}
	public void setWriters(List<BoardPermissionDTO> writers) {
		this.writers = writers;
	}
	

	
	
}
