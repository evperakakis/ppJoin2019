package ppJoin;

import ppJoin.pojos.Record;

import static oracle.jrockit.jfr.events.Bits.intValue;

public class PPjoinUtils {

    public static int calculate_prefixLength(Record record, Double similarityThreshold) {
        int recordSize = record.getNGramsList().size();
        return  Math.min(recordSize, intValue(recordSize - Math.ceil(similarityThreshold * recordSize) + 1));
    }

    public static int calculate_prefixLength_TEST(int recordSize, Double similarityThreshold) {
        return  Math.min(recordSize, intValue(recordSize - Math.ceil(similarityThreshold * recordSize) + 1));
    }

    public static int calculate_a(Double similarityThreshold, int recordSizeX, int recordSizeY) {
        return intValue(Math.ceil((similarityThreshold * (recordSizeX + recordSizeY)) /
                (1 + similarityThreshold)));
    }

    public static int calculate_ubound(int recordSizeX, int recordSizeY,
                                           int positionI, int positionJ) {
        return 1 + Math.min(recordSizeX - positionI, recordSizeY - positionJ);
    }

}
