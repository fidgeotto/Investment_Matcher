package com.df.landbay.investment_matcher.controller;

import java.util.Comparator;

import com.df.landbay.investment_matcher.model.Investment;

/**
 * Comparator that will define the order Investments are sorted in. This comparator
 * ensures that <ul><li>investments are sorted by term first, shortest to longest</li>
 * <li>then by amount, smallest to largest</li><li>then alphabetical by product type</li>
 * <li>then alphabetical by investor name</li></ul>
 * @author David Fidgett
 *
 */
public class InvestmentComparator implements Comparator<Investment> {

	public int compare(Investment investment1, Investment investment2) {
		
		int returnValue = 0;
		
		//sort by lowest term first (negative is sorted first)
		returnValue = investment1.getTerm()-investment2.getTerm();
		//then least amount first if terms are the same
		if (returnValue == 0) {
			returnValue = investment1.getAmount() - investment2.getAmount();
		}
		//then arbitrary choice of product type (Fixed then Tracker)
		if (returnValue == 0) {
			returnValue = investment1.getProductType().getDescription().compareTo(investment2.getProductType().getDescription());
		}
		//then alphabetical by investorName
		if (returnValue == 0) {
			returnValue = investment1.getInvestorName().compareTo(investment2.getInvestorName());
		}
		
		
		return returnValue;
	}

}
