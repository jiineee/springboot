package com.example.project.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.project.agent.service.AdminService;
import com.example.project.command.AgentMemberVO;
import com.example.project.command.NoticeVO;
import com.example.project.command.UserVO;
import com.example.project.util.Criteria;
import com.example.project.util.PageVO;


@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	@Qualifier("adminService")
	private AdminService adminService;


	@GetMapping("userNoticeList")
	public String userNoticeList() {

		return "/admin/userNoticeList";
	}

	@GetMapping("agentNoticeList")
	public String agentNoticeList() {
		
		
		
		return "/admin/agentNoticeList";
	}


	@GetMapping("userList")
	public String userList(Model model, Criteria cri) {


		ArrayList<UserVO> list = adminService.getUserList(cri);
		int total = adminService.getUserTotal(cri);
		PageVO pageVO = new PageVO(cri, total);

		model.addAttribute("list", list);
		model.addAttribute("pageVO", pageVO);
		
		System.out.println(list.toString());
		System.out.println(pageVO.toString());

		return "/admin/userList"; 
	}


	@GetMapping("agentList")
	public String agentList(Model model, Criteria cri) {
		
		ArrayList<AgentMemberVO> list = adminService.getAgentList(cri);
		int total = adminService.getAgentTotal(cri);
		PageVO pageVO = new PageVO(cri, total);

		model.addAttribute("list", list);
		model.addAttribute("pageVO", pageVO);
		
		System.out.println(list.toString());
		System.out.println(pageVO.toString());


		return "/admin/agentList";
	}
	
	@GetMapping("agentCheckList")
	public String agentCheckList(Model model, Criteria cri) {
		ArrayList<AgentMemberVO> list = adminService.getAgentCheckList(cri);
		int total = adminService.getAgentCheckTotal(cri);
		PageVO pageVO = new PageVO(cri, total);

		model.addAttribute("list", list);
		model.addAttribute("pageVO", pageVO);
		
		System.out.println(list.toString());
		System.out.println(pageVO.toString());
		
		return "/admin/agentCheckList";
	}
	
	
	@GetMapping("userNotice")
	public String userNotice(Model model) {
		ArrayList<NoticeVO> list = adminService.getUserNotice();

		model.addAttribute("list", list);
		
		System.out.println(list.toString());
		
		return "/admin/userNotice";
	}

	@GetMapping("agentNotice")
	public String agentNotice(Model model) {
		ArrayList<NoticeVO> list = adminService.getAgentNotice();

		model.addAttribute("list", list);
		
		System.out.println(list.toString());
		
		return "/admin/agentNotice";
	}
	
	
	//공지 디테일
	@GetMapping("/noticeDetail")
	public String detail(@RequestParam("notice_num") int notice_num,
						 Model model) {
		
		//조회 notice_num 필요
		NoticeVO vo= adminService.getNoticeDetail(notice_num);
		
		model.addAttribute("vo", vo);
		
		return "admin/noticeDetail";
	}
	
	//파트너체크디테일
		@GetMapping("/agentCheckDetail")
		public String agentCheckDetail(@RequestParam("a_no") int a_no,
							 Model model) {
			
			//조회 notice_num 필요
			AgentMemberVO vo= adminService.getAgentCheckDetail(a_no);
			
			model.addAttribute("vo", vo);
			return "admin/agentCheckDetail";
		}
	
	@PostMapping("/checkForm")
	public String checkForm(AgentMemberVO vo, RedirectAttributes ra) {

			//메서드명 = productUpdate()
			//데이터베이스 업데이트 작업을 진행
			//업데이트된 결과를 반환받아서 list화면으로 
			//"업데이트성공" 메시지를 띄워주세요.
		//System.out.println(vo.toString());
		System.out.println(vo.toString());
			int result = adminService.agentUpdate(vo);
			String msg = result == 1 ? "파트너로 승인 했습니다." : "실패";
			
			ra.addFlashAttribute("msg", msg);
		
		
		
		return "redirect:/admin/agentCheckList";
	}
	
	
	@GetMapping("/main")
	public String main() {
		
		return "admin/main";
	}
	
	@GetMapping("/map")
	public String map() {
		
		return "admin/map";
	}


	@GetMapping("/adminLogin")
	public String Adminlogin() {
		return "admin/adminLogin";
	}
	

}
