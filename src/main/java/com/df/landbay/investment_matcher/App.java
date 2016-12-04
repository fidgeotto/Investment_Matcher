package com.df.landbay.investment_matcher;

import java.util.Comparator;
import java.util.List;

import com.df.landbay.investment_matcher.controller.GivenBusinessRulesInvestmentMatcher;
import com.df.landbay.investment_matcher.controller.InvestmentComparator;
import com.df.landbay.investment_matcher.controller.InvestmentMatcher;
import com.df.landbay.investment_matcher.controller.LoansComparator;
import com.df.landbay.investment_matcher.model.Investment;
import com.df.landbay.investment_matcher.model.LandbayCsvFileReader;
import com.df.landbay.investment_matcher.model.Loan;
import com.df.landbay.investment_matcher.view.JsonView;
import com.df.landbay.investment_matcher.view.View;

/**
 * Class containing main for this program which will match loans with investments.
 * Can be supplied with the filepath to another loans file and investment file respectively.
 * to investors
 */
public class App 
{
    public static void main( String[] args )
    {
        LandbayCsvFileReader fileReader = new LandbayCsvFileReader();
    	
        //default file paths
        String loansFilePath = "C:\\Users\\david\\Documents\\Projects\\Landbay Challenge\\loans.csv";
        String investmentsFilePath = "C:\\Users\\david\\Documents\\Projects\\Landbay Challenge\\investmentRequests.csv";
        
        if ( args.length == 2) {
        	loansFilePath = args[0];
        	investmentsFilePath = args[1];
        } 
        
    	//read in csv files and create list of loans - have to manually change at the moment
		List<Loan> loans = fileReader.readLoansFile(loansFilePath);
		
		//read in csv files and create list of investments - have to manually change at the moment
		List<Investment> investments = fileReader.readInvestmentsFile(investmentsFilePath);
		
		//can change business logic by implementing different comparators and investmentMatchers
		//ideally this would all be done with Spring or something
		Comparator<Loan> loansComparator = new LoansComparator();
		Comparator<Investment> investmentComparator = new InvestmentComparator();
		InvestmentMatcher matcher = new GivenBusinessRulesInvestmentMatcher(loansComparator, investmentComparator);
		
		//match loans to investments
		List<Loan> outLoans = matcher.getFundedLoans(loans, investments);	
  
		View view = new JsonView();
		
		view.displayFundedLoans(outLoans);
		
    }
}
