package com.revature.service;

import com.revature.dto.RoomDTO;
import com.revature.model.Reservation;

import java.util.List;

public interface ReservationService {

    List<Reservation> getAllReservations();
    Reservation getReservationById(Integer reservationId);
    List<Reservation> getAllReservationsByRoomId(Integer roomId);
    Reservation addReservation(Reservation reservation);
    Reservation updateReservation(Reservation reservation);
    void deleteReservation(Integer reservationId);
    void assignBatch(Integer reservationId, Integer batchId);
  	boolean isValidReservation(Reservation reservation);
    List<RoomDTO> getAllAvailableMeetingRooms(Integer BuildingId, String startDate, String endDate);
    List<Reservation> getTrainingStationReservations();
    List<Reservation> getTrainingStationReservationsByBuildingId(Integer buildingId);
	List<RoomDTO> getAllAvailableTrainingStations(Integer buildingId, String startDate, String endDate);

}
