package ppJoin.pojos;

/*
*
* This is Iw from "Efficient Similarity Joins for Near Duplicate Detection" paper
*
* States that Token (w) appears at word (x) in position (i)
*
* */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Singleton
public final class InvertedIndex {
    private static InvertedIndex INVERTED_INDEX;
    private Map<String, List<Occurrence>> index = new HashMap<>();

    private InvertedIndex() {}

    public static InvertedIndex getInstance() {
        if(INVERTED_INDEX == null) {
            INVERTED_INDEX = new InvertedIndex();
        }
        return INVERTED_INDEX;
    }

    public Map<String, List<Occurrence>> getIndex() {
        return index;
    }

    public void addToInvertedIndex(String nGram, Integer recordId, Integer position) {
        if (getInstance().getIndex().get(nGram) != null) {
            getInstance().getIndex().get(nGram).add(new Occurrence(recordId, position));
        } else {
            List<Occurrence> occurrences = new ArrayList<>();
            occurrences.add(new Occurrence(recordId, position));
            getInstance().getIndex().put(nGram, occurrences);
        }
    }

}