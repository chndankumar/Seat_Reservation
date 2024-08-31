package com.chandan.CompanyAssignment.helper;

import org.springframework.stereotype.Component;

import com.chandan.CompanyAssignment.Model.Seat;
@Component
public class SeatWithPrice {
	
	private Seat seat;
	private Double price;
	
	public Seat getSeat() {
		return seat;
	}
	public void setSeat(Seat seat) {
		this.seat = seat;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price2) {
		this.price = price2;
	}
}
