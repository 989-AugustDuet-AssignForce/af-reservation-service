package com.revature.service;

import static org.junit.Assert.*;

import org.junit.Test;
import com.revature.util.RoomType;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.List;
import com.revature.dto.RoomDTO;
import com.revature.model.Reservation;
import com.revature.repository.ReservationRepository;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AvailableTrainingStationsTest {
    @InjectMocks
    @Autowired
    private ReservationServiceImpl reservationService;
    @MockBean
    private ReservationRepository repository;
    @MockBean
    private RestTemplate restTemplate;


    @Before
    public void setUp() throws Exception {

        reservationService.setRestTemplate(restTemplate);
    }

    @Test
    public void viewAvailableStationsTest(){

        RoomDTO trn1 = new RoomDTO();
        trn1.setId(1);
        trn1.setType("REMOTE");
        trn1.setOccupation("TRAINING");

        RoomDTO trn2 = new RoomDTO();
        trn2.setId(2);
        trn2.setType("REMOTE");
        trn2.setOccupation("TRAINING");

        Reservation reservation1 = new Reservation(1,1,1,1,1,RoomType.REMOTE,"Junit",
                "02-25-2021 07:00","02-25-2021 08:00");
        Reservation reservation2 = new Reservation(1,1,1,1,1,RoomType.REMOTE,"Junit",
                "02-25-2021 12:00","02-25-2021 14:00");

        Mockito.when(repository.findAllReservationsByRoomId(1)).thenReturn(Arrays.asList(reservation1,reservation2));

        RoomDTO[] stations = {trn1, trn2};

        ResponseEntity<RoomDTO[]> responseEntity = new ResponseEntity<>(stations,HttpStatus.OK);
        Mockito.when(restTemplate.getForEntity(ArgumentMatchers.anyString(),
                Mockito.eq(RoomDTO[].class)))
                .thenReturn(responseEntity);

        List<RoomDTO> stnList = reservationService.getAllAvailableTrainingStations(1,"02-25-2021 07:00","02-25-2021 08:00");
        assertEquals(1, stnList.toArray().length);

    }

}
