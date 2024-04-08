package ezen.ezencompany.vo;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

//시큐리티에서 사용할 VO
public class UserVO extends User{

	//생성자 만들기
	public UserVO(String username, String password, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities, String authority, String email,
			int mno, String mphone, String mname) {
		
			super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
			
			// security 외 필요한 유저 정보를 추가한다.(어디에 사용할 지는 연구 필요)
			this.mid = username;
			this.mpassword = password;
			this.authority = authority;
			this.email = email;
			this.mno = mno;
			this.mphone = mphone;
			this.mname = mname;
	}
	
	private int mno;
	private String mid;
	private String mpassword;
	private String mname;
	private String email;
	private String authority;
	private String mphone;
	private int enabled;
	
	
	public int getMno() {
		return mno;
	}
	public void setMno(int mno) {
		this.mno = mno;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public String getMpassword() {
		return mpassword;
	}
	public void setMpassword(String mpassword) {
		this.mpassword = mpassword;
	}
	public String getMname() {
		return mname;
	}
	public void setMname(String mname) {
		this.mname = mname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAuthority() {
		return authority;
	}
	public void setAuthority(String authority) {
		this.authority = authority;
	}
	public String getMphone() {
		return mphone;
	}
	public void setMphone(String mphone) {
		this.mphone = mphone;
	}
	public int getEnabled() {
		return enabled;
	}
	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}
	
	
}
