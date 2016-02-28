package com.dorukkangal.analytics.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.dorukkangal.analytics.annotation.TrackScreen;
import com.dorukkangal.analytics.sample.library.SampleLibraryActivity;

public class SampleActivity extends AppCompatActivity implements View.OnClickListener {

    @TrackScreen(name = R.string.analytics_screen)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        findViewById(R.id.button_click_me).setOnClickListener(this);
        findViewById(R.id.button_next).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, SampleLibraryActivity.class);
        startActivity(intent);
    }
}
