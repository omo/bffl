package es.flakiness.bffl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BuildPreso {

    static private SimpleDateFormat sFormatter = new SimpleDateFormat("yyyy/MM/dd-hh:mm:ss");
    static private Date parseDate(String string) {
        try {
            return sFormatter.parse(string);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private Build mModel;

    public static BuildPreso getMockFailInstance() {
        return new BuildPreso(
                Build.create(
                        Long.valueOf(BuildStatus.FAILED.sequence()), "ninja -j 500 -C ./out/Debug chrome",
                        parseDate("2014/11/01-21:00:01"), parseDate("2014/11/01-21:15:11")));
    }

    public static BuildPreso getMockPassInstance() {
        return new BuildPreso(
                Build.create(
                        Long.valueOf(BuildStatus.PASSED.sequence()), "ninja -j 500 -C ./out/Debug blink_tests",
                        parseDate("2014/11/01-09:00:01"), parseDate("2014/11/01-09:15:11")));
    }

    public BuildPreso(Build model) {
        mModel = model;
    }

    public BuildStatus getStatus() {
        return BuildStatus.valueOf(mModel.status.intValue());
    }

    public boolean hasImageURL() {
        return getImageURL() != null;
    }

    public String getImageURL() {
        // TODO: impl
        return "https://farm3.staticflickr.com/2943/15158953820_54028b62e9_c.jpg";
    }

    public String getStatusText() {
        switch (getStatus()) {
            case FAILED:
                return "Failed...";
            case PASSED:
                return "Passed!";
            default:
                return "...";
        }
    }

    public String getStatusEmoji() {
        switch (getStatus()) {
            case FAILED:
                return "\uD83D\uDC4E";
            case PASSED:
                return "\uD83D\uDC4D";
            default:
                return "";
        }
    }

    public String getReport() {
        return mModel.report;
    }

    // XXX: impl
    public String getDurationText() {
        return "12 minutes";
    }

    // XXX: impl
    public String getAgeText() {
        return "1 minute";
    }

    public String getTimestampText() {
        return String.format("%s ago, took %s", getAgeText(), getDurationText());
    }
}
