package ppJoin.pojos;

import java.util.List;

public class Record {

    private String textValue;
    private double axisValueX;
    private double axisValueY;
    private List<String> nGramsList;

    public Record(String textValue, double axisValueX, double axisValueY, List<String> nGramsList) {
        this.textValue = textValue;
        this.axisValueX = axisValueX;
        this.axisValueY = axisValueY;
        this.nGramsList = nGramsList;
    }

    public Record() {}

    public String getTextValue() {
        return textValue;
    }

    public void setTextValue(String textValue) {
        this.textValue = textValue;
    }

    public double getAxisValueX() {
        return axisValueX;
    }

    public void setAxisValueX(double axisValueX) {
        axisValueX = axisValueX;
    }

    public double getAxisValueY() {
        return axisValueY;
    }

    public void setAxisValueY(double axisValueY) {
        axisValueY = axisValueY;
    }

    public List<String> getNGramsList() {
        return nGramsList;
    }

    public void setNGramsList(List<String> nGramsList) {
        this.nGramsList = nGramsList;
    }

    @Override
    public String toString() {
        return "Record{" +
                "textValue='" + textValue + '\'' +
                ", axisValueX=" + axisValueX +
                ", axisValueY=" + axisValueY +
                ", nGramsList=" + nGramsList +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Record)) {
            return false;
        }
        final Record record = (Record) obj;

        return this.getAxisValueX() == record.getAxisValueX()
                && this.getAxisValueY() == record.getAxisValueY()
                && this.getTextValue().equalsIgnoreCase(record.getTextValue());
    }

    @Override
    public int hashCode() {
        return (int) (19*this.getAxisValueX()
                +this.getAxisValueY()
                +this.getTextValue().length()
                +(this.getAxisValueY()*this.getAxisValueX()));
    }
}
