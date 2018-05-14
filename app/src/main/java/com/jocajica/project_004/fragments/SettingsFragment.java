package com.jocajica.project_004.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;

import com.jocajica.project_004.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment implements View.OnClickListener {

    private Button mButtonAccept;
    private Button mButtonCancel;

    private EditText mEditTextStepsRevolution;
    private EditText mEditTextStepDistance;
    private EditText mEditTextSpeed;
    private EditText mEditTextDelayBetweenPhotos;
    private EditText mEditTextExpositionTime;
    private EditText mEditTextStepsBetweenPhotos;

    SharedPreferences mPrefs;
    SharedPreferences.Editor mEditorPrefs;

    private OnSettingsListener mCallback;

    public SettingsFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mPrefs = context.getSharedPreferences("MACROBTPREFS", Context.MODE_PRIVATE);

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
        loadSavedPreferences();

        return v;
    }

    private void loadSavedPreferences() {
        String strDelayBetweenPhotos = mPrefs.getInt("DELAYBETWEENPHOTOS", 1) + "";
        String strExpositionTime = mPrefs.getFloat("EXPOSITIONTIME", (float) 1.6) + "";
        String strSpeed = mPrefs.getInt("SPEED", 10) + "";
        String strStepDistance = mPrefs.getFloat("STEPDISTANCE", (float) 0.4) + "";
        String strStepsBetweenPhotos = mPrefs.getInt("STEPSBETWEENPHOTOS", 1) + "";
        String strStepsRevolution = mPrefs.getInt("STEPSREVOLUTION", 200) + "";

        mEditTextDelayBetweenPhotos.setText(strDelayBetweenPhotos);
        mEditTextExpositionTime.setText(strExpositionTime);
        mEditTextSpeed.setText(strSpeed);
        mEditTextStepDistance.setText(strStepDistance);
        mEditTextStepsBetweenPhotos.setText(strStepsBetweenPhotos);
        mEditTextStepsRevolution.setText(strStepsRevolution);
    }

    private void saveCurrentValues() {
        int nDelayBetweenPhotos = Integer.valueOf(mEditTextDelayBetweenPhotos.getText().toString());
        float nExpositionTime = Float.valueOf(mEditTextExpositionTime.getText().toString());
        int nSpeed = Integer.valueOf(mEditTextSpeed.getText().toString());
        float nStepDistance = Float.valueOf(mEditTextStepDistance.getText().toString());
        int nStepsBetweenPhotos = Integer.valueOf(mEditTextStepsBetweenPhotos.getText().toString());
        int nStepsRevolution = Integer.valueOf(mEditTextStepsRevolution.getText().toString());

        mEditorPrefs = mPrefs.edit();
        mEditorPrefs.putInt("DELAYBETWEENPHOTOS", nDelayBetweenPhotos);
        mEditorPrefs.putFloat("EXPOSITIONTIME", nExpositionTime);
        mEditorPrefs.putInt("SPEED", nSpeed);
        mEditorPrefs.putFloat("STEPDISTANCE", nStepDistance);
        mEditorPrefs.putInt("STEPSBETWEENPHOTOS", nStepsBetweenPhotos);
        mEditorPrefs.putInt("STEPSREVOLUTION", nStepsRevolution);
        mEditorPrefs.commit();
    }

    private void initUI(View v) {
        mButtonAccept = v.findViewById(R.id.buttonAccept);
        mButtonCancel = v.findViewById(R.id.buttonCancel);

        mEditTextDelayBetweenPhotos = v.findViewById(R.id.editTextDelayBetweenPhotos);
        mEditTextExpositionTime = v.findViewById(R.id.editTextExpositionTime);
        mEditTextSpeed = v.findViewById(R.id.editTextSpeed);
        mEditTextStepDistance = v.findViewById(R.id.editTextStepDistance);
        mEditTextStepsBetweenPhotos = v.findViewById(R.id.editTextStepsBetweenPhotos);
        mEditTextStepsRevolution = v.findViewById(R.id.editTextStepsRevolution);

        mButtonAccept.setOnClickListener(this);
        mButtonCancel.setOnClickListener(this);

        mEditTextStepsRevolution.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_UP) {
                    String strValue = ((EditText) view).getText().toString();
                }
                return false;
            }
        });
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

