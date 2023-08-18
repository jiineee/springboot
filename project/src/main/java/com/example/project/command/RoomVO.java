package com.example.project.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomVO {

	private Integer room_id;
	private String room_writer;
	private String room_enddate;
	private Integer deposit;
	private Integer rent;
	private String structure;
	private Integer maintenanceCost;
	private Integer size;
	private String room_sqft;
	private Integer room_floors;
	private Integer room_floor;
	private String direction;
	private String room_opt;
	private String loan;
	private String pet;
	private String parking;
	private String elevator;
	private String moveDate;
	private String title;
	private String content;
	private String message;
	private String r_address;
	private String r_address_detail;
	private String r_agree;
}


	  
	    
	    
	    
