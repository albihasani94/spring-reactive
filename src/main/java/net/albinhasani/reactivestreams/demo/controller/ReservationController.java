package net.albinhasani.reactivestreams.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.albinhasani.reactivestreams.demo.dto.ReservationDto;
import net.albinhasani.reactivestreams.demo.service.ReservationService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public Flux<ReservationDto> getAll() {
        return reservationService.findAll();
    }

    @GetMapping("/{id}")
    public Mono<ReservationDto> getById(@PathVariable Integer id) {
        return reservationService.findById(id);
    }

    
}
