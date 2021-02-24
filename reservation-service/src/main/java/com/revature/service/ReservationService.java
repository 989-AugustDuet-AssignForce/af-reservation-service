package com.revature.service;

import com.revature.model.Reservation;

import java.util.List;

public interface ReservationService {

    Reservation addReservation(Reservation reservation);
    Reservation updateReservation(Reservation reservation);
    void deleteReservation(Integer reservationId);
    List<Reservation> getAllReservations();
    void assignBatch(Integer batchId);
    List<Reservation> getAllReservationsByRoomId(Integer roomId);

}
