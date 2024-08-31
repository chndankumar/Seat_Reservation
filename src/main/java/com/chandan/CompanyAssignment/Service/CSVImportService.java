package com.chandan.CompanyAssignment.Service;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

//import com.chandan.CompanyAssignment.Model.BookingStatus;
import com.chandan.CompanyAssignment.Model.Seat;
import com.chandan.CompanyAssignment.Model.SeatPrice;
import com.chandan.CompanyAssignment.Repo.SeatPriceRepo;
import com.chandan.CompanyAssignment.Repo.SeatRepo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Service
public class CSVImportService {
	@Autowired
	private SeatRepo seatRepo;

	@Autowired
	private SeatPriceRepo seatPriceRepo;

	// Method to import Seat data from CSV
	public void importSeats(MultipartFile file) {
		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));
				CSVParser csvParser = new CSVParser(reader,
						CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {

			for (CSVRecord csvRecord : csvParser) {
				Seat seat = new Seat();
				seat.setSeat_identifier(csvRecord.get("seat_identifier"));
				seat.setSeat_class(csvRecord.get("seat_class"));
//				seat.setStatus(BookingStatus.valueOf(csvRecord.get("status").toUpperCase()));

				seatRepo.save(seat);
			}
		} catch (IOException e) {
			throw new RuntimeException("Failed to parse CSV file: " + e.getMessage());
		}
	}

	// Method to import SeatPrice data from CSV
	public void importSeatPrices(MultipartFile file) {
		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));
				CSVParser csvParser = new CSVParser(reader,
						CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {

			for (CSVRecord csvRecord : csvParser) {
				SeatPrice seatPrice = new SeatPrice();
				seatPrice.setSeat_class(csvRecord.get("seat_class"));
				String minPriceStr = csvRecord.get("min_price");
				if (minPriceStr != null && !minPriceStr.trim().isEmpty()) {
					minPriceStr = minPriceStr.replaceAll("[$,]", "");
					seatPrice.setMinPrice(Double.parseDouble(minPriceStr));
				} else {
					seatPrice.setMinPrice(null);
				}
				
				String normalPriceStr = csvRecord.get("normal_price");
			    normalPriceStr = normalPriceStr.replaceAll("[$,]", "");
			    seatPrice.setNormalPrice(Double.parseDouble(normalPriceStr));

				String maxPriceStr = csvRecord.get("max_price");
				if (maxPriceStr != null && !maxPriceStr.trim().isEmpty()) {
			        maxPriceStr = maxPriceStr.replaceAll("[$,]", "");
			        seatPrice.setMaxPrice(Double.parseDouble(maxPriceStr));
				} else {
					seatPrice.setMaxPrice(null);
				}
				seatPriceRepo.save(seatPrice);
			}
		} catch (IOException e) {
			throw new RuntimeException("Failed to parse CSV file: " + e.getMessage());
		}
	}
}
