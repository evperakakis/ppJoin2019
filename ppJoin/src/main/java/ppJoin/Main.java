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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static ppJoin.enums.Settings.RECORD_100000_FILE_PATH;
import static ppJoin.enums.Settings.RECORD_10000_FILE_PATH;
import static ppJoin.enums.Settings.TEMP_RECORDS_PATH;

public class Main {
    static private ReaderService readerService = new ReaderService();

    public static void main(String[] args) {
        System.out.println("Starting...");
        // Read from csv to List<Record>
//        List<Record> recordList = readerService.csvToRecordList(RECORD_FILE_PATH.getValue(), 3, true);
//        List<Record> recordList = readerService.csvToRecordList(SECONDARY_TEST_RECORD_FILE_PATH.getValue(), 3, true); //0.71428571
//        List<Record> recordList = readerService.csvToRecordList(RECORD_1000_FILE_PATH.getValue(), 3, true);
//        List<Record> recordList = readerService.csvToRecordList(RECORD_10000_FILE_PATH.getValue(), 3, true);
//        List<Record> initialRecordList = readerService.csvToRecordList(RECORD_100000_FILE_PATH.getValue(), 3, false);
        List<Record> initialRecordList = readerService.csvToRecordList(RECORD_100000_FILE_PATH.getValue(), 3, true);


//               !!!  WITHOUT PARTITIONING  !!!
        System.out.println("\n" + "Starting ppJoin...");
        PPjoinExecutor ex = new PPjoinExecutor();
//        BruteForceExecutor ex = new BruteForceExecutor();
        ex.execute(initialRecordList);

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


//        SpatialPartitioner spatialPartitioner = new SpatialPartitioner();
//        spatialPartitioner.partitionData(initialRecordList);
//
//        LocalDateTime startTime = LocalDateTime.now();
//
//        File dir = new File(TEMP_RECORDS_PATH.getValue());
//        File[] directoryListing = dir.listFiles();
//        if (directoryListing != null) {
//            for (File file : directoryListing) {
//                System.out.println("\n" + "Starting ppJoin...");
//               PPjoinExecutor ex = new PPjoinExecutor();
//                ex.execute(readerService.csvToRecordList(file.getAbsolutePath(), 3, true));
//                InvertedIndex.getInstance().getIndex().clear();
//                FrequencyIndex.getInstance().getIndex().clear();
//            }
//        }
//
//        LocalDateTime stopTime = LocalDateTime.now();
//        long executionTime = ChronoUnit.SECONDS.between(startTime, stopTime);
//        System.out.println("\n" + "Total Execution Time :: " + executionTime + " seconds.");


        Set<RecordPair> results = new HashSet<>(PPjoinExecutor.recordPairs);

        results = results;


    }

}
