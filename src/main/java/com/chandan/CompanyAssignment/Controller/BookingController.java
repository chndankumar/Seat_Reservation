package com.chandan.CompanyAssignment.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.chandan.CompanyAssignment.Model.Booking;
import com.chandan.CompanyAssignment.Service.BookingService;
import com.chandan.CompanyAssignment.helper.BookingDetail;
import com.chandan.CompanyAssignment.helper.BookingRequest;

import jakarta.validation.Valid;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/bookings")
public class BookingController {

	@Autowired
	private BookingService bookingService;

	  @PostMapping("/booking")
	    public ResponseEntity<?> createBooking(@Valid @RequestBody BookingRequest bookingRequest, BindingResult result) {
	        if (result.hasErrors()) {
	            // Return the validation error messages
	            List<String> errorMessages = result.getAllErrors().stream()
	                .map(DefaultMessageSourceResolvable::getDefaultMessage)
	                .collect(Collectors.toList());
	            return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
	        }

	        try {
	            // If no validation errors, proceed to create the booking
	            BookingDetail bookingDetail = bookingService.createBooking(
	                    bookingRequest.getSeatIds(),
	                    bookingRequest.getName(),
	                    bookingRequest.getEmail(),
	                    Long.parseLong(bookingRequest.getPhone())
	            );
	            return new ResponseEntity<>(bookingDetail, HttpStatus.CREATED);
	        } catch (Exception e) {
	            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
	        }
	    }
	  
	@GetMapping
	public ResponseEntity<?> getBookingsByUserIdentifier(@RequestParam("userIdentifier") String userIdentifier) {
		try {
			List<Booking> bookings = bookingService.getBookingsByUserIdentifier(userIdentifier);

			return new ResponseEntity<>(bookings, HttpStatus.OK);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
}