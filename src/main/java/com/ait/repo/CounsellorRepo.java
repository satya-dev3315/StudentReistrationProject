package com.ait.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ait.entity.Counsellor;

@Repository
public interface CounsellorRepo extends JpaRepository<Counsellor, Integer> {

	// for gettin 2 variables, repo dont have predefined methods, 
	//so use findBy methods in repo for login funtionality
	
	public Counsellor findByEmailAndPassword(String email, String password);
	
	//for register method :verify email if with this mail already record is created or nt
	//reuse this method for forget pwd	
	public Counsellor findByEmail(String email);
	
}
