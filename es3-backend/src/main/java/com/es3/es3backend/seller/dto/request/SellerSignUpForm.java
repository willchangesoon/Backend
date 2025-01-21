package com.es3.es3backend.seller.dto.request;

public record SellerSignUpForm(
	String name,
	String email,
	String password,
	String mobile,
	String brn,
	String postCode,
	String address,
	String bank,
	String accountNumber,
	String accountHolder
) {

}
