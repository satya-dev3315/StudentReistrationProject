package com.ait.service;

import com.ait.binding.DashboardResponse;
import com.ait.entity.Counsellor;

public interface CounsellorService {

	//signup
	public String saveCounsellor(Counsellor counsellor);
	
	
	//login
	public Counsellor loginCheck(String email, String password);  // for gettin 2 variables, repo dont have predefined methods, so use findBy methods in repo for login funtionality

    // forget password by sending mail-recover pwd
	
	public boolean recoverPwd(String email);
	
	//counsellor dashboard
	
	public DashboardResponse getDashboardInfo(Integer counsellorId);
}
