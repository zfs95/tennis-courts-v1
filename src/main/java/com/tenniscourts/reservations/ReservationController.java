package com.tenniscourts.reservations;

import com.tenniscourts.config.BaseRestController;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/reservation")
@AllArgsConstructor
public class ReservationController extends BaseRestController {

    private final ReservationService reservationService;

    @PostMapping(path = "/add")
    public ResponseEntity<List<ReservationDTO>> bookReservation(@RequestBody List<CreateReservationRequestDTO> createReservationRequestDTO) {
        return ResponseEntity.ok(reservationService.bookReservation(createReservationRequestDTO));
    }
    @GetMapping(value = "/{reservationId}")
    public ResponseEntity<ReservationDTO> findReservation(@PathVariable Long reservationId) {
        return ResponseEntity.ok(reservationService.findReservation(reservationId));
    }

    @GetMapping(value = "/previous/reservations/{date}")
    public ResponseEntity<List<ReservationDTO>> findPreviousReservations(@PathVariable @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm")LocalDateTime date){
        return ResponseEntity.ok(reservationService.findPreviousReservation(date));
    }

    @DeleteMapping(value = "/delete/{reservationId}")
    public ResponseEntity<ReservationDTO> cancelReservation(@PathVariable Long reservationId) {
        return ResponseEntity.ok(reservationService.cancelReservation(reservationId));
    }

    @PatchMapping(value = "/update/{reservationId}/{scheduleId}")
    public ResponseEntity<ReservationDTO> rescheduleReservation(@PathVariable Long reservationId,@PathVariable Long scheduleId) {
        ReservationDTO result = null;
        try{
            result = reservationService.rescheduleReservation(reservationId, scheduleId);
        }catch (IllegalArgumentException ex){
            //log here
        }
        return result == null ? ResponseEntity.badRequest().body(null) : ResponseEntity.ok(result);
    }
}
