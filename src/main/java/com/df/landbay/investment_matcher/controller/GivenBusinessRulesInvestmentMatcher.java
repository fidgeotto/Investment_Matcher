package com.df.landbay.investment_matcher.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.df.landbay.investment_matcher.model.Investment;
import com.df.landbay.investment_matcher.model.Loan;
import com.df.landbay.investment_matcher.model.ProductType;

/**
 * Class implementing the InvestmentMatcher interface which will use the given business
 * rules in this challenge to match loans with Investments.
 * 
 * @author David Fidgett
 *
 */
public class GivenBusinessRulesInvestmentMatcher extends InvestmentMatcher {
	
	public GivenBusinessRulesInvestmentMatcher (Comparator<Loan> loansComparator, Comparator<Investment> investmentComparator) {
		super(loansComparator, investmentComparator);
	}
	
	/**
	 * Method returns a list of investments that are allowed based on the given rules
	 * i.e. investments must be the same product type and at least as long as the loan term
	 * 
	 * @param loan
	 * @param investments
	 * @return
	 */
	protected List<Investment> getValidInvestments(Loan loan, List<Investment> investments) {
		//could use a map/object here for a list and total amount to save 2 iterations if performance
		//is a major concern.
		List<Investment> validInvestments = new ArrayList<Investment>();
		
		int loanTerm = loan.getTerm();
		ProductType loanProductType = loan.getProductType();
		
		for (Investment investment : investments) {
			//Only investments that have a longer term and are the same product type are valid
			if (loanTerm <= investment.getTerm() && loanProductType.equals(investment.getProductType())) {
				validInvestments.add(investment);
			}
		}
		
		return validInvestments;
	}
	
}
