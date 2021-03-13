package com.revature.service;

import com.revature.dto.RoomDTO;
import com.revature.model.Reservation;

import com.revature.repository.ReservationRepository;
import com.revature.util.RoomOccupation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import java.util.stream.Collectors;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import static com.revature.util.RoomOccupation.TRAINING;
import static com.revature.util.RoomType.PHYSICAL;
import static com.revature.util.RoomType.VIRTUAL;


import com.revature.dto.BatchDTO;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository repository;
    
    @Value("${revature.caliberUrl}")
    private String caliberUrl;

	@Value("${revature.locationServiceUrl}")
	private String locationServiceUrl;
    
    private RestTemplate restTemplate;
    
    @Autowired
    public ReservationServiceImpl( ReservationRepository repository ) {
        this.repository = repository;
    }
    public RestTemplate getRestTemplate() {
		return restTemplate;
	}
    
    @Autowired
	public void setRestTemplate( RestTemplate restTemplate ) {
		this.restTemplate = restTemplate;
	}

	@Override
    public Reservation addReservation( Reservation reservation ) {
        if (!isValidReservation(reservation)) {
        	throw new EntityExistsException("Entered time-slot is not available for this reservation");
		}

		Reservation savedReservation = repository.save(reservation);
        repository.flush();
        return savedReservation;
	}

    @Override
    public Reservation updateReservation( Reservation reservation ) {
		List<Reservation> reservations = repository.findAllReservationsByRoomId(reservation.getRoomId());
		Reservation temp = getReservationById(reservation.getReservationId());
		reservations.remove(temp);
		for(Reservation res : reservations) {
			if(  reservation.getStartDate().before( res.getStartDate() ) &&
					reservation.getEndDate().after( res.getStartDate() ) ) {
				addReservation(res);
				throw new EntityExistsException("Entered time-slot is not available for this reservation");
				}
			}
		Reservation savedReservation = repository.save(reservation);
		repository.flush();
		return savedReservation;
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
    																	IllegalStateException,
    																	RestClientException {
    	
    	Reservation reservation = repository.findById( reservationId ).get();
    	
    	if( reservation.getBatchId() != null ) {
    		throw new IllegalStateException( "Batch is already assigned to Reservation" );
    	}
    	
    	// Throws RestClientException if cannot convert to the object
		BatchDTO batchDto = restTemplate.getForObject(caliberUrl + 
									    				"batch/" + 
									    				batchId, 
									    				BatchDTO.class);
		
		batchDto.formatDate();
		
    	// Validate dates
		if( !datesAreEqualWithoutTime( reservation.getStartDate(), batchDto.startTime )) {
			throw new IllegalStateException("Batch and Reservation start dates do not match");
		}
		
		if( !datesAreEqualWithoutTime( reservation.getEndDate(), batchDto.endTime )) {
			throw new IllegalStateException("Batchand Reservation end dates do not match");
		}
		
    	reservation.setBatchId(batchId);
    	repository.save(reservation);
    }

	@Override
	public List<Reservation> getAllReservationsByRoomId(Integer roomId) {
		return repository.findAllReservationsByRoomId(roomId);
	}


    @Override
    public List<RoomDTO> getAllAvailableMeetingRooms( Integer BuildingId, String startDate, 
    		String endDate ) {
		// make request to locations service to get the list of rooms by building id
		// from the list, extract all the rooms that have not been reserved yet in a specific time 
    	//frame, filter by startDate and endDate
		// filter the list further by room occupation (by meeting) and return the list of rooms

		Map<String,Date> dates = new HashMap<>();
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy HH:mm");
			dates.put("startDate",formatter.parse( startDate )); //for start date
			dates.put("endDate", formatter.parse( endDate )); //for end date
		}catch ( ParseException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//String locationServiceUrl = "http://localhost:8080/api/location/rooms";

		UriComponentsBuilder uriBuilder =UriComponentsBuilder.fromUriString(
				locationServiceUrl
		)
				.path(String.valueOf(BuildingId));
	//	URI uri = URI.create( locationServiceUrl + BuildingId );

		ResponseEntity<RoomDTO[]> responseEntity = restTemplate.getForEntity( 
				uriBuilder.toUriString(), RoomDTO[].class );


		List<RoomDTO> rooms = Arrays.stream(responseEntity.getBody()).filter(roomDTO -> 
		roomDTO.getOccupation().equalsIgnoreCase("MEETING")).collect(Collectors.toList());

		List<Integer> unavailableRoomIds = new ArrayList<>();

		for ( int i = 0; i < rooms.size() ; ++i ) {

			//populate list with all reservations with a matching room number
			List<Reservation> reservations = repository.findAllReservationsByRoomId(rooms.get(i).getId())
					.stream()
					.filter(x -> x.getRoomType() == PHYSICAL)
					.collect(Collectors.toList());

			for (int j = 0; j < reservations.size(); j++) {

				if(dates.get("startDate").after(reservations.get(j).getStartDate()) &&
						dates.get("startDate").before(reservations.get(j).getEndDate())) {
					unavailableRoomIds.add(reservations.get(j).getRoomId());
				}
				if (dates.get("endDate").after(reservations.get(j).getStartDate()) &&
						dates.get("endDate").before(reservations.get(j).getEndDate())) {
					unavailableRoomIds.add(reservations.get(j).getRoomId());
				}
				if (dates.get("startDate").before(reservations.get(j).getStartDate()) &&
						dates.get("endDate").after(reservations.get(j).getEndDate())) {
					unavailableRoomIds.add(reservations.get(j).getRoomId());
				}
				if (dates.get("startDate").equals(reservations.get(j).getStartDate()) &&
						dates.get("endDate").equals(reservations.get(j).getEndDate())) {
					unavailableRoomIds.add(reservations.get(j).getRoomId());
				}

			}

		}
		ArrayList<RoomDTO> toBeRemoved = new ArrayList<RoomDTO>();
		
		for ( int i = 0; i < rooms.size() ; ++i ) {

			for ( int k = 0; k < unavailableRoomIds.size() ; ++k ) {

				if( rooms.get(i).getId() == unavailableRoomIds.get(k)){
					//add to the list for removal from list to be returned
					toBeRemoved.add( rooms.get( i ) );
				}
			}
		}
		for(RoomDTO i : toBeRemoved) {
			rooms.remove(i);
		}
        return rooms;
    }


	@Override
	public List<Reservation> getTrainingStationReservations() {
    	List<Reservation> list = repository.findAll().stream().filter(x -> x.getRoomOccupation() == TRAINING).collect(Collectors.toList());
    	if (list.size() == 0) {
    		throw new EntityNotFoundException("no training station reservation found");
		}
		return list;
	}

	@Override
	public List<Reservation> getTrainingStationReservationsByBuildingId(Integer buildingId) {
		List<Reservation> list = repository.findAll().stream().filter(x -> x.getRoomOccupation() == TRAINING && x.getBuildingId() == buildingId).collect(Collectors.toList());
		if (list.size() == 0) {
			throw new EntityNotFoundException("no training station reservation found");
		}
		return list;
	}

	@Override
	public boolean isValidReservation(Reservation reservation) {
		
		
		//list for reservations		
		//populate list with all reservations with a matching room number
		 List<Reservation> reservations = repository.findAllReservationsByRoomId( 
				 reservation.getRoomId() );

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
			
			//or if the proposed start date and end date are in between a listed start and end dates
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

	private boolean  datesAreEqualWithoutTime(Date firstDate, Date secondDate) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String tempDateOne = dateFormat.format(firstDate);
		String tempDateTwo = dateFormat.format(secondDate);
		
		
		return tempDateOne.equals(tempDateTwo);
	}

}
