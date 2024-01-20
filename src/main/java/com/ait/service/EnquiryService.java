package com.ait.service;

import java.util.List;

import com.ait.binding.SearchCriteria;
import com.ait.entity.StudentEnquiry;

public interface EnquiryService {
	
	public boolean addEnq(StudentEnquiry se);

	public List<StudentEnquiry> getEnquiries(Integer counsellorId, SearchCriteria s);

}
