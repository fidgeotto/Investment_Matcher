package com.df.landbay.investment_matcher;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.df.landbay.investment_matcher.controller.InvestmentComparator;
import com.df.landbay.investment_matcher.model.Investment;
import com.df.landbay.investment_matcher.model.ProductType;

public class InvestmentComparatorTest {

	@Test
	public void testCompareReturnsPositiveIfLongerTermComparedToShorterTermInvestment() {
		InvestmentComparator comparator = new InvestmentComparator();
		
		Investment investment1 = new Investment("Alice", 100, ProductType.TRACKER, 30);
		Investment investment2 = new Investment("Bob", 100, ProductType.TRACKER, 12);
		
		assertTrue(0 < comparator.compare(investment1, investment2));
	}
	
	@Test
	public void testCompareReturnsPositiveIfSmallerAmountComparedToLarger() {
		InvestmentComparator comparator = new InvestmentComparator();
		
		Investment investment1 = new Investment("Alice", 200, ProductType.TRACKER, 12);
		Investment investment2 = new Investment("Bob", 100, ProductType.TRACKER, 12);
		
		assertTrue(0 < comparator.compare(investment1, investment2));
	}
	
	@Test
	public void testCompareReturnsPositiveIfProductTrackersComparedInNonAlphabeticalOrder() {
		InvestmentComparator comparator = new InvestmentComparator();
		
		Investment investment1 = new Investment("Alice", 100, ProductType.TRACKER, 12);
		Investment investment2 = new Investment("Bob", 100, ProductType.FIXED, 12);
		
		assertTrue(0 < comparator.compare(investment1, investment2));
	}
	
	@Test
	public void testCompareReturnsPositiveIfInvestorNamesComparedInNonAlphabeticalOrder() {
		InvestmentComparator comparator = new InvestmentComparator();
		
		Investment investment1 = new Investment("Bob", 100, ProductType.TRACKER, 12);
		Investment investment2 = new Investment("Alice", 100, ProductType.TRACKER, 12);
		
		assertTrue(0 < comparator.compare(investment1, investment2));
	}
	
}
