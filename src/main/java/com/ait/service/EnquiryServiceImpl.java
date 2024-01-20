package com.ait.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.ait.binding.SearchCriteria;
import com.ait.entity.StudentEnquiry;
import com.ait.repo.StudentEnquiryRepo;

@Service
public class EnquiryServiceImpl implements EnquiryService {

	@Autowired
	private StudentEnquiryRepo enquiryRepo;

	@Override
	public boolean addEnq(StudentEnquiry se) {
		StudentEnquiry savedEnquiry = enquiryRepo.save(se);
		if(savedEnquiry.getEnquiryId() != null) {
			return true;
		}
		return false;
	}

	@Override
	public List<StudentEnquiry> getEnquiries(Integer counsellorId, SearchCriteria sc) {
		StudentEnquiry enquiry = new StudentEnquiry();
		
		//bsed on cid, get data , so set it 
		enquiry.setCounsellorId(counsellorId);
		
		//filtering , if mode selected add it to query
		if(sc.getMode() != null && !sc.getMode().equals("")) {
			enquiry.setMode(sc.getMode());
		}
		
		if(sc.getCourseName() != null && !sc.getCourseName().equals("")) {
			enquiry.setCourse(sc.getCourseName());
		}
		
		if(sc.getEnquiryStatus() != null && !sc.getEnquiryStatus().equals("")) {
			enquiry.setStatus(sc.getEnquiryStatus());
		}
		
		//queryByExample: use to filter n prepare the query dynamatically
		Example<StudentEnquiry> of = Example.of(enquiry);
		List<StudentEnquiry> enquires = enquiryRepo.findAll(of);
		
		return enquires;
	}

}
