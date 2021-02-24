package com.revature.controller;

import com.revature.service.ReservationService;
import org.springframework.stereotype.Controller;

@Controller
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }



}
