package kh.spring.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import kh.spring.dto.MembersDTO;
import kh.spring.service.MemberService;

@Controller
@RequestMapping("/member")
public class MemberController {
	
	@Autowired
	private MemberService memService;
	
	@RequestMapping("signupProc")
	public String signup(Model model,MembersDTO dto,String phone1,String phone2,String phone3) {
		String phone = phone1+phone2+phone3;
		System.out.println(dto.getEmail());
		System.out.println(phone);
		System.out.println("성별 제대로? "+dto.getGender());
		dto.setPhone(phone);
		
		try {
			memService.create(dto);
			
			model.addAttribute("auth_check", 0);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "";
	}
	
	@RequestMapping("signinProc")
	public String signup(String email,String pw) {
		System.out.println(email+"/"+pw);
		
		return "";
	}

}