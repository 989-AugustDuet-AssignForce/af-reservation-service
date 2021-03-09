package com.revature.service;

import static org.junit.Assert.*;

import com.revature.dto.BatchDTO;
import com.revature.model.Reservation;
import com.revature.model.RoomType;
import com.revature.repository.ReservationRepository;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestCaliberIntegrations {
	
	private Reservation reservation;
	
	@MockBean
    private ReservationRepository repository;
	
	@MockBean
	private RestTemplate restTemplate;
	
    @InjectMocks
    @Autowired
    private ReservationServiceImpl reservationService;
    
    private BatchDTO batch;
	
	@Before
	public void setUp() throws Exception {
		
		reservation = new Reservation( 1, 1, 1, 1, 
										RoomType.PHYSICAL, 
										"JUnit Test", 
										"02-02-2021 00:00", 
										"02-02-2021 01:00" );
		
		reservationService.setRestTemplate(restTemplate);
		batch = new BatchDTO(101, "TR-1201", "Mock Batch 101", "2021-02-02","2021-02-02");
	}
	
	@Test
	public void testAddingABatchToReservation() {
		
		Reservation expected = new Reservation( 1, 101, 1, 1, 1,
												RoomType.PHYSICAL,
												"JUnit Test",
												"02-02-2021 00:00",
												"02-02-2021 01:00" );
		
		String message = "Test to add a batch to the reservation";
		
		Mockito.when( restTemplate.getForObject( Mockito.anyString(), 
													Mockito.eq(BatchDTO.class) ))
														.thenReturn( batch );
		
		Mockito.when( repository.findById( reservation.getReservationId() ))
											.thenReturn( Optional.of( reservation ));
		
		ArgumentCaptor<Reservation> savedReservation = ArgumentCaptor.forClass( Reservation.class );
		
		Mockito.when( repository.save( savedReservation.capture() )).thenReturn( null );
		
		reservationService.assignBatch( reservation.getReservationId(), 101);
		
		assertEquals(message, expected, savedReservation.getValue());
	} 
	
	@Test(expected = IllegalStateException.class)
	public void testTryAddingBatchToReservationWithBatchExisting() {
		
		Reservation expected = new Reservation( 1, 101, 1, 1, 1,
												RoomType.PHYSICAL,
												"JUnit Test",
												"02-02-2021 00:00",
												"02-02-2021 01:00" );
		
		String message = "Test to try to add a batch to the reservation when the reservation " +
						"already has a batch should throw exception";
		
		Mockito.when( restTemplate.getForObject( Mockito.anyString(), 
													Mockito.eq(BatchDTO.class) ))
														.thenReturn( batch );

		Mockito.when( repository.findById( reservation.getReservationId() ))
												.thenReturn( Optional.of( reservation ));
		
		ArgumentCaptor<Reservation> savedReservation = ArgumentCaptor.forClass( Reservation.class );
		
		Mockito.when( repository.save( savedReservation.capture() )).thenReturn( null );
		
		reservationService.assignBatch( reservation.getReservationId(), 101);
		
		batch.id = 102;
		
		Mockito.when( restTemplate.getForObject( Mockito.anyString(), 
													Mockito.eq( BatchDTO.class )))
													.thenThrow( new IllegalStateException( message ));
		
		reservationService.assignBatch( reservation.getReservationId(), 102);
		
		assertEquals(message, expected, savedReservation.getValue());
	}
	
	@Test(expected = RestClientException.class)
	public void testAddingInvalidBatchId() {
		Reservation expected = new Reservation( 1, 1, 1, 1,
				RoomType.PHYSICAL,
				"JUnit Test",
				"02-02-2021 00:00",
				"02-02-2021 01:00" );

		String message = "Test to see if an invalid batch id throws an exception";
		
		Mockito.when( restTemplate.getForObject( Mockito.anyString(), 
													Mockito.eq( BatchDTO.class )))
													.thenThrow(new RestClientException( message ));
		
		Mockito.when( repository.findById( reservation.getReservationId() ))
						.thenReturn( Optional.of( reservation ));
		
		reservationService.assignBatch( reservation.getReservationId(), -1);
		
		assertEquals(message, expected, reservation);
	}
	
	@Test(expected = IllegalStateException.class)
	public void testAddingABatchToReservationWithMissMatchedDates() {
		
		Reservation expected = new Reservation( 1, 1, 1, 1,
												RoomType.PHYSICAL,
												"JUnit Test",
												"02-02-2021 00:00",
												"02-02-2021 01:00" );
		
		String message = "Test to add a batch to the reservation";
		
		batch.startDate = "2021-01-01";
		batch.endDate = "2021-01-31";
		
		Mockito.when( restTemplate.getForObject( Mockito.anyString(), 
													Mockito.eq(BatchDTO.class) ))
														.thenReturn( batch );
		
		Mockito.when( repository.findById( reservation.getReservationId() ))
											.thenReturn( Optional.of( reservation ));
		
		ArgumentCaptor<Reservation> savedReservation = ArgumentCaptor.forClass( Reservation.class );
		
		Mockito.when( repository.save( savedReservation.capture() )).thenReturn( null );
		
		reservationService.assignBatch( reservation.getReservationId(), 101);
		
		assertEquals(message, expected, savedReservation.getValue());
	}
}
