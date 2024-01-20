package com.ait.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ait.entity.StudentEnquiry;

@Repository
public interface StudentEnquiryRepo extends JpaRepository<StudentEnquiry, Integer> {

	//method for dashboard-for getting counsellors id into session
	
	//here one counsellor can have many enquires and one enquiry is one object.
	
	public List<StudentEnquiry> findByCounsellorId(Integer counsellorId);
}
