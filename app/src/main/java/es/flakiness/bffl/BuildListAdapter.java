package es.flakiness.bffl;

import android.content.Context;
import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

import javax.inject.Inject;

import dagger.Provides;

public class BuildListAdapter implements ListAdapter {

    private DataSetObservable mObservable = new DataSetObservable();

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

    private BuildCardView createView(ViewGroup parent) {
        return (BuildCardView)LayoutInflater.from(parent.getContext()).inflate(R.layout.card_build, parent, false);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        BuildCardView theView = null != view ? (BuildCardView)view : createView(viewGroup);
        theView.setPreso(i % 2 == 0 ? BuildCardPreso.getMockFailInstance() : BuildCardPreso.getMockPassInstance());
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
