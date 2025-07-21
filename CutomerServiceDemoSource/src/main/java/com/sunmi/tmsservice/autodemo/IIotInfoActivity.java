package com.sunmi.tmsservice.autodemo;

import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;
import android.view.View;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.sunmi.cutomerservicedemosource.R;
import com.sunmi.tmsmaster.aidl.iot.IGetPrintInfoCallback;
import com.sunmi.tmsmaster.aidl.iot.IIotInfo;
import com.sunmi.tmsservice.apidemo.TMSMaster;

public class IIotInfoActivity extends AppCompatActivity {

    private static final String TAG = IIotInfoActivity.class.getName();

    private LinearLayout layout;
    private TextView tvLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_i_iot_info);
        layout = findViewById(R.id.layout);
        tvLog = new TextView(this);
    }

    private boolean isCheckBoxSelected(int cbId) throws Exception {
        return ((CheckBox) findViewById(cbId)).isChecked();
    }

    private String getEtString(int etId) throws Exception {
        return ((EditText) findViewById(etId)).getText().toString();
    }

    private void log(String text, View view) {
        Log.d(TAG, text);
        if (view != null) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    layout.removeView(tvLog);
                    tvLog.setText(text);
                    // 获取 Button 在 LinearLayout 中的位置
                    int buttonIndex = layout.indexOfChild(view);
                    // 在 Button 的下方插入 TextView
                    layout.addView(tvLog, buttonIndex + 1);
                }
            });
        } else {
            runOnUiThread(() -> Toast.makeText(IIotInfoActivity.this, text, Toast.LENGTH_SHORT).show());
        }
    }

    public IIotInfo getData() {
        return TMSMaster.getInstance().getIotInfo();
    }

    public void getTotalPrintLength(View view) {
        try {
            getData().getTotalPrintLength(new IGetPrintInfoCallback.Stub() {
                @Override
                public void onReturnString(String result) throws RemoteException {
                    log("getTotalPrintLength: onReturnString result=" + result, view);

                }

                @Override
                public void onError(int errorCode, String msg) throws RemoteException {
                    log("getTotalPrintLength: onError errorCode=" + errorCode + ", msg" + msg, view);

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            log("getTotalPrintLength error!", view);
        }
    }

    public void getFrontCameraOpenCount(View view) {
        try {
            int result = getData().getFrontCameraOpenCount();
            log("getFrontCameraOpenCount: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getFrontCameraOpenCount error!", view);
        }
    }

    public void getRearCameraOpenCount(View view) {
        try {
            int result = getData().getRearCameraOpenCount();
            log("getRearCameraOpenCount: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getRearCameraOpenCount error!", view);
        }
    }

    public void getSwipingCardTimes(View view) {
        try {
            int result = getData().getSwipingCardTimes();
            log("getSwipingCardTimes: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getSwipingCardTimes error!", view);
        }
    }

    public void getDipInsertTimes(View view) {
        try {
            int result = getData().getDipInsertTimes();
            log("getDipInsertTimes: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getDipInsertTimes error!", view);
        }
    }

    public void getNFCCardReadTimes(View view) {
        try {
            int result = getData().getNFCCardReadTimes();
            log("getNFCCardReadTimes: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getNFCCardReadTimes error!", view);
        }
    }
}