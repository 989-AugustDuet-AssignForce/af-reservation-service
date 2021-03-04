package com.revature.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name="reservation")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Reservation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer reservationId;
	private Integer batchId;
	private Integer buildingId;
	private Integer locationId;
	private Integer roomId;
	private RoomType roomType;
	private String reserver;

	@Column(name = "start_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date startDate;

	@Column(name = "end_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date endDate;

	@Transient
	private final SimpleDateFormat DATEFORMAT = new SimpleDateFormat("MM-dd-yyyy HH:mm");


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
		try {
			this.startDate = DATEFORMAT.parse( startDate );
			this.endDate = DATEFORMAT.parse( endDate );
		} catch ( ParseException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Reservation(Integer reservationId, Integer buildingId, 
			Integer locationId, Integer roomId, RoomType roomType, String reserver, String startDate,
			String endDate) {
		
		this( reservationId, null, buildingId, locationId, roomId, roomType, reserver, 
				startDate, endDate );
		
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
	
	@Override
	public boolean equals(Object object) {
		if( !( object instanceof Reservation ) ) {
			return false;
		}
		
		Reservation compare = (Reservation) object;
		
		if( this.reservationId != compare.getReservationId() ) {
			return false;
		}
		
		if( this.batchId != compare.getBatchId() ) {
			return false;
		}
		
		if( this.buildingId != compare.getBuildingId() ) {
			return false;
		}
		
		if( this.locationId != compare.getLocationId() ) {
			return false;
		}
		
		if( this.roomId != compare.getRoomId() ) {
			return false;
		}
		
		if( this.roomType != compare.getRoomType() ) {
			return false;
		}
		
		if( !this.reserver.equals( compare.getReserver() )) {
			return false;
		}
		
		return true;
	}
	
	@Override
	public String toString() {
		return "ReservationID: " + reservationId + " BatchID: " + batchId + " BuildingID: " + 
					buildingId + " LocationID: " + locationId + " roomId: " + roomId +
					" RoomType: " + roomType + " Reserver: " + reserver;
	}
}
