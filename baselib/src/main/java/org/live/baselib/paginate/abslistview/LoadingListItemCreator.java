package org.live.baselib.paginate.abslistview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.live.baselib.R;

/** AbsListView creator that will be called to create and bind loading list item */
public interface LoadingListItemCreator {

    /**
     * Create/inflate new loading list item view.
     *
     * @param position loading list item position.
     * @param parent   parent ViewGroup.
     * @return view that will be displayed as loading list item (loading indicator).
     */
    View newView(int position, ViewGroup parent);

    /**
     * Bind the loading list item.
     *
     * @param position loading list item position.
     * @param view     loading list item view.
     */
    void bindView(int position, View view);

    LoadingListItemCreator DEFAULT = new LoadingListItemCreator() {
        @Override
        public View newView(int position, ViewGroup parent) {
            return LayoutInflater.from(parent.getContext()).inflate(R.layout.nn_common_foot_loading, parent, false);
        }

        @Override
        public void bindView(int position, View view) {
            // No binding for default loading row
        }
    };

}