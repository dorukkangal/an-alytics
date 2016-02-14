package com.dorukkangal.analytics;

import android.support.annotation.StringRes;

/**
 * @author Doruk Kangal
 * @since 1.0.0
 */
public class Event {

    private int category;

    private int action;

    private int label;

    private long value;

    private Event() {
    }

    public Event(@StringRes int category, @StringRes int action, @StringRes int label, long value) {
        this.category = category;
        this.action = action;
        this.label = label;
        this.value = value;
    }

    @StringRes
    public int getCategory() {
        return category;
    }

    public void setCategory(@StringRes int category) {
        this.category = category;
    }

    @StringRes
    public int getAction() {
        return action;
    }

    public void setAction(@StringRes int action) {
        this.action = action;
    }

    @StringRes
    public int getLabel() {
        return label;
    }

    public void setLabel(@StringRes int label) {
        this.label = label;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public static class Builder {
        private final Event event = new Event();

        public Builder withCategory(@StringRes int category) {
            event.setCategory(category);
            return this;
        }

        public Builder withAction(@StringRes int action) {
            event.setAction(action);
            return this;
        }

        public Builder withLabel(@StringRes int label) {
            event.setLabel(label);
            return this;
        }

        public Builder withValue(long value) {
            event.setValue(value);
            return this;
        }

        public Event build() {
            return event;
        }
    }
}
