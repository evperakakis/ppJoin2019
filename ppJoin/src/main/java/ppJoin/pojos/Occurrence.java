package ppJoin.pojos;

public class Occurrence {
    private int recordId;
    private int position;

    public Occurrence() {}

    public Occurrence(int recordId, int position) {
        this.recordId = recordId;
        this.position = position;
    }

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "Occurrence{" +
                "recordId=" + recordId +
                ", position=" + position +
                '}';
    }
}
