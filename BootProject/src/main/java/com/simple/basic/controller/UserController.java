package com.simple.basic.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.simple.basic.command.UserVO;

import com.simple.basic.service.UserService;

import ch.qos.logback.classic.Logger;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/main")
	public String main(Model model) {

		return "user/main";
	}
	
	
	@GetMapping("/join")
	public String join() {
		return "user/join";
	}
	
	@PostMapping("/joinForm")
	public String joinForm(@Valid UserVO userVO,BindingResult br,Model model) {
		
		if(br.hasErrors()) {
			/*회원가입 실패시 입력 데이터 값을 유지*/
			model.addAttribute("UserVO", userVO);
			/*유효성 통과 못한 필드와 메세지를 핸들링*/
			Map<String, String> errporMap = new HashMap<>();
			
			for(FieldError err : br.getFieldErrors()) {
				if(err.isBindingFailure()) {
					model.addAttribute("valid_"+err.getField(), "잘못입력된 값 입니다");
				}else {
					model.addAttribute("valid_"+err.getField(), err.getDefaultMessage());
				}
			}
			return "user/join";
		}
		
		
		userService.join(userVO);
		
		return "redirect:/user/login";
	}
	
	
	@GetMapping("/login")
	public String login() {
		return "user/login";
	}
	
	@PostMapping("/loginForm")
	public String loginForm(UserVO userVO,
							HttpServletRequest req,
							RedirectAttributes rttr){
		
		HttpSession session = req.getSession();
		UserVO user = userService.login(userVO);
		
		if(user == null) {
			session.setAttribute("user", null);
			rttr.addFlashAttribute("msg",false);
			System.out.println("로그인 실패");
			return "redirect:/user/login";
		}else {
			session.setAttribute("user", user);
			System.out.println("로그인성공");
		}
			return "redirect:/user/main";
	}
	
	
	@GetMapping("/mypage")
	public String mypage() {
		return "user/mypage";
	}
	
	@PostMapping("/idCheck") // 아이디 중복확인을 위한 값으로 따로 매핑
	@ResponseBody // 값 변환을 위해 꼭 필요함
	public String user_idCheck(String user_id) throws Exception{
		
		int result = userService.idCheck(user_id);
		if(result != 0) {
			return "fail";
		}else {
			return "success";
		}
	}
	
	@PostMapping("/emailCheck")
	@ResponseBody
	public String user_emailCheck(String user_email) throws Exception{
		
		int result = userService.emailCheck(user_email);
		if(result != 0) {
			return "fail";
		}else {
			return "success";
		}
	}
	
	@PostMapping("/login_id")
	@ResponseBody
	public String user_idLogin(String user_id)throws Exception {
		int result = userService.login_id(user_id);
		if(result != 0) {
			return "fail";
		}else {
			return "success";
		}
	}
	
	

	@RequestMapping("/logout")
	public ModelAndView logout(HttpSession session) {
		userService.logout(session);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("user/login");
		mav.addObject("msg","logout");
		return mav;
	}

	
	@RequestMapping("/roomDetail")
	public String roomDetail() {
		return "user/roomDetail";
	}
	
	
	
}



















