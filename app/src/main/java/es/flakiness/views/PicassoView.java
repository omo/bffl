package es.flakiness.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class PicassoView extends ImageView {

    public interface ContextRequirement {
        Picasso getPicasso();
    }

    public void setImagePath(String imagePath) {
        if (imagePath.equals(mImagePath))
            return;
        mImagePath = imagePath;
        loadImageIfNeeded();
    }

    public String getImagePath() {
        return mImagePath;
    }

    private String mImagePath;
    private Picasso mActiveLoader;

    public PicassoView(Context context) {
        super(context);
    }

    public PicassoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PicassoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PicassoView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        loadImageIfNeeded();
    }

    @Override
    protected void onDetachedFromWindow() {
        cancelLoading();
        super.onDetachedFromWindow();
    }

    private boolean shouldLoadImage() {
        // No URL is given
        if (null == mImagePath)
            return false;
        // No real estate to show the image
        if (getWidth() <= 0|| getHeight() <= 0)
            return false;
        return true;
    }

    private void loadImageIfNeeded() {
        if (!shouldLoadImage())
            return;
        if (null != mActiveLoader)
            cancelLoading();
        // TODO(morrita): Should provide fallback default.
        Picasso loader = ((ContextRequirement)getContext()).getPicasso();
        // TODO(morrita): This should be configurable
        loader.load(mImagePath).resize(getWidth(), getHeight()).centerCrop().into(this);
        mActiveLoader = loader;
    }

    private void cancelLoading() {
        if (null == mActiveLoader)
            return;
        mActiveLoader.cancelRequest(this);
        mActiveLoader = null;
    }
}
