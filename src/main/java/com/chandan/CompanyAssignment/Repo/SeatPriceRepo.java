package com.chandan.CompanyAssignment.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.chandan.CompanyAssignment.Model.SeatPrice;

@Repository
public interface SeatPriceRepo extends JpaRepository<SeatPrice, Integer> {
	    @Query("SELECT sp FROM SeatPrice sp WHERE sp.seat_class = :seatClass")
	    Object findBySeatClass(@Param("seatClass") String seatClass);
}
