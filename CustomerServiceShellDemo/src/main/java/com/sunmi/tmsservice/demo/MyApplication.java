package com.sunmi.tmsservice.demo;

import android.app.Application;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.sunmi.tmsmaster.aidl.IDeviceService;
import com.sunmi.tmsservice.apidemo.TMSMaster;

public class MyApplication extends Application {

    private static final String TAG = MyApplication.class.getSimpleName();
    private static final String ACTION_TMS_SERVICE = "com.sunmi.tms_service";
    private static final String PACKAGE_NAME_TMS_SERVICE = "com.sunmi.tmservice";

    private final ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            log("onServiceConnected");
            IDeviceService iDeviceService = IDeviceService.Stub.asInterface(service);
            TMSMaster.getInstance().init(MyApplication.this, iDeviceService);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            log("onServiceDisconnected");
            TMSMaster.getInstance().clear();
            bindService();
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        bindService();
    }

    private void bindService() {
        Intent intent = new Intent(ACTION_TMS_SERVICE);
        intent.setPackage(PACKAGE_NAME_TMS_SERVICE);
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);
    }

    private void log(String msg) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        Log.e(TAG, "msg: " + msg);
        new Handler(Looper.getMainLooper()).post(() -> Toast.makeText(MyApplication.this, msg, Toast.LENGTH_SHORT).show());
    }
}
