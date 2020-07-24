package com.thoughtworks.looperpractice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.handler_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createHandlerActivity();
            }
        });
    }

    public void createHandlerActivity() {
        Intent intent = new Intent(this, HandlerActivity.class);
        startActivity(intent);
    }
}