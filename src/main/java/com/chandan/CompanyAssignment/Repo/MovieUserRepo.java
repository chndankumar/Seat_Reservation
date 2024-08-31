package com.chandan.CompanyAssignment.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chandan.CompanyAssignment.Model.MovieUser;
@Repository
public interface MovieUserRepo extends JpaRepository<MovieUser, Integer>{

	MovieUser findByPhone(long phone);

	MovieUser findByEmail(String userIdentifier);
}
