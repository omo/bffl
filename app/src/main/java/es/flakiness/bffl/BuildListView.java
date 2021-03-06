package es.flakiness.bffl;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

import javax.inject.Inject;

public class BuildListView extends ListView {

    @Inject
    BuildListPreso mAdapter;

    public BuildListView(Context context) {
        super(context);
    }

    public BuildListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BuildListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        App.get(getContext()).inject(this);
        setAdapter(mAdapter);
    }
}
