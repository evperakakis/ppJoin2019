package ppJoin.Services;

import org.simpleflatmapper.csv.CsvParser;
import ppJoin.enums.Settings;
import ppJoin.pojos.Record;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ReaderService {

    public List<Record> csvToRecordList (String csvLocation, int n , boolean includeNgrams) {
        ArrayList<Record> recordList = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\s"); // pattern to check if String contains whitespaces

        try {
            BufferedReader br = new BufferedReader(new FileReader(csvLocation));

            CsvParser.mapTo(Record.class).headers("textValue", "axisValueX", "axisValueY")
                    .stream(br).forEach(recordList::add);

            if (includeNgrams) {
                for (Record record : recordList) {
                    String textValue = record.getTextValue();
                    List<String> nGrams = new ArrayList<>();
                    for (int i = 0; i < textValue.length() - n + 1; i++) {
                        boolean stringHasWhitespaces = pattern.matcher(textValue.substring(i, i + n)).find();
                        if (!stringHasWhitespaces) {
                            nGrams.add(textValue.substring(i, i + n));
                        }
                    }
                    record.setNGramsList(nGrams);
                }
            }
            br.close();

        } catch (FileNotFoundException e) {
            System.out.println("FILE NOT FOUND");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("IO EXCEPTION");
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("NUMBER FORMAT EXCEPTION");
            e.printStackTrace();
        }

        return recordList;
    }

}
