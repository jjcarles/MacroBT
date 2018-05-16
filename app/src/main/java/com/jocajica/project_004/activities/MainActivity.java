package com.jocajica.project_004.activities;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.jocajica.project_004.R;
import com.jocajica.project_004.fragments.MainFragment;
import com.jocajica.project_004.fragments.SettingsFragment;
import com.jocajica.project_004.tools.Preferences;

import java.util.Set;

public class MainActivity extends AppCompatActivity implements MainFragment.OnMainListener, SettingsFragment.OnSettingsListener {

    private int mPosStart;
    private int mPosEnd;

    private boolean mIsConnected;
    private boolean mIsStartPositionActive;
    private boolean mIsRunning;

    private Menu mToolbarMenu;

    private BluetoothAdapter mBluetoothAdapter;
    private String mDeviceHardwareAddress;

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
        mToolbarMenu = menu;
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.connectBluetooth) {
            if (!mIsConnected) {
                connectToDevice();
                showBluetoothStatus(mIsConnected);
            } else {
                disconnectDevice();
                showBluetoothStatus(mIsConnected);
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void disconnectDevice() {
        mBluetoothAdapter.disable();
        mIsConnected = false;
    }

    public void showBluetoothStatus(boolean status) {
        if (status) {
            mToolbarMenu.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_bluetooth_on));
        } else {
            mToolbarMenu.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_bluetooth_off));
        }
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
        mIsConnected = false;

        if (!bluetoothAvailable()) {
            Toast.makeText(this, "Dispositivo no compatible", Toast.LENGTH_LONG).show();
            finish();
        } else {
            connectToDevice();
        }
    }

    private boolean bluetoothAvailable() {
        if (mBluetoothAdapter == null) {
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            return mBluetoothAdapter != null;
        }

        return true;
    }

    private void connectToDevice() {
        Preferences prefs = new Preferences(this);
        prefs.loadPreferences();

        if (bluetoothAvailable()) {
            if (mBluetoothAdapter.isEnabled()) {
                Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

                if (pairedDevices.size() > 0) {
                    for (BluetoothDevice device : pairedDevices) {
                        if (device.getName() == prefs.getDeviceName()) {
                            mDeviceHardwareAddress = device.getAddress();
                            mIsConnected = true;
                            break;
                        }
                    }
                }
            }
        }
    }

    private void init() {
        mPosStart = 0;
        mPosEnd = 0;
        mIsStartPositionActive = true;
        mIsRunning = false;
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
        mIsStartPositionActive = !mIsStartPositionActive;

        ((MainFragment) mCurrentFragment).setPositionStatus(mIsStartPositionActive);

        if (mIsStartPositionActive) {

        } else {

        }
    }

    private void moveForward() {
        if (!mIsConnected)
            return;

        if (mIsStartPositionActive) {
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

        if (mIsStartPositionActive) {
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

        if (mIsStartPositionActive) {
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

        if (mIsStartPositionActive) {
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
