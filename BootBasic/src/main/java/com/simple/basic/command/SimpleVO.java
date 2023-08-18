package com.simple.basic.command;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SimpleVO {
	
	private int sno;
	private String first; //성
	private String last; //이름
	private LocalDateTime regdate;
}
