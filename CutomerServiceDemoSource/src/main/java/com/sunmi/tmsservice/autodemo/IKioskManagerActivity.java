package com.sunmi.tmsservice.autodemo;

import android.os.Bundle;
import android.os.Handler;
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
import com.sunmi.tmsmaster.aidl.kioskmanager.IKioskManager;
import com.sunmi.tmsservice.apidemo.TMSMaster;
import com.sunmi.tmsservice.autodemo.util.Util;

import java.util.Arrays;
import java.util.List;

public class IKioskManagerActivity extends AppCompatActivity {

    private static final String TAG = IKioskManagerActivity.class.getName();

    private LinearLayout layout;
    private TextView tvLog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_i_kiosk_manager);
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
            runOnUiThread(() -> Toast.makeText(IKioskManagerActivity.this, text, Toast.LENGTH_SHORT).show());
        }
    }

    public IKioskManager getData() {
        return TMSMaster.getInstance().getKioskManager();
    }

    public void enableKioskFunction(View view) {
        try {
            boolean flag = isCheckBoxSelected(R.id.cb_enable_kiosk_function_flag);
            boolean result = getData().enableKioskFunction(flag);
            log("enableKioskFunction: flag=" + flag + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("enableKioskFunction error!", view);
        }
    }

    public void isKioskFunctionEnabled(View view) {
        try {
            boolean result = getData().isKioskFunctionEnabled();
            log("isKioskFunctionEnabled:  " + "result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("isKioskFunctionEnabled error!", view);
        }
    }

    public void getKioskModeStatus(View view) {
        try {
            boolean result = getData().getKioskModeStatus();
            log("getKioskModeStatus: result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getKioskModeStatus error!", view);
        }
    }

    public void addAppToKioskList(View view) {
        try {
            String packageNames = getEtString(R.id.et_add_app_to_kiosk_list_package_names);
            List<String> list = Arrays.asList(packageNames.split(","));
            boolean result = getData().addAppToKioskList(list);
            log("addAppToKioskList: list=" + Util.object2json(list) + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("addAppToKioskList error!", view);
        }
    }

    public void removeAppFromKioskList(View view) {
        try {
            String packageNames = getEtString(R.id.et_remove_app_from_kiosk_list_package_names);
            List<String> list = Arrays.asList(packageNames.split(","));
            boolean result = getData().removeAppFromKioskList(list);
            log("removeAppFromKioskList: list=" + Util.object2json(list) + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("removeAppFromKioskList error!", view);
        }
    }

    public void getKioskAppList(View view) {
        try {
            List<String> result = getData().getKioskAppList();
            log("getKioskAppList: result=" + Util.object2json(result), view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getKioskAppList error!", view);
        }
    }

    public void isKioskApp(View view) {
        try {
            String packageName = getEtString(R.id.et_is_Kiosk_App_package_Name);
            boolean result = getData().isKioskApp(packageName);
            log("isKioskApp: result=" + result + ", packageName=" + packageName, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("isKioskApp error!", view);
        }
    }

    public void setKioskPwdByType(View view) {
        try {
            int type = Integer.parseInt(getEtString(R.id.et_set_kiosk_pwd_by_type_type));
            String psw = getEtString(R.id.et_set_kiosk_pwd_by_type_psw);
            boolean result = getData().setKioskPwdByType(type, psw);
            log("setKioskPwdByType: type=" + type + ", psw=" + psw + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("setKioskPwdByType error!", view);
        }
    }

}