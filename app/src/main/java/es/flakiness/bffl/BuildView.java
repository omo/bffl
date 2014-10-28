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

// TODO: set default background color
public class BuildView extends CardView {

    @InjectView(R.id.build_card_image) ImageView mImage;
    @InjectView(R.id.build_card_texts) LinearLayout mTexts;
    @InjectView(R.id.build_card_headline) TextView mHeadline;
    @InjectView(R.id.build_card_timestamp) TextView mTimestamp;
    @InjectView(R.id.build_card_report) TextView mReport;
    @Inject Picasso mPicasso;

    BuildPreso mPreso = BuildPreso.getUncertainInstance();

    public BuildView(Context context) {
        super(context);
    }

    public BuildView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BuildView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setPreso(BuildPreso preso) {
        // TODO(morrita): Ellipsize: http://stackoverflow.com/questions/4700650/how-do-i-set-the-android-text-view-to-cut-any-letters-that-dont-fit-in-a-layout
        mPreso = preso;
        mTexts.setBackgroundColor(Color.argb(96, 0, 0, 0));
        mHeadline.setText(mPreso.getStatusText());
        mReport.setText(String.format("%s", mPreso.getReport()));
        mTimestamp.setText(String.format("%s ago, took %s", mPreso.getAgeText(), mPreso.getDurationText()));
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
        mPicasso.load(mPreso.getImageURL()).resize(getWidth(), getHeight()).centerCrop().into(mImage);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mPicasso.cancelRequest(mImage);
    }
}
