package com.df.landbay.investment_matcher.view;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.df.landbay.investment_matcher.model.Loan;

/**
 * Class implementing a view object that will output the loans in JSON format to standard output
 * @author David Fidgett
 *
 */
public class JsonView implements View {

	/* (non-Javadoc)
	 * @see com.df.landbay.investment_matcher.view.View#displayFundedLoans(java.util.List)
	 */
	public void displayFundedLoans(List<Loan> outLoans) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.setDateFormat((DateFormat)sdf);;;
		
		try {
			System.out.println(mapper.writeValueAsString(outLoans));
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
