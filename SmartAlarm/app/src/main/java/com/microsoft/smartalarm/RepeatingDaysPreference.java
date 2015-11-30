package com.microsoft.smartalarm;

import android.content.Context;
import android.support.v14.preference.MultiSelectListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceViewHolder;
import android.util.AttributeSet;

import java.util.Set;

public class RepeatingDaysPreference extends MultiSelectListPreference {

    private boolean mDirty;
    private boolean[] mRepeatingDays;

    public RepeatingDaysPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        mRepeatingDays = new boolean[getEntryValues().length];
    }

    @Override
    public void onBindViewHolder(PreferenceViewHolder holder) {
        super.onBindViewHolder(holder);
        setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                @SuppressWarnings("unchecked")
                Set<String> repeatingDays = (Set<String>) o;
                setSummaryValues(repeatingDays);
                setDirty(true);
                return true;
            }
        });
    }

    public boolean isDirty() {
        return mDirty;
    }

    public void setDirty(boolean dirty) {
        mDirty = dirty;
    }

    public boolean[] getRepeatingDays() {
        CharSequence[] menuItems = getEntryValues();
        Set<String> checkedMenuItems = getValues();
        for (int i = 0; i < menuItems.length; i++) {
            mRepeatingDays[i] = checkedMenuItems.contains(menuItems[i].toString());
        }
        return mRepeatingDays;
    }

    public void setSummaryValues(Set<String> values) {
        CharSequence[] menuItems = getEntryValues();
        CharSequence[] menuItemsDisplay = getEntries();
        String summaryString = "";
        for (int i = 0; i < menuItems.length; i++) {
            if (values.contains(menuItems[i].toString())) {
                String dayName = menuItemsDisplay[i].toString();
                if (summaryString.isEmpty()) {
                    summaryString = dayName;
                } else {
                    summaryString += ", " + dayName;
                }
            }
        }
        if (summaryString.isEmpty()) {
            summaryString = getContext().getString(R.string.pref_no_repeating);
        }
        setSummary(summaryString);
    }
}