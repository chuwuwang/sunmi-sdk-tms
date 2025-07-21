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
import com.sunmi.tmsmaster.aidl.certificatemanager.ICertificateManager;
import com.sunmi.tmsservice.apidemo.TMSMaster;

public class ICertificateManagerActivity extends AppCompatActivity {

    private static final String TAG = ICertificateManagerActivity.class.getName();

    private LinearLayout layout;
    private TextView tvLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_i_certificate_manager);
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
            runOnUiThread(() -> Toast.makeText(ICertificateManagerActivity.this, text, Toast.LENGTH_SHORT).show());
        }
    }

    public ICertificateManager getData() {
        return TMSMaster.getInstance().getCertificateManager();
    }

    public void updateCertificate(View view) {
        try {
            String certPath = new String(getEtString(R.id.et_update_certificate_cert_path));
            int result = getData().updateCertificate(certPath);
            log("updateCertificate: certPath=" + certPath + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("updateCertificate error!", view);
        }
    }

    public void getCertificateInfo(View view) {
        try {
            String result = getData().getCertificateInfo();
            log("getCertificateInfo: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getCertificateInfo error!", view);
        }
    }

    public void getTrustedFileCertChain(View view) {
        try {
            String result = getData().getTrustedFileCertChain();
            log("getTrustedFileCertChain: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getTrustedFileCertChain error!", view);
        }
    }

    public void installSystemCA(View view) {
        try {
            String path = new String(getEtString(R.id.et_install_system_c_a_path));
            boolean result = getData().installSystemCA(path);
            log("installSystemCA: path=" + path + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("installSystemCA error!", view);
        }
    }

    public void uninstallSystemCA(View view) {
        try {
            boolean result = getData().uninstallSystemCA();
            log("uninstallSystemCA: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("uninstallSystemCA error!", view);
        }
    }

    public void uninstallSystemCAbyPath(View view) {
        try {
            String path = new String(getEtString(R.id.et_uninstall_system_c_aby_path_path));
            boolean result = getData().uninstallSystemCAbyPath(path);
            log("uninstallSystemCAbyPath: path=" + path + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("uninstallSystemCAbyPath error!", view);
        }
    }

    public void isSupportISOCertificate(View view) {
        try {
            boolean result = getData().isSupportISOCertificate();
            log("isSupportISOCertificate: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("isSupportISOCertificate error!", view);
        }
    }
}