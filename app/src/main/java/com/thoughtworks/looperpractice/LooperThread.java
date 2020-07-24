package com.thoughtworks.looperpractice;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

public class LooperThread extends Thread {
    public Handler myHandler;

    @Override
    public void run() {
        Looper looper = Looper.myLooper();
        if (looper == null) {
            Looper.prepare();
            looper = Looper.myLooper();
        }
        if (looper == null) {
            throw new RuntimeException("Current Thread create looper failure");
        } else {
            myHandler = new Handler(looper) {
                public void handleMessage(@NonNull Message msg) {
                    if (msg.what == HandlerActivity.MESSAGE_B || msg.what == HandlerActivity.MESSAGE_A) {
                        post((Runnable)msg.obj);
                    }
                }
            };
            Looper.loop();
        }
    }
}