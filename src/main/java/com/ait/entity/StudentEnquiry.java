package com.ait.entity;

import java.time.LocalDate;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "STUDENT_ENQUIRY_INFO")
@Data
public class StudentEnquiry {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "enquiry_id")
	private Integer enquiryId;

	private String name;

	@Column(name = "phone_no")
	private String phoneNo;

	private String mode;

	private String course;

	private String status;

	@CreationTimestamp
	@Column(name = "created_date")
	private LocalDate createdDate;

	@Column(name = "counsellor_id")
	private Integer counsellorId;

}
