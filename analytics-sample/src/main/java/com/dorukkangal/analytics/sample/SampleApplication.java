package com.dorukkangal.analytics.sample;

import android.app.Application;

import com.dorukkangal.analytics.AnalyticsManager;

/**
 * @author Doruk Kangal
 */
public class SampleApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        AnalyticsManager.getInstance().init(this, R.xml.analytics_config);
    }
}
