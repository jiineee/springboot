package com.example.project.agent.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.project.command.AgentMemberVO;
import com.example.project.command.NoticeVO;
import com.example.project.command.UserVO;
import com.example.project.util.Criteria;



@Service("adminService")
public class AdminServiceImpl implements AdminService{

	@Autowired
	private AdminMapper adminMapper;

	@Override
	public ArrayList<UserVO> getUserList(Criteria cri) {
		
		return adminMapper.getUserList(cri);
	}
	
	@Override
	public int getUserTotal(Criteria cri) {

		return adminMapper.getUserTotal(cri);
	}

	@Override
	public ArrayList<AgentMemberVO> getAgentList(Criteria cri) {
		return adminMapper.getAgentList(cri);
	}

	@Override
	public int getAgentTotal(Criteria cri) {
		return adminMapper.getAgentTotal(cri);
	}
	@Override
	public ArrayList<AgentMemberVO> getAgentCheckList(Criteria cri) {
		return adminMapper.getAgentCheckList(cri);
	}
	
	@Override
	public int getAgentCheckTotal(Criteria cri) {
		return adminMapper.getAgentCheckTotal(cri);
	}

	@Override
	public ArrayList<NoticeVO> getUserNotice() {
		return adminMapper.getUserNotice();
	}

	@Override
	public ArrayList<NoticeVO> getAgentNotice() {
		return adminMapper.getAgentNotice();
	}

	@Override
	public NoticeVO getNoticeDetail(int notice_num) {
		return adminMapper.getNoticeDetail(notice_num) ;
	}

	@Override
	public int agentUpdate(AgentMemberVO vo) {

		return adminMapper.agentUpdate(vo);
	}

	@Override
	public AgentMemberVO getAgentCheckDetail(int a_no) {
		
		return adminMapper.getAgentCheckDetail(a_no);
	}



}
