package ramsa.sid.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ramsa.sid.entities.LoginModel;
import ramsa.sid.services.LoginService;
@CrossOrigin(origins = "*")
@RestController
public class LoginController {
	@Autowired
	private LoginService loginservice;
	@RequestMapping(value="/Login", method= RequestMethod.POST)
	public LoginModel  Login(@RequestBody LoginModel loginForm) {
		return (LoginModel) loginservice.ValidateLogin( loginForm.getUsername(), loginForm.getPassword());
	}
	@RequestMapping(value="/SaveUser", method= RequestMethod.POST)
	public Boolean  SaveUser(@RequestBody LoginModel loginForm) {
		return  loginservice.SaveUser( loginForm.getUsername(), loginForm.getPassword(),loginForm.getRoles());
	}
}
