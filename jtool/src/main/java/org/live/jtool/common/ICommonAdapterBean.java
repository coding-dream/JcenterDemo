package org.live.jtool.common;

/**
 * Created by lk on 2017/9/20.
 */

public interface ICommonAdapterBean {

    class ViewType {
        final static int VIEW_TYPE_ERROR = 100001;
    }

    int getViewType();
}
