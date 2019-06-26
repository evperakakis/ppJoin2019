package ppJoin;

import org.danilopianini.util.FlexibleQuadTree;
import org.danilopianini.util.SpatialIndex;
import ppJoin.Services.ReaderService;
import ppJoin.pojos.FrequencyIndex;
import ppJoin.pojos.InvertedIndex;
import ppJoin.pojos.Record;
import ppJoin.pojos.RecordPair;

import java.io.File;
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
        List<Record> initialRecordList = readerService.csvToRecordList(RECORD_1000_FILE_PATH.getValue(), 3, true);
//        List<Record> initialRecordList = readerService.csvToRecordList(RECORD_10000_FILE_PATH.getValue(), 3, false);
//        List<Record> initialRecordList = readerService.csvToRecordList(RECORD_100000_FILE_PATH.getValue(), 3, false);
//        List<Record> initialRecordList = readerService.csvToRecordList(RECORD_100000_FILE_PATH.getValue(), 3, true);


////               !!!  WITHOUT PARTITIONING  !!!
//        System.out.println("\n" + "Starting ppJoin...");
//        PPjoinExecutor ex = new PPjoinExecutor();
////        BruteForceExecutor ex = new BruteForceExecutor();
//        ex.execute(initialRecordList);

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        SpatialPartitioner spatialPartitioner = new SpatialPartitioner();
        spatialPartitioner.partitionData(initialRecordList);

        LocalDateTime startTime = LocalDateTime.now();

        File dir = new File(TEMP_RECORDS_PATH.getValue());
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File file : directoryListing) {
                System.out.println("\n" + "Starting ppJoin...");
               PPjoinExecutor ex = new PPjoinExecutor();
                ex.execute(readerService.csvToRecordList(file.getAbsolutePath(), 3, true));
                InvertedIndex.getInstance().getIndex().clear();
                FrequencyIndex.getInstance().getIndex().clear();
            }
        }

        LocalDateTime stopTime = LocalDateTime.now();
        long executionTime = ChronoUnit.SECONDS.between(startTime, stopTime);
        System.out.println("\n" + "Total Execution Time :: " + executionTime + " seconds.");

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        //********************************************************************************************//
        List<RecordPair> results = new ArrayList<>();
        for (RecordPair rp : PPjoinExecutor.recordPairs) {
            if (!results.contains(rp)) {
                results.add(rp);
            }
        }
        results = results;
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
