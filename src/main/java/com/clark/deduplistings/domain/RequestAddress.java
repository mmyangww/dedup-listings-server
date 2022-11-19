package com.clark.deduplistings.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestAddress {
    private String street_address;
    private String city;
    private String region;
    private String postal_code = "";
    private String iso_country_code = "US";

    public RequestAddress(String address, String city, String state) {
    }
}
