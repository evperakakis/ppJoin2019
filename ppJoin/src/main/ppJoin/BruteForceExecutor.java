package ppJoin;

import info.debatty.java.stringsimilarity.Jaccard;
import org.junit.Test;
import org.junit.runners.Parameterized;
import ppJoin.Services.InitializationService;
import ppJoin.Services.ReaderService;
import ppJoin.interfaces.TextualJoinExecutor;
import ppJoin.pojos.Record;
import ppJoin.pojos.RecordPair;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;


public class BruteForceExecutor implements TextualJoinExecutor {

    public static List<RecordPair> recordPairs = new ArrayList<>();

    public void execute(List<Record> recordList, Double similarityThreshold) {

        LocalDateTime startTime = LocalDateTime.now();

        for (int i = 0; i < recordList.size(); i++) {
            for (int j = i+1; j < recordList.size(); j++) {
                Record recordX = recordList.get(i);
                Record recordY = recordList.get(j);
                double similarity = similarity(recordX.getNGramsList(), recordY.getNGramsList());
                if (similarity >= similarityThreshold) {
                    recordPairs.add(new RecordPair(recordX, recordY));
                }
            }
        }
        LocalDateTime stopTime = LocalDateTime.now();
        long executionTime = ChronoUnit.SECONDS.between(startTime, stopTime);
        System.out.println("\n" + "Execution Time :: "
                + String.format("%02d:%02d:%02d",(executionTime/3600), ((executionTime % 3600)/60), (executionTime % 60))
                + ". (" + executionTime + " seconds)");
    }

    private Double similarity(List<String> nGramList1, List<String> nGramList2) {
        Set<String> nGramListProxy1 = new HashSet<>(nGramList1);
        Set<String> nGramListProxy2 = new HashSet<>(nGramList2);

        Set<String> nGramSet = new HashSet<>(nGramList1);
        nGramSet.addAll(nGramListProxy2);
        Double overlap = 0d;
        Double intersection;
        nGramListProxy1.retainAll(nGramListProxy2);
        intersection = Double.parseDouble(String.valueOf(nGramListProxy1.size()));
        overlap = overlap + intersection;

        return overlap/(nGramSet.size());
    }

    @Test
    public void similarityTest() {
        Set<String> nGramList1 = new HashSet<>(Arrays.asList("tri", "dio", "ena", "tes", "pen", "xxx", "oct"));
        Set<String> nGramList2 = new HashSet<>(Arrays.asList("tri", "dio", "ooo", "tes", "pen", "eks", "ene"));
        Set<String> nGramSet = new HashSet<>(nGramList1);
        nGramSet.addAll(nGramList2);
        Double overlap = 0d;
        Double intersection;
        nGramList1.retainAll(nGramList2);
        intersection = Double.parseDouble(String.valueOf(nGramList1.size()));
        overlap = overlap + intersection;

        Double result = overlap/(nGramSet.size());
        overlap = overlap;
    }

}

