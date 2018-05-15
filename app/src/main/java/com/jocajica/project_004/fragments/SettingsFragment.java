package com.jocajica.project_004.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.jocajica.project_004.R;
import com.jocajica.project_004.tools.Preferences;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment implements View.OnClickListener {

    private Button mButtonAccept;
    private Button mButtonCancel;

    private EditText mEditTextDeviceName;
    private EditText mEditTextStepsRevolution;
    private EditText mEditTextStepDistance;
    private EditText mEditTextSpeed;
    private EditText mEditTextDelayBetweenPhotos;
    private EditText mEditTextExpositionTime;
    private EditText mEditTextStepsBetweenPhotos;

    Preferences mPrefs;

    private OnSettingsListener mCallback;

    public SettingsFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mPrefs = new Preferences(context);
        mPrefs.loadPreferences();

        try {
            mCallback = (OnSettingsListener) context;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_settings, container, false);

        initUI(v);
        assignDefaultValues();

        return v;
    }

    private void assignDefaultValues() {
        String strDeviceName = mPrefs.getDeviceName();
        String strDelayBetweenPhotos = mPrefs.getDelayBetweenPhotos() + "";
        String strExpositionTime = mPrefs.getExpositionTime() + "";
        String strSpeed = mPrefs.getSpeed() + "";
        String strStepDistance = mPrefs.getStepDistance() + "";
        String strStepsBetweenPhotos = mPrefs.getStepsBetweenPhotos() + "";
        String strStepsRevolution = mPrefs.getStepsRevolution() + "";

        mEditTextDeviceName.setText(strDeviceName);
        mEditTextDelayBetweenPhotos.setText(strDelayBetweenPhotos);
        mEditTextExpositionTime.setText(strExpositionTime);
        mEditTextSpeed.setText(strSpeed);
        mEditTextStepDistance.setText(strStepDistance);
        mEditTextStepsBetweenPhotos.setText(strStepsBetweenPhotos);
        mEditTextStepsRevolution.setText(strStepsRevolution);
    }

    private void saveCurrentValues() {
        mPrefs.setDeviceName(mEditTextDeviceName.getText().toString());
        mPrefs.setDelayBetweenPhotos(Integer.valueOf(mEditTextDelayBetweenPhotos.getText().toString()));
        mPrefs.setExpositionTime(Float.valueOf(mEditTextExpositionTime.getText().toString()));
        mPrefs.setSpeed(Integer.valueOf(mEditTextSpeed.getText().toString()));
        mPrefs.setStepDistance(Float.valueOf(mEditTextStepDistance.getText().toString()));
        mPrefs.setStepsBetweenPhotos(Integer.valueOf(mEditTextStepsBetweenPhotos.getText().toString()));
        mPrefs.setStepsRevolution(Integer.valueOf(mEditTextStepsRevolution.getText().toString()));
        mPrefs.savePreferences();
    }

    private void initUI(View v) {
        mButtonAccept = v.findViewById(R.id.buttonAccept);
        mButtonCancel = v.findViewById(R.id.buttonCancel);

        mEditTextDeviceName = v.findViewById(R.id.editTextDeviceName);
        mEditTextDelayBetweenPhotos = v.findViewById(R.id.editTextDelayBetweenPhotos);
        mEditTextExpositionTime = v.findViewById(R.id.editTextExpositionTime);
        mEditTextSpeed = v.findViewById(R.id.editTextSpeed);
        mEditTextStepDistance = v.findViewById(R.id.editTextStepDistance);
        mEditTextStepsBetweenPhotos = v.findViewById(R.id.editTextStepsBetweenPhotos);
        mEditTextStepsRevolution = v.findViewById(R.id.editTextStepsRevolution);

        mButtonAccept.setOnClickListener(this);
        mButtonCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonAccept:
                acceptSettings();
                break;
            case R.id.buttonCancel:
                cancelSettings();
                break;
        }
    }

    private void cancelSettings() {
        mCallback.OnCancel();
    }

    private void acceptSettings() {
        saveCurrentValues();
        mCallback.OnAccept();
    }

    public interface OnSettingsListener {
        void OnAccept();
        void OnCancel();
    }
}

