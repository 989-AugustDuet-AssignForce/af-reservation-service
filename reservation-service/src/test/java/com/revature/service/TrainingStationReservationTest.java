package com.revature.service;

import com.revature.model.Reservation;
import com.revature.util.RoomOccupation;
import com.revature.util.RoomType;
import com.revature.repository.ReservationRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static com.revature.util.RoomType.VIRTUAL;
import static org.mockito.Mockito.*;


@RunWith(SpringRunner.class)
@DataJpaTest
public class TrainingStationReservationTest {

    @MockBean
    private ReservationRepository repository;
    @InjectMocks
    private ReservationServiceImpl reservationService;
    @BeforeClass
    public static void beforeClass() {

    }

    Reservation reservation1 = new Reservation();
    Reservation reservation2 = new Reservation();
    Reservation meetingRoom = new Reservation();
    List<Reservation> list = new ArrayList<>();


    @Before
    public void before() {
        repository = Mockito.mock(ReservationRepository.class);

        reservationService = new ReservationServiceImpl(repository);
        reservation1.setReservationId(1);
        reservation1.setRoomOccupation(RoomOccupation.TRAINING);
        reservation1.setReserver("Revature CEO");
        reservation1.setBuildingId(101);
        reservation1.setRoomId(101);

        reservation2.setReservationId(112);
        reservation2.setRoomOccupation(RoomOccupation.TRAINING);

        reservation2.setReserver("Revature CEO");
        reservation2.setBuildingId(101);
        reservation2.setRoomId(201);

        meetingRoom.setReservationId(113);
        meetingRoom.setRoomType(RoomType.PHYSICAL);
        meetingRoom.setReserver("Revature CEO");
        meetingRoom.setBuildingId(101);
        meetingRoom.setRoomId(303);

        List<Reservation> list = new ArrayList<>();
        doReturn(reservation1).when(repository).getOne(reservation1.getReservationId());
        doReturn(reservation2).when(repository).getOne(reservation2.getReservationId());
        doReturn(meetingRoom).when(repository).getOne(meetingRoom.getReservationId());
        doReturn(reservation1).when(repository).save(reservation1);
        doReturn(reservation2).when(repository).save(reservation2);
        doReturn(meetingRoom).when(repository).save(meetingRoom);

    }


    @Test
    public void addTrainingStation(){
        Reservation test = reservationService.addReservation(reservation1);
        Assert.assertEquals(reservation1, test);
    }

    @Test
    public void GetTrainingStationById(){

        Reservation getRes = reservationService.getReservationById(1);
        Assert.assertEquals(reservation1, getRes);
    }

    @Test
    public void getAllTrainingStations(){
        list.add(reservationService.addReservation(reservation1));
        list.add(reservationService.addReservation(reservation2));
        list.add(reservationService.addReservation(meetingRoom));
        doReturn(list).when(repository).findAll();
        Assert.assertEquals(2, reservationService.getTrainingStationReservations().size());
    }

    @Test
    public void deleteTrainingStationById(){
        list.add(reservationService.addReservation(reservation1));
        list.add(reservationService.addReservation(reservation2));
        list.add(reservationService.addReservation(meetingRoom));
        doReturn(list).when(repository).findAll();
        Assert.assertEquals(2, reservationService.getTrainingStationReservations().size());

        reservationService.deleteReservation(reservation2.getReservationId());
        verify(repository, times(1)).deleteById(reservation2.getReservationId());
        }

    @Test
    public void updateTrainingStation(){
        reservationService.addReservation(reservation1);
        reservationService.addReservation(reservation2);
        Reservation test = reservationService.addReservation(reservation1);
        Assert.assertEquals(reservation1, reservationService.getReservationById(reservation1.getReservationId()));

        reservation1.setReserver("new reserver");
        reservationService.updateReservation(reservation1);
        Reservation updatedRes = reservationService.getReservationById(reservation1.getReservationId());
        Assert.assertEquals(reservation1.getReserver(), updatedRes.getReserver());
    }
}
