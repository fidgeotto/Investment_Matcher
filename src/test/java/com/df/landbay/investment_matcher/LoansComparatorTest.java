package com.df.landbay.investment_matcher;

import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.Test;

import com.df.landbay.investment_matcher.controller.LoansComparator;
import com.df.landbay.investment_matcher.model.Loan;
import com.df.landbay.investment_matcher.model.ProductType;

public class LoansComparatorTest {

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	@Test
	public void testCompareReturnsPositiveWhenComparingNewerToOlder() throws ParseException {
		LoansComparator comparator = new LoansComparator();
		
		Loan olderLoan = new Loan(1, 10, ProductType.TRACKER, 12, sdf.parse("2016-01-01"));
		Loan newerLoan = new Loan(2, 10, ProductType.TRACKER, 12, sdf.parse("2016-02-01"));
		
		assertTrue(0 < comparator.compare(newerLoan, olderLoan));
	}
	
	@Test
	public void testCompareReturnsPositiveIfDateIsTheSameButComparingLessToMore() throws ParseException {
		LoansComparator comparator = new LoansComparator();
		
		Loan loanForLess = new Loan(1, 10, ProductType.TRACKER, 12, sdf.parse("2016-01-01"));
		Loan loanForMore = new Loan(2, 20, ProductType.TRACKER, 12, sdf.parse("2016-01-01"));
		
		assertTrue(0 < comparator.compare(loanForLess, loanForMore));
	}
	
	@Test
	public void testCompareReturnsPositiveIfDateAndAmountSameButComparingLongerTermToShorter() throws ParseException {
		LoansComparator comparator = new LoansComparator();
		
		Loan loanForLonger = new Loan(1, 10, ProductType.TRACKER, 30, sdf.parse("2016-01-01"));
		Loan loanForShorter = new Loan(2, 10, ProductType.TRACKER, 12, sdf.parse("2016-01-01"));
		
		assertTrue(0 < comparator.compare(loanForLonger, loanForShorter));
	}
	
	@Test
	public void testCompareReturnsPositiveCompareProductTypesInNonAlphabeticalOrder() throws ParseException {
		LoansComparator comparator = new LoansComparator();
		
		Loan trackerLoan = new Loan(1, 10, ProductType.TRACKER, 12, sdf.parse("2016-01-01"));
		Loan fixedLoan = new Loan(2, 10, ProductType.FIXED, 12, sdf.parse("2016-01-01"));
		
		assertTrue(0 < comparator.compare(trackerLoan, fixedLoan));
	}
	
	@Test
	public void testCompareReturnsPositiveCompareLoansInNonIdOrder() throws ParseException {
		LoansComparator comparator = new LoansComparator();
		
		Loan trackerLoan = new Loan(2, 10, ProductType.TRACKER, 12, sdf.parse("2016-01-01"));
		Loan fixedLoan = new Loan(1, 10, ProductType.TRACKER, 12, sdf.parse("2016-01-01"));
		
		assertTrue(0 < comparator.compare(trackerLoan, fixedLoan));
	}
	
}
