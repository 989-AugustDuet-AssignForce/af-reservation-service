package com.revature.model;

public class Revservation {
	
	private int batchId;
	private int buildingId;
	private int locationId;
	private int reservationId;
	private int roomId;
	
	private RoomType roomType;
	
	private String reserver;
	
	//TODO: Start Time / end Time with the Lux API
	
	public Revservation() {
		
	}
	
	public Revservation( int batchId, 
						int buildingId, 
						int locationId, 
						int reservationId, 
						int roomId, 
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

	public int getBatchId() {
		return batchId;
	}

	public void setBatchId(int batchId) {
		this.batchId = batchId;
	}

	public int getBuildingId() {
		return buildingId;
	}

	public void setBuildingId(int buildingId) {
		this.buildingId = buildingId;
	}

	public int getLocationId() {
		return locationId;
	}

	public void setLocationId(int locationId) {
		this.locationId = locationId;
	}

	public int getReservationId() {
		return reservationId;
	}

	public void setReservationId(int reservationId) {
		this.reservationId = reservationId;
	}

	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
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
