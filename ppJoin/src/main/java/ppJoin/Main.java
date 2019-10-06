package ppJoin;

import org.danilopianini.util.FlexibleQuadTree;
import org.danilopianini.util.SpatialIndex;
import ppJoin.Services.ReaderService;
import ppJoin.interfaces.TextualJoinExecutor;
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

        // False is for spatio-textual !!!

//        List<Record> initialRecordList = readerService.csvToRecordList(RECORD_FILE_PATH.getValue(), 3, false);
//        List<Record> initialRecordList = readerService.csvToRecordList(SECONDARY_TEST_RECORD_FILE_PATH.getValue(), 3, true); //0.71428571
//        List<Record> initialRecordList = readerService.csvToRecordList(RECORD_1000_FILE_PATH.getValue(), 3, true);
//        List<Record> initialRecordList = readerService.csvToRecordList(RECORD_1000_FILE_PATH.getValue(), 3, false);
//        List<Record> initialRecordList = readerService.csvToRecordList(RECORD_10000_FILE_PATH.getValue(), 3, true);
//        List<Record> initialRecordList = readerService.csvToRecordList(RECORD_100000_FILE_PATH.getValue(), 3, true);
//        List<Record> initialRecordList = readerService.csvToRecordList(RECORD_1000000_FILE_PATH.getValue(), 3, true);

//        List<Record> initialRecordList = readerService.csvToRecordList(RECORD_10000_FILE_PATH.getValue(), 3, false);
//        List<Record> initialRecordList = readerService.csvToRecordList(RECORD_100000_FILE_PATH.getValue(), 3, false);
//        List<Record> initialRecordList = readerService.csvToRecordList(RECORD_1000000_FILE_PATH.getValue(), 3, false);

//        List<Record> initialRecordList = readerService.csvToRecordList(RECORD_10000_CLUSTERED_FILE_PATH.getValue(), 3, false);
//        List<Record> initialRecordList = readerService.csvToRecordList(RECORD_100000_CLUSTERED_FILE_PATH.getValue(), 3, false);
        List<Record> initialRecordList = readerService.csvToRecordList(RECORD_1000000_CLUSTERED_FILE_PATH.getValue(), 3, false);

//               !!!  WITHOUT PARTITIONING  !!!
//        PPjoinExecutor ex = new PPjoinExecutor();
//        BruteForceExecutor ex = new BruteForceExecutor();
//        System.out.println("\n" + "Initiating " + ex.getClass().getSimpleName() +"...");
//        ex.execute(initialRecordList, 0.4999);

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Actual impl starts from here....



        Double similarityThreshold = 0.4999;
//        Double similarityThreshold = 0.6999;
//        Double similarityThreshold = 0.8999;

//        Double distanceThreshold = 10.0;
//        Double distanceThreshold = 40.0;
        Double distanceThreshold = 100.0;

        File dir = new File(TEMP_RECORDS_PATH.getValue());

        // Clean Temporary Directory
        if (dir.listFiles() != null && dir.listFiles().length > 0)
            Arrays.stream(dir.listFiles()).forEach(File::delete);

         TextualJoinExecutor ex = new PPjoinExecutor();
//        TextualJoinExecutor ex = new BruteForceExecutor();

        SpatialPartitioner spatialPartitioner = new SpatialPartitioner();
        spatialPartitioner.partitionData(initialRecordList, distanceThreshold);

        LocalDateTime startTime = LocalDateTime.now();

        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            int fileLoopCounter = 1;
            for (File file : directoryListing) {
                System.out.println("\n" + "Working on data set partition " + fileLoopCounter + "/" + directoryListing.length +"...");
                ex.execute(readerService.csvToRecordList(file.getAbsolutePath(), 3, true), similarityThreshold);
                InvertedIndex.getInstance().getIndex().clear();
                FrequencyIndex.getInstance().getIndex().clear();
                fileLoopCounter++;
            }
        }

        LocalDateTime stopTime = LocalDateTime.now();
        long executionTime = ChronoUnit.SECONDS.between(startTime, stopTime);
                System.out.println("\n" + "Execution Time :: "
                + String.format("%02d:%02d:%02d",(executionTime/3600), ((executionTime % 3600)/60), (executionTime % 60))
                + "  (" + executionTime + " seconds)");
        System.out.println("Max execution Time on Thread : " + ex.getMaxExecutionTimeOnThread() + " seconds.");

//        Set<RecordPair> resultsSet = new HashSet<>(PPjoinExecutor.recordPairs); // !!!!!!!!!!!!!!!!!!  <--- i keep this in case next line fails
        Set<RecordPair> resultsSet = ex.getRecordPairsToSet();
        System.out.println("Found " + resultsSet.size() + " matching pairs.");
        resultsSet = resultsSet;
// to here....
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        //********************************************************************************************//
//        List<RecordPair> results = new ArrayList<>();
//        for (RecordPair rp : PPjoinExecutor.recordPairs) {
//            if (!results.contains(rp)) {
//                results.add(rp);
//            }
//        }

//        Set<RecordPair> resultsSet = new HashSet<>(BruteForceExecutor.recordPairs);
//        System.out.println("Found " + resultsSet.size() + " matching pairs.");


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
