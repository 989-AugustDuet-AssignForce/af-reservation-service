package com.revature.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.revature.model.Reservation;


@SpringBootTest
public class TestMakeReservationTimeSlot {

	@Autowired
	ArrayList<Reservation> testResList; //invalid reservation that doesn't exist
		
	
//	unneeded? commented out
//	@Autowired
//	RserevationRepository resRepo; //repository of reservations 
	
	//what should be used to interact with the reservation repository
	@Autowired
	ReservationController resCtrl; //reservation controller 
	
	@BeforeClass
	void createResList() {
		testResList = resCtrl.getAllReservations();
	}
	
	@Test
    public void reservationIsListed(Reservation r) {
		//check to see if reservation is present in the repo  
		assertTrue(testResList.contains(r));
	
	}
	
	@Test
	public void duplicateReservation(Reservation dupeRes) {
		assertTrue(testResList.contains(dupeRes));
	}
	
	
	
	@Test
	public void addReservationToRepository(Reservation r) {
		//first affirm that the reservation does not exist in the repo
		assertFalse(testResList.contains(r));
		//add the reservation to the repo
		resCtrl.addReservation(r);
		//recreate Reservation List and confirm it is now listed
		createResList();
		assertTrue(testResList.contains(r));
	}
	
	
	
	@Test
	void reservationIsNotListed(Reservation r) {
		//make sure it is a bad match
		assertFalse(testResList.contains(r));
		
	}
	
	
	@Test
	void makeReservation(Reservation r) {
		//see if making a reservation was successful
		reservationIsNotListed(r);
		addReservationToRepository(r);
		
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
