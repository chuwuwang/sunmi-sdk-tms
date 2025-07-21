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
import com.sunmi.tmsmaster.aidl.host.IHostManager;
import com.sunmi.tmsservice.apidemo.TMSMaster;

public class IHostManagerActivity extends AppCompatActivity {

    private static final String TAG = IHostManagerActivity.class.getName();

    private LinearLayout layout;
    private TextView tvLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_i_host_manager);
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
            runOnUiThread(() -> Toast.makeText(IHostManagerActivity.this, text, Toast.LENGTH_SHORT).show());
        }
    }

    public IHostManager getData() {
        return TMSMaster.getInstance().getHost();
    }

    public void readHost(View view) {
        try {
            java.util.List<String> result = getData().readHost();
            log("readHost: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("readHost error!", view);
        }
    }

    public void writeHost(View view) {
        try {
            String ip = new String(getEtString(R.id.et_write_host_ip));
            String address = new String(getEtString(R.id.et_write_host_address));
            boolean result = getData().writeHost(ip, address);
            log("writeHost: ip=" + ip + ", address=" + address + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("writeHost error!", view);
        }
    }

    public void deleteHost(View view) {
        try {
            String ip = new String(getEtString(R.id.et_delete_host_ip));
            String address = new String(getEtString(R.id.et_delete_host_address));
            boolean result = getData().deleteHost(ip, address);
            log("deleteHost: ip=" + ip + ", address=" + address + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("deleteHost error!", view);
        }
    }
}