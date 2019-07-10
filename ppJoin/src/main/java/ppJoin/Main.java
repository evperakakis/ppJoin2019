package ppJoin;

import org.danilopianini.util.FlexibleQuadTree;
import org.danilopianini.util.SpatialIndex;
import ppJoin.Services.ReaderService;
import ppJoin.pojos.FrequencyIndex;
import ppJoin.pojos.InvertedIndex;
import ppJoin.pojos.Record;
import ppJoin.pojos.RecordPair;

import java.io.File;
import java.sql.SQLOutput;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

import static ppJoin.enums.Settings.*;

public class Main {
    static private ReaderService readerService = new ReaderService();

    public static void main(String[] args) {
        System.out.println("Starting...");
        // Read from csv to List<Record>
//        List<Record> initialRecordList = readerService.csvToRecordList(RECORD_FILE_PATH.getValue(), 3, false);
//        List<Record> initialRecordList = readerService.csvToRecordList(SECONDARY_TEST_RECORD_FILE_PATH.getValue(), 3, true); //0.71428571
//        List<Record> initialRecordList = readerService.csvToRecordList(RECORD_1000_FILE_PATH.getValue(), 3, true);
//        List<Record> initialRecordList = readerService.csvToRecordList(RECORD_1000_FILE_PATH.getValue(), 3, false);
//        List<Record> initialRecordList = readerService.csvToRecordList(RECORD_10000_FILE_PATH.getValue(), 3, true);
//        List<Record> initialRecordList = readerService.csvToRecordList(RECORD_10000_FILE_PATH.getValue(), 3, false);
//        List<Record> initialRecordList = readerService.csvToRecordList(RECORD_100000_FILE_PATH.getValue(), 3, false);
//        List<Record> initialRecordList = readerService.csvToRecordList(RECORD_100000_FILE_PATH.getValue(), 3, true);
//        List<Record> initialRecordList = readerService.csvToRecordList(RECORD_1000000_FILE_PATH.getValue(), 3, false);
        List<Record> initialRecordList = readerService.csvToRecordList(RECORD_1000000_FILE_PATH.getValue(), 3, true);


//               !!!  WITHOUT PARTITIONING  !!!
//        PPjoinExecutor ex = new PPjoinExecutor();
        BruteForceExecutor ex = new BruteForceExecutor();
        System.out.println("\n" + "Initiating " + ex.getClass().getSimpleName() +"...");
        ex.execute(initialRecordList, 0.6999);

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Actual impl starts from here....

//        Double distanceThreshold = 100.0;  // standard test = 100
//        Double similarityThreshold = 0.6999;
//
//        SpatialPartitioner spatialPartitioner = new SpatialPartitioner();
//        spatialPartitioner.partitionData(initialRecordList, distanceThreshold);
//
//        LocalDateTime startTime = LocalDateTime.now();
//
//        File dir = new File(TEMP_RECORDS_PATH.getValue());
//        File[] directoryListing = dir.listFiles();
//        if (directoryListing != null) {
//            int fileLoopCounter = 1;
//            for (File file : directoryListing) {
//                System.out.println("\n" + "PPjoin on data set partition " + fileLoopCounter + "/" + directoryListing.length +"...");
//                PPjoinExecutor ex = new PPjoinExecutor();
//                ex.execute(readerService.csvToRecordList(file.getAbsolutePath(), 3, true), similarityThreshold);
//                InvertedIndex.getInstance().getIndex().clear();
//                FrequencyIndex.getInstance().getIndex().clear();
//                fileLoopCounter++;
//            }
//        }
//
//        LocalDateTime stopTime = LocalDateTime.now();
//        long executionTime = ChronoUnit.SECONDS.between(startTime, stopTime);
//                System.out.println("\n" + "Execution Time :: "
//                + String.format("%02d:%02d:%02d",(executionTime/3600), ((executionTime % 3600)/60), (executionTime % 60))
//                + "  (" + executionTime + " seconds)");
//
//
//        Set<RecordPair> resultsSet = new HashSet<>(PPjoinExecutor.recordPairs);
//        System.out.println("Found " + resultsSet.size() + " matching pairs.");
// to here....
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        //********************************************************************************************//
//        List<RecordPair> results = new ArrayList<>();
//        for (RecordPair rp : PPjoinExecutor.recordPairs) {
//            if (!results.contains(rp)) {
//                results.add(rp);
//            }
//        }

        Set<RecordPair> resultsSet = new HashSet<>(BruteForceExecutor.recordPairs);
        System.out.println("Found " + resultsSet.size() + " matching pairs.");


        //********************************************************************************************//


//        List<Record> results = new ArrayList<>();
//        File dir = new File(TEMP_RECORDS_PATH.getValue());
//        File[] directoryListing = dir.listFiles();
//        if (directoryListing != null) {
//            for (File file : directoryListing) {
//                System.out.println("\n" + "Starting ppJoin...");
//                for (Record r : readerService.csvToRecordList(file.getAbsolutePath(), 3, false)) {
//                    if (!results.contains(r)) {
//                        results.add(r);
//                    }
//                }
//            }
//        }
//
//        initialRecordList = initialRecordList;
//        results = results;
//        initialRecordList.removeAll(results);
//        results = results;


    }

}
