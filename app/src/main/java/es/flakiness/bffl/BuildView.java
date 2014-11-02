package es.flakiness.bffl;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import es.flakiness.views.PicassoView;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;

// TODO: set default background color
public class BuildView extends CardView {

    @InjectView(R.id.build_card_image) PicassoView mImage;
    @InjectView(R.id.build_card_texts) LinearLayout mTexts;
    @InjectView(R.id.build_card_headline) TextView mHeadline;
    @InjectView(R.id.build_card_timestamp) TextView mTimestamp;
    @InjectView(R.id.build_card_report) TextView mReport;

    private Subscription mSubscription;
    private BuildPreso mLastPreso;

    public BuildView(Context context) {
        super(context);
    }

    public BuildView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BuildView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void present(Observable<BuildPreso> preso) {
        assert mSubscription == null;
        mSubscription = preso.subscribe(new Action1<BuildPreso>() {
            @Override
            public void call(BuildPreso buildPreso) {
                // TODO(morrita): Apply diff only.
                // TODO(morrita): Ellipsize: http://stackoverflow.com/questions/4700650/how-do-i-set-the-android-text-view-to-cut-any-letters-that-dont-fit-in-a-layout
                mTexts.setBackgroundColor(Color.argb(96, 0, 0, 0));
                mHeadline.setText(buildPreso.getStatusText());
                mReport.setText(String.format("%s", buildPreso.getReport()));
                mTimestamp.setText(buildPreso.getTimestampText());
                if (buildPreso.hasImageURL())
                    mImage.setImagePath(buildPreso.getImageURL());
                mLastPreso = buildPreso;
            }
        });
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.inject(this);
        App.get(getContext()).inject(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (null != mSubscription) {
            mSubscription.unsubscribe();
            mSubscription = null;
        }
    }
}
