package com.revature.dataLooader;

import com.revature.model.Reservation;
import com.revature.util.RoomOccupation;
import com.revature.util.RoomType;
import com.revature.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@Profile("dev")
public class BootstrapData implements CommandLineRunner {


    private final ReservationRepository repository;

    public BootstrapData(ReservationRepository repository) {
        this.repository = repository;
    }

    @Value("${my.value}")
    String myValue;

    @Override
    public void run(String... args) throws Exception {

        System.out.println("Environment Running: " + myValue.toUpperCase());

        Reservation reservation1 = new Reservation(
                9,1,2,1,1, RoomType.PHYSICAL,"Revature CEO",
                "01-17-2021 09:00","01-17-2021 17:00", RoomOccupation.MEETING);
        
        Reservation reservation2 = new Reservation(
                6,1,1,1,2,RoomType.PHYSICAL,"Revature CEO",
                "01-18-2021 09:00","01-18-2021 17:00", RoomOccupation.TRAINING);
        
        Reservation reservation3 = new Reservation(
                8,1,2,1,2,RoomType.VIRTUAL,"Revature CEO",
                "01-19-2021 09:00","01-19-2021 17:00", RoomOccupation.MEETING);
        
        Reservation reservation4 = new Reservation(
                7,1,1,1,RoomType.PHYSICAL,"Revature CEO",
                "02-12-2021 09:00","04-23-2021 17:00");

        Reservation reservation5 = new Reservation(
                9,3,2,1,3,RoomType.VIRTUAL,"Revature CEO",
                "03-19-2021 09:00","03-19-2021 17:00", RoomOccupation.TRAINING);

        List<Reservation> reservationList = new ArrayList<>( Arrays.asList( reservation1,
        																	reservation2,
        																	reservation3,
        																	reservation4,
                                                                            reservation5));

        repository.saveAll(reservationList);
    }
}
