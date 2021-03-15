package com.revature.controller;

import com.revature.dto.RoomDTO;
import com.revature.model.Reservation;
import com.revature.service.ReservationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;

import java.util.List;
import java.util.NoSuchElementException;

@Api("Reservations Controller")
@RestController
@RequestMapping(path="/api/")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping(path="/{reservationId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Reservation> getReservationById(@PathVariable Integer reservationId){
    	
         Reservation reservationById = reservationService.getReservationById(reservationId);
         return new ResponseEntity<Reservation>(reservationById,HttpStatus.OK);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Reservation>> getAllReservations(){
    	
        reservationService.getAllReservations();
        return new ResponseEntity<List<Reservation>>(reservationService.getAllReservations(),HttpStatus.OK);
    }

    @ApiOperation(value = "List of Reservations by Room Id", notes = "This controller returns a list of reservation objects")
    @GetMapping(path = "/rooms/{roomId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Reservation>> getAllReservationsByRoomId(@PathVariable Integer roomId) {
    	
        List<Reservation> allReservations = reservationService.getAllReservationsByRoomId(roomId);
        return new ResponseEntity<>(allReservations,HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Reservation> addReservation(@RequestBody Reservation reservation) {
    	
        Reservation res = reservationService.addReservation(reservation);

        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Reservation> updateReservation(@RequestBody Reservation reservation) {
    	
        Reservation res = reservationService.updateReservation(reservation);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{reservationId}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Integer reservationId) {
    	
        reservationService.deleteReservation(reservationId);
        return  new ResponseEntity<Void>(HttpStatus.OK);
    }
    
    @ApiOperation(value="Add batch to reservation", notes="calls caliber service to match a batch to reservation")
    @PutMapping(path="/{reservationId}/{batchId}")
    public ResponseEntity<String> assignBatch( @PathVariable Integer reservationId, 
    											@PathVariable Integer batchId ) {
    	
    	try {
    		reservationService.assignBatch(reservationId, batchId);
    		
    	} catch( NoSuchElementException noSuchElementException ) {
    		return new ResponseEntity<String>( noSuchElementException.getMessage(),
    											HttpStatus.BAD_REQUEST );
    		
    	} catch( IllegalStateException illegalStateException ) {
    		return new ResponseEntity<String>( illegalStateException.getMessage(),
														HttpStatus.BAD_REQUEST );
    		
    	} catch( RestClientException restClientException ) {
    		
    		return new ResponseEntity<String>(  "Batch id: " + batchId +
    												" not found, Caliber response: " +
    												restClientException.getMessage(),
													HttpStatus.NOT_FOUND );
    	}
    	
        return new ResponseEntity<String>( "{" + '"' + "body" + '"' + ":" + '"' + 
        					"Batch assigned successfully"+ '"' +"}", HttpStatus.CREATED );
    }

    @GetMapping(path = "/trainingstations", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Reservation>> getTrainingStationReservations() {
    	
        return new ResponseEntity<List<Reservation>>(reservationService.getTrainingStationReservations(),HttpStatus.OK);
    }

    @GetMapping(path = "/trainingstations/building/{buildingId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Reservation>> getTrainingStationReservationsByBuildingId(@PathVariable Integer buildingId) {
    	
        return new ResponseEntity< List<Reservation> >(
		        		reservationService.getTrainingStationReservationsByBuildingId(buildingId),
		        		HttpStatus.OK);
    }

    @GetMapping(path="/{buildingId}/meetingrooms")
    public ResponseEntity<List<RoomDTO>> getAllAvailableMeetingRooms(@PathVariable Integer buildingId,
    																@RequestParam String startDate,
    																@RequestParam String endDate) {
    	
        List<RoomDTO> availableMeetingRooms = reservationService.getAllAvailableMeetingRooms(buildingId, startDate, endDate);
        return new ResponseEntity<>(availableMeetingRooms,HttpStatus.OK);

    }

    @GetMapping(path="/{buildingId}/trainingstations")
    public ResponseEntity<List<RoomDTO>> getAllAvailableTrainingStations(@PathVariable Integer buildingId, @RequestParam String startDate, @RequestParam String endDate){
        List<RoomDTO> availableTrainingStations = reservationService.getAllAvailableTrainingStations(buildingId, startDate, endDate);
        return new ResponseEntity<>(availableTrainingStations,HttpStatus.OK);

    }
}
