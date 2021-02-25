package com.revature.service;

import com.revature.model.Reservation;
import com.revature.repository.ReservationRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository repository;

    public ReservationServiceImpl(ReservationRepository repository) {
        this.repository = repository;
    }

    @Override
    public Reservation addReservation(Reservation reservation) {
        return null;
    }

    @Override
    public Reservation updateReservation(Integer reservationId, Reservation reservation) {
        return null;
    }

    @Override
    public void deleteReservation(Integer reservationId) {

    }

    @Override
    public List<Reservation> getAllReservations() {
        return null;
    }

    @Override
    public Reservation getReservationById(Integer reservationId) {
        return null;
    }

    @Override
    public void assignBatch(Integer batchId) {

    }

    @Override
    public List<Reservation> getAllReservationsByRoomId(Integer buildingId, Integer roomId) {
        return null;
    }

	@Override
	public boolean isValidReservation(Reservation reservation) {
//		TODO Complete method
//		List<Reservation> reservations = new ArrayList<>();
//		repository.findAllReservationsByRoomId(reservation.getRoomId()).forEach(
//				res -> reservations.add(res));
//		
//		for(Reservation res : reservations) {
//			if( reservation.getEndDate()  ) {
//				
//			}
//		}
		return false;
	}

}
