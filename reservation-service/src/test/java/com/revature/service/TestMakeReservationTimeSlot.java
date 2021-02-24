package com.revature.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.revature.model.Reservation;
import com.revature.model.RoomType;


@SpringBootTest
public class TestMakeReservationTimeSlot {

	//  list of reservations from the repo, via the controller
	@Autowired
	ArrayList<Reservation> testResList; 

	@Autowired
	Reservation reservation;

	//	unneeded? commented out
	//	@Autowired
	//	RserevationRepository resRepo; //repository of reservations 

	//what should be used to interact with the reservation repository
	@Autowired
	ReservationController reserveControl; //reservation controller 

	@BeforeClass
	void createNewTestReservation() {
		//hard coded reservation for now
		reservation = new Reservation(1010010, 13, 7, 007, 1402, RoomType.PHYSICAL,
				"BobTheBuilder" );
	}

	@BeforeClass
	void createResList() {
		//populate the list with the controller
		testResList = reserveControl.getAllReservations();
	}

	@Test
	public void reservationIsListed() {
		//check to see if reservation is present in the repo  
		assertTrue( testResList.contains( reservation ) );

	}

	@Test
	public void duplicateReservation() {
		
		//make a duplicate reservation and assert that it is already in the list
		Reservation dupeReservation = reservation;
		assertTrue( testResList.contains( dupeReservation ) );
	}

	@Test
	public void isValidReservation() {

		//does not exist yet
		assertTrue( reserveControl.isValidReservation( reservation.getRoomId(), 
				reservation.getStartDate(), reservation.getEndDate() ) );



	}


	@Test
	public void addReservationToRepository() {
		
		//first affirm that the reservation does not exist in the repo
		assertFalse( testResList.contains( reservation ) );

		//add the reservation to the repo
		reserveControl.addReservation( reservation );

		//recreate Reservation List and confirm it is now listed
		createResList();
		assertTrue( testResList.contains( reservation ) );
	}



	@Test
	void reservationIsNotListed() {
		
		//make sure it is a bad match
		assertFalse( testResList.contains( reservation ) );

	}


	@Test
	void makeReservation() {
		
		//does reservation exist?
		assertFalse( testResList.contains( reservation ) );
		
		//are the dates valid?
		reserveControl.isValidReservation( reservation.getRoomId(), reservation.getReservationId() )
		
		//add the reservation
		reserveControl.add(	reservation	);
		
		//repopulate list with current list from  database
		createResList();
		
		//is the reservation in the most recent list?
		assertTrue( testResList.contains( reservation ) );

	}

	@Test
	void cancelReservation() {
		
		//check to see if reservation exists
		assertTrue( testResList.contains( reservation ) );
		
		//remove reservation
		reserveControl.deleteReservation( reservation.getReservationId() );
		createResList();
		
		//check to see if removal of reservation was successful
		assertFalse( testResList.contains( reservation ) );
		
	}




}
