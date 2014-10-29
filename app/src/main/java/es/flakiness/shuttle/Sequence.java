package es.flakiness.shuttle;

public class Sequence {
    private String mName = "";

    public Sequence() {
    }

    public Sequence(String name) {
        mName = name;
    }

    public String getName() {
        return mName;
    }

    @Override
    public String toString() {
        return "Sequence(" + mName + ")";
    }
}
