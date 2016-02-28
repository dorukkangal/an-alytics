package com.dorukkangal.analytics.sample.library;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.dorukkangal.analytics.annotation.library.TrackEvent;

public class SampleLibraryActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_library);

        findViewById(R.id.button).setOnClickListener(this);
    }

    @TrackEvent(category = B.string.analytics_category, action = B.string.analytics_action, label = B.string.analytics_label)
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
