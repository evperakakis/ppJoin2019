package ppJoin.enums;

public enum Settings {
    RECORD_FILE_PATH("C:\\Users\\Evaggelos\\Desktop\\Projects\\personal\\iliko_ptixiakis\\ppJoin\\src\\main\\resources\\records.csv"),

    SECONDARY_TEST_RECORD_FILE_PATH("C:\\Users\\Evaggelos\\Desktop\\Projects\\personal\\iliko_ptixiakis\\ppJoin\\src\\main\\resources\\records2.csv"),

    THIRD_TEST_RECORD_FILE_PATH("C:\\Users\\Evaggelos\\Desktop\\Projects\\personal\\iliko_ptixiakis\\ppJoin\\src\\main\\resources\\records3.csv");

    Settings(String value) {
        this.value = value;
    }

    private String value;

    public String getValue() {
        return value;
    }
}
