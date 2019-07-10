package ppJoin.pojos;

public class Occurrence {
    private Integer recordId;
    private Integer position;

    public Occurrence() {}

    public Occurrence(Integer recordId, Integer position) {
        this.recordId = recordId;
        this.position = position;
    }

    public Integer getRecordId() {
        return recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
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
