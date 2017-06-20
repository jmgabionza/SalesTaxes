Design and Specifications:

1. The Java application is designed to accept a flat file (CSV) as input.
	CSV Format:
			Quantity,Item Classification,Item Description,Amount,imported<optional:if item is imported>
			
2. The list of item classification that will have a sales tax exemption is located on resources/taxExempted.csv. As per requirement, the csv file contains the 3 items that are exempted for sales tax (book,food,medical). this can accomodate any additional items in the future.

3. The output receipt is only shown in the console. The design also allows for other forms of output in the future (file,database, etc.)