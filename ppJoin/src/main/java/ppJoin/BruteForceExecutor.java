package ppJoin;

import info.debatty.java.stringsimilarity.Jaccard;
import ppJoin.Services.InitializationService;
import ppJoin.Services.ReaderService;
import ppJoin.interfaces.TextualJoinExecutor;
import ppJoin.pojos.Record;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;


public class BruteForceExecutor implements TextualJoinExecutor {

    private ReaderService readerService = new ReaderService();
    private InitializationService initializationService = new InitializationService();

    private List<String> temporaryPairs =  new ArrayList<>();

    public void execute(List<Record> recordList) {

        Double similarityThreshold = 0.6999;
        Jaccard jaccard = new Jaccard(3);

        System.out.println("\n" + "Starting bruteForce...");
        LocalDateTime startTime = LocalDateTime.now();

// Uncomment for the even brute-r algorithm
//        for (Record recordX : recordList) {
//            for (Record recordY : recordList) {
//                if (recordX.getTextValue().equals(recordY.getTextValue())
//                        && recordX.getAxisValueX() == recordY.getAxisValueX()
//                        && recordX.getAxisValueY() == recordY.getAxisValueY()) {
//                    break;
//                }
//                if (jaccard.similarity(recordX.getTextValue(), recordY.getTextValue()) >= 0.6999) {
//                    temporaryPairs.add(recordX.toString() + "\n   ++++PAIR++++ \n" + recordY.toString() +
//                            "\n\n\n");
//                }
//            }
//        }

        for (int i = 0; i < recordList.size(); i++) {
            for (int j = i+1; j < recordList.size(); j++) {
                Record recordX = recordList.get(i);
                Record recordY = recordList.get(j);
                if (jaccard.similarity(recordX.getTextValue(), recordY.getTextValue()) >= similarityThreshold) {
                    temporaryPairs.add(recordX.toString() + "\n   ++++PAIR++++ \n" + recordY.toString() +
                            "\n\n\n");
                }
            }
        }

        LocalDateTime stopTime = LocalDateTime.now();
        long executionTime = ChronoUnit.SECONDS.between(startTime, stopTime);

        System.out.println(temporaryPairs);
        System.out.println("Found " + temporaryPairs.size() + " pairs for similarity threshold " + similarityThreshold);
        System.out.println("\n" + "Execution Time :: " + executionTime + " seconds.");


    }


}

