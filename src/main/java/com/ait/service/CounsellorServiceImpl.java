package com.ait.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ait.binding.DashboardResponse;
import com.ait.entity.Counsellor;
import com.ait.entity.StudentEnquiry;
import com.ait.repo.CounsellorRepo;
import com.ait.repo.StudentEnquiryRepo;
import com.ait.utils.EmailUtils;

@Service
public class CounsellorServiceImpl implements CounsellorService {

	@Autowired
	private CounsellorRepo counsellorRepo;

	@Autowired
	private EmailUtils emailUtils;

	@Autowired
	private StudentEnquiryRepo studentEnquiryRepo;

	@Override
	public String saveCounsellor(Counsellor counsellor) {
		// verify email if with this mail already record is created
		Counsellor findByEmail = counsellorRepo.findByEmail(counsellor.getEmail());
		if (findByEmail != null) {
			return "Email already present...";
		}
		Counsellor saveCounsellor = counsellorRepo.save(counsellor);
		if (saveCounsellor.getCounsellorId() != null) {
			return "Counsellor Registration successful...";
		}
		return "Counsellor Registration failed...";
	}

	@Override
	public Counsellor loginCheck(String email, String password) {
		return counsellorRepo.findByEmailAndPassword(email, password);

	}

	@Override
	public boolean recoverPwd(String email) {
		Counsellor counsellor = counsellorRepo.findByEmail(email);
		if (counsellor == null) {
			return false;
		}

		// if counsellor is nt false then send email

		String subject = "Please recover your password...";
		String body = "<h1> Your Password is : " + counsellor.getPassword() + "</h1>";

		return emailUtils.sendEmail(subject, body, email);  // email is to ,ie: // to whom i need to send the mail- we
															// will get it frm UI
		// it can b true or false
	}

	@Override
	public DashboardResponse getDashboardInfo(Integer counsellorId) {
		
		List<StudentEnquiry> listAllExquires = studentEnquiryRepo.findByCounsellorId(counsellorId);
		
		//find all enquiry data which is enrolled n collect in a list
		int enrolledEnquires = listAllExquires.stream().filter(e -> e.getStatus().equals("Enrolled")).collect(Collectors.toList()).size();
		
		DashboardResponse dashboardResponse = new DashboardResponse();
		
		dashboardResponse.setTotalEnquires(listAllExquires.size());
		dashboardResponse.setEnrolledEnquires(enrolledEnquires);
		dashboardResponse.setLostEnquires(listAllExquires.size() - enrolledEnquires);
		
		return dashboardResponse;
	}

}
