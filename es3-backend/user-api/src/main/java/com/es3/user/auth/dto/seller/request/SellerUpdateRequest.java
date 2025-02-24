package com.es3.user.auth.dto.seller.request;

public record SellerUpdateRequest() {
	public record Email(String email) { }
	public record Mobile(String mobile) { }
	public record Address(String address) { }
	public record Name(String name) { }

}
