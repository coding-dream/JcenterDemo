package org.live.test.util;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by deeper on 2018/5/6.
 */
public class RxUtils {

    private static ObservableTransformer sSchedulerTransformer = new ObservableTransformer() {
        @Override
        public ObservableSource apply(@NonNull Observable upstream) {
            return upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        }
    };

    public static <T> ObservableTransformer<T, T> getSchedulerTransformer() {
        return sSchedulerTransformer;
    }

    public static abstract class SimpleObserver<T> implements Observer<T> {

        private static final String TAG = SimpleObserver.class.getSimpleName();

        @Override
        public void onSubscribe(@NonNull Disposable d) {}

        @Override
        public void onError(@NonNull Throwable e) {}

        @Override
        public void onComplete() {}
    }
}