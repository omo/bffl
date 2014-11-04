package es.flakiness.bffl;

import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

import javax.inject.Inject;

import rx.subjects.PublishSubject;

public class BuildListPreso implements ListAdapter {

    private DataSetObservable mObservable = new DataSetObservable();
    private PictureStore mPictureStore;

    @Inject
    public BuildListPreso(PictureStore pictureStore) {
        mPictureStore = pictureStore;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override
    public boolean isEnabled(int i) {
        return true;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver dataSetObserver) {
        mObservable.registerObserver(dataSetObserver);
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {
        mObservable.unregisterObserver(dataSetObserver);
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int i) {
        // TODO(morrita): implement
        return null;
    }

    @Override
    public long getItemId(int i) {
        // TODO(morrita): implement
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    private BuildView createView(ViewGroup parent) {
        return (BuildView)LayoutInflater.from(parent.getContext()).inflate(R.layout.card_build, parent, false);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        BuildView theView = null != view ? (BuildView)view : createView(viewGroup);
        theView.present(i % 2 == 0 ? BuildPreso.getMockFailInstance(mPictureStore) : BuildPreso.getMockPassInstance(mPictureStore));
        return theView;
    }

    @Override
    public int getItemViewType(int i) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
