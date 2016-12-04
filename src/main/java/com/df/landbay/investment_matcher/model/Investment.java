package com.df.landbay.investment_matcher.model;

/**
 * Class modelling an investment an investor wishes to make
 * 
 * @author David Fidgett
 *
 */
public class Investment {

	private String investorName;
	private int amount;
	private ProductType productType;
	private int term;
	
	/**
	 * Constructor for an investment
	 * 
	 * @param investorName
	 * @param amount
	 * @param productType
	 * @param term
	 */
	public Investment(String investorName, int amount, ProductType productType, int term){
		this.investorName = investorName;
		this.amount = amount;
		this.productType = productType;
		this.term = term;
	}
	
	public String getInvestorName() {
		return investorName;
	}
	public void setInvestorName(String investorName) {
		this.investorName = investorName;
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
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("Name: ");
		sb.append(this.investorName);
		sb.append("|");
		sb.append("Amount: ");
		sb.append(this.amount);
		sb.append("|");
		sb.append("Product Type: ");
		sb.append(this.productType.getDescription());
		sb.append("|");
		sb.append("Term: ");
		sb.append(this.term);
		
		return sb.toString();
	}
	
}
