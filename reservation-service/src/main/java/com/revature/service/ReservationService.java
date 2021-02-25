package com.revature.service;

import com.revature.model.Reservation;

import java.util.List;

public interface ReservationService {

    List<Reservation> getAllReservations();
    Reservation getReservationById(Integer reservationId);
    List<Reservation> getAllReservationsByRoomId(Integer roomId);
    Reservation addReservation(Reservation reservation);
    Reservation updateReservation(Reservation reservation);
    void deleteReservation(Integer reservationId);
    void assignBatch(Integer batchId);
  	boolean isValidReservation(Reservation reservation);
}
