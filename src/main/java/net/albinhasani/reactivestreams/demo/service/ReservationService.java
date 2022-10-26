package net.albinhasani.reactivestreams.demo.service;

import org.springframework.stereotype.Service;

import net.albinhasani.reactivestreams.demo.dto.ReservationDto;
import net.albinhasani.reactivestreams.demo.repository.ReservationRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static net.albinhasani.reactivestreams.demo.mapper.ReservationMapper.*;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository repository) {
        this.reservationRepository = repository;
    }

    public Flux<ReservationDto> findAll() {
        return reservationRepository.findAll().map(toReservationDto());
    }

    public Mono<ReservationDto> findById(Integer id) {
        return reservationRepository.findById(id).map(toReservationDto());
    }
}
