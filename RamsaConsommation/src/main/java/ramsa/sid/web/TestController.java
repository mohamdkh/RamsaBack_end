package ramsa.sid.web;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RequestMapping("test/")
@RestController
public class TestController {
	@GetMapping(path="home")
	public String GetAvailableYears(){
		return "2019";
	}
}
