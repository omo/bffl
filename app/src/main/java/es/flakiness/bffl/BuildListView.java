package es.flakiness.bffl;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ListView;

public class BuildListView extends ListView {
    public BuildListView(Context context) {
        super(context);
    }

    public BuildListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BuildListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
