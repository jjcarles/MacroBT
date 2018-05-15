package com.jocajica.project_004.fragments;


import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.jocajica.project_004.R;
import com.jocajica.project_004.tools.Preferences;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment implements View.OnClickListener, View.OnTouchListener {

    private Button mButtonStepBackward;
    private Button mButtonStepForward;
    private Button mButtonBackward;
    private Button mButtonForward;
    private Button mButtonStartEndPosition;
    private Button mButtonSettings;
    private Button mButtonRunStop;
    private Button mButtonTakePhoto;

    private TextView mTextViewDistance;
    private TextView mTextViewSteps;
    private TextView mTextViewTime;

    private int mPosStart;
    private int mPosEnd;

    private Preferences mPrefs;

    private OnMainListener mCallback;

    private Handler mHandler = new Handler();

    private Runnable mButtonForwardHoldTask = new Runnable() {
        @Override
        public void run() {
            mCallback.OnMoveForward();
            mHandler.postAtTime(this, SystemClock.uptimeMillis() + 100);
        }
    };

    private Runnable mButtonBackwardHoldTask = new Runnable() {
        @Override
        public void run() {
            mCallback.OnMoveBackward();
            mHandler.postAtTime(this, SystemClock.uptimeMillis() + 100);
        }
    };

    public MainFragment() {
        mPosStart = 0;
        mPosEnd = 0;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mPrefs = new Preferences(context);
        mPrefs.loadPreferences();

        try {
            mCallback = (OnMainListener) context;
        } catch (Exception e) {
            try {
                throw new Exception(e.getMessage());
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);

        initUI(v);
        updateInfo();

        return v;
    }

    private void initUI(View v) {
        mButtonStepBackward = v.findViewById(R.id.buttonStepBackward);
        mButtonStepForward = v.findViewById(R.id.buttonStepForward);
        mButtonBackward = v.findViewById(R.id.buttonBackward);
        mButtonForward = v.findViewById(R.id.buttonForward);
        mButtonStartEndPosition = v.findViewById(R.id.buttonStartEndPosition);
        mButtonSettings = v.findViewById(R.id.buttonSettings);
        mButtonRunStop = v.findViewById(R.id.buttonRunStop);
        mButtonTakePhoto = v.findViewById(R.id.buttonTakePhoto);

        mButtonStepBackward.setOnClickListener(this);
        mButtonStepForward.setOnClickListener(this);
        mButtonBackward.setOnTouchListener(this);
        mButtonForward.setOnTouchListener(this);
        mButtonStartEndPosition.setOnClickListener(this);
        mButtonSettings.setOnClickListener(this);
        mButtonRunStop.setOnClickListener(this);
        mButtonTakePhoto.setOnClickListener(this);

        mTextViewDistance = v.findViewById(R.id.textViewDistance);
        mTextViewSteps = v.findViewById(R.id.textViewSteps);
        mTextViewTime = v.findViewById(R.id.textViewTime);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonStepBackward:
                moveStepBackward();
                break;
            case R.id.buttonStepForward:
                moveStepForward();
                break;
            case R.id.buttonBackward:
                moveBackward();
                break;
            case R.id.buttonForward:
                moveForward();
                break;
            case R.id.buttonStartEndPosition:
                setPosition();
                break;
            case R.id.buttonSettings:
                configure();
                break;
            case R.id.buttonRunStop:
                startEndSession();
                break;
            case R.id.buttonTakePhoto:
                takePhoto();
                break;
        }
    }

    private void takePhoto() {
        mCallback.OnTakePhoto();
    }

    private void startEndSession() {
        mCallback.OnStartEndSession();
    }

    private void configure() {
        mCallback.OnConfigure();
    }

    private void setPosition() {
        mCallback.OnSetPosition();
    }

    private void moveForward() {
        mCallback.OnMoveForward();
    }

    private void moveBackward() {
        mCallback.OnMoveBackward();
    }

    private void moveStepForward() {
        mCallback.OnMoveStepForward();
    }

    private void moveStepBackward() {
        mCallback.OnMoveStepBackward();
    }

    public void setInterval(int posStart, int posEnd) {
        mPosStart = posStart;
        mPosEnd = posEnd;
        updateInfo();
    }

    public void setStartEndStatus(boolean status) {
        enableUI(!status);

        if (status) {
            mButtonRunStop.setText(R.string.parar);
        } else {
            mButtonRunStop.setText(R.string.ejecutar);
        }
    }

    private void enableUI(boolean b) {
        mButtonBackward.setEnabled(b);
        mButtonForward.setEnabled(b);
        mButtonSettings.setEnabled(b);
        mButtonStartEndPosition.setEnabled(b);
        mButtonStepBackward.setEnabled(b);
        mButtonStepForward.setEnabled(b);
        mButtonTakePhoto.setEnabled(b);
        mButtonSettings.setEnabled(b);
    }

    public void setRunStopStatus(boolean status) {
        if (status) {
            mButtonStartEndPosition.setText(R.string.posicion_inicial);
        } else {
            mButtonStartEndPosition.setText(R.string.posicion_final);
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int action = motionEvent.getAction();

        switch (view.getId()) {
            case R.id.buttonForward:
                if (action == MotionEvent.ACTION_DOWN) {
                    mHandler.removeCallbacks(mButtonForwardHoldTask);
                    mHandler.postAtTime(mButtonForwardHoldTask, SystemClock.uptimeMillis() + 50);
                } else if (action == MotionEvent.ACTION_UP) {
                    mHandler.removeCallbacks(mButtonForwardHoldTask);
                }
                break;
            case R.id.buttonBackward:
                if (action == MotionEvent.ACTION_DOWN) {
                    mHandler.removeCallbacks(mButtonBackwardHoldTask);
                    mHandler.postAtTime(mButtonBackwardHoldTask, SystemClock.uptimeMillis() + 50);
                } else if (action == MotionEvent.ACTION_UP) {
                    mHandler.removeCallbacks(mButtonBackwardHoldTask);
                }
                break;
        }

        return false;
    }

    public interface OnMainListener {
        void OnMoveStepForward();

        void OnMoveForward();

        void OnMoveStepBackward();

        void OnMoveBackward();

        void OnSetPosition();

        void OnConfigure();

        void OnStartEndSession();

        void OnTakePhoto();
    }

    private void updateInfo() {
        Resources res = getResources();

        int delayBetweenPhotos = mPrefs.getDelayBetweenPhotos();
        float expositionTime = mPrefs.getExpositionTime();
        int speed = mPrefs.getSpeed();
        float stepDistance = mPrefs.getStepDistance();
        int stepsBetweenPhotos = mPrefs.getStepsBetweenPhotos();
        int stepsRevolution = mPrefs.getStepsRevolution();

        float timeMove = (mPosEnd - mPosStart) * (60 / (speed * stepsRevolution));
        float timePhotos = (mPosEnd - mPosStart) * (delayBetweenPhotos + expositionTime) / stepsBetweenPhotos;

        String strDistance = String.format(res.getString(R.string.mostrar_distancia), (mPosEnd - mPosStart) * stepDistance);
        String strSteps = String.format(res.getString(R.string.mostrar_pasos), mPosEnd - mPosStart);
        String strTime = String.format(res.getString(R.string.mostrar_tiempo), timeMove + timePhotos);

        mTextViewDistance.setText(strDistance);
        mTextViewSteps.setText(strSteps);
        mTextViewTime.setText(strTime);
    }

}
