package com.jocajica.project_004.tools;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {

    private static final String PREFERENCESID = "MACROBTPREFS";

    private static final String DEVICENAME = "DEVICENAME";
    private static final String DELAYBETWEENPHOTOS = "DELAYBETWEENPHOTOS";
    private static final String EXPOSITIONTIME = "EXPOSITIONTIME";
    private static final String SPEED = "SPEED";
    private static final String STEPDISTANCE = "STEPDISTANCE";
    private static final String STEPSBETWEENPHOTOS = "STEPSBETWEENPHOTOS";
    private static final String STEPSREVOLUTION = "STEPSREVOLUTION";

    private SharedPreferences mPrefs;

    private String mDeviceName;
    private int mDelayBetweenPhotos;
    private float mExpositionTime;
    private int mSpeed;
    private float mStepDistance;
    private int mStepsBetweenPhotos;
    private int mStepsRevolution;

    public Preferences(Context context) {
        mPrefs = context.getSharedPreferences(PREFERENCESID, Context.MODE_PRIVATE);
    }

    public void loadPreferences() {
        mDeviceName = mPrefs.getString(DEVICENAME, "MACROBT");
        mDelayBetweenPhotos = mPrefs.getInt(DELAYBETWEENPHOTOS, 1);
        mExpositionTime = mPrefs.getFloat(EXPOSITIONTIME, (float) 1.6);
        mSpeed = mPrefs.getInt(SPEED, 10);
        mStepDistance = mPrefs.getFloat(STEPDISTANCE, (float) 0.4);
        mStepsBetweenPhotos = mPrefs.getInt(STEPSBETWEENPHOTOS, 1);
        mStepsRevolution = mPrefs.getInt(STEPSREVOLUTION, 200);
    }

    public void savePreferences() {
        SharedPreferences.Editor editorPrefs = mPrefs.edit();
        editorPrefs.putString(DEVICENAME, mDeviceName);
        editorPrefs.putInt(DELAYBETWEENPHOTOS, mDelayBetweenPhotos);
        editorPrefs.putFloat(EXPOSITIONTIME, mExpositionTime);
        editorPrefs.putInt(SPEED, mSpeed);
        editorPrefs.putFloat(STEPDISTANCE, mStepDistance);
        editorPrefs.putInt(STEPSBETWEENPHOTOS, mStepsBetweenPhotos);
        editorPrefs.putInt(STEPSREVOLUTION, mStepsRevolution);
        editorPrefs.commit();
    }

    public String getDeviceName() {
        return mDeviceName;
    }

    public void setDeviceName(String mDeviceName) {
        this.mDeviceName = mDeviceName;
    }

    public int getDelayBetweenPhotos() {
        return mDelayBetweenPhotos;
    }

    public void setDelayBetweenPhotos(int mDelayBetweenPhotos) {
        this.mDelayBetweenPhotos = mDelayBetweenPhotos;
    }

    public float getExpositionTime() {
        return mExpositionTime;
    }

    public void setExpositionTime(float mExpositionTime) {
        this.mExpositionTime = mExpositionTime;
    }

    public int getSpeed() {
        return mSpeed;
    }

    public void setSpeed(int mSpeed) {
        this.mSpeed = mSpeed;
    }

    public float getStepDistance() {
        return mStepDistance;
    }

    public void setStepDistance(float mStepDistance) {
        this.mStepDistance = mStepDistance;
    }

    public int getStepsBetweenPhotos() {
        return mStepsBetweenPhotos;
    }

    public void setStepsBetweenPhotos(int mStepsBetweenPhotos) {
        this.mStepsBetweenPhotos = mStepsBetweenPhotos;
    }

    public int getStepsRevolution() {
        return mStepsRevolution;
    }

    public void setStepsRevolution(int mStepsRevolution) {
        this.mStepsRevolution = mStepsRevolution;
    }
}
