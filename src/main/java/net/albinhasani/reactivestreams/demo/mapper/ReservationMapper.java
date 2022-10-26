package net.albinhasani.reactivestreams.demo.mapper;

import java.util.function.Function;

import net.albinhasani.reactivestreams.demo.dto.ReservationDto;
import net.albinhasani.reactivestreams.demo.entity.Reservation;

public class ReservationMapper {

    private ReservationMapper() {
    }

    public static Function<Reservation, ReservationDto> toReservationDto() {
        return reservation -> new ReservationDto(reservation.getId(), reservation.getName());
    }

}
