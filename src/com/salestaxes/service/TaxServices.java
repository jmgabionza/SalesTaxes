/**
 * 
 */
package com.salestaxes.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import com.salestaxes.domain.Input;

/**
 * @author JANMICHAEL.GABIONZA
 *
 */
public class TaxServices {
	private static final int DOMESTIC_TAX_RATE = 10;
	private static final int IMPORT_DUTIES_RATE = 5;
	private static final BigDecimal ROUNDING = new BigDecimal(0.05).round(new MathContext(1, RoundingMode.HALF_UP));
	
	private List<String> exemptedItems;
	
	public TaxServices() {
		this.exemptedItems = getExemptedItems();
	}
	
	private List<String> getExemptedItems() {
		String taxExemptedPath = TaxServices.class.getClassLoader().getResource("taxExempted.csv").getPath();
		List<String> exemptedItems = new ArrayList<String>();
		try (BufferedReader br = new BufferedReader(new FileReader(taxExemptedPath))) {
			String currentLine;
			while ((currentLine = br.readLine()) != null) {
				String[] values = currentLine.split(",");
				for (String string : values) {
					exemptedItems.add(string);
				}
			}
		} catch (IOException e) {
			System.err.println("Error while getting exempted items.");
			e.printStackTrace();
		}
		return exemptedItems;
	}

	/**
	 * Calculates the Domestic sales tax. <br>
	 * The rounding rules for sales tax are that for a tax rate of n%, a shelf price of p contains (np/100 rounded up to the nearest 0.05) amount of sales tax.
	 * 
	 * @param inputList
	 */
	public void calculateDomesticTax(List<Input> inputList){
		for (Input input : inputList) {
			boolean isExemptedItem = false;
			for (String exemptedItem : exemptedItems) {
				if (input.getClassification().equalsIgnoreCase(exemptedItem)) {
					isExemptedItem = true;
					break;
				}
			}
			if (!isExemptedItem) {
				BigDecimal salesTax = (input.getAmount().multiply(new BigDecimal(DOMESTIC_TAX_RATE))).divide(new BigDecimal(100));
				salesTax = round(salesTax, ROUNDING);
				input.setSalesTax(salesTax);
			}else {
				input.setSalesTax(BigDecimal.ZERO);
			}
		}
	}

	/**
	 * Calculates import duties for imported items<br>
	 * The rounding rules for sales tax are that for a tax rate of n%, a shelf price of p contains (np/100 rounded up to the nearest 0.05) amount of sales tax.
	 * 
	 * @param inputList
	 */
	public void calculateImportDuties(List<Input> inputList) {
		for (Input input : inputList) {
			if (input.isImported()) {
				BigDecimal importDuty = (input.getAmount().multiply(new BigDecimal(IMPORT_DUTIES_RATE))).divide(new BigDecimal(100));
				importDuty = round(importDuty, ROUNDING);
				input.setImportDuty(importDuty);
			}else {
				input.setImportDuty(BigDecimal.ZERO);
			}
		}
	}
	
	private BigDecimal round(BigDecimal value, BigDecimal rounding){
		if (rounding.signum() == 0) {
			return value;
		}else {
			return (value.divide(rounding,0,BigDecimal.ROUND_UP)).multiply(rounding);
		}
	}
}
