package kh.spring.project;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import kh.spring.Utils.CheckXss;
import kh.spring.dto.ClosetDTO;
import kh.spring.dto.DressDTO;
import kh.spring.dto.DressImgDTO;
import kh.spring.dto.MembersDTO;
import kh.spring.service.ClosetService;
import kh.spring.service.MemberService;

@Controller
@RequestMapping("/member")
public class MemberController {
	
	@Autowired
	private MemberService memService;
	
	@Autowired
	private ClosetService cloService;
	
	@Autowired
	private HttpSession session;
	
	
	// 회원가입
	@RequestMapping("signupProc")
	public String signup(Model model,MembersDTO dto,String phone1,String phone2,String phone3) {
		String phone = phone1+phone2+phone3;
		System.out.println(dto.getEmail());
		dto.setPhone(phone);
		dto.setName(CheckXss.checkXss(dto.getName()));
		dto.setNickname(CheckXss.checkXss(dto.getNickname()));
		dto.setEmail(CheckXss.checkXss(dto.getEmail()));
		
		try {
			memService.create(dto);
			
			model.addAttribute("auth_check", 0);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("등록된 이메일 :::: ");
			return "login/emailConfirm";
			
		}
		
		return "login/signupConfirm";
	}
	
	//회원가입 확인 (이메일 인증 보내고 넘어가는 창)
	@RequestMapping("joinConfirm")
	public String joinConrim(Model model,String email,String authkey) {
		
		try {
			String DBauthKey = memService.selectAuthKey(email);
			
			if(DBauthKey.contentEquals(authkey)) {
				memService.updateAuth(email);
				model.addAttribute("auth_check", 1);
			}
		}catch(Exception e) {
			return "error";
		}
		
		return "login/signupConfirm";
	}
	
	// 로그인
	@RequestMapping("signinProc")
	@ResponseBody
	public String signup(Model model,String email,String pw) {
		int result = memService.logInOk(email, pw);
		
		if(result > 0) {
			int auth = memService.returnAuthStatus(email);
			String ban = memService.returnBan(email);
			if(auth == 1 && ban.contentEquals("N")) {
				
				String nickname = memService.returnNickname(email);
				
				session.setAttribute("email", email);
				session.setAttribute("nick", nickname);
				
				return "do";
			}
			if(ban.contentEquals("Y")) {
				return "2";
			}
			
		}
		
		return result+"";
		
	}

	// 닉네임 중복확인
	@RequestMapping("nickCheckProc")
	@ResponseBody
	public String nickCheckProc(String nickname) {
		
		int result=memService.checkNickName(nickname);
		if(result >0) {
			return "done";
		}else {
			return "fail";
		}

	}
	
	// 로그아웃
	@RequestMapping("logoutProc")
	public String logoutProc() {
		
		session.invalidate();
		
		return "redirect:/";
				
	}
	
	//이메일 찾기
	@RequestMapping(value="/idFindProc",produces = "text/html; charset=UTF-8")
	@ResponseBody
	public String idFindProc(String nickname, String phone) {
	
		String email=memService.findEmail(nickname, phone);
		if(email==null) {
			return "이메일이 존재하지 않습니다.";			
		}
		
		return email;
	}
	
	//비밀번호 찾기
	@RequestMapping("/findPWProc")
	public String findPWProc(Model model ,String email) {
		
		try {
			String authkey=memService.sendFindPWEmail(email);
			
			model.addAttribute("authkey", authkey);
			model.addAttribute("email",email);
			
			return "login/pwConfirm";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "";	
	}
	
	// 비밀번호 찾기에서 등록된 이메일인지 확인하는거였음
	@RequestMapping("/emailCheck")
	@ResponseBody
	public String emailCheck(String email) {
		
		String result = memService.returnNickname(email);
		if(result == null) {
			return "fail";
		}
		
		return "good";
	}
	
	// 비밀번호 변경
	@RequestMapping("/changePwProc")
	public String changePwProc(String pw,String email) {
		
		memService.changePwProc(email, pw);
		
		return "login/pwChangeConfirm";
	}
	
	// 회원 정보 보기
	@RequestMapping("/InfoCheck")
	public String pwCheck(Model model,String email,String pw) {
		
		int result = memService.logInOk(email, pw);
		
		if(result > 0) {
			MembersDTO dto = memService.selectDTO(email);
			model.addAttribute("dto", dto);
		}
		model.addAttribute("result", result);
		
		return "mypage/info/infoModify";
	}
	
	// 탈퇴
	@RequestMapping("/withDrawProc")
	public String withDrawProc() {
		String email = (String)session.getAttribute("email");
		memService.withDraw(email);
		session.invalidate();
		
		return "redirect:/";
	}
	
	// 수정
	@RequestMapping("/modifyProc")
	public String modifyProc(MembersDTO dto) {
	
		memService.modify(dto);
		
		return "mypage/info/modifyConfirm";
	}
	

}
