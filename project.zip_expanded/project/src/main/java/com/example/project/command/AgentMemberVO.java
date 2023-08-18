package com.example.project.command;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AgentMemberVO {
	
	private Integer a_no;
	private String agent;
	private String a_name;
	private String a_phone;
	private String a_email; //id
	private String a_pw;
	private String a_agree;
	private LocalDateTime a_regdate;
}
