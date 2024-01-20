package com.ait.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.ait.binding.SearchCriteria;
import com.ait.entity.StudentEnquiry;
import com.ait.service.EnquiryService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class EnquiryController {

	private static final String Integer = null;
	@Autowired
	private EnquiryService enquiryService;

	@GetMapping("/enquiry")
	public String enqPage(Model model) {
		model.addAttribute("enquiry", new StudentEnquiry());
		return "addEnquiryView";

	}

	@PostMapping("/enquiry")
	public String addEnquiry(@ModelAttribute("enquiry") StudentEnquiry enquiry, Model model, HttpServletRequest request) {

		// get which counsellor is addin the enquiry, so get existing session by usin
		// false value.(Note : True will get new session)
		HttpSession session = request.getSession(false);
		Integer cid = (Integer) session.getAttribute("CID"); // typecast session frm obj to int
		
		//if in session id is not available then redirect to login page, dont allow to use page functionality
		if(cid == null){
			return "redirect:/logout";
		}

		enquiry.setCounsellorId(cid); // ehich counsellor add this enq , we r settin it to obj

		boolean addEnq = enquiryService.addEnq(enquiry);
		if (addEnq) {
			model.addAttribute("successMsg", "Enquiry added ...");
		} else {
			model.addAttribute("errorMsg", "Enquiry failed to add ...");

		}
		return "addEnquiryView";

	}

	@GetMapping("/enquiries")
	public String viewEnquiries(Model model,HttpServletRequest request) {
		
		HttpSession session = request.getSession(false);      
		Integer cid = (Integer) session.getAttribute("CID"); 
		
		//send search criteria empty form obj when this page is loading .we r doin this for filtering based on search criteria.we r sending sc obj to do form binding
		if(cid == null){
			return "redirect:/logout";
		}
		
		model.addAttribute("sc", new SearchCriteria());
		
		List<StudentEnquiry> enquiriesList = enquiryService.getEnquiries(cid, new SearchCriteria());
		model.addAttribute("enquiries", enquiriesList);
		return "displayEnquiryView";

	}

	@PostMapping("/filterEnquiries")
	public String filterEnquiries(@ModelAttribute("sc") SearchCriteria sc, Model model,HttpServletRequest request) {
		
		HttpSession session = request.getSession(false);      
		Integer cid = (Integer) session.getAttribute("CID");
		
		if(cid == null){
			return "redirect:/logout";
		}
		
		List<StudentEnquiry> enquiriesList = enquiryService.getEnquiries(cid, sc);
		model.addAttribute("enquiries", enquiriesList);
		return "filterEnquiryView";
	}

}
