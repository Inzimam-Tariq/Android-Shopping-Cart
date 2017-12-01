package com.qemasoft.alhabibshop.app.model;

/**
 * Created by Inzimam on 29-Nov-17.
 */

public class Address {
    private String id;
    private String firstName;
    private String lastName;
    private String company;
    private String address;
    private String city;
    private String postalCode;
    private String country;
    private String state;
    private boolean defaultAddress;



    public Address(String id, String firstName, String lastName, String company,
                   String address, String city, String postalCode, String country,
                   String state, boolean defaultAddress) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.company = company;
        this.address = address;
        this.city = city;
        this.postalCode = postalCode;
        this.country = country;
        this.state = state;
        this.defaultAddress = defaultAddress;
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCompany() {
        return company;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getCountry() {
        return country;
    }

    public String getState() {
        return state;
    }

    public boolean isDefaultAddress() {
        return defaultAddress;
    }
}
