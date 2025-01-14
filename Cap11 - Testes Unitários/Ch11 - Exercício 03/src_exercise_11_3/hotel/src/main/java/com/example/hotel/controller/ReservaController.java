package com.example.hotel.controller;

import com.example.hotel.entity.Reserva;
import com.example.hotel.service.ReservaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservas")
public class ReservaController {

    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @GetMapping
    public List<Reserva> listarReservas() {
        return reservaService.listarReservas();
    }

    @GetMapping("/cliente/{cliente}")
    public List<Reserva> buscarPorCliente(@PathVariable String cliente) {
        return reservaService.buscarReservasPorCliente(cliente);
    }

    @PostMapping
    public Reserva salvarReserva(@RequestBody Reserva reserva) {
        return reservaService.salvarReserva(reserva);
    }
}
