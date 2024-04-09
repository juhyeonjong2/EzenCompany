package ezen.ezencompany.vo;

public class ChatVO {
	private String chattingRoom; // uuid
	private String chat; //채팅내역
	private String cdate;
	
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
