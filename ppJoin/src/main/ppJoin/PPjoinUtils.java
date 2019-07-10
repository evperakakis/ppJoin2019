package ppJoin;

import ppJoin.pojos.Record;

import static oracle.jrockit.jfr.events.Bits.intValue;

public class PPjoinUtils {

    public static Integer calculate_prefixLength(Record record, Double similarityThreshold) {
        Integer recordSize = record.getNGramsList().size();
        return  Math.min(recordSize, intValue(recordSize - Math.ceil(similarityThreshold * recordSize) + 1));
    }

    public static Integer calculate_prefixLength_TEST(Integer recordSize, Double similarityThreshold) {
        return  Math.min(recordSize, intValue(recordSize - Math.ceil(similarityThreshold * recordSize) + 1));
    }

    public static Integer calculate_a(Double similarityThreshold, Integer recordSizeX, Integer recordSizeY) {
        return intValue(Math.ceil((similarityThreshold * (recordSizeX + recordSizeY)) /
                (1 + similarityThreshold)));
    }

    public static Integer calculate_ubound(Integer recordSizeX, Integer recordSizeY,
                                           Integer positionI, Integer positionJ) {
        return 1 + Math.min(recordSizeX - positionI, recordSizeY - positionJ);
    }

}
