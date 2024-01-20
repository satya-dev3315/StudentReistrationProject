package com.ait.binding;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SearchCriteria {  //this class is used to filter data

	private String courseName;
	private String enquiryStatus;
	private String mode;
}
