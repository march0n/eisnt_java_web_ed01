package com.example.hotel.service;

import com.example.hotel.entity.Reserva;
import com.example.hotel.repository.ReservaRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class ReservaServiceTest {

    private final ReservaRepository reservaRepository = Mockito.mock(ReservaRepository.class);
    private final ReservaService reservaService = new ReservaService(reservaRepository);

    @Test
    void testListarReservas() {
        Reserva reserva1 = new Reserva("Ana", LocalDate.now(), LocalDate.now().plusDays(3));
        Reserva reserva2 = new Reserva("Carlos", LocalDate.now(), LocalDate.now().plusDays(1));

        when(reservaRepository.findAll()).thenReturn(Arrays.asList(reserva1, reserva2));

        List<Reserva> reservas = reservaService.listarReservas();
        assertEquals(2, reservas.size());
    }

    @Test
    void testBuscarReservasPorCliente() {
        Reserva reserva = new Reserva("Ana", LocalDate.now(), LocalDate.now().plusDays(3));

        when(reservaRepository.findByCliente("Ana")).thenReturn(List.of(reserva));

        List<Reserva> reservas = reservaService.buscarReservasPorCliente("Ana");
        assertEquals(1, reservas.size());
        assertEquals("Ana", reservas.get(0).getCliente());
    }
}
