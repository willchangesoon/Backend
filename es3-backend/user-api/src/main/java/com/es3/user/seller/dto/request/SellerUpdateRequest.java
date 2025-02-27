package com.es3.user.seller.dto.request;

public record SellerUpdateRequest() {
	public record Email(String email) { }
	public record Mobile(String mobile) { }
	public record Name(String name) { }

}
