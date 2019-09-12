package ppJoin.interfaces;

import ppJoin.pojos.Record;
import ppJoin.pojos.RecordPair;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public interface TextualJoinExecutor {
    List<RecordPair> recordPairs = new ArrayList<>();

    long maxExecutionTimeOnThread = 0L;

    void execute(List<Record> recordList, Double similarityThreshold);

    default HashSet<RecordPair> getRecordPairsToSet() {
        return new HashSet<>(recordPairs);
    }

    default long getMaxExecutionTimeOnThread() {
        return maxExecutionTimeOnThread;
    }
}
