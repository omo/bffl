package es.flakiness.bffl;

import android.view.View;

import org.ocpsoft.prettytime.Duration;
import org.ocpsoft.prettytime.PrettyTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.security.auth.Subject;

import rx.Observable;
import rx.android.operators.OperatorConditionalBinding;
import rx.functions.Func1;
import rx.subjects.PublishSubject;
import rx.subjects.ReplaySubject;

public class BuildPreso implements View.OnAttachStateChangeListener {

    static private SimpleDateFormat sFormatter = new SimpleDateFormat("yyyy/MM/dd-hh:mm:ss");

    static public Date parseStubDate(String string) {
        try {
            return sFormatter.parse(string);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private AtomicPredicate mValid = new AtomicPredicate(true);
    private Build mModel;
    private PictureStore mPictureStore;

    public static BuildPreso getMockFailInstance(PictureStore store) {
        return new BuildPreso(
                Build.create(
                        Long.valueOf(BuildStatus.FAILED.sequence()), "ninja -j 500 -C ./out/Debug chrome",
                        parseStubDate("2014/11/01-21:00:01"), parseStubDate("2014/11/01-21:15:11")), store);
    }

    public static BuildPreso getMockPassInstance(PictureStore store) {
        return new BuildPreso(
                Build.create(
                        Long.valueOf(BuildStatus.PASSED.sequence()), "ninja -j 500 -C ./out/Debug blink_tests",
                        parseStubDate("2014/11/01-09:00:01"), parseStubDate("2014/11/01-09:15:11")), store);
    }

    public BuildPreso(Build model, PictureStore pictureStore) {
        mModel = model;
        mPictureStore = pictureStore;
    }

    @Override
    public void onViewAttachedToWindow(View view) {
    }

    @Override
    public void onViewDetachedFromWindow(View view) {
        mValid.set(false);
    }

    public BuildStatus getStatus() {
        return BuildStatus.valueOf(mModel.status.intValue());
    }

    public Observable<String> getImageURL() {
        return mPictureStore.find(mModel.status, mModel.finishedAt.hashCode()).lift(new OperatorConditionalBinding(this, mValid)).map(new Func1<Picture, String>() {
            @Override
            public String call(Picture picture) {
                return picture.mediumImage;
            }
        });
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
