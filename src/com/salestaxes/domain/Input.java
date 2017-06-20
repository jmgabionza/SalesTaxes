package com.salestaxes.domain;

import java.math.BigDecimal;

/**
 * The domain object for Input
 * 
 * @author JANMICHAEL.GABIONZA
 *
 */
public class Input {
	private boolean isImported;
	private int quantity;
	private String classification;
	private String description;
	private BigDecimal amount;
	private BigDecimal salesTax;
	private BigDecimal importDuty;
	
	public boolean isImported() {
		return isImported;
	}
	public void setImported(boolean isImported) {
		this.isImported = isImported;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public String getClassification() {
		return classification;
	}
	public void setClassification(String classification) {
		this.classification = classification;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	public BigDecimal getSalesTax() {
		return salesTax;
	}
	public void setSalesTax(BigDecimal salesTax) {
		this.salesTax = salesTax;
	}
	
	public BigDecimal getImportDuty() {
		return importDuty;
	}
	public void setImportDuty(BigDecimal importDuty) {
		this.importDuty = importDuty;
	}
	public Input() {
		// Default constructor
	}
	
	public Input(int quantity, String classification, String description, BigDecimal amount, boolean isImported) {
		this.quantity = quantity;
		this.classification = classification;
		this.description = description;
		this.amount = amount;
		this.isImported = isImported;
	}
}
