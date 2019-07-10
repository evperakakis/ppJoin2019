package ppJoin.enums;

public enum Settings {
    RECORD_FILE_PATH("C:\\Users\\Evaggelos\\Desktop\\Projects\\personal\\iliko_ptixiakis\\ppJoin\\src\\main\\resources\\records.csv"),

    SECONDARY_TEST_RECORD_FILE_PATH("C:\\Users\\Evaggelos\\Desktop\\Projects\\personal\\iliko_ptixiakis\\ppJoin\\src\\main\\resources\\records2.csv"),

    THIRD_TEST_RECORD_FILE_PATH("C:\\Users\\Evaggelos\\Desktop\\Projects\\personal\\iliko_ptixiakis\\ppJoin\\src\\main\\resources\\records3.csv"),

    RECORD_1000_FILE_PATH("C:\\Users\\Evaggelos\\Desktop\\Projects\\personal\\iliko_ptixiakis\\ppJoin\\src\\main\\resources\\Output1000.csv"),

    RECORD_10000_FILE_PATH("C:\\Users\\Evaggelos\\Desktop\\Projects\\personal\\iliko_ptixiakis\\ppJoin\\src\\main\\resources\\Output10000.csv"),

    RECORD_100000_FILE_PATH("C:\\Users\\Evaggelos\\Desktop\\Projects\\personal\\iliko_ptixiakis\\ppJoin\\src\\main\\resources\\Output100000.csv"),

    RECORD_1000000_FILE_PATH("C:\\Users\\Evaggelos\\Desktop\\Projects\\personal\\iliko_ptixiakis\\ppJoin\\src\\main\\resources\\Output1000000.csv"),

    TEMP_RECORDS_PATH("C:\\Users\\Evaggelos\\Desktop\\Projects\\personal\\iliko_ptixiakis\\ppJoin\\src\\main\\resources\\temp\\");

    Settings(String value) {
        this.value = value;
    }

    private String value;

    public String getValue() {
        return value;
    }
}
