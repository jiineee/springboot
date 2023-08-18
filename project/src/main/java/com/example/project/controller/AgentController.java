package com.example.project.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.project.agent.service.AgentService;
import com.example.project.command.AgentMemberVO;
import com.example.project.command.PhotoVO;
import com.example.project.command.RoomVO;
import com.example.project.util.Criteria;
import com.example.project.util.PageVO;

@Controller
@RequestMapping("/agent")
public class AgentController {

	@Autowired
	@Qualifier("agentService")
	private AgentService agentService;
	
	//업로드 경로
	@Value("${project.upload.path}")
	private String uploadPath;

	
	
	//메인 화면
	@GetMapping("/index")
	public String index() {
		return "agent/index";
	}
	
	
	//중개사 로그인 창
	@GetMapping("/agentLogin")
	public String agentLogin() {
		return "agent/agentLogin";
	}
	
	//중개사 로그인
	@PostMapping("/agentLoginForm")
	public String agentLoginForm(String a_email, String a_pw, HttpSession session,
			RedirectAttributes rttr) {
		
		AgentMemberVO vo = new AgentMemberVO();
		vo.setA_email(a_email);
		vo.setA_pw(a_pw);
		
		AgentMemberVO agentLogin = agentService.agentLogin(vo);
		if(agentLogin == null) {
			rttr.addFlashAttribute("error", "승인 되지 않았거나 로그인 오류 입니다. 관리자에게 문의하세요.");
	        return "redirect:/agent/agentLogin"; // 로그인 실패 시 로그인 페이지로 리다이렉트
		}
		session.setAttribute("agentLogin", agentService.agentLogin(vo));
		AgentMemberVO agentLogin2 = (AgentMemberVO)session.getAttribute("agentLogin");
		
		System.out.println(agentLogin2.toString());
		rttr.addFlashAttribute("msg", "환영합니다." + agentLogin2.getA_name() + "님");
		
		return "redirect:/agent/roomList"; // 로그인 성공 시 방 목록 페이지로 리다이렉트
	}
	
	//중개사 로그아웃
	@GetMapping("/agentLogout")
	public String logout(HttpSession session, RedirectAttributes rttr) {
		
		// 로그아웃 처리
		session.removeAttribute("agentLogin");
		System.out.println(session.getAttribute("agentLogin")); 
		rttr.addFlashAttribute("msg", "로그아웃 되었습니다. 이용해 주셔서 감사합니다.");
		
		return "redirect:/agent/agentLogin";
	}
	
	//중개사 회원가입창
	@GetMapping("/agentRegister")
	public String agentRegister() {
		
		return "agent/agentRegister";
	}
	
	//중개사 회원가입
	@PostMapping("/agentRegistForm")
	public String agentRegistForm(AgentMemberVO vo, RedirectAttributes ra) {
		agentService.agentRegist(vo);
		String msg = "관리자 검토 후 가입 여부가 결정됩니다";

		ra.addFlashAttribute("msg", msg);

		
		return "redirect:/agent/agentLogin"; // 리다이렉트
	}

	//중개사 방 목록
	@GetMapping("/roomList")
	public String list(Model model, HttpSession session) {
		AgentMemberVO agentLogin = (AgentMemberVO)session.getAttribute("agentLogin");
		
		ArrayList<RoomVO> list = agentService.getList(agentLogin.getA_name());
		System.out.println(agentLogin.getA_name());
		model.addAttribute("list", list);
		 
		System.out.println("리스트=++++++" + list.toString());

		return "agent/roomList";
	}
	
	//방 등록
	@GetMapping("/roomRegist")
	public String roomRegist(Model model, HttpSession session) {
		AgentMemberVO agentLogin = (AgentMemberVO)session.getAttribute("agentLogin");
		
		model.addAttribute("a_name", agentLogin.getA_name());
		
		return "agent/roomRegist";
	}
	
	//방 등록 완료 화면
	@GetMapping("/roomRegistOk")
	public String roomRegistOk(Model model, HttpSession session) {
		AgentMemberVO agentLogin = (AgentMemberVO)session.getAttribute("agentLogin");
		
		System.out.println("로그인~~~~~~~~~~~~~~" + agentLogin);
		RoomVO vo = agentService.getOk(agentLogin.getA_name());
		System.out.println(vo.toString());
		model.addAttribute("vo", vo);
		return "agent/roomRegistOk";
	}
	
	//방 등록
	@PostMapping("/registForm")
	public String registForm(RoomVO vo, RedirectAttributes ra, @RequestParam("file") List<MultipartFile> list // 업로드
	) {

		// 1. 빈객체검사
		list = list.stream().filter(t -> t.isEmpty() == false).collect(Collectors.toList());

		// 2. 확장자검사
		for (MultipartFile file : list) {
			if (file.getContentType().contains("image") == false) {
				ra.addFlashAttribute("msg", "jpg, jpeg, png 이미지 파일만 등록이 가능합니다");
				return "redirect:/agent/roomList"; // 이미지가 아니라면 list목록으로
			}
		}

		int result = agentService.roomRegist(vo, list);
		String msg = result == 1 ? "등록 되었습니다" : "등록 실패. 관리자에게 문의하세요";

		ra.addFlashAttribute("msg", msg);

		return "redirect:/agent/roomRegistOk"; // 리다이렉트
	}
	
	//수정 화면 데이터
	@PostMapping("/roomUpdate")
	public String roomUpdate(Model model) {
		int room_id = 2;
		RoomVO vo = agentService.getRoomData(room_id);
		System.out.println("수정 화면 데이터=" + vo.toString());
		model.addAttribute("vo", vo);
		return "agent/roomUpdate";
	}
	
	//수정
	@PostMapping("/updateForm")
	public String updateForm(RoomVO vo, RedirectAttributes ra, @RequestParam("file") List<MultipartFile> list,
								HttpSession session) {


		int result = agentService.roomUpdate(vo/* , r_id, list */);
		String msg = result == 1 ? "수정 되었습니다" : "수정 실패. 관리자에게 문의하세요";

		ra.addFlashAttribute("msg", msg);


		return "redirect:/agent/roomList"; // 리다이렉트
	}
	
	//삭제
	@GetMapping("/deleteForm")
	public String deleteForm() {
		
		return "redirect:/agent/roomList"; //
	}
	
		
		//이미지 데이터 가져오기
		@PostMapping("/getAjaxImg")
		public ResponseEntity<ArrayList<PhotoVO>> getAjaxImg(@RequestBody PhotoVO vo) {
			
			ArrayList<PhotoVO> list = agentService.getAjaxImg(vo.getRoom_writer());
			System.out.println(list.toString());
			return new ResponseEntity<>(list, HttpStatus.OK);
		}
		//이미지 데이터 가져오기
			@PostMapping("/getAjaxUpdateImg")
			public ResponseEntity<ArrayList<PhotoVO>> getAjaxUpdateImg(@RequestBody PhotoVO vo) {
				
				ArrayList<PhotoVO> list = agentService.getAjaxUpdateImg(vo.getRoom_id());
				System.out.println("업데이트" + list.toString());
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
		//세션
			@GetMapping("/get-session-value")
			public ResponseEntity<AgentMemberVO> session(HttpSession session) {
				AgentMemberVO agentLogin = (AgentMemberVO)session.getAttribute("agentLogin");
				System.out.println("세션~~~~~~~" + agentLogin.toString());
				return new ResponseEntity<>(agentLogin, HttpStatus.OK);
			}
	

}
