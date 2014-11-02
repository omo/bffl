package es.flakiness.bffl;

public enum BuildStatus {
    PASSED(1), FAILED(2), UNCERTAIN(42);

    private int mSequence;

    public int sequence() { return mSequence; }

    BuildStatus(int sequence) {
        mSequence = sequence;
    }

    public static BuildStatus valueOf(int sequence) {
        for (BuildStatus v : values()) {
            if (v.sequence() == sequence)
                return v;
        }

        return null;
    }
}
