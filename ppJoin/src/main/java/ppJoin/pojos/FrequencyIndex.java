package ppJoin.pojos;

import java.util.TreeMap;

// Singleton
public final class FrequencyIndex {
    private static FrequencyIndex FREQUENCY_INDEX;
    private TreeMap<String, Integer> index = new TreeMap<>();

    private FrequencyIndex() {}

    public static FrequencyIndex getInstance() {
        if(FREQUENCY_INDEX == null) {
            FREQUENCY_INDEX = new FrequencyIndex();
        }
        return FREQUENCY_INDEX;
    }

    public TreeMap<String, Integer> getIndex() {
        return index;
    }

    public void addToFrequencyIndex(String key) {
        if (getInstance().getIndex().get(key) != null) {
            getInstance().getIndex().put(key, getInstance().getIndex().get(key) + 1);
        } else {
            getInstance().getIndex().put(key, 1);
        }
    }

}
