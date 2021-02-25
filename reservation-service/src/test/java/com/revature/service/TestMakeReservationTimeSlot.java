package com.revature.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import com.revature.model.Reservation;
import com.revature.model.RoomType;


@SpringBootTest
public class TestMakeReservationTimeSlot {



	// list of reservations from the repo, via the controller
	// needed to test the functionality of making a reservation and adding it to the repository
	@Autowired
	ArrayList<Reservation> testResList; 

	//	need a reservation object
	@Autowired
	Reservation reservation;

	//	autowired service for testing
	@Autowired
	ReservationService reserveControl; 

	//	instantiate the reservation object with hard-coded values for now
	@Before
	void createNewTestReservation() {
		
		//	hard coded reservation for now
		reservation = new Reservation(1010010, 13, 7, 007, 1402, RoomType.PHYSICAL,
				"BobTheBuilder", "11-22-1999 11:30", "11-22-1999 13:30" );
	}

	//	instantiate the list of reservations from the Reservation Service
	@BeforeClass
	void createResList() {
		
		//	populate the list with the controller
		repopulateTestList();
	}

	private void incrementReservation() {
//		modify reservation slightly to avoid duplicate
			reservation.setReservationId( reservation.getReservationId() + 1 );
			reservation.setRoomId( reservation.getRoomId() + 1 );
	}
	
	private void repopulateTestList() {
		testResList = (ArrayList<Reservation>) reserveControl.getAllReservations();
	}
	
	@Test
	public void reservationIsListed() {
		if( !testResList.contains( reservation ) ) {
			reserveControl.addReservation( reservation );
		}
		//	check to see if reservation is present in the repo  
		assertTrue(reserveControl.getAllReservations().contains(reservation));

	}

	@Test
	public void isInvalidReservation() {

		//	make a duplicate reservation and assert that it is already in the list
		if ( !testResList.contains( reservation ) ) {
			reserveControl.addReservation( reservation );
		}
		
		assertFalse( reserveControl.isValidReservationTime( reservation.getRoomId(), 
				reservation.getStartDate(), reservation.getEndDate() ) );
		
	}

	@Test
	public void isValidReservation() {
		//depending on when this test is run, some adjust may need to happen
		if( testResList.contains( reservation ) ) {
			incrementReservation();
			repopulateTestList();
		}
		
		//if the reservation is already in the list of reservations 
		//reserveControl.isValidReservation( roomId, startDate, endDat ) should return false
		//as the reservation already exists, the reservation is invalid
		assertTrue( reserveControl.isValidReservationTime( reservation.getRoomId(), 
				reservation.getStartDate(), reservation.getEndDate() ) );


	}


	@Test
	public void addReservationToRepository() {

		incrementReservation();

		//	first affirm that the reservation does not exist in the repo
		//		assertFalse( testResList.contains( reservation ) );

		//	add the reservation to the repo
		reserveControl.addReservation( reservation );

		//recreate Reservation List and confirm it is now listed
		repopulateTestList();
		
		assertTrue( testResList.contains( reservation ) );
	}



	@Test
	void reservationIsNotListed() {

		if( testResList.contains( reservation ) ) {
			incrementReservation();
		}
		//	make sure it is a bad match
		assertFalse( testResList.contains( reservation ) );

	}


	@Test
	void makeReservation() {
		
		//	modify reservation slightly to avoid duplicate
		incrementReservation();
		

		//	are the dates valid?
		if( reserveControl.isValidReservationTime( reservation.getRoomId(), reservation.getStartDate(), 
				reservation.getEndDate() ) {

			//	add the reservation
			reserveControl.addReservation(	reservation	);

			//	repopulate list with current list from  database
			repopulateTestList();


			//	is the reservation in the most recent list?
			assertTrue( testResList.contains( reservation ) );

		} else {
			fail("unable too make Reservation ");
		}
	}

	@Test
	void cancelReservation() {

		if( !testResList.contains( reservation ) ) {
			reserveControl.addReservation( reservation );
			repopulateTestList();
		}
		
		//	remove reservation
		reserveControl.deleteReservation( reservation.getReservationId() );
		repopulateTestList();

		//	check to see if removal of reservation was successful
		assertTrue( !testResList.contains( reservation ) );

	}




}
