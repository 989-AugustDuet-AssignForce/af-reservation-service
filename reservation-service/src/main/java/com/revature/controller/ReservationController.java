package com.revature.controller;

import com.revature.model.Reservation;
import com.revature.service.ReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(path="/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping(path="/{reservationId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Reservation> getReservationById(@PathVariable Integer reservationId){
        reservationService.getReservationById(reservationId);
        return new ResponseEntity<Reservation>(HttpStatus.OK);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Reservation>> getAllReservations(){
        reservationService.getAllReservations();
        return new ResponseEntity<List<Reservation>>(HttpStatus.OK);
    }

    @GetMapping(path = "/{buildingId}/{roomId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Reservation>> getAllReservationsByRoomId(@PathVariable Integer buildingId, @PathVariable Integer roomId){
        reservationService.getAllReservationsByRoomId(buildingId,roomId);
        return new ResponseEntity<List<Reservation>>(HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Reservation> addReservation(@RequestBody Reservation reservation){
        reservationService.addReservation(reservation);
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{reservationId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Reservation> updateReservation(@RequestBody Reservation reservation, @PathVariable Integer reservationId){
        reservationService.updateReservation(reservationId,reservation);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{reservationId}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Integer reservationId){
        return  new ResponseEntity<Void>(HttpStatus.OK);
    }

    //pending, needs more discussion
    public ResponseEntity<Void> assignBatch(@PathVariable Integer batchId){
        reservationService.assignBatch(batchId);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }


}
