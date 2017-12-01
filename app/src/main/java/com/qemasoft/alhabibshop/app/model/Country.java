package com.qemasoft.alhabibshop.app.model;

public class Country{
	private String isoCode2;
	private String addressFormat;
	private String isoCode3;
	private String name;
	private String postcodeRequired;
	private String countryId;
	private String status;

	public Country(String name, String countryId) {
		this.name = name;
		this.countryId = countryId;
	}

	public String getIsoCode2(){
		return isoCode2;
	}

	public String getAddressFormat(){
		return addressFormat;
	}

	public String getIsoCode3(){
		return isoCode3;
	}

	public String getName(){
		return name;
	}

	public String getPostcodeRequired(){
		return postcodeRequired;
	}

	public String getCountryId(){
		return countryId;
	}

	public String getStatus(){
		return status;
	}
}
