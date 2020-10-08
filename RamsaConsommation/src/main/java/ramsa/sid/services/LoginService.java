package ramsa.sid.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ramsa.sid.dao.UserRepository;
import ramsa.sid.entities.LoginModel;

@Service
public class LoginService {
	@Autowired
	private UserRepository userRepos;
	private LoginModel login;
	public  LoginModel FindUserByUername(String username) {
		return userRepos.FindByusername(username);
	}
	public LoginModel ValidateLogin(String username,String password) {
		 login=this.FindUserByUername(username);
		if(login!=null) {
			if(login.getPassword().equals(password)) {
				return login;
			}
		}
		return null;
	}
	public boolean SaveUser(String username,String password,String role) {
		login=this.FindUserByUername(username);
		if(login==null) {
			login=new LoginModel();
			login.setPassword(password);
			login.setUsername(username);
			login.setRoles(role);
			userRepos.save(login);
			return true;
		}
		return false;
	}
}
