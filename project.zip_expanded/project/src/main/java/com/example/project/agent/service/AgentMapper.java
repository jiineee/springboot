package com.example.project.agent.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import com.example.project.command.AgentMemberVO;
import com.example.project.command.PhotoVO;
import com.example.project.command.RoomVO;
import com.example.project.util.Criteria;

@Mapper
public interface AgentMapper {

	public AgentMemberVO agentLogin(AgentMemberVO vo);
	public int agentRegist(AgentMemberVO vo);
	
	public int roomRegist(RoomVO vo);
	public void roomFileRegist(PhotoVO vo);
	
	public ArrayList<RoomVO> getList(@Param("vo") AgentMemberVO vo);
	//수정 화면 데이터
	public RoomVO getRoomData(int room_id);
	
	public int roomUpdate(RoomVO vo);
	public void roomFileUpdate(@Param("vo") PhotoVO vo, @Param("r_id") int r_id);
	
	public RoomVO getOk(String room_writer);
	
	public ArrayList<PhotoVO> getAjaxImg(String room_writer);
	public ArrayList<PhotoVO> getAjaxUpdateImg(int room_id);
}
