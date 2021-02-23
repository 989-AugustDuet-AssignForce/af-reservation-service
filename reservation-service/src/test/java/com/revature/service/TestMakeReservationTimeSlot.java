package com.revature.service;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;

import com.revature.model.Reservation;


class TestMakeReservationTimeSlot {

	
Reservation testRes;
	
	@Test
    void isValidReservation(Reservation r, Location rmId) {
		//check to see if room is available for a  
		//reservation during proposed time slot
		
		//make sure that room and reservations location id's all match 
		AssertTrue(r.getBuildingId() == rmId.getBuildingId() 
				&& r.getRoomId() == rmId.getRoomId() 
				&& r.getLocationId() == rmId.getLocationId());
		//		now assert that the room is available from start to stop times need lux api call??
		//		AssertTrue(rmId.isAvailable(r.))
	}
	
	
	@Test
	void isInvalidReservation(Reservation r, Room rmId) {
		//make sure it is a bad match
		AssertFalse(r.getBuildingId() == rmId.getBuildingId() 
		&& r.getRoomId() == rmId.getRoomId() 
		&& r.getLocationId() == rmId.getLocationId());
//		now assert that the room is not available from start to stop times need lux api call??
//		AssertFalse(rmId.isAvailable(r.))
	}
	
	
	@Test
	void makeReservation(Reservation r, Location rmId) {
		//see if making a reservation was successful
	}
	
	@Test
	void cancelReservation(Reservation r, Location rmId) {
		//check to see if removal of reservation was successful
	}
	
	
	@Test
	void test() {
		fail("Not yet implemented");
	}

}
