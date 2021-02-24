package com.revature.service;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.revature.model.Reservation;

@SpringBootTest
public class TestMakeReservationTimeSlot {

	@Autowired
	Reservation testRes;
		
	
	
	@Test
    void isValidReservation(Reservation r) {
		//check to see if room is available for a  
		//reservation during proposed time slot
		
		//make sure that room and reservations location id's all match 
		AssertTrue(r.getBuildingId() == testRes.getBuildingId() 
				&& r.getRoomId() == testRes.getRoomId() 
				&& r.getLocationId() == testRes.getLocationId());
		//		now assert that the room is available from start to stop times need lux api call??
		//		AssertTrue(testRes.isAvailable(r.))
	}
	
	
	@Test
	void isInvalidReservation(Reservation r) {
		//make sure it is a bad match
		AssertFalse(r.getBuildingId() == testRes.getBuildingId() 
		&& r.getRoomId() == testRes.getRoomId() 
		&& r.getLocationId() == testRes.getLocationId());
//		now assert that the room is not available from start to stop times need lux api call??
//		AssertFalse(testRes.isAvailable(r.))
	}
	
	
	@Test
	void makeReservation(Reservation r) {
		//see if making a reservation was successful
	}
	
	@Test
	void cancelReservation(Reservation r) {
		//check to see if removal of reservation was successful
	}
	
	
	@Test
	void test() {
		fail("Not yet implemented");
	}

}
