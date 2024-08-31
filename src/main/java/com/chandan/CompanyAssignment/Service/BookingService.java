package com.chandan.CompanyAssignment.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chandan.CompanyAssignment.Model.Booking;
import com.chandan.CompanyAssignment.Model.BookingStatus;
import com.chandan.CompanyAssignment.Model.MovieUser;
import com.chandan.CompanyAssignment.Model.Seat;
import com.chandan.CompanyAssignment.Repo.BookingRepo;
import com.chandan.CompanyAssignment.Repo.MovieUserRepo;
import com.chandan.CompanyAssignment.Utility.Utility;
import com.chandan.CompanyAssignment.helper.BookingDetail;
import com.chandan.CompanyAssignment.helper.SeatWithPrice;

import jakarta.transaction.Transactional;

@Service
public class BookingService {

	@Autowired
	private SeatService seatService;

	@Autowired
	private BookingRepo bookingRepo;

	@Autowired
	private MovieUserRepo userRepo;

	@Autowired
	private Utility utility;

	@Transactional
	public BookingDetail createBooking(List<Integer> seatIds, String name, String email, long phone) {

		MovieUser user = userRepo.findByPhone(phone);
		if (user == null) {
			// If the user doesn't exist, create a new user
			user = new MovieUser();
			user.setName(name);
			user.setEmail(email);
			user.setPhone(phone);
			userRepo.save(user);
		}

		// Create a new booking
		Booking booking = new Booking();
		booking.setUser(user);

		double totalAmount = 0;

		// Save the booking first to generate the booking ID
		bookingRepo.save(booking);
		List<Seat> bookedSeats = new ArrayList<>();
		for (int id : seatIds) {

			SeatWithPrice seatWithPrice = seatService.getSeat(id);

			if (seatWithPrice == null) {
				throw new RuntimeException("Invalid seat ID: " + id);
			}

			Seat seat = seatWithPrice.getSeat();

			if (seat.getStatus() == BookingStatus.BOOKED) {
				throw new RuntimeException("Seat already booked: " + id);
			} else {
				totalAmount += seatWithPrice.getPrice();
				seat.setStatus(BookingStatus.BOOKED);
				seat.setBooking(booking); // Set the booking in the seat
				// seatService.updateSeat(seat); // Save the updated seat information do not
				// need this as we have cascade.All this
				bookedSeats.add(seat);
			}
		}
		totalAmount = Math.round(totalAmount * 100.0) / 100.0;
		// Set the total amount for the booking
		booking.setAmount(totalAmount);
		booking.setSeats(bookedSeats);
		// Save the updated booking details with the total amount
		bookingRepo.save(booking);

		BookingDetail bookingDetail = new BookingDetail();
		bookingDetail.setBookingNumber(booking.getId());
		bookingDetail.setBookingAmount(totalAmount);
		return bookingDetail;
	}

	public List<Booking> getBookingsByUserIdentifier(String userIdentifier) {
		if (userIdentifier == null || userIdentifier.trim().isEmpty()) {
			throw new IllegalArgumentException("User identifier (email or phone number) is required.");
		}

		MovieUser user;

		if (utility.isValidEmail(userIdentifier)) {
			user = userRepo.findByEmail(userIdentifier);
		} else if (utility.isValidPhoneNumber(userIdentifier)) {
			long phoneNumber = Long.parseLong(userIdentifier);
			user = userRepo.findByPhone(phoneNumber);
		} else {
			throw new RuntimeException("Invalid user identifier format: " + userIdentifier);
		}

		if (user == null) {
			throw new RuntimeException("No user found with the provided identifier: " + userIdentifier);
		}

		return bookingRepo.findByUser(user);
	}

}
