package com.salestaxes.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.salestaxes.domain.Input;
import com.salestaxes.service.OutputService;
import com.salestaxes.service.TaxServices;

/**
 * The Main Class of the application.
 * The application accepts a CSV file, computes for sales taxes,
 * and outputs the details and total amounts on the console.
 * 
 * @author JANMICHAEL.GABIONZA
 *
 */
public class Main {

	/**
	 * The main class takes in a CSV location (absolute path) as an argument
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		String filename = "";
		if (args.length > 0) {
			filename = args[0];
		}
		Main main = new Main();
		if (isValid(filename)) {
			main.process(filename);
		}else {
			System.err.println("Input path does not exist. Using the sample file on resources.");
			filename = Main.class.getClassLoader().getResource("input.csv").getPath();
			main.process(filename);
		}
	}

	private void process(String filename) {
		TaxServices taxServices = new TaxServices();
		OutputService outputService = new OutputService();
		List<Input> inputList = readInput(filename);
		taxServices.calculateDomesticTax(inputList);
		taxServices.calculateImportDuties(inputList);
		outputService.printOutput(inputList);
	}

	private List<Input> readInput(String filename) {
		
		List<Input> inputList = new ArrayList<Input>();
		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			String currentLine;
			while ((currentLine = br.readLine()) != null) {
				String[] values = currentLine.split(",");
				int quantity = Integer.parseInt(values[0]);
				String classification = values[1];
				String description = values[2];
				BigDecimal amount = new BigDecimal(values[3]);
				boolean isImported = false;
				String imported ="";
				if (values.length > 4) {
					imported = values[4];
				}
				if (null != imported && imported.equalsIgnoreCase("imported")) {
					isImported = true;
				}
				Input input = new Input(quantity, classification, description, amount, isImported);
				inputList.add(input);
			}

		} catch (IOException e) {
			System.err.println("Error while reading input.");
			e.printStackTrace();
		}
		return inputList;
	}

	private static boolean isValid(String filename) {
		File file = new File(filename);
		if (file.isFile()) {
			return true;
		}else {
			return false;
		}
	}
	

}
