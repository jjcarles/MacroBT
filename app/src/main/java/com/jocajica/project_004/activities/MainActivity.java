package com.jocajica.project_004.activities;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.jocajica.project_004.R;
import com.jocajica.project_004.fragments.MainFragment;
import com.jocajica.project_004.fragments.SettingsFragment;

import java.util.Set;

public class MainActivity extends AppCompatActivity implements MainFragment.OnMainListener, SettingsFragment.OnSettingsListener {

    private int mPosStart;
    private int mPosEnd;

    private boolean mIsConnected;
    private boolean mIsStartActive;
    private boolean mIsRunning;

    private BluetoothAdapter mBluetoothAdapter;
    private Set<BluetoothDevice> mPairedDevices;

    private Fragment mCurrentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolbar();
        init();

        //initBT();

        mCurrentFragment = new MainFragment();
        changeFragment(mCurrentFragment);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.connectBluetooth) {
            mIsConnected = true;
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void changeFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.containerFragment, fragment);
        transaction.commit();
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.ic_launcher);
        }
    }

    private void initBT() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "Dispositivo no compatible", Toast.LENGTH_SHORT).show();
        }
    }

    private void init() {
        mPosStart = 0;
        mPosEnd = 0;
        mIsStartActive = true;
        mIsRunning = false;
        mIsConnected = false;
    }

    private void takePhoto() {

    }

    private void startEndSession() {
        mIsRunning = !mIsRunning;

        ((MainFragment) mCurrentFragment).setStartEndStatus(mIsRunning);

        if (mIsRunning) {
            sendDataToBt("START;");
        } else {
            sendDataToBt("FINISH;");
        }
    }

    private void sendDataToBt(String command) {

    }

    private void configure() {
        mCurrentFragment = new SettingsFragment();
        changeFragment(mCurrentFragment);
    }

    private void setPosition() {
        mIsStartActive = !mIsStartActive;

        ((MainFragment) mCurrentFragment).setRunStopStatus(mIsStartActive);

        if (mIsStartActive) {

        } else {

        }
    }

    private void moveForward() {
        if (!mIsConnected)
            return;

        if (mIsStartActive) {
            mPosStart++;

            if (mPosStart > mPosEnd) {
                mPosEnd = mPosStart;
            }
        } else {
            mPosEnd++;
        }

        ((MainFragment) mCurrentFragment).setInterval(mPosStart, mPosEnd);
    }

    private void moveBackward() {
        if (!mIsConnected)
            return;

        if (mIsStartActive) {
            mPosStart--;
        } else {
            mPosEnd--;

            if (mPosEnd < mPosStart) {
                mPosStart = mPosEnd;
            }
        }

        ((MainFragment) mCurrentFragment).setInterval(mPosStart, mPosEnd);
    }

    private void moveStepForward() {
        if (!mIsConnected)
            return;

        if (mIsStartActive) {
            mPosStart++;

            if (mPosStart > mPosEnd) {
                mPosEnd = mPosStart;
            }
        } else {
            mPosEnd++;
        }

        ((MainFragment) mCurrentFragment).setInterval(mPosStart, mPosEnd);
    }

    private void moveStepBackward() {
        if (!mIsConnected)
            return;

        if (mIsStartActive) {
            mPosStart--;
        } else {
            mPosEnd--;

            if (mPosEnd < mPosStart) {
                mPosStart = mPosEnd;
            }
        }

        ((MainFragment) mCurrentFragment).setInterval(mPosStart, mPosEnd);
    }

    @Override
    public void OnMoveStepForward() {
        moveStepForward();
    }

    @Override
    public void OnMoveForward() {
        moveForward();
    }

    @Override
    public void OnMoveStepBackward() {
        moveStepBackward();
    }

    @Override
    public void OnMoveBackward() {
        moveBackward();
    }

    @Override
    public void OnSetPosition() {
        setPosition();
    }

    @Override
    public void OnConfigure() {
        configure();
    }

    @Override
    public void OnStartEndSession() {
        startEndSession();
    }

    @Override
    public void OnTakePhoto() {
        takePhoto();
    }

    @Override
    public void OnAccept() {
        mCurrentFragment = new MainFragment();
        changeFragment(mCurrentFragment);
    }

    @Override
    public void OnCancel() {
        mCurrentFragment = new MainFragment();
        changeFragment(mCurrentFragment);
    }
}
