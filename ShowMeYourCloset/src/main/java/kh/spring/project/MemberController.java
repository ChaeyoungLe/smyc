package kh.spring.project;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import kh.spring.dto.MembersDTO;

@Controller
@RequestMapping("/member")
public class MemberController {
	
	@RequestMapping("signupProc")
	public String signupProc() {
		
		return "";
	}
}
