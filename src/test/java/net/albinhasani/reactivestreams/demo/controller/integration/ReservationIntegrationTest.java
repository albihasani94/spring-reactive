package net.albinhasani.reactivestreams.demo.controller.integration;

import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Testcontainers
class ReservationIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Container
    private static PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:14-alpine");

    @LocalServerPort
    private int port;

    @DynamicPropertySource
    static void postgresqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.r2dbc.url", () -> container.getJdbcUrl().replace("jdbc", "r2dbc"));
        registry.add("spring.r2dbc.username", container::getUsername);
        registry.add("spring.r2dbc.password", container::getPassword);
        registry.add("spring.flyway.url", container::getJdbcUrl);
        registry.add("spring.flyway.user", container::getUsername);
        registry.add("spring.flyway.password", container::getPassword);
    }

    @Test
    void actuator() {
        this.webTestClient
                .get().uri("/actuator/health/")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.status")
                .value(is("UP"));
    }

    @Test
    void findFirst() {
        this.webTestClient.get().uri("/reservations/1")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id")
                .value(is(1))
                .jsonPath("$.name")
                .value(is("Frank"));
    }

}
