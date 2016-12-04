package com.df.landbay.investment_matcher.model;

/**
 * Enum defining all the allowed Product Types - can be added to if more Product Types
 * are added in the future
 * @author david
 *
 */
public enum ProductType {
	
	FIXED("Fixed"),
	TRACKER("Tracker");
	
	//A more human readable representation of the product type for output purposes
	private final String description;
	
	private ProductType (String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}

}
