package net.albinhasani.reactivestreams.demo.repository;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import reactor.test.StepVerifier;

@AutoConfigureTestDatabase(replace = NONE)
@DataR2dbcTest
@Testcontainers
class ReservationRepositoryTests {

    @Container
    private static PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:14-alpine");

    @DynamicPropertySource
    static void postgresqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.r2dbc.url", () -> container.getJdbcUrl().replace("jdbc", "r2dbc"));
        registry.add("spring.r2dbc.username", container::getUsername);
        registry.add("spring.r2dbc.password", container::getPassword);
        registry.add("spring.flyway.url", container::getJdbcUrl);
        registry.add("spring.flyway.user", container::getUsername);
        registry.add("spring.flyway.password", container::getPassword);
    }

    @Autowired
    private ReservationRepository reservationRepository;

    @Test
    void repositoryIsLoaded() {
        assertNotNull(reservationRepository);
    }

    @Test
    void resultHasCorrectCount() {
        var result = reservationRepository.findAll();
        StepVerifier.create(result)
            .expectNextCount(5L)
            .verifyComplete();
    }

}
