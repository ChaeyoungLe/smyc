package kh.spring.project;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	@RequestMapping("/")
	public String home() {
		return "index";
	}

	@RequestMapping("/signup")
	public String signup() {
		return "login/signup";
	}

	@RequestMapping("/signin")
	public String signin() {
		return "login/signin";
	}
	
	@RequestMapping("/clothesUpload")
	public String clothesUpload() {
		return "member/closet/clothesUpload";
	}
	
	@RequestMapping("/myCloset")
	public String myCloset() {
		return "member/closet/myCloset";
	}
	
	@RequestMapping("/closetUpload")
	public String closetUpload() {
		return "member/closet/closetUpload";
	}
	
	@RequestMapping("/idFind")
	public String idFind() {
		return "login/idFind";
	}
	
	@RequestMapping("/pwFind")
	public String pwFind() {
		return "login/pwFind";
	}
	
	@RequestMapping("/pwChange")
	public String pwChange() {
		return "login/pwChange";
	}
}
