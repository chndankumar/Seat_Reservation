package com.chandan.CompanyAssignment.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.chandan.CompanyAssignment.Model.Seat;
@Repository
public interface SeatRepo extends JpaRepository<Seat, Integer>{
	
	@Query("SELECT (COUNT(s) * 100.0 / (SELECT COUNT(s2) FROM Seat s2 WHERE s2.seat_class = :className)) " +
		       "FROM Seat s WHERE s.seat_class = :className AND s.status = 'BOOKED'")
     double findBookedSeatPercentage(@Param("className") String className);

}
