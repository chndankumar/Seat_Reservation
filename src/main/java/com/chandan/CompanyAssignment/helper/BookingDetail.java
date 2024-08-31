package com.chandan.CompanyAssignment.helper;

import org.springframework.stereotype.Component;

@Component
public class BookingDetail {
	
	private long bookingNumber;
	private Double bookingAmount;
	
	public long getBookingNumber() {
		return bookingNumber;
	}
	public void setBookingNumber(long bookingNumber) {
		this.bookingNumber = bookingNumber;
	}
	public double getBookingAmount() {
		return bookingAmount;
	}
	public void setBookingAmount(double bookingAmount) {
		this.bookingAmount = bookingAmount;
	}
	
}
