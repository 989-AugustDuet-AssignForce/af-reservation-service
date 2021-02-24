package com.revature.model;

public class Reservation {

	private Integer reservationId;
	private Integer batchId;
	private Integer buildingId;
	private Integer locationId;
	private Integer roomId;
	private RoomType roomType;
	private String reserver;
	
	//TODO: Start Time / end Time with the Lux API
	//TODO: Start Date/ end Date

	public Reservation() {
		
	}
	
	public Reservation( Integer batchId,
						Integer buildingId,
						Integer locationId,
						Integer reservationId,
						Integer roomId,
						RoomType roomType, 
						String reserver ) {
		this.batchId = batchId;
		this.buildingId = buildingId;
		this.locationId = locationId;
		this.reservationId = reservationId;
		this.roomId = roomId;
		this.roomType = roomType;
		this.reserver = reserver;
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
}
