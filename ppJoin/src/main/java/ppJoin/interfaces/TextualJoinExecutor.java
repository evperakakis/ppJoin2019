package ppJoin.interfaces;

import ppJoin.pojos.Record;

import java.util.List;

public interface TextualJoinExecutor {
    void execute(List<Record> recordList, Double similarityThreshold);
}
