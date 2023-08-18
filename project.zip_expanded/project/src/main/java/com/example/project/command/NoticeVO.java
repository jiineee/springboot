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
public class NoticeVO {
	
	private Integer notice_num;
	private String notice_type; //유저 공지, 파트너 공지
	private String notice_id; //admin만 작성 가능
	private String notice_title;
	private String notice_writing;
	private LocalDateTime notice_regdate;
}
