package com.chandan.CompanyAssignment.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chandan.CompanyAssignment.Model.Seat;
import com.chandan.CompanyAssignment.Model.SeatPrice;
import com.chandan.CompanyAssignment.Repo.SeatPriceRepo;
import com.chandan.CompanyAssignment.Repo.SeatRepo;
import com.chandan.CompanyAssignment.helper.SeatWithPrice;
@Service
public class SeatService {

	@Autowired
	private SeatRepo seatRepo;

	@Autowired
	private SeatPriceRepo seatPriceRepo;

	public List<Seat> getAllSeat() {
		List<Seat> seatList = seatRepo.findAll();
		return seatList;
	}

	public SeatWithPrice getSeat(int id) {
		
		Seat seat = seatRepo.findById(id).orElseThrow();
		String className = seat.getSeat_class();
		
		SeatPrice seatPrice = (SeatPrice) seatPriceRepo.findBySeatClass(className);
		
		double bookedPercentage = seatRepo.findBookedSeatPercentage(className);
		
		Double price;
		if (bookedPercentage >= 60 && seatPrice.getMaxPrice() != null) {
			price = seatPrice.getMaxPrice();

		} else if (bookedPercentage >= 40 && seatPrice.getMinPrice() != null) {
			price = seatPrice.getMinPrice();

		} else {
			price = seatPrice.getNormalPrice();
		}

		SeatWithPrice seatWithPrice = new SeatWithPrice();
		seatWithPrice.setSeat(seat);
		seatWithPrice.setPrice(price);
		return seatWithPrice;
	}

	public void updateSeat(Seat seat) {	
	    seatRepo.save(seat);
	}

}
