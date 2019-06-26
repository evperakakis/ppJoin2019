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

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof RecordPair)) {
            return false;
        }
        final RecordPair recordPair = (RecordPair) obj;

        return this.record1.getTextValue().equalsIgnoreCase(recordPair.record1.getTextValue())
                && this.record2.getTextValue().equalsIgnoreCase(recordPair.record2.getTextValue())
                && this.record1.getAxisValueX() == recordPair.record1.getAxisValueX()
                && this.record2.getAxisValueX() == recordPair.record2.getAxisValueX()
                && this.record1.getAxisValueY() == recordPair.record1.getAxisValueY()
                && this.record2.getAxisValueY() == recordPair.record2.getAxisValueY();
    }

    @Override
    public int hashCode()
    {
        return (int) (this.getRecord1().getAxisValueX()
                + this.getRecord2().getAxisValueX()
                +this.getRecord1().getAxisValueY()
                +this.getRecord2().getAxisValueY());
    }
}
