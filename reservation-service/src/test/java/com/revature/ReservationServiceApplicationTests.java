package com.revature;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ReservationServiceApplicationTests {

	
	Reservation testRes;
	
	@Test
	void isValidReservation(Reservation r, Location rmId) {
		//check to see if room is available for a  
		//reservation during proposed time slot
	}
	
	@Test
	void isInvalidReservation(Reservation r, Location rmId) {
		
	}
	@Test
	void makeReservation(Reservation r, Location rmId) {
		//see if making a reservation was successful
	}
	
	@Test
	void cancelReservation(Reservation r, Location rmId) {
		//check to see if removal of reservation was successful
	}
	
	
	

}
