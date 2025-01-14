package com.example.hotel.repository;

import com.example.hotel.entity.Reserva;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class ReservaRepositoryTest {

    @Autowired
    private ReservaRepository reservaRepository;

    @Test
    void testSaveAndFindByCliente() {
        Reserva reserva = new Reserva("João Silva", LocalDate.now(), LocalDate.now().plusDays(2));
        reservaRepository.save(reserva);

        List<Reserva> reservas = reservaRepository.findByCliente("João Silva");
        assertNotNull(reservas);
        assertEquals(1, reservas.size());
        assertEquals("João Silva", reservas.get(0).getCliente());
    }
}
