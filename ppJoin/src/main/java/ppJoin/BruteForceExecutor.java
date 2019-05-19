package ppJoin;

import info.debatty.java.stringsimilarity.Jaccard;
import ppJoin.Services.InitializationService;
import ppJoin.Services.ReaderService;
import ppJoin.pojos.Record;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static ppJoin.enums.Settings.RECORD_1000_FILE_PATH;
import static ppJoin.enums.Settings.RECORD_10000_FILE_PATH;
import static ppJoin.enums.Settings.RECORD_100000_FILE_PATH;


public class BruteForceExecutor {

    private ReaderService readerService = new ReaderService();
    private InitializationService initializationService = new InitializationService();

    private List<String> temporaryPairs =  new ArrayList<>();

    void execute() {

        List<Record> recordList = readerService.csvToRecordList(RECORD_10000_FILE_PATH.getValue(), 3);
        Jaccard jaccard = new Jaccard(3);

        System.out.println("\n" + "Starting bruteForce...");
        LocalDateTime startTime = LocalDateTime.now();

        for (Record recordX : recordList) {
            for (Record recordY : recordList) {
                if (recordX.getTextValue().equals(recordY.getTextValue())
                        && recordX.getAxisValueX() == recordY.getAxisValueX()
                        && recordX.getAxisValueY() == recordY.getAxisValueY()) {
                    break;
                }
                if (jaccard.similarity(recordX.getTextValue(), recordY.getTextValue()) >= 0.6999) {
                    temporaryPairs.add(recordX.toString() + "\n   ++++PAIR++++ \n" + recordY.toString() +
                            "\n\n\n");
                }
            }
        }

        LocalDateTime stopTime = LocalDateTime.now();
        long executionTime = ChronoUnit.SECONDS.between(startTime, stopTime);

        System.out.println(temporaryPairs);

        System.out.println("\n" + "Execution Time :: " + executionTime + " seconds.");


    }


}

