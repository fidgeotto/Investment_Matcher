package com.df.landbay.investment_matcher.view;

import java.util.List;

import com.df.landbay.investment_matcher.model.Loan;

/**
 * Interface of a view object
 * 
 * @author David Fidgett
 *
 */
public interface View {

	/**
	 * Method that will display the list of loans to the user
	 * @param outLoans list of loans to be displayed
	 */
	void displayFundedLoans(List<Loan> outLoans);

}