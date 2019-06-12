package ppJoin.pojos;

public class RecordPair {

    private Record record1;

    private Record record2;

    public RecordPair(Record record1, Record record2) {
        this.record1 = record1;
        this.record2 = record2;
    }

    public Record getRecord1() {
        return record1;
    }

    public void setRecord1(Record record1) {
        this.record1 = record1;
    }

    public Record getRecord2() {
        return record2;
    }

    public void setRecord2(Record record2) {
        this.record2 = record2;
    }

    @Override
    public String toString() {
        return "RecordPair{" +
                "record1=" + record1 +
                ", record2=" + record2 +
                '}';
    }
}
