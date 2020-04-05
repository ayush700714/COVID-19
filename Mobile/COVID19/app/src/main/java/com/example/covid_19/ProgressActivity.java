package com.example.covid_19;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ProgressActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
        Intent intent = getIntent();
        String prob = intent.getStringExtra("prob");
        String fact = intent.getStringExtra("factor");
        TextView t1 = (TextView)findViewById(R.id.t1);
        TextView t2 = (TextView)findViewById(R.id.t2);
        t1.setText(prob);
        t2.setText(fact);
    }
}
