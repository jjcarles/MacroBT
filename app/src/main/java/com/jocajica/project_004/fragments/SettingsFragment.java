package com.jocajica.project_004.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;

import com.jocajica.project_004.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment implements View.OnClickListener {

    private Button mButtonAccept;
    private Button mButtonCancel;

    private OnSettingsListener mCallback;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

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

        return v;
    }

    private void initUI(View v) {
        mButtonAccept = v.findViewById(R.id.buttonAccept);
        mButtonCancel = v.findViewById(R.id.buttonCancel);

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
        mCallback.OnAccept();
    }

    public interface OnSettingsListener {
        void OnAccept();

        void OnCancel();
    }
}

