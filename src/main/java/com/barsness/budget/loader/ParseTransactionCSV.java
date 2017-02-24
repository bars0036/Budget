package com.barsness.budget.loader;


import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import com.barsness.budget.service.domain.Transaction;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.util.List;

/**
 * Created by matt.barsness on 2/8/17.
 */
public class ParseTransactionCSV {
    private static final String[] FILE_HEADER_MAPPING = {"Date", "Description", "Institution", "Account", "Category", "Is Hidden", "Value"};

    private static final String DATE = "Date";
    private static final String DESCRIPTION = "Description";
    private static final String INSTITUTION = "Institution";
    private static final String ACCOUNT = "Account";
    private static final String CATEGORY = "Category";
    private static final String ISHIDDEN = "Is Hidden";
    private static final String VALUE = "Value";

    private static final DateTimeFormatter df = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");

    public static List<Transaction> parseTransactionCSV(String fileName){

        List<Transaction> transactions = new ArrayList<>();
        FileReader fileReader = null;
        CSVParser csvParser = null;

        CSVFormat csvFormat = CSVFormat.DEFAULT.withHeader(FILE_HEADER_MAPPING);

        try{
            fileReader = new FileReader(fileName);
            csvParser = new CSVParser(fileReader, csvFormat);
            List<CSVRecord> csvRecords = csvParser.getRecords();


            for(int i = 1; i<csvRecords.size(); i++){
                CSVRecord record = csvRecords.get(i);
                boolean isHidden = false;
                if(record.get(ISHIDDEN) =="Yes"){
                    isHidden = true;
                }
                Transaction tran = new Transaction(
                        LocalDateTime.parse(record.get(DATE) + " 00:00:00", df),
                        record.get(DESCRIPTION),
                        record.get(INSTITUTION),
                        record.get(ACCOUNT),
                        record.get(CATEGORY),
                        new Boolean(isHidden),
                        new BigDecimal(record.get(VALUE).replace("$", "").replace(",", "")),
                        "File Load");
                transactions.add(tran);
            }

        }
        catch(Exception e){
            System.out.println("Error reading CSV File: " + e.getMessage() );
            e.printStackTrace();
        }
        finally{
            try{
                fileReader.close();
                csvParser.close();
            }
            catch(IOException e){
                System.out.println("Error Closing Readers");
                e.printStackTrace();
            }
        }
        return transactions;
    }
}
