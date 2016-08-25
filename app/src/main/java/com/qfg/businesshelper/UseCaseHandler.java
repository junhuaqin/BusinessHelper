package com.qfg.businesshelper;

import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by rbtq on 8/25/16.
 */
public class UseCaseHandler {
    public static <T extends UseCase.RequestValues, R extends UseCase.ResponseValue>
        void execute(
                    final UseCase<T, R> useCase,
                    T values,
                    Scheduler scheduler,
                    Subscriber<R> subscriber
    ) {
        useCase.setRequestValues(values);
        useCase.execute()
                .subscribeOn(scheduler)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
