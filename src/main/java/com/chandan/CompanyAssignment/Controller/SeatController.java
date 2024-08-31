package com.chandan.CompanyAssignment.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.chandan.CompanyAssignment.Model.Seat;
import com.chandan.CompanyAssignment.Service.SeatService;
import com.chandan.CompanyAssignment.helper.SeatWithPrice;

import java.util.List;

@RestController
@RequestMapping("/api/seats")
public class SeatController {

	@Autowired
	private SeatService seatService;

	@GetMapping
	public ResponseEntity<List<Seat>> getAllSeats() {
		List<Seat> seats = seatService.getAllSeat();
		return ResponseEntity.ok(seats);
	}

	@GetMapping("/{id}")
	public ResponseEntity<SeatWithPrice> getSeatWithPrice(@PathVariable("id") int id) {
		SeatWithPrice seatWithPrice = seatService.getSeat(id);
		if (seatWithPrice == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(seatWithPrice);
	}

	// Update seat availability
	@PutMapping("/{id}")
	public ResponseEntity<Seat> updateSeat(@PathVariable("id") int id, @RequestBody Seat seat) {
		Seat existingSeat = seatService.getSeat(id).getSeat();
		if (existingSeat == null) {
			return ResponseEntity.notFound().build();
		}
		existingSeat.setStatus(seat.getStatus());
		seatService.updateSeat(existingSeat);
		return ResponseEntity.ok(existingSeat);
	}
}
