package com.chandan.CompanyAssignment.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chandan.CompanyAssignment.Model.Booking;
import com.chandan.CompanyAssignment.Model.MovieUser;
@Repository
public interface BookingRepo extends JpaRepository<Booking, Integer>{

	List<Booking> findByUser(MovieUser user);


}
