package com.jocajica.project_004.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.jocajica.project_004.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment implements View.OnClickListener {

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

    SharedPreferences mPrefs;

    int mDelayBetweenPhotos;
    float mExpositionTime;
    int mSpeed;
    float mStepDistance;
    int mStepsBetweenPhotos;
    int mStepsRevolution;

    private OnMainListener mCallback;

    public MainFragment() {
        mPosStart = 0;
        mPosEnd = 0;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mPrefs = context.getSharedPreferences("MACROBTPREFS", Context.MODE_PRIVATE);

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
        loadSavedPreferences();
        updateInfo();

        return v;
    }

    private void loadSavedPreferences() {
        mDelayBetweenPhotos = mPrefs.getInt("DELAYBETWEENPHOTOS", 1);
        mExpositionTime = mPrefs.getFloat("EXPOSITIONTIME", (float) 1.6);
        mSpeed = mPrefs.getInt("SPEED", 10);
        mStepDistance = mPrefs.getFloat("STEPDISTANCE", (float) 0.4);
        mStepsBetweenPhotos = mPrefs.getInt("STEPSBETWEENPHOTOS", 1);
        mStepsRevolution = mPrefs.getInt("STEPSREVOLUTION", 200);
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
        mButtonBackward.setOnClickListener(this);
        mButtonForward.setOnClickListener(this);
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
        if (status) {
            mButtonRunStop.setText(R.string.parar);
        } else {
            mButtonRunStop.setText(R.string.ejecutar);
        }
    }

    public void setRunStopStatus(boolean status) {
        if (status) {
            mButtonStartEndPosition.setText(R.string.posicion_inicial);
        } else {
            mButtonStartEndPosition.setText(R.string.posicion_final);
        }
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

        String strDistance = String.format(res.getString(R.string.mostrar_distancia), (mPosEnd - mPosStart) * mStepDistance);
        String strSteps = String.format(res.getString(R.string.mostrar_pasos), mPosEnd - mPosStart);
        String strTime = String.format(res.getString(R.string.mostrar_tiempo), (mPosEnd - mPosStart) * (mDelayBetweenPhotos + mExpositionTime) / mStepsBetweenPhotos);

        mTextViewDistance.setText(strDistance);
        mTextViewSteps.setText(strSteps);
        mTextViewTime.setText(strTime);
    }

}
