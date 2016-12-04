package com.df.landbay.investment_matcher.controller;

import java.util.Comparator;

import com.df.landbay.investment_matcher.model.Loan;

/**
 * Comparator that will define the order Loans are sorted in. This comparator
 * ensures that <ul><li>loans are sorted by completed date, oldest to youngest</li>
 * <li>then by amount, greatest to smallest</li><li>then by term, shortest to longest</li>
 * <li>the alphabetically by product type</li><li>lastly by ID in numerical order</li></ul>
 * @author David Fidgett
 *
 */
public class LoansComparator implements Comparator<Loan> {

	public int compare(Loan loan1, Loan loan2) {
		
		//older dates are sorted so they are returned first
		long millisDiff = loan1.getCompletedDate().getTime() - loan2.getCompletedDate().getTime(); 
		int out=0;
		if (millisDiff > 0) {
			out = 1;
		} else if (millisDiff < 0) {
			out = -1;
		}
		
		//if no difference in completed dates, 
		//greater loan amounts are sorted so they are returned first
		if (out == 0) {
			out = loan2.getAmount() - loan1.getAmount();
		}
		
		//if no difference in completed dates and amount
		//shorter term loans are sorted so they are returned first
		if (out == 0) {
			out = loan1.getTerm() - loan2.getTerm();
		}
		
		//if no difference in completed dates, amount and terms
		//loans are sorted in product type alphabetical order
		if (out == 0) {
			out = loan1.getProductType().getDescription().compareTo(loan2.getProductType().getDescription());
		}
		
		//if no difference in completed dates, amount, terms and productType
		//loans are sorted in id numerical order
		if (out == 0) {
			out = loan1.getId() - loan2.getId();
		}
		
		return out;
	}
	
}
