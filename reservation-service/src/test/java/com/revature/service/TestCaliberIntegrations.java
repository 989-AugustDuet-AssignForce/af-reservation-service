package com.revature.service;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.revature.controller.ReservationController;
import com.revature.model.Reservation;
import com.revature.model.RoomType;
import com.revature.repository.ReservationRepository;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mockito;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestCaliberIntegrations {
	
	private Reservation reservation;
	
	private static ReservationController reservationController;
	
	@MockBean
    private ReservationRepository repository;
	
    @InjectMocks
    private  ReservationServiceImpl reservationService;
	
	@Before
	public void setUp() throws Exception {
		
		reservation = new Reservation( -1, 1, 1, 1, 1, 
										RoomType.PHYSICAL, 
										"JUnit Test", 
										"02-02-2021 00:00", 
										"02-02-2021 01:00" );
		
		reservationService = new ReservationServiceImpl( repository );
		reservationController = new ReservationController( reservationService );
	}

	@Test
	public void testAddingABatchToReservation() {
		
		Reservation expected = new Reservation( 101, 1, 1, 1, 1,
												RoomType.PHYSICAL,
												"JUnit Test",
												"02-02-2021 00:00",
												"02-02-2021 01:00" );
		
		String message = "Test to add a batch to the reservation";
		
		ArgumentCaptor<Integer> valueCapture = ArgumentCaptor.forClass(Integer.class);
		Mockito.doNothing().when( reservationService )
			.assignBatch( Mockito.anyInt(), valueCapture.capture() );
		
		reservationController.assignBatch( reservation.getReservationId(), 101);
		assertEquals(101, valueCapture.getValue());
		
		assertEquals(message, expected, reservation);
	}
	
	@Test
	public void testTryAddingBatchToReservationWithBatchExisting() {
		
		Reservation expected = new Reservation( 101, 1, 1, 1, 1,
												RoomType.PHYSICAL,
												"JUnit Test",
												"02-02-2021 00:00",
												"02-02-2021 01:00" );
		
		String message = "Test to try to add a batch to the reservation when the reservation " +
						"already has a batch";
		
		ArgumentCaptor<Integer> valueCapture = ArgumentCaptor.forClass(Integer.class);
		Mockito.doNothing().when( reservationService )
			.assignBatch( Mockito.anyInt(), valueCapture.capture() );
		
		reservationController.assignBatch(reservation.getReservationId(), 101);
		assertEquals(101, valueCapture.getValue());
		
		reservationController.assignBatch(reservation.getReservationId(), 102);
		assertEquals(102, valueCapture.getValue());
		
		assertEquals(message, expected, reservation);
	}
	
	@Test
	public void getTests() {
		Reservation expected = new Reservation( 101, 1, 1, 1, 1,
				RoomType.PHYSICAL,
				"JUnit Test",
				"02-02-2021 00:00",
				"02-02-2021 01:00" );
		
		reservationController.assignBatch(1, 101);
		assertEquals("Test", expected, reservation);
	}
}
