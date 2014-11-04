package es.flakiness.bffl;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
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

    private BuildPreso mLastPreso;

    public BuildView(Context context) {
        this(context, null);
    }

    public BuildView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BuildView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.card_build, this, true);
        ButterKnife.inject(this);
        App.get(getContext()).inject(this);
    }

    public void present(BuildPreso preso) {
        mTexts.setBackgroundColor(Color.argb(96, 0, 0, 0));
        mHeadline.setText(preso.getStatusText());
        mReport.setText(String.format("%s", preso.getReport()));
        mTimestamp.setText(preso.getTimestampText());

        preso.getImageURL().subscribe(new Action1<String>() {
            @Override
            public void call(String path) {
               mImage.setImagePath(path);
            }
        });

        if (null != mLastPreso)
            removeOnAttachStateChangeListener(mLastPreso);
        addOnAttachStateChangeListener(preso);
        mLastPreso = preso;
    }
}
