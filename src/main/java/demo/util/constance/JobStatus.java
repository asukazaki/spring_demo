package demo.util.constance;


public enum JobStatus {
	EROOR(-4),
	BRANK(-3),
	ABSENCE(-2),
	LATE(-1),
	NORMAL(0),
	HALF_DAY_OFF(1),
	ALL_DAY_OFF(2),
	OTHER(3);
	
	private final int code;

    private JobStatus(final int id) {
        this.code = id;
    }

    public int getCode() {
        return code;
    }
    
}
