package com.revature.controller;

import com.revature.model.Reservation;
import com.revature.util.RoomType;
import com.revature.service.ReservationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest
public class ReservationControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Mock
    private ReservationService reservationService;

    ReservationController controller;

    @Before
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        controller = new ReservationController(reservationService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void getTrainingStationReservations() throws Exception {
        Reservation reservation1 = new Reservation(
                1,1,1,1,1, RoomType.VIRTUAL,"Revature CEO",
                "01-17-2021 09:00","01-17-2021 17:00");
        Reservation reservation2 = new Reservation(
                2,1,1,1,1,RoomType.VIRTUAL,"Revature CEO",
                "01-18-2021 09:00","01-18-2021 17:00");

        List<Reservation> trainingStations = new ArrayList<>();
        trainingStations.add(reservation1);
        trainingStations.add(reservation2);
        doReturn(trainingStations).when(reservationService).getTrainingStationReservations();

        mockMvc.perform(MockMvcRequestBuilders.get("/api/trainingstations")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(jsonPath("$", hasSize(2))).andDo(print());
    }

    @Test
    public void getReservationsByRoomId() throws Exception {

        Reservation reservation1 = new Reservation(
                1,1,1,1,1, RoomType.PHYSICAL,"Revature CEO",
                "01-17-2021 09:00","01-17-2021 17:00");
        Reservation reservation2 = new Reservation(
                2,1,1,1,1,RoomType.PHYSICAL,"Revature CEO",
                "01-18-2021 09:00","01-18-2021 17:00");
        Reservation reservation3 = new Reservation(
                3,1,1,1,1,RoomType.PHYSICAL,"Revature CEO",
                "01-19-2021 09:00","01-19-2021 17:00");

        List<Reservation> reservationList =
                new ArrayList<>(Arrays.asList(reservation1, reservation2,reservation3));

        Mockito.when(reservationService.getAllReservationsByRoomId(1)).thenReturn(reservationList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/rooms/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3))).andDo(print());
    }


}
