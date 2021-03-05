package com.revature.service;

import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.revature.model.Reservation;
import com.revature.model.RoomType;
import com.revature.repository.ReservationRepository;


@SpringBootTest
@RunWith(SpringRunner.class)
public class TestMakeReservationTimeSlot {



	// list of reservations from the repo, via the controller
	// needed to test the functionality of making a reservation and adding it to the repository
	@Autowired
	@MockBean
	private ArrayList<Reservation> testResList; 

	//	need a reservation object
	@Autowired
	private Reservation reservation;

	//	autowired and injected service for testing
	@InjectMocks
	@Autowired
	private ReservationServiceImpl reserveServe; 
	
	//needed for testing
	@MockBean
	private RestTemplate restTemplate;
	
	@MockBean
    private ReservationRepository repository;

	//	instantiate the reservation object with hard-coded values for now
	@Before
	public void createTestReservationVariables() {
		
		//	hard coded reservation for now
		reservation = new Reservation(1010010, 13, 7, 007, 1402, RoomType.PHYSICAL,
				"BobTheBuilder", "11-22-1999 11:30", "11-22-1999 13:30" );
		//set the mocked restTemplate for testing
		reserveServe.setRestTemplate(restTemplate);
		repopulateTestList();
	}


	public void incrementReservation() {
//		modify reservation slightly to avoid duplicate
			reservation.setReservationId( reservation.getReservationId() + 1 );
			reservation.setRoomId( reservation.getRoomId() + 1 );
	}
	
	public void repopulateTestList() {
		testResList = (ArrayList<Reservation>) reserveServe.getAllReservations();
	}
	
	@Test
	public void reservationIsListed() {
		if( !testResList.contains( reservation ) ) {
			reserveServe.addReservation( reservation );
		}
		//	check to see if reservation is present in the repo  
		assertTrue(reserveServe.getAllReservations().contains(reservation));

	}

	@Test
	public void isInvalidReservation() {

		//	make a duplicate reservation and assert that it is already in the list
		if ( !testResList.contains( reservation ) ) {
			reserveServe.addReservation( reservation );
		}
		
		assertFalse( reserveServe.isValidReservation( reservation ) ) ;
		
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
		assertTrue( reserveServe.isValidReservation( reservation ) );


	}


	@Test
	public void addReservationToRepository() {

		incrementReservation();

		//	first affirm that the reservation does not exist in the repo
		//		assertFalse( testResList.contains( reservation ) );

		//	add the reservation to the repo
		reserveServe.addReservation( reservation );

		//recreate Reservation List and confirm it is now listed
		repopulateTestList();
		
		assertTrue( testResList.contains( reservation ) );
	}



	@Test
	public void reservationIsNotListed() {

		if( testResList.contains( reservation ) ) {
			incrementReservation();
		}
		//	make sure it is a bad match
		assertFalse( testResList.contains( reservation ) );

	}


	@Test
	public void makeReservation() {
		
		//	modify reservation slightly to avoid duplicate
		incrementReservation();
		

		//	are the dates valid?
		if( reserveServe.isValidReservation( reservation ) ) {

			//	add the reservation
			reserveServe.addReservation(	reservation	);

			//	repopulate list with current list from  database
			repopulateTestList();


			//	is the reservation in the most recent list?
			assertTrue( testResList.contains( reservation ) );

		} else {
			fail( "unable too make Reservation ");
		}
	}

	@Test
	public void cancelReservation() {

		if( !testResList.contains( reservation ) ) {
			reserveServe.addReservation( reservation );
			repopulateTestList();
		}
		
		//	remove reservation
		reserveServe.deleteReservation( reservation.getReservationId() );
		repopulateTestList();

		//	check to see if removal of reservation was successful
		assertTrue( !testResList.contains( reservation ) );

	}




}