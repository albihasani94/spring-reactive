package net.albinhasani.reactivestreams.demo.service;

import static net.albinhasani.reactivestreams.demo.mapper.ReservationMapper.toReservationDto;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.lenient;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import net.albinhasani.reactivestreams.demo.entity.Reservation;
import net.albinhasani.reactivestreams.demo.repository.ReservationRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceTests {

    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private ReservationService reservationService;

    private static final List<Reservation> reservations = List.of(
            new Reservation(1, "John"),
            new Reservation(2, "Maria"),
            new Reservation(3, "Joanna"));

    @BeforeEach
    public void setUp() {
        lenient().when(reservationRepository.findAll()).thenReturn(Flux.fromIterable(reservations));
        lenient().when(reservationRepository.findById(anyInt())).thenReturn(Mono.just(reservations.get(0)));
    }

    @Test
    public void testFindAll() {
        var result = reservationService.findAll();

        var reservationDtos = reservations.stream().map(toReservationDto()).toList();

        StepVerifier.create(result)
                .expectNext(reservationDtos.get(0))
                .expectNextCount(1)
                .expectNextMatches(reservation -> reservation.name().equals("Joanna"))
                .expectComplete()
                .verify();
    }

    @Test
    void testById() {
        var result = reservationService.findById(1);

        var expected = toReservationDto().apply(reservations.get(0));

        StepVerifier.create(result)
                .expectNext(expected)
                .expectComplete()
                .verify();
    }

    @Test
    void testFindAllCount() {
        var result = reservationService.findAll();

        StepVerifier.create(result)
                .expectNextCount(3L)
                .verifyComplete();
    }

}
