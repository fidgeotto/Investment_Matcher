package com.df.landbay.investment_matcher.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Class modelling a loan that we need to try to fund.
 * 
 * @author David Fidgett
 *
 */
public class Loan {
	
	private int id;
	private int amount;
	private ProductType productType;
	private int term;
	private Date completedDate;
	private boolean isFunded;
	private Map<String, Integer> funders;
	
	/**
	 * Constructor for making a new, unfunded loan
	 * 
	 * @param id Unique loan identifier
	 * @param amount The amount the loan is for
	 * @param productType The type of product this loan is for
	 * @param term How long the loan is to be for
	 * @param completedDate Date the loan request was made (I think)
	 */
	public Loan(int id, int amount, ProductType productType, int term, Date completedDate) {
		this.id = id;
		this.amount = amount;
		this.productType = productType;
		this.term = term;
		this.completedDate = completedDate;
		
		//default values when creating new loan
		this.isFunded = false;
		this.funders = new HashMap<String, Integer>();
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public boolean isFunded() {
		return isFunded;
	}
	
	public void setFunded(boolean isFunded) {
		this.isFunded = isFunded;
	}
	
	public Map<String, Integer> getFunders() {
		return funders;
	}
	
	public void setFunders(Map<String, Integer> funders) {
		this.funders = funders;
	}
	
	public int getAmount() {
		return amount;
	}
	
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	public ProductType getProductType() {
		return productType;
	}
	
	public void setProductType(ProductType productType) {
		this.productType = productType;
	}
	
	public int getTerm() {
		return term;
	}
	
	public void setTerm(int term) {
		this.term = term;
	}
	
	public Date getCompletedDate() {
		return completedDate;
	}
	
	public void setCompletedDate(Date completedDate) {
		this.completedDate = completedDate;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("Id: ");
		sb.append(this.id);
		sb.append("|");
		sb.append("Amount: ");
		sb.append(this.amount);
		sb.append("|");
		sb.append("Product Type: ");
		sb.append(this.productType.getDescription());
		sb.append("|");
		sb.append("Term: ");
		sb.append(this.term);
		sb.append("|");
		sb.append("Completed Date");
		sb.append(this.completedDate.toString());
		sb.append("|");
		sb.append("Is Funded: ");
		sb.append(this.isFunded);
		sb.append("|");
		sb.append("Funders: ");
		sb.append(this.funders.toString());
		sb.append("|");
		
		return sb.toString();
	}
	
}
