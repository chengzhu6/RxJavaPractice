package com.thoughtworks.looperpractice;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class HandlerActivity extends AppCompatActivity {
    public static final int MESSAGE_A = 1;
    public static final int MESSAGE_B = 2;
    private LooperThread looperThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler);

        looperThread = new LooperThread();
        looperThread.start();

        findViewById(R.id.send_message_a).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage(MESSAGE_A);
            }
        });

        findViewById(R.id.send_message_b).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage(MESSAGE_B);
            }
        });
    }

    class ShowMessageType implements Runnable {
        private int messageType;

        public ShowMessageType(int messageType) {
            this.messageType = messageType;
        }

        @Override
        public void run() {
            Toast.makeText(getApplicationContext(), String.valueOf(messageType), Toast.LENGTH_SHORT).show();
        }
    }

    private void sendMessage(int messageType) {
        Message message = looperThread.myHandler.obtainMessage(messageType, new ShowMessageType(messageType));
        looperThread.myHandler.sendMessage(message);
    }
}