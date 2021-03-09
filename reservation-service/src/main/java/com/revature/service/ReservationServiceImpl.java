package com.revature.service;

import com.revature.model.Reservation;
import com.revature.model.Room;
import com.revature.repository.ReservationRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static com.revature.model.RoomType.VIRTUAL;

import com.revature.dto.BatchDTO;

@Service
public class ReservationServiceImpl implements ReservationService {


    private final ReservationRepository repository;
    
    @Value("${revature.caliberUrl}")
    private String caliberUrl;
    
    private RestTemplate restTemplate;
    
    public ReservationServiceImpl( ReservationRepository repository ) {
        this.repository = repository;
    }

    public RestTemplate getRestTemplate() {
		return restTemplate;
	}

	public void setRestTemplate( RestTemplate restTemplate ) {
		this.restTemplate = restTemplate;
	}

	@Override
    public Reservation addReservation( Reservation reservation ) {
        return repository.save( reservation );
    }

    @Override
    public Reservation updateReservation( Reservation reservation ) {
        deleteReservation( reservation.getReservationId() );
        return addReservation( reservation );
    }

    @Override
    public void deleteReservation( Integer reservationId ) {
        repository.deleteById( reservationId );
    }

	@Override
	public List<Reservation> getAllReservations() {
		return repository.findAll();
	}

    @Override
    public Reservation getReservationById( Integer reservationId ) {
        return repository.getOne( reservationId );
    }

    @Override
    public void assignBatch( Integer reservationId, Integer batchId ) throws NoSuchElementException, 
    																	IllegalArgumentException {
    	
    	Reservation reservation = repository.findById( reservationId ).get();
    	
    	if( reservation.getBatchId() != null ) {
    		// throw exception
    		return;
    	}
    	
    	// Can throw IllegalArgumentException / 500 http Status code
    	// but pass the exception to the calling method
		BatchDTO batchDto = restTemplate.getForObject(caliberUrl + 
									    				"batch/" + 
									    				batchId, 
									    				BatchDTO.class);
		
		batchDto.formatDate();
		
    	// Validate dates
		
    	reservation.setBatchId(batchId);
    	repository.save(reservation);
    }


	@Override
	public List<Reservation> getAllReservationsByRoomId(Integer roomId) {
		return repository.findAllReservationsByRoomId(roomId);
	}

	@Override
	public List<Room> getAllAvailableMeetingRooms(Integer BuildingId, String startDate, String endDate) {
		// make request to locations service to get the list of rooms by building id
		// from the list, extract all the rooms that have not been reserved yet in a specific time frame, filter by startDate and endDate
		// filter the list further by room occupation (by meeting) and return the list of rooms
		return null;
	}

	@Override
	public List<Reservation> getTrainingStationReservations() {
		return repository.findAll().stream().filter(x -> x.getRoomType().equals(VIRTUAL)).collect(Collectors.toList());
	}

	@Override
	public boolean isValidReservation(Reservation reservation) {
		
		
		//list for reservations		
		//populate list with all reservations with a matching room number
		 List<Reservation> reservations = repository.findAllReservationsByRoomId( reservation.getRoomId() );

		//for each reservation in the list of shared room numbers check to see if the reservations
		//start date and end date fall conflict with any of the already listed reservations
		for(Reservation res : reservations) {
			
			//reject if the proposed start date is before a listed start date, and the proposed
			//end date is after the proposed start date  
			if(  reservation.getStartDate().before( res.getStartDate() ) && 
					reservation.getEndDate().after( res.getStartDate() ) ) {
				
				//reservation is invalid result is false
				return false;

			}
			
			// or if the proposed start date and end date are in between a listed start and end dates
			if( reservation.getStartDate().after(res.getStartDate() )   && 
					reservation.getEndDate().before(res.getEndDate() )  ) {
				return false;
			}
			
			// or the proposed reservation wraps a prior reservation 
			if( reservation.getStartDate().before(res.getStartDate() )   && 
					reservation.getEndDate().after(res.getEndDate() )  ) {
				return false;
			}
			
			// or if the proposed start date is before a listed end date and the proposed end date 
			// is after a listed end date
			if( reservation.getStartDate().before(res.getEndDate()) &&
					reservation.getEndDate().after( res.getEndDate() ) ) {
				return false;
			}
			
			// or if the proposed start date and end date match 
			if( reservation.getStartDate().equals( res.getStartDate() ) ||
					reservation.getEndDate().equals( res.getEndDate() ) ) {
				
				//fail schedule conflicts
				return false;
			}
		}
		
		return true;
	}



}
