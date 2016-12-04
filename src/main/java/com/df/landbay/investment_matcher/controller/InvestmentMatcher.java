package com.df.landbay.investment_matcher.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.df.landbay.investment_matcher.model.Investment;
import com.df.landbay.investment_matcher.model.Loan;

/**
 * Abstract class with the implementation of the method that contains the core matching logic.
 * Business logic is contained in concretion of this method for the getValidInvestments and
 * the comparators passed in to the constructor.
 * 
 * @author David Fidgett
 *
 */
public abstract class InvestmentMatcher {

	protected Comparator<Loan> loansComparator;
	protected Comparator<Investment> investmentComparator;
	
	public InvestmentMatcher (Comparator<Loan> loansComparator, Comparator<Investment> investmentComparator) {
		this.loansComparator = loansComparator;
		this.investmentComparator = investmentComparator;
	}
	
	/**
	 * Method that will return a list of loans that can be funded with investments  
	 * according to the business rules in the Comparators passed in the constructor and in
	 * this classes getValidInvestments
	 * 
	 * @param loans A Set of loans (we shouldn't be funding duplicate loans) to fund
	 * @param investments A Set of investments (investments should be unique) we use to fund loans
	 * @return Set The same set of loans that have been updated if they can be funded
	 * @throws NullPointerException
	 */
	public List<Loan> getFundedLoans(List<Loan> loans, List<Investment> investments) throws IllegalArgumentException {
		
		List<Loan> fundedLoans = new ArrayList<Loan>();
		
		if (loans == null) {
			throw new IllegalArgumentException();
		}
		
		if (investments == null) {
			throw new IllegalArgumentException();
		}
		
		//attempt to fund loans in the order based on the business rules in this comparator
		loans.sort(loansComparator);
		
		for (Loan loan : loans) {
			
			
			List<Investment> validInvestments = getValidInvestments(loan, investments);
			
			//calculate total amount of investments that are valid
			int totalPot = 0;
			
			for (Investment investment : validInvestments) {
				totalPot += investment.getAmount();
			}
			
			//If there is enough money available in all the valid investments, fund with
			//investments
			if (totalPot >= loan.getAmount()) {
				
				//sort investments in order based on the business rules in our comparator
				validInvestments.sort(investmentComparator);
				int amountLeftToFund = loan.getAmount();
				int index = 0;
				
				//fund in order
				while (amountLeftToFund > 0) {
					Investment investment = validInvestments.get(index);
					if (amountLeftToFund >= investment.getAmount()) {
						amountLeftToFund -= investment.getAmount();
						loan.getFunders().put(investment.getInvestorName(), investment.getAmount());
						investments.remove(investment);
					} else {
						loan.getFunders().put(investment.getInvestorName(), amountLeftToFund);
						investment.setAmount(investment.getAmount() - amountLeftToFund);
						amountLeftToFund = 0;
					}
					index ++;
				}
				loan.setFunded(true);
				fundedLoans.add(loan);
			}
		}
		
		return fundedLoans;
	}
	
	/**
	 * Abstract method that must be implemented to return a sub-list of investments that are valid
	 * to fund the given Loan.
	 * 
	 * @param loan Loan to base investment validity on
	 * @param investments List of Investments to be assessed
	 * @return List List of Investments that are valid to fund this input loan
	 */
	protected abstract List<Investment> getValidInvestments(Loan loan, List<Investment> investments);
	
}
