package com.example.hotel.controller;

import com.example.hotel.entity.Reserva;
import com.example.hotel.service.ReservaService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReservaController.class)
public class ReservaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservaService reservaService;

    @Test
    void testListarReservas() throws Exception {
        Reserva reserva = new Reserva("Maria", LocalDate.now(), LocalDate.now().plusDays(1));

        when(reservaService.listarReservas()).thenReturn(List.of(reserva));

        mockMvc.perform(get("/api/reservas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].cliente").value("Maria"));
    }

    @Test
    void testSalvarReserva() throws Exception {
        Reserva reserva = new Reserva("José", LocalDate.now(), LocalDate.now().plusDays(3));

        when(reservaService.salvarReserva(Mockito.any(Reserva.class))).thenReturn(reserva);

        mockMvc.perform(post("/api/reservas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"cliente\": \"José\", \"dataEntrada\": \"2025-01-14\", \"dataSaida\": \"2025-01-17\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cliente").value("José"));
    }
}
