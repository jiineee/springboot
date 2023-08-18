package com.example.project.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
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

import com.example.project.command.*;

import com.example.project.agent.service.*;

import ch.qos.logback.classic.Logger;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	//업로드 경로
		@Value("${project.upload.path}")
		private String uploadPath;
	
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
	public String roomDetail(Model model) {
		
		int room_id = 1;
		
		RoomVO vo = userService.getRoomData(room_id);
		model.addAttribute("vo" , vo);
		return "user/roomDetail";
		
	}
	
	
	@RequestMapping("/u_roomList")
	public String roomList(Model model) {
		
		String yn ="y";
		
		ArrayList<RoomVO> list = userService.getList(yn);
		
		model.addAttribute("list", list);
		 
		System.out.println("리스트=++++++" + list.toString());

		
		return "user/u_roomList";
	}
	

	
	@GetMapping("/e_login")
	public String e_login() {
		return "user/e_login";
	}
	
	
	
	//이미지 데이터 가져오기
	@PostMapping("/getAjaxImg")
	public ResponseEntity<ArrayList<PhotoVO>> getAjaxImg(@RequestBody PhotoVO vo) {
		
		ArrayList<PhotoVO> list = userService.getAjaxImg(vo.getRoom_writer());
		System.out.println(list.toString());
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	
	//이미지 src값 응답하기
	@GetMapping("/display")
	public ResponseEntity<byte[]> display(@RequestParam("filename") String filename,
										  @RequestParam("filepath") String filepath,
										  @RequestParam("uuid") String uuid) {
		
		String path = uploadPath + "/" + filepath + "/" + uuid + "_" + filename;
		System.out.println("------" + filepath);
		File file = new File(path);
		
		byte[] arr = null;
		try {
			arr = FileCopyUtils.copyToByteArray(file); //파일 경로 기반으로 데이터를 구함
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<>(arr, HttpStatus.OK);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}



















