package com.revature.repository;

import com.revature.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation,Integer> {

    @Query("select r from Reservation r where r.roomId = ?1")
    List<Reservation> findAllReservationsByRoomId(Integer roomId);

	Reservation getReservationById(Integer reservationId);

}
