package com.df.landbay.investment_matcher.model;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.opencsv.CSVReader;

/**
 * Class for reading in the loans and investments from csv files
 * 
 * @author David Fidgett
 *
 */
public class LandbayCsvFileReader {

	/**
	 * Method that parses the loans file after reading it in and outputs a list of
	 * Loan objects
	 * @param filename path to file containing the csv file of loans
	 * @return List list of loans contained in the file
	 */
	public List<Loan> readLoansFile(String filename) {
		
		List<Loan> loans = new ArrayList<Loan>();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		try {
        	CSVReader reader = getCsvReader(filename);
        	String[] nextLine;
        	
        	//skip over headings
			reader.readNext();
			
        	while ((nextLine = reader.readNext()) != null) {
        		int id = Integer.parseInt(nextLine[0]);
        		int amount = Integer.parseInt(nextLine[1]);
        		ProductType productType = null;
        		if (nextLine[2].equals(ProductType.TRACKER.name())) {
        			productType = ProductType.TRACKER;
        		} else if (nextLine[2].equals(ProductType.FIXED.name())) {
        			productType = ProductType.FIXED;
        		} else {
        			throw new IllegalArgumentException();
        		}
        		int term = Integer.parseInt(nextLine[3]);
        		Date completedDate = null;
        		try {
					completedDate = sdf.parse(nextLine[4]);
				} catch (ParseException e) {
					e.printStackTrace();
				}
        		Loan loan = new Loan(id, amount, productType, term, completedDate);
        		loans.add(loan);
        	}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
        
        return loans;
	}

	/**
	 * Method that parses the investments file after reading it in and outputs a list of
	 * Investment objects
	 * @param filename path to file containing the csv file of investments
	 * @return List list of investments contained in the file
	 */
	public List<Investment> readInvestmentsFile(String filename) {
		List<Investment> investments = new ArrayList<Investment>();
		
		try {
        	CSVReader reader = getCsvReader(filename);
        	String[] nextLine;
        	
        	//skip over headings
			reader.readNext();
			
        	while ((nextLine = reader.readNext()) != null) {
        		String name = nextLine[0];
        		int amount = Integer.parseInt(nextLine[1]);
        		ProductType productType = null;
        		if (nextLine[2].equals(ProductType.TRACKER.name())) {
        			productType = ProductType.TRACKER;
        		} else if (nextLine[2].equals(ProductType.FIXED.name())) {
        			productType = ProductType.FIXED;
        		} else {
        			throw new IllegalArgumentException();
        		}
        		int term = Integer.parseInt(nextLine[3]);
        		
        		Investment investment = new Investment(name, amount, productType, term);
        		investments.add(investment);
        	}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		return investments;
	}
	
	/**
	 * Method that encapsulates the creation of the CSVReader object given a filename
	 * @param filename name of the file to create a reader for
	 * @return CSVReader 
	 */
	private CSVReader getCsvReader(String filename) {
		
		CSVReader reader = null;
			try {
				reader = new CSVReader(new FileReader(filename));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		
		return reader;
	}
	
}
