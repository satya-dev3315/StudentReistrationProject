package com.ait.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "COUNSELLORS_INFO")
@Data
public class Counsellor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "counsellor_id")
	private Integer counsellorId;

	private String name;

	private String email;

	private String password;

	@Column(name = "phone_no")
	private String phoneNo;
}
