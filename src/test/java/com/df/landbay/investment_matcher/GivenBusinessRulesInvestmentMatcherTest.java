package com.df.landbay.investment_matcher;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Rule;
import org.junit.rules.ExpectedException;

import com.df.landbay.investment_matcher.controller.GivenBusinessRulesInvestmentMatcher;
import com.df.landbay.investment_matcher.controller.InvestmentComparator;
import com.df.landbay.investment_matcher.controller.LoansComparator;
import com.df.landbay.investment_matcher.model.Investment;
import com.df.landbay.investment_matcher.model.Loan;
import com.df.landbay.investment_matcher.model.ProductType;

import static org.junit.Assert.*;

import org.junit.Test;


public class GivenBusinessRulesInvestmentMatcherTest {

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	//Should mock these really
	//this is where the business logic for processing loans in what order is
	private LoansComparator loansComparator = new LoansComparator();
	//this is where the business logic for assigning investments in what order is
	private InvestmentComparator investmentComparator = new InvestmentComparator();
	
	@Rule
    public ExpectedException thrown= ExpectedException.none();
    
    @Test
    public void testGetFundedLoansReturnsIllegalArgumentIfLoansIsNull() {
    	GivenBusinessRulesInvestmentMatcher matcher = new GivenBusinessRulesInvestmentMatcher(loansComparator, investmentComparator);
    	List<Investment> investments = new ArrayList<Investment>();
    	
    	thrown.expect(IllegalArgumentException.class);
    	matcher.getFundedLoans(null, investments);
    }
    
    @Test
    public void testGetFundedLoansReturnsIllegalArgumentIfInvestmentsIsNull() {
    	GivenBusinessRulesInvestmentMatcher matcher = new GivenBusinessRulesInvestmentMatcher(loansComparator, investmentComparator);
    	List<Loan> loans = new ArrayList<Loan>();
    	
    	thrown.expect(IllegalArgumentException.class);
    	matcher.getFundedLoans(loans, null);
    }
    
    @Test
    public void testGetFundedLoansReturnsSameLoansListIfBothLoansAndInvestmentsAreEmpty()
    {
        GivenBusinessRulesInvestmentMatcher matcher = new GivenBusinessRulesInvestmentMatcher(loansComparator, investmentComparator);
        List<Loan> loans = new ArrayList<Loan>();
        List<Investment> investments = new ArrayList<Investment>();
    	
    	List<Loan> output = matcher.getFundedLoans(loans, investments);
    	
    	assertEquals(loans, output);
    }
    
    @Test
    public void testGetFundedLoansReturnsEmptyLoansListIfThereAreLoansButNoInvestments(){
    	GivenBusinessRulesInvestmentMatcher matcher = new GivenBusinessRulesInvestmentMatcher(loansComparator, investmentComparator);
    	List<Loan> loans = new ArrayList<Loan>();
        List<Investment> investments = new ArrayList<Investment>();
        Loan loan = new Loan(1, 100000, ProductType.FIXED, 12, new Date());
        loans.add(loan);
        
        List<Loan> output = matcher.getFundedLoans(loans, investments);
    	
    	assertEquals(0, output.size()); 	
    }
    
    @Test
    public void testGetFundedLoansFundsLoanWithMatchingInvestment() throws ParseException {
    	GivenBusinessRulesInvestmentMatcher matcher = new GivenBusinessRulesInvestmentMatcher(loansComparator, investmentComparator);
    	List<Loan> loans = new ArrayList<Loan>();
        List<Investment> investments = new ArrayList<Investment>();
        
        Date date = sdf.parse("2016-01-01");
        
        Loan loan = new Loan(1, 100000, ProductType.FIXED, 12, date);
        loans.add(loan);
        
        Investment investment = new Investment("Alice", 100000, ProductType.FIXED, 12);
        investments.add(investment);
        
        List<Loan> output = matcher.getFundedLoans(loans, investments);
        Iterator<Loan> it = output.iterator();
        while (it.hasNext()) {
        	Loan outputLoan = it.next();
        	assertTrue(outputLoan.isFunded());
        }
    }
    
    @Test
    public void testGetFundedLoansRemovesFullyUsedInvestmentFromAvailableInvstments() throws ParseException {
    	GivenBusinessRulesInvestmentMatcher matcher = new GivenBusinessRulesInvestmentMatcher(loansComparator, investmentComparator);
    	List<Loan> loans = new ArrayList<Loan>();
        List<Investment> investments = new ArrayList<Investment>();
        
        Date date1 = sdf.parse("2016-01-01");
        
        Loan loan1 = new Loan(1, 100000, ProductType.FIXED, 12, date1);
        
        loans.add(loan1);
        
        Investment investment = new Investment("Alice", 100000, ProductType.FIXED, 12);
        investments.add(investment);
        
        matcher.getFundedLoans(loans, investments);
        
        assertEquals(0, investments.size());
    }
    
    @Test
    public void testGetFundedLoansDeductsPartOfInvestmentForMatchedLoan() throws ParseException {
    	GivenBusinessRulesInvestmentMatcher matcher = new GivenBusinessRulesInvestmentMatcher(loansComparator, investmentComparator);
    	List<Loan> loans = new ArrayList<Loan>();
        List<Investment> investments = new ArrayList<Investment>();
        
        Date date1 = sdf.parse("2016-01-01");
        
        Loan loan1 = new Loan(1, 100000, ProductType.FIXED, 12, date1);
        
        loans.add(loan1);
        
        Investment investment = new Investment("Alice", 100010, ProductType.FIXED, 12);
        investments.add(investment);
        
        matcher.getFundedLoans(loans, investments);
        
        for (Investment outInvestment : investments) {
        	if (outInvestment.getInvestorName().equals("Alice")) {
        		assertEquals(10, outInvestment.getAmount());
        	}
        }
        
    }
    
    @Test
    public void testGetFundedLoansDoesNotMatchDifferentProductTypeLoanAndInvestment() throws ParseException {
    	GivenBusinessRulesInvestmentMatcher matcher = new GivenBusinessRulesInvestmentMatcher(loansComparator, investmentComparator);
    	List<Loan> loans = new ArrayList<Loan>();
        List<Investment> investments = new ArrayList<Investment>();
        
        Date date1 = sdf.parse("2016-01-01");
        
        Loan loan1 = new Loan(1, 100000, ProductType.FIXED, 12, date1);
        
        loans.add(loan1);
        
        Investment investment = new Investment("Alice", 100010, ProductType.TRACKER, 12);
        investments.add(investment);
        
        List<Loan> output = matcher.getFundedLoans(loans, investments);
    	
    	assertEquals(0, output.size()); 
        
    }
    
    @Test
    public void testGetFundedLoansDoesNotMatchInvestmentTermLessThanLoanAndInvestment() throws ParseException {
    	GivenBusinessRulesInvestmentMatcher matcher = new GivenBusinessRulesInvestmentMatcher(loansComparator, investmentComparator);
    	List<Loan> loans = new ArrayList<Loan>();
        List<Investment> investments = new ArrayList<Investment>();
        
        Date date1 = sdf.parse("2016-01-01");
        
        Loan loan1 = new Loan(1, 100000, ProductType.FIXED, 12, date1);
        
        loans.add(loan1);
        
        Investment investment = new Investment("Alice", 100010, ProductType.FIXED, 10);
        investments.add(investment);
        
        List<Loan> output = matcher.getFundedLoans(loans, investments);
    	
    	assertEquals(0, output.size()); 
        
    }
    
    @Test
    public void testGetFundedLoansFundsLoanWithMultipleInvestments() throws ParseException {
    	GivenBusinessRulesInvestmentMatcher matcher = new GivenBusinessRulesInvestmentMatcher(loansComparator, investmentComparator);
    	List<Loan> loans = new ArrayList<Loan>();
        List<Investment> investments = new ArrayList<Investment>();
        
        Date date = sdf.parse("2016-01-01");
        
        Loan loan = new Loan(1, 200000, ProductType.FIXED, 12, date);
        loans.add(loan);
        
        Investment investment1 = new Investment("Alice", 100000, ProductType.FIXED, 12);
        Investment investment2 = new Investment("Bob", 100000, ProductType.FIXED, 12);
        
        investments.add(investment1);
        investments.add(investment2);
        
        List<Loan> output = matcher.getFundedLoans(loans, investments);
        Iterator<Loan> it = output.iterator();
        while (it.hasNext()) {
        	Loan outputLoan = it.next();
        	assertTrue(outputLoan.isFunded());
        }
    }
    
    @Test
    public void testGetFundedLoansSetsMultipleInvestorNames() throws ParseException {
    	GivenBusinessRulesInvestmentMatcher matcher = new GivenBusinessRulesInvestmentMatcher(loansComparator, investmentComparator);
    	List<Loan> loans = new ArrayList<Loan>();
        List<Investment> investments = new ArrayList<Investment>();
        
        Date date = sdf.parse("2016-01-01");
        
        Loan loan = new Loan(1, 200000, ProductType.FIXED, 12, date);
        loans.add(loan);
        
        Investment investment1 = new Investment("Alice", 100000, ProductType.FIXED, 12);
        Investment investment2 = new Investment("Bob", 100000, ProductType.FIXED, 12);
        
        investments.add(investment1);
        investments.add(investment2);
        
        List<Loan> output = matcher.getFundedLoans(loans, investments);
        Iterator<Loan> it = output.iterator();
        while (it.hasNext()) {
        	Loan outputLoan = it.next();
        	assertTrue(outputLoan.getFunders().containsKey("Alice"));
        	assertTrue(outputLoan.getFunders().containsKey("Bob"));
        }
    }
    
    @Test
    public void testGetFundedLoansSetsCorrectAmounts() throws ParseException {
    	GivenBusinessRulesInvestmentMatcher matcher = new GivenBusinessRulesInvestmentMatcher(loansComparator, investmentComparator);
    	List<Loan> loans = new ArrayList<Loan>();
        List<Investment> investments = new ArrayList<Investment>();
        
        Date date = sdf.parse("2016-01-01");
        
        Loan loan = new Loan(1, 200000, ProductType.FIXED, 12, date);
        loans.add(loan);
        
        Investment investment1 = new Investment("Alice", 200000, ProductType.FIXED, 12);
        Investment investment2 = new Investment("Bob", 50000, ProductType.FIXED, 12);
        
        investments.add(investment1);
        investments.add(investment2);
        
        List<Loan> output = matcher.getFundedLoans(loans, investments);
        Iterator<Loan> it = output.iterator();
        while (it.hasNext()) {
        	Loan outputLoan = it.next();
        	assertEquals(new Integer (150000), outputLoan.getFunders().get("Alice"));
        	assertEquals(new Integer(50000), outputLoan.getFunders().get("Bob"));
        }
    }
}


