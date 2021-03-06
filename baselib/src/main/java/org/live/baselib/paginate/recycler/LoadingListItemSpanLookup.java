package org.live.baselib.paginate.recycler;

/** SpanSizeLookup that will be used to determine the span of loading list item. */
public interface LoadingListItemSpanLookup {

    /** @return the span of loading list item. */
    int getSpanSize();
}