package com.es3.order.store.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

public class StoreUpdateRequest {

    @Getter
    public static class Address {
        @NotBlank(message = "Address cannot be empty")
        private String address;
    }

    @Getter
    public static class ContactNumber {
        @NotBlank(message = "Contact Number cannot be empty")
        private String contactNumber;
    }

    @Getter
    public static class LogoImg {
        @NotBlank(message = "Logo Image cannot be empty")
        private String logoImg;
    }

    @Getter
    public static class Description {
        @NotBlank(message = "Description cannot be empty")
        private String description;
    }
}
