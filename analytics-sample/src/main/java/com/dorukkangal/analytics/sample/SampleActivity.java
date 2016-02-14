package com.dorukkangal.analytics.sample;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.dorukkangal.analytics.annotation.TrackEvent;
import com.dorukkangal.analytics.annotation.TrackScreen;

public class SampleActivity extends AppCompatActivity implements View.OnClickListener {

    @TrackScreen(name = R.string.analytics_screen)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        findViewById(R.id.button).setOnClickListener(this);
    }

    @TrackEvent(category = R.string.analytics_category, action = R.string.analytics_action, label = R.string.analytics_label)
    @Override
    public void onClick(View v) {
        new AlertDialog.Builder(this)
                .setTitle("Analytics Info")
                .setMessage("Category: " + getString(R.string.analytics_category) +
                        "\nAction: " + getString(R.string.analytics_action) +
                        "\nLabel: " + getString(R.string.analytics_label)
                )
                .show();
    }
}
