package com.dorukkangal.analytics;

import android.content.Context;
import android.support.annotation.StringRes;
import android.support.annotation.XmlRes;
import android.util.Log;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

/**
 * @author Doruk Kangal
 * @since 1.0.0
 */
public final class AnalyticsManager {
    private static final String TAG = "An-alytics";

    private static AnalyticsManager instance;

    private Context context = null;
    private Tracker tracker;
    private int configResId;
    private boolean debugMode = false;

    public static AnalyticsManager getInstance() {
        if (instance == null) {
            instance = new AnalyticsManager();
        }
        return instance;
    }

    private AnalyticsManager() {
    }

    public void init(Context context, @XmlRes int configResId) {
        init(context, configResId, true);
    }

    public void init(Context context, @XmlRes int configResId, boolean debugMode) {
        this.context = context;
        this.configResId = configResId;
        this.debugMode = debugMode;
    }

    public void setDebugMode(boolean debugMode) {
        this.debugMode = debugMode;
    }

    public void trackEvent(Event event) {
        trackEvent(event.getCategory(), event.getAction(), event.getLabel(), event.getValue());
    }

    public void trackEvent(String category, String action, String label, long value) {
        trackEvent(getStringResId(category), getStringResId(action), getStringResId(label), value);
    }

    public void trackEvent(@StringRes int category, @StringRes int action, @StringRes int label, long value) {

        Tracker tracker = getTracker();
        tracker.send(new HitBuilders.EventBuilder()
                .setCategory(context.getString(category))
                .setAction(context.getString(action))
                .setLabel(context.getString(label))
                .setValue(value)
                .build());

        if (debugMode) {
            Log.i(TAG, String.format("trackEvent(%s, %s, %s, %s)", category, action, label, value));
        }
    }

    public void trackScreen(String screenName) {
        trackScreen(getStringResId(screenName));
    }

    public void trackScreen(@StringRes int screenName) {
        Tracker tracker = getTracker();
        tracker.setScreenName(context.getString(screenName));
        tracker.send(new HitBuilders.ScreenViewBuilder().build());

        if (debugMode) {
            Log.i(TAG, String.format("trackScreen(%s)", context.getString(screenName)));
        }
    }

    private Tracker getTracker() {
        if (context == null) {
            throw new IllegalStateException("An-alytics is not initialized. Call 'init()' before.");
        }

        if (tracker == null) {
            tracker = GoogleAnalytics.getInstance(context).newTracker(configResId);
        }
        return tracker;
    }

    @StringRes
    private int getStringResId(String resourceName) {
        return context.getResources()
                .getIdentifier(resourceName, "string", context.getApplicationContext().getPackageName());
    }
}
