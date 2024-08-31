package com.chandan.CompanyAssignment.Utility;

import org.springframework.stereotype.Component;

@Component
public class Utility {

	// Utility method to check if a string is a valid email
	public boolean isValidEmail(String email) {
		String emailRegex = "^[\\w-_.+]*[\\w-_.]@[\\w]+\\.[a-zA-Z]{2,}$";
		return email != null && email.matches(emailRegex);
	}

	// Utility method to check if a string is a valid phone number
	public boolean isValidPhoneNumber(String phoneNumber) {
		// Basic check for phone number being digits and having a length typical for
		// phone numbers
		return phoneNumber != null && phoneNumber.matches("\\d+");
	}

}
