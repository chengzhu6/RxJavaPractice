package com.thoughtworks.looperpractice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.Objects;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class RxActivity extends AppCompatActivity {

    private Disposable disposable;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx);
        findViewById(R.id.start_task).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update((Button)view);
            }
        });
    }

    private void update(final Button button) {
        button.setClickable(false);
        final String baseText = "The number is";
        disposable = Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) {
                for (int i = 0; i < 10; i++) {
                    emitter.onNext(i);
                    SystemClock.sleep(1000);
                }
                emitter.onComplete();
            }
        }, BackpressureStrategy.ERROR)
                .map(new Function<Integer, String>() {
                    @Override
                    public String apply(Integer integer) {
                        return baseText + integer;
                    }
                }).subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) {
                        button.setText(s);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Log.e("RxActivity", Objects.requireNonNull(throwable.getMessage()));
                    }
                }, new Action() {
                    @Override
                    public void run() {
                        button.setClickable(true);
                    }
                });
    }
}