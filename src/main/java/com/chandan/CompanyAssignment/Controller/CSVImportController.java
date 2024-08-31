package com.chandan.CompanyAssignment.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.chandan.CompanyAssignment.Service.CSVImportService;

@RestController
@RequestMapping("/import")
public class CSVImportController {

    @Autowired
    private CSVImportService csvImportService;

    @PostMapping("/seats")
    public ResponseEntity<String> importSeats(@RequestParam("SeatData") MultipartFile file) {
        if (file.isEmpty()) {
            return new ResponseEntity<>("Please upload a CSV file.", HttpStatus.BAD_REQUEST);
        }

        try {
            csvImportService.importSeats(file);
            return new ResponseEntity<>("Seats imported successfully.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to import seats: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/seat-prices")
    public ResponseEntity<String> importSeatPrices(@RequestParam("SeatPricing") MultipartFile file) {
        if (file.isEmpty()) {
            return new ResponseEntity<>("Please upload a CSV file.", HttpStatus.BAD_REQUEST);
        }

        try {
            csvImportService.importSeatPrices(file);
            return new ResponseEntity<>("Seat prices imported successfully.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to import seat prices: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
