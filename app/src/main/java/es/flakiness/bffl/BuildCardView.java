package es.flakiness.bffl;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;

// TODO: set default background color
public class BuildCardView extends CardView {

    @InjectView(R.id.build_card_image) ImageView mImage;
    @InjectView(R.id.build_card_headline) TextView mHeadline;
    @InjectView(R.id.build_card_timestamp) TextView mTimestamp;
    @InjectView(R.id.build_card_report) TextView mReport;

    BuildCardPreso mPreso = BuildCardPreso.getUncertainInstance();

    public BuildCardView(Context context) {
        super(context);
    }

    public BuildCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BuildCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setPreso(BuildCardPreso preso) {
        // TODO(morrita): Ellipsize: http://stackoverflow.com/questions/4700650/how-do-i-set-the-android-text-view-to-cut-any-letters-that-dont-fit-in-a-layout
        mPreso = preso;
        mHeadline.setText(mPreso.getStatusText());
        mReport.setText(String.format("%s", mPreso.getReport()));
        mTimestamp.setText(String.format("%s ago, took %s", mPreso.getAgeText(), mPreso.getDurationText()));
        // TODO(morrita): Update styles based on the status.
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.inject(this);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Picasso.with(getContext()).load(mPreso.getImageURL()).resize(getWidth(), getHeight()).centerCrop().into(mImage);
    }
}
