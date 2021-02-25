package com.revature.repository;

import com.revature.model.Reservation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReservationRepository extends CrudRepository<Reservation,Integer> {

    List<Reservation> findAllReservationsByRoomId(Integer roomId);
}
