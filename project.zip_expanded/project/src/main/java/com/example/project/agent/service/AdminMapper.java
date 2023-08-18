package com.example.project.agent.service;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.example.project.command.AgentMemberVO;
import com.example.project.command.NoticeVO;
import com.example.project.command.UserVO;
import com.example.project.util.Criteria;

@Mapper
public interface AdminMapper {

	public ArrayList<UserVO> getUserList(Criteria cri);
	public int getUserTotal(Criteria cri);
	
	public ArrayList<AgentMemberVO> getAgentList(Criteria cri);
	public int getAgentTotal(Criteria cri);
	
	public ArrayList<AgentMemberVO> getAgentCheckList(Criteria cri);
	public int getAgentCheckTotal(Criteria cri);
	
	public AgentMemberVO getAgentCheckDetail(int a_no);
	
	
	public ArrayList<NoticeVO> getUserNotice();
	public ArrayList<NoticeVO> getAgentNotice();
	public NoticeVO getNoticeDetail(int notice_num);
	
	
	public int agentUpdate(AgentMemberVO vo);//승인 미승인
}
