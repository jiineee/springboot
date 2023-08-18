package com.example.project.agent.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.project.command.AgentMemberVO;
import com.example.project.command.PhotoVO;
import com.example.project.command.RoomVO;
import com.example.project.util.Criteria;

public interface AgentService {

	//로그인
	public AgentMemberVO agentLogin(AgentMemberVO vo);
	//회원가입
	public int agentRegist(AgentMemberVO vo);
	
	public int roomRegist(RoomVO vo, List<MultipartFile> list);
	public ArrayList<RoomVO> getList(AgentMemberVO vo);
	//수정 화면 데이터 띄우기
	public RoomVO getRoomData(int room_id);
	public int roomUpdate(RoomVO vo, int r_id, List<MultipartFile> list);
	
	public RoomVO getOk(String room_writer);
	
	public ArrayList<PhotoVO> getAjaxImg(String room_writer);
	public ArrayList<PhotoVO> getAjaxUpdateImg(int room_id);
}
