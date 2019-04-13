package ppJoin;

import ppJoin.Services.InitializationService;
import ppJoin.Services.ReaderService;
import ppJoin.pojos.FrequencyIndex;
import ppJoin.pojos.InvertedIndex;
import ppJoin.pojos.Occurrence;
import ppJoin.pojos.Record;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ppJoin.enums.Settings.RECORD_FILE_PATH;
import static ppJoin.enums.Settings.SECONDARY_TEST_RECORD_FILE_PATH;
import static ppJoin.enums.Settings.THIRD_TEST_RECORD_FILE_PATH;

class PPjoinExecutor {

    private ReaderService readerService = new ReaderService();
    private InitializationService initializationService = new InitializationService();

    private List<String> temporaryPairs =  new ArrayList<>();


    PPjoinExecutor() {}

    void execute() {

        // Read from csv to List<Record>
        //List<Record> recordList = readerService.csvToRecordList(RECORD_FILE_PATH.getValue(), 3);
        //List<Record> recordList = readerService.csvToRecordList(SECONDARY_TEST_RECORD_FILE_PATH.getValue(), 3); //0.71428571
        List<Record> recordList = readerService.csvToRecordList(THIRD_TEST_RECORD_FILE_PATH.getValue(), 3);

        System.out.println("--- RECORD LIST: ---");
        for (Record record : recordList)
            System.out.println(record.toString());
        System.out.println("----------------------------------------------------------------------------");

        recordList = initializationService.initializeForPPjoin(recordList);

        System.out.println("--- SORTED RECORD LIST: ---");
        for (Record record : recordList)
            System.out.println(record.toString());
        System.out.println("----------------------------------------------------------------------------");

        System.out.println("--- FREQUENCY INDEX: ---");
        System.out.println(FrequencyIndex.getInstance().getIndex());
        System.out.println("----------------------------------------------------------------------------");

        final Map<Integer, Record> recordMap = initializationService.listOfRecordsToIdMap(recordList);

        findPairs(recordMap, 0.1999999999);
    }


    private void findPairs(final Map<Integer, Record> recordMap, final Double similarityThreshold) {

        for (Map.Entry<Integer, Record> entryRM : recordMap.entrySet()) {
            Integer recordIdX = entryRM.getKey();
            Record recordX = entryRM.getValue();

            Integer recordSizeX = recordX.getNGramsList().size();

            //This is A from "Efficient Similarity Joins for Near Duplicate Detection" paper
            HashMap<Integer, Integer> overlapMap = new HashMap<>();

            // Get prefix length
            Integer prefixLength = PPjoinUtils.calculate_prefixLength(recordX, similarityThreshold);

            Integer positionI = 0;
            for (String nGram : recordX.getNGramsList().subList(0, prefixLength)) {
                positionI++;
                if (InvertedIndex.getInstance().getIndex().get(nGram) != null) {
                    for (Occurrence occurrence : InvertedIndex.getInstance().getIndex().get(nGram)) {
                        Integer recordIdY = occurrence.getRecordId();
                        if (recordIdX.equals(recordIdY)) break;

                        Integer positionJ = occurrence.getPosition();
                        Integer recordSizeY = recordMap.get(recordIdY).getNGramsList().size();

                        if ((recordSizeY >= (similarityThreshold * recordSizeX))) {
                            Integer a = PPjoinUtils.calculate_a(similarityThreshold, recordSizeX, recordSizeY);
                            Integer ubound = PPjoinUtils.calculate_ubound(recordSizeX, recordSizeY,
                                    positionI, positionJ);
                            if ((overlapMap.getOrDefault(recordIdY, 0) + ubound >= a))
                                overlapMap.put(recordIdY, (overlapMap.getOrDefault(recordIdY, 0) + 1));
                            else
                                overlapMap.put(recordIdY, 0);
                        }
                    }
                }

                InvertedIndex.getInstance().addToInvertedIndex(nGram, recordIdX, positionI);
            }

            System.out.println("----------------------------------");
            System.out.println("VERIFY :: record = " + recordX + ", ");
            overlapMap.forEach((recordId, overlap) -> System.out.println("recordOverlap = " +
                    recordMap.get(recordId) + ", Overlap = " + overlap));
            System.out.println("----------------------------------");

            final Map<Record, Integer> overlapMapVerify = new HashMap<>();

            overlapMap.forEach((recordId, overlap) -> overlapMapVerify.put(recordMap.get(recordId), overlap));

            ppJoinVerify(recordX, overlapMapVerify, similarityThreshold);
        }

        System.out.println("--- INVERTED INDEX: ---");
        System.out.println(InvertedIndex.getInstance().getIndex());
        System.out.println("----------------------------------------------------------------------------------------------------------------");
        System.out.println("----------------------PAIRS----------------------\n");
        System.out.println(temporaryPairs);
    }


    private void ppJoinVerify (Record record, Map<Record,Integer> overlapMap, double simThreshold) {

        Integer prefixLengthX = PPjoinUtils.calculate_prefixLength(record, simThreshold);

        overlapMap.forEach((recordFromOverlapMap, overlap) -> {
            int intersection;
            Integer ubound;
            List<String> tokenSet1 =  new ArrayList<>(), tokenSet2 =  new ArrayList<>();

            Integer a = PPjoinUtils.calculate_a(simThreshold,
                    record.getNGramsList().size(), recordFromOverlapMap.getNGramsList().size());

            if (overlap > 0) {
                Integer prefixLengthY = PPjoinUtils.calculate_prefixLength(recordFromOverlapMap, simThreshold);
                String wx = record.getNGramsList().get(prefixLengthX - 1);
                Integer wxFreq = FrequencyIndex.getInstance().getIndex().get(wx);
                String wy = recordFromOverlapMap.getNGramsList().get(prefixLengthY - 1);
                Integer wyFreq = FrequencyIndex.getInstance().getIndex().get(wy);
                // valueO is O as named in Verify
                int valueO = overlap;

                if (wxFreq <= wyFreq) {  //  Global Frequency Ordering (Odf) favors rare tokens :: it means "is Wx rarest OR as rare as Wy?"
                    ubound = valueO + record.getNGramsList().size() - prefixLengthX;
                    if (ubound >= a) {
                        for (int i = prefixLengthX; i < record.getNGramsList().size(); i++) {
                            tokenSet1.add(record.getNGramsList().get(i));
                        }
                        for (int i = overlap; i < recordFromOverlapMap.getNGramsList().size(); i++) {
                            tokenSet2.add(recordFromOverlapMap.getNGramsList().get(i));
                        }
                    }
                } else {
                    ubound = valueO + recordFromOverlapMap.getNGramsList().size() - prefixLengthY;
                    if (ubound >= a) {
                        for (int i = overlap; i < record.getNGramsList().size(); i++) {
                            tokenSet1.add(record.getNGramsList().get(i));
                        }
                        for (int i = prefixLengthY; i < recordFromOverlapMap.getNGramsList().size(); i++) {
                            tokenSet2.add(recordFromOverlapMap.getNGramsList().get(i));
                        }
                    }
                }
                tokenSet1.retainAll(tokenSet2);
                intersection = tokenSet1.size();
                valueO = valueO + intersection;

                if (valueO >= a) {
                    temporaryPairs.add(record.toString() + "\n   ++++PAIR++++ \n" + recordFromOverlapMap.toString() +
                    "\n\n\n");
                }
            }

        });
    }

}
