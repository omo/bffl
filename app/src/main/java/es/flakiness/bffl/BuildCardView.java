package es.flakiness.bffl;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;

// TODO: set default background color
public class BuildCardView extends CardView {

    @InjectView(R.id.build_card_image)
    ImageView mImage;

    public BuildCardView(Context context) {
        super(context);
    }

    public BuildCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BuildCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        ButterKnife.inject(this);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Picasso picasso = Picasso.with(getContext());
        picasso.setLoggingEnabled(true);
        picasso.load("https://farm8.staticflickr.com/7301/9935571905_674272afd3_b.jpg").resize(getWidth(), getHeight()).centerCrop().into(mImage);
    }
}
