package es.flakiness.bffl;

import org.ocpsoft.prettytime.Duration;
import org.ocpsoft.prettytime.PrettyTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BuildPreso {

    static private SimpleDateFormat sFormatter = new SimpleDateFormat("yyyy/MM/dd-hh:mm:ss");

    static public Date parseStubDate(String string) {
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
                        parseStubDate("2014/11/01-21:00:01"), parseStubDate("2014/11/01-21:15:11")));
    }

    public static BuildPreso getMockPassInstance() {
        return new BuildPreso(
                Build.create(
                        Long.valueOf(BuildStatus.PASSED.sequence()), "ninja -j 500 -C ./out/Debug blink_tests",
                        parseStubDate("2014/11/01-09:00:01"), parseStubDate("2014/11/01-09:15:11")));
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

    public String getTimestampText() {
        PrettyTime finishTime = new PrettyTime(mModel.finishedAt);
        Duration d = finishTime.approximateDuration(mModel.startedAt);
        PrettyTime currentTime = new PrettyTime(now());
        return String.format("%s, took %s", currentTime.format(mModel.finishedAt), finishTime.formatDuration(d));
    }

    // A testability hook
    public Date now() {
        return new Date();
    }
}
