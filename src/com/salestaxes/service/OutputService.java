/**
 * 
 */
package com.salestaxes.service;

import java.math.BigDecimal;
import java.util.List;

import com.salestaxes.domain.Input;

/**
 * @author JANMICHAEL.GABIONZA
 *
 */
public class OutputService {
	
	/**
	 * Prints to console a receipt which lists the name of all the items 
	 * and their price (including tax), finishing with the total cost of the items, 
	 * and the total amounts of sales taxes paid.
	 * 
	 * @param inputList
	 */
	public void printOutput(List<Input> inputList){
		BigDecimal totalSalesTax = new BigDecimal(0);
		BigDecimal totalAmount = new BigDecimal(0);
		for (Input input : inputList) {
			totalSalesTax = totalSalesTax.add(input.getSalesTax()).add(input.getImportDuty());
			input.setAmount(input.getAmount().add(input.getSalesTax()).add(input.getImportDuty()));
			totalAmount = totalAmount.add(input.getAmount());
			
			StringBuilder sb = new StringBuilder();
			sb.append(input.getQuantity());
			if (input.isImported()) {
				sb.append(" imported ");
			}
			sb.append(" " + input.getDescription() + ": ");
			sb.append(input.getAmount());
			
			System.out.println(sb.toString());
		}
		
		System.out.println("Sales Taxes: " + totalSalesTax);
		System.out.println("Total: " + totalAmount);
		
	}

}
