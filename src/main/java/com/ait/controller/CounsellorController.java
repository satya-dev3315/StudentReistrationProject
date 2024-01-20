package com.ait.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ait.binding.DashboardResponse;
import com.ait.entity.Counsellor;
import com.ait.service.CounsellorService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class CounsellorController {

	@Autowired
	private CounsellorService counsellorService;

	// login methods

	@GetMapping("/")
	public String logInPage(Model model) {
		model.addAttribute("counsellor", new Counsellor());
		return "loginView";
	}
	
	//logout so session should b removed
	
	@GetMapping("/logout")
	public String logout(Model model,HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		session.invalidate();
		return "redirect:/";
		}
	

	@PostMapping("/login")
	public String handleLogin(Counsellor c, Model model,HttpServletRequest request) {
		Counsellor counsellor = counsellorService.loginCheck(c.getEmail(), c.getPassword());
		if (counsellor == null) { // user will login with der accounts,to check if record is available or nt with
									// this email n pwd
			model.addAttribute("errorMsg", "Invalid Credentials!");
			return "loginView";
		}
			//here login is success so maintain a session for dashboard functionality, check down for more info
			
			HttpSession session = request.getSession(true);   //here true means give new session, false means gives existing session.
			session.setAttribute("CID", counsellor.getCounsellorId());
		
		 return "redirect:dashboard";

	}

	// signUp methods

	@GetMapping("/register")
	public String registrationPage(Model model) {
		model.addAttribute("counsellor", new Counsellor());
		return "registerView";
	}

	@PostMapping("/register") // we use this in form
	public String handleRegistration(Counsellor c, Model model) {
		String msg = counsellorService.saveCounsellor(c);
		model.addAttribute("msg", msg);
		return "registerView";

	}

	// forgot password/recover pwd

	@GetMapping("/forgotPassword")
	public String recoverPasswordPage(Model model) { // here addAttribute binding is nt required because here only one
														// text-field is available
		return "forgotPasswordView";

	}

	@GetMapping("/recoverPassword")
	public String recoverPwd(@RequestParam("email") String email, Model model) {
		boolean status = counsellorService.recoverPwd(email);
		if (status) {
			model.addAttribute("successMsg", "Password sent successfully to your email...");
		} else {
			model.addAttribute("errorMsg", "Invalid email. Try again...");
		}
		return "forgotPasswordView";

	}
	/*IMP: Session:check login method, there we have used session
	 * Once counsellor is logged in , display data based on the particular logged in
	 * consoller.ie:in that session ,store counsellorId So until a counsellor logged out, 
	 * maintain a SESSION. For this,
	 * maintain counsellor ID in session, use session so we can see the enquires
	 * this particular counseller had stored. Every counsellor has its own session.
	 */
	
	//dashboard for councellor :  dashboard page for a particular logged in councellor.Check login functionality for dashboard creation above
	
	@GetMapping("/dashboard")
	public String buildDashboard(Model model,HttpServletRequest request) {
		/*
		 * now our counsellor is logged in in log in page above, now we need to use its
		 * existing session (by takin session as false), so that particular counsellors
		 * data only will b displayed in dashboard,data means lost enquires,total enq
		 * etc.
		 */
		
		HttpSession session = request.getSession(false);
		Object object = session.getAttribute("CID");     //cid is logged in counsellor id ,check handleLogin() method above
		Integer cid = (Integer)object;   //typecast counsellor id to integer frm obj
		
		DashboardResponse dashboardInfo = counsellorService.getDashboardInfo(cid);
		model.addAttribute("dashboard", dashboardInfo);
		return "dashboardView";
		
	}

	

}
