package es.flakiness.bffl;

public class BuildCardPreso {

    private BuildStatus mBuildStatus;
    private String mImageURL;
    private String mReport;

    private static BuildCardPreso sUncertain = new BuildCardPreso(BuildStatus.UNCERTAIN, "", "");

    public static BuildCardPreso getUncertainInstance() {
        return sUncertain;
    }

    public static BuildCardPreso getMockFailInstance() {
        return new BuildCardPreso(BuildStatus.FAILED, "https://farm8.staticflickr.com/7301/9935571905_674272afd3_b.jpg", "ninja -j 500 -C ./out/Debug chrome");
    }

    public static BuildCardPreso getMockPassInstance() {
        // https://www.flickr.com/photos/lens-cap/15158953820
        return new BuildCardPreso(BuildStatus.PASSED, "https://farm3.staticflickr.com/2943/15158953820_54028b62e9_c.jpg", "ninja -j 500 -C ./out/Debug chrome");
    }

    public BuildCardPreso(BuildStatus status, String imageURL, String report) {
        mBuildStatus = status;
        mImageURL = imageURL;
        mReport = report;
    }

    public BuildStatus getStatus() {
        return mBuildStatus;
    }

    public String getImageURL() {
        return mImageURL;
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
        return mReport;
    }

    public String getDurationText() {
        return "12 minutes";
    }

    public String getAgeText() {
        return "1 minute";
    }
}
