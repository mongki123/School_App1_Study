package com.example.minigames;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button1).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        Intent intent = null;
        switch (view.getId()) {

            case R.id.button1:
                intent = new Intent(MainActivity.this, Game1.class);
                break;

            case R.id.button2:
                intent = new Intent(MainActivity.this, LottoActivity.class);
                break;

        }

        startActivity(intent);
    }
}