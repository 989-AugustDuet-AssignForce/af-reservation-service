package com.revature.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Reservation {

	private Integer reservationId;
	private Integer batchId;
	private Integer buildingId;
	private Integer locationId;
	private Integer roomId;
	private RoomType roomType;
	private String reserver;
	private Date startDate;
	private Date endDate;


	
	//set date/time format
	private final SimpleDateFormat DATEFORMAT = new SimpleDateFormat("MM-DD-YYYY HH:MM");


	public Reservation() {

	}

	public Reservation(Integer reservationId, Integer batchId, Integer buildingId, 
			Integer locationId, Integer roomId, RoomType roomType, String reserver, String startDate,
			String endDate) {
		super();
		this.reservationId = reservationId;
		this.batchId = batchId;
		this.buildingId = buildingId;
		this.locationId = locationId;
		this.roomId = roomId;
		this.roomType = roomType;
		this.reserver = reserver;
		DATEFORMAT.format(this.startDate); 
		try {
			this.startDate = DATEFORMAT.parse( startDate );
			this.endDate = DATEFORMAT.parse( endDate );
		} catch ( ParseException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	public Integer getReservationId() {
		return reservationId;
	}

	public void setReservationId(Integer reservationId) {
		this.reservationId = reservationId;
	}

	public Integer getBatchId() {
		return batchId;
	}

	public void setBatchId(Integer batchId) {
		this.batchId = batchId;
	}

	public Integer getBuildingId() {
		return buildingId;
	}

	public void setBuildingId(Integer buildingId) {
		this.buildingId = buildingId;
	}

	public Integer getLocationId() {
		return locationId;
	}

	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}

	public Integer getRoomId() {
		return roomId;
	}

	public void setRoomId(Integer roomId) {
		this.roomId = roomId;
	}

	public RoomType getRoomType() {
		return roomType;
	}

	public void setRoomType(RoomType roomType) {
		this.roomType = roomType;
	}

	public String getReserver() {
		return reserver;
	}

	public void setReserver(String reserver) {
		this.reserver = reserver;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		try {
		this.startDate = DATEFORMAT.parse(startDate);
		} 
		catch ( ParseException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {

		try {
			this.endDate = DATEFORMAT.parse( endDate );
		} catch ( ParseException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//	public Date getStartTime() {
	//		return startTime;
	//	}
	//
	//	public void setStartTime(Time startTime) {
	//		this.startTime = startTime;
	//	}
	//
	//	public Time getEndTime() {
	//		return endTime;
	//	}
	//
	//	public void setEndTime(Time endTime) {
	//		this.endTime = endTime;
	//	}
}
