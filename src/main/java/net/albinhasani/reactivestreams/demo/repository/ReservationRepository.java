package net.albinhasani.reactivestreams.demo.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import net.albinhasani.reactivestreams.demo.entity.Reservation;

@Repository
public interface ReservationRepository extends ReactiveCrudRepository<Reservation, Integer> {

}
