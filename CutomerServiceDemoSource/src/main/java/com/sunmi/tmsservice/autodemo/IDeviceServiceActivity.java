package com.sunmi.tmsservice.autodemo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.sunmi.cutomerservicedemosource.R;
import com.sunmi.tmsmaster.aidl.IDeviceService;

public class IDeviceServiceActivity extends AppCompatActivity {

    private static final String TAG = IDeviceServiceActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_i_device_service);
    }

    public void getDeviceInfoBinder(View view) {
        startActivity(new Intent(this, IDeviceInfoActivity.class));
    }

    public void getDeviceManagerBinder(View view) {
        startActivity(new Intent(this, IDeviceManagerActivity.class));
    }

    public void getSoftwareManagerBinder(View view) {
        startActivity(new Intent(this, ISoftwareManagerActivity.class));
    }

    public void getSystemManagerBinder(View view) {
        startActivity(new Intent(this, ISystemManagerActivity.class));
    }

    public void getSystemUIManagerBinder(View view) {
        startActivity(new Intent(this, ISystemUIManagerActivity.class));
    }

    public void getKioskManagerBinder(View view) {
        startActivity(new Intent(this, IKioskManagerActivity.class));
    }

    public void getDeviceRunningInfoBinder(View view) {
        startActivity(new Intent(this, IDeviceRunningInfoActivity.class));
    }

    public void getCertificateManagerBinder(View view) {
        startActivity(new Intent(this, ICertificateManagerActivity.class));
    }

    public void getNetworkManagerBinder(View view) {
        startActivity(new Intent(this, INetworkManagerActivity.class));
    }

    public void getAllPackageInfo(View view) {
        startActivity(new Intent(this, IPackageCTPAActivity.class));
    }

    public void getPreference(View view) {
        startActivity(new Intent(this, IServicePreferenceActivity.class));
    }

    public void getTelephoneManager(View view) {
        startActivity(new Intent(this, ITelephoneManagerActivity.class));
    }

    public void getIotInfoBinder(View view) {
        startActivity(new Intent(this, IIotInfoActivity.class));
    }

    public void getHostManager(View view) {
        startActivity(new Intent(this, IHostManagerActivity.class));
    }

    public void getSettingsUIManagerBinder(View view) {
        startActivity(new Intent(this, ISettingsUIManagerActivity.class));
    }
}