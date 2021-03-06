package com.revature.service;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
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
		repopulateTestList();
		//set the mocked restTemplate for testing
		reserveServe.setRestTemplate(restTemplate);

	}




	public void repopulateTestList() {

		testResList = new ArrayList<Reservation>();

		//add 100 randomly generated reservations to the list
		//added constraints so that after 100 iterations, there should be
		//a couple of duplicates for validation testing
		for (int i = 0; i>100; ++i) {
			testResList.add( randomReservation() );

		}
		//make reservation a random reservation
		reservation = randomReservation();

	}

	public Reservation randomReservation() {
		 Random random = new Random();
		 //create a reservation with a random int in the following inclusive ranges
		 //resId 0-500000, BldgID 0-4, LocID 0-10, RmID 0-30 
		 //batchId set to null as theses are not assumed to be batch reservations
		return new Reservation( random.nextInt(500000), null, 
				random.nextInt(4), random.nextInt( 10 ), 
				random.nextInt( 30 ), RoomType.PHYSICAL, "BobTheBuilder", 
				"11-22-2018 14:30", "11-22-2018 15:30" );
	}

	@Test
	public void reservationIsListed() {
		
		Reservation temp = randomReservation();
		
		
		ArgumentCaptor<Reservation> savedReservation = ArgumentCaptor.forClass( Reservation.class );
		
		Mockito.when( repository.save( savedReservation.capture() )).thenReturn(temp);
		
		//add the temp reservation to the mocked
		if( !testResList.contains( temp ) ) {
			testResList.add( reserveServe.addReservation( temp ) );
		}
		
		Mockito.when( repository.findAll( ))
		.thenReturn( testResList );
		//	check to see if reservation is present in the repo  
		assertTrue(reserveServe.getAllReservations().contains( temp ) );
		
		

	}

	@Test
	public void isInvalidReservation() {
		
		Reservation temp = randomReservation();
		ArgumentCaptor<Reservation> savedReservation = ArgumentCaptor.forClass( Reservation.class );

		Mockito.when( repository.save( savedReservation.capture() )).thenReturn(temp);

		
		//	make a duplicate reservation and assert that it is already in the list
		if ( !testResList.contains( temp ) ) {
			testResList.add( reserveServe.addReservation( temp ) );
		}
		
//		Mockito.when( repository.findAllReservationsByRoomId( temp.getRoomId() ) ).thenAnswer(tes));
		
		assertFalse( reserveServe.isValidReservation( temp ) ) ;

	}

	@Test
	public void isValidReservation() {
		
		Reservation temp = randomReservation();
		
		//depending on when this test is run, some adjust may need to happen
		if( testResList.contains( temp ) ) {
			//start the test over until the list doesn't have temp
			isValidReservation();
		}

		//if the reservation is already in the list of reservations 
		//reserveServe.isValidReservation( Reservation r ) should return false
		//as the reservation already exists, the reservation is invalid
		assertTrue( reserveServe.isValidReservation( temp ) );


	}


	@Test
	public void reservationIsNotListed() {
		Reservation temp = randomReservation();
		if( testResList.contains( temp ) ) {
			temp = randomReservation();
		}
		//	make sure it is a bad match
		assertFalse( testResList.contains( temp ) );

	}


	@Test
	public void makeReservation() {

		//	partially random reservation to avoid duplicate
		Reservation temp = randomReservation();


		//	are the dates valid?
		if( reserveServe.isValidReservation( temp ) ) {

			//	add the reservation
			reserveServe.addReservation(	temp	);

			//	repopulate list with current list from  database
			//repopulateTestList();


			//	is the reservation in the most recent list?
			assertTrue( testResList.contains( temp ) );

		} else {
			fail( "unable too make Reservation ");
		}
	}

	@Test
	public void cancelReservation() {
		Reservation temp = randomReservation();
		if( !testResList.contains( temp ) ) {
			reserveServe.addReservation( temp );
			//repopulateTestList();
		}

		//	remove reservation
		reserveServe.deleteReservation( temp.getReservationId() );
		// repopulateTestList();

		//	check to see if removal of reservation was successful
		assertFalse( testResList.contains( temp ) );

	}




}