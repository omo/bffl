package es.flakiness.bffl;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;

// TODO: set default background color
public class BuildView extends CardView {

    @InjectView(R.id.build_card_image) ImageView mImage;
    @InjectView(R.id.build_card_texts) LinearLayout mTexts;
    @InjectView(R.id.build_card_headline) TextView mHeadline;
    @InjectView(R.id.build_card_timestamp) TextView mTimestamp;
    @InjectView(R.id.build_card_report) TextView mReport;
    @Inject Picasso mPicasso;

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
                // TODO(morrita): Ellipsize: http://stackoverflow.com/questions/4700650/how-do-i-set-the-android-text-view-to-cut-any-letters-that-dont-fit-in-a-layout
                mTexts.setBackgroundColor(Color.argb(96, 0, 0, 0));
                mHeadline.setText(buildPreso.getStatusText());
                mReport.setText(String.format("%s", buildPreso.getReport()));
                mTimestamp.setText(String.format("%s ago, took %s", buildPreso.getAgeText(), buildPreso.getDurationText()));
                // TODO(morrita): Request image if layout is already made. Probably PicassoImageView is needed.
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
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        // This have to be here because we refer the view size.
        if (null != mLastPreso)
           mPicasso.load(mLastPreso.getImageURL()).resize(getWidth(), getHeight()).centerCrop().into(mImage);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mPicasso.cancelRequest(mImage);
        if (null != mSubscription) {
            mSubscription.unsubscribe();
            mSubscription = null;
        }
    }
}
