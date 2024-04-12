package ezen.ezencompany.vo;

public class ChatVO {
	private String chattingRoom; // uuid
	private String chat; //채팅내역
	private String cdate;
	private int mno;
	private int cindex;
	
	public int getMno() {
		return mno;
	}
	public void setMno(int mno) {
		this.mno = mno;
	}
	public int getCindex() {
		return cindex;
	}
	public void setCindex(int cindex) {
		this.cindex = cindex;
	}
	public String getChattingRoom() {
		return chattingRoom;
	}
	public void setChattingRoom(String chattingRoom) {
		this.chattingRoom = chattingRoom;
	}
	public String getChat() {
		return chat;
	}
	public void setChat(String chat) {
		this.chat = chat;
	}
	public String getCdate() {
		return cdate;
	}
	public void setCdate(String cdate) {
		this.cdate = cdate;
	}
}
