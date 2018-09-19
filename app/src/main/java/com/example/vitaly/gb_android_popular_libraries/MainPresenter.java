package com.example.vitaly.gb_android_popular_libraries;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {

    private static final int FIRST = 0;
    private static final int SECOND = 1;
    private static final int THIRD = 2;

    private CounterModel model;
    private SchedulersProvider schedulers;
    private Observable<Integer> firstObservable, secondObservable, thirdObservable;
    private Observer<Integer> firstObserver, secondObserver, thirdObserver;
    private Disposable firstDisposable, secondDisposable, thirdDisposable;

    MainPresenter(SchedulersProvider schedulers) {
        this.schedulers = schedulers;
        this.model = new CounterModel();
    }

    public void counterClick(int id) {
        switch (id) {
            case R.id.btn_one:
                if (firstObservable == null) {
                    firstObservable = getObservable(FIRST);
                }
                if (firstObserver == null) {
                    firstObserver = getFirstObserver(id);
                }
                firstObservable.subscribe(firstObserver);
                break;
            case R.id.btn_two:
                if (secondObservable == null) {
                    secondObservable = getObservable(SECOND);
                }
                if (secondObserver == null) {
                    secondObserver = getSecondObserver(id);
                }
                secondObservable.subscribe(secondObserver);
                break;
            case R.id.btn_three:
                if (thirdObservable == null) {
                    thirdObservable = getObservable(THIRD);
                }
                if (thirdObserver == null) {
                    thirdObserver = getThirdObserver(id);
                }
                thirdObservable.subscribe(thirdObserver);
                break;
        }
    }

    private Observable<Integer> getObservable(int index) {
        switch (index) {
            case 0:
                return firstObservable = model.getCounter(index)
                        .subscribeOn(schedulers.io())
                        .observeOn(schedulers.computation())
                        .map(integer -> model.calculate(index))
                        .observeOn(schedulers.ui());
            case 1:
                return secondObservable = model.getCounter(index)
                        .subscribeOn(schedulers.io())
                        .observeOn(schedulers.computation())
                        .map(integer -> model.calculate(index))
                        .observeOn(schedulers.ui());
            case 2:
                thirdObservable = model.getCounter(index)
                        .subscribeOn(schedulers.io())
                        .observeOn(schedulers.computation())
                        .map(integer -> model.calculate(index))
                        .observeOn(schedulers.ui());
            default:
                return thirdObservable;
        }
    }

    private Observer<Integer> getFirstObserver(final int id) {
        return firstObserver = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                firstDisposable = d;
            }

            @Override
            public void onNext(Integer integer) {
                getViewState().setButtonText(id, integer);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
    }

    private Observer<Integer> getSecondObserver(final int id) {
        return secondObserver = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                secondDisposable = d;
            }

            @Override
            public void onNext(Integer integer) {
                getViewState().setButtonText(id, integer);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
    }

    private Observer<Integer> getThirdObserver(final int id) {
        return thirdObserver = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                thirdDisposable = d;
            }

            @Override
            public void onNext(Integer integer) {
                getViewState().setButtonText(id, integer);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
    }

    @Override
    public void detachView(MainView view) {
        if (firstDisposable != null) {
            firstDisposable.dispose();
        }
        if (secondDisposable != null) {
            secondDisposable.dispose();
        }
        if (thirdDisposable != null) {
            thirdDisposable.dispose();
        }
        super.detachView(view);
    }
}
