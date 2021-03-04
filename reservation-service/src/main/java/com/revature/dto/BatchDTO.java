package com.revature.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BatchDTO {
	
	public int id;
	
	public String batchId;
	
	public String name;
	
	public String startDate;
	
	public String endDate;
	
	@JsonIgnore
	public Date startTime;
	
	@JsonIgnore
	public Date endTime;
	
	public BatchDTO() {
		
	}
	
	public BatchDTO(int id, String batchId, String name, String startDate, String endDate) {
		this.id = id;
		this.batchId = batchId;
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	@Override
	public String toString() {
		return "ID: " + id + " Batch Id: " + batchId + " Name: " + name + " date: " + startDate +
				" - " + endDate;
	}
	
	public void formatDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try {
			this.startTime = dateFormat.parse( startDate + " 00:00" );
			this.endTime = dateFormat.parse( endDate + " 00:00");
		} catch ( ParseException e ) {
			e.printStackTrace();
		}
	}
}
