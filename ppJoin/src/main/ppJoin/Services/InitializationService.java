package ppJoin.Services;

import ppJoin.pojos.FrequencyIndex;
import ppJoin.pojos.Record;

import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class InitializationService {


    public List<Record> initializeForPPjoin(List<Record> recordList) {
        produceFrequencyIndex(recordList);
        return sortRecordListBasedOnFrequencyIndex(sizeSort(recordList));
    }

    private void produceFrequencyIndex(List<Record> recordList) {
        for (Record record : recordList) {
            for (String nGram : record.getNGramsList()) {
                FrequencyIndex.getInstance().addToFrequencyIndex(nGram);
            }
        }
    }

    private List<Record> sizeSort(List<Record> recordList) {
        recordList.sort((record1, record2) -> {
            removeDuplicateNgrams(record1);
            removeDuplicateNgrams(record2);
            int size1 = record1.getNGramsList().size();
            int size2 = record2.getNGramsList().size();
            return (size1 - size2);
        });
        return recordList;
    }

    private List<Record> sortRecordListBasedOnFrequencyIndex(List<Record> recordList) {
        for (Record record : recordList) {
            record.getNGramsList().sort(String::compareTo); // Sort Based on Alphabetical order first (if not --> bug)
            record.getNGramsList().sort( (nGram1, nGram2) -> {
                int frequency1 = FrequencyIndex.getInstance().getIndex().get(nGram1);
                int frequency2 = FrequencyIndex.getInstance().getIndex().get(nGram2);
                    return frequency1 - frequency2;

            });
            // Uncomment bellow line to remove duplicate nGrams from list TODO :: Check if redundant
            removeDuplicateNgrams(record);
        }
        return recordList;
    }

    private void removeDuplicateNgrams(Record record) {
        record.setNGramsList(record.getNGramsList().stream().distinct().collect(Collectors.toList()));
    }

    public TreeMap<Integer, Record> listOfRecordsToIdMap(List<Record> recordList){
        TreeMap<Integer, Record > map = new TreeMap<>();
        Integer id = 1;
        for (Record record : recordList) {
            map.put(id, record);
            id++;
        }
        return map;
    }

}
