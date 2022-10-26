package net.albinhasani.reactivestreams.demo.controller.web;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import net.albinhasani.reactivestreams.demo.controller.ReservationController;
import net.albinhasani.reactivestreams.demo.dto.ReservationDto;
import net.albinhasani.reactivestreams.demo.service.ReservationService;
import reactor.core.publisher.Mono;

@WebFluxTest(ReservationController.class)
class ReservationControllerTests {

    @MockBean
    private ReservationService reservationService;

    @Autowired
    private WebTestClient webClient;

    private final ReservationDto reservationDto = new ReservationDto(1, "Maria");

    @Test
    void upAndRunning() {
        given(this.reservationService.findById(1)).willReturn(Mono.just(reservationDto));

        this.webClient.get().uri("/reservations/1")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id")
                .value(is(reservationDto.id()))
                .jsonPath("$.name")
                .value(is(reservationDto.name()));
    }
}
