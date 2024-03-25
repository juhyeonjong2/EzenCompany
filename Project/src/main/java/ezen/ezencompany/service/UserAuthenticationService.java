package ezen.ezencompany.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import ezen.ezencompany.vo.UserVO;

//시큐리티 인터셉터에 걸치게 되는 경우 이게 처음으로 실행된다
public class UserAuthenticationService implements UserDetailsService {
	
	//autowried해서 만들어도 가능. 단 지금은 아래에 생성자를 만들어서 사용하고 있다.
	SqlSession sqlSession;
	//기본생성자 (혹시 몰라서 만듬)
	public UserAuthenticationService() {}
	//security-context에서 constructor-arg이부분이 생성자니까 그거 때매 만듬
	public UserAuthenticationService(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Map<String,Object> user 
			= sqlSession.selectOne("ezen.ezencompany.mapper.userMapper.selectLogin",username);

		if(user == null) {
			System.out.println("user::"+user);
			throw new UsernameNotFoundException(username);
		}
		
		List<GrantedAuthority> authority = new ArrayList<>();
		authority.add(new SimpleGrantedAuthority(user.get("authority").toString()));

		//생성자 호출
		UserVO vo = new UserVO(user.get("username").toString()
							   ,user.get("mpassword").toString()
							   ,(Integer)Integer.valueOf(user.get("enabled").toString()) == 1
							   ,true
							   ,true
							   ,true
							   ,authority
							   ,user.get("authority").toString());
		
		return vo;
	}

}