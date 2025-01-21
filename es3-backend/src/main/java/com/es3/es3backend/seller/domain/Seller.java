package com.es3.es3backend.seller.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Seller {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "name", nullable = false)
	private String name;
	@Column(name = "email", nullable = false)
	private String email;
	@Column(name = "mobile", nullable = false)
	private String mobile;
	@Column(name = "address", nullable = false)
	private String address;
	@Column(name = "brn", nullable = false)
	private String brn;

	@Column(name = "bank") // nullable = false
	private String bank;
	@Column(name = "account_number") // nullable = false
	private String accountNumber;
	@Column(name = "account_holder") // nullable = false
	private String accountHolder;

	@Column(name = "seller_status")
	private boolean sellerStatus = false;
}
