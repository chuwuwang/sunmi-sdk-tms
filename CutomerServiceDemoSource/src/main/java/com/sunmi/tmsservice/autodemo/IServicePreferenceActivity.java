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
import com.sunmi.tmsmaster.aidl.pm.IServicePreference;
import com.sunmi.tmsservice.apidemo.TMSMaster;

public class IServicePreferenceActivity extends AppCompatActivity {

    private static final String TAG = IServicePreferenceActivity.class.getName();

    private LinearLayout layout;
    private TextView tvLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_i_service_preference);
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
            runOnUiThread(() -> Toast.makeText(IServicePreferenceActivity.this, text, Toast.LENGTH_SHORT).show());
        }
    }

    public IServicePreference getData() {
        return TMSMaster.getInstance().getServicePreference();
    }

    public void updateResPack(View view) {
        try {
            String ResPackPath = new String(getEtString(R.id.et_update_res_pack__res_pack_path));
            int result = getData().updateResPack(ResPackPath);
            log("updateResPack: ResPackPath=" + ResPackPath + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("updateResPack error!", view);
        }
    }

    public void getResVer(View view) {
        try {
            String result = getData().getResVer();
            log("getResVer: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getResVer error!", view);
        }
    }

    public void switchTmsDomainFlag(View view) {
        try {
            String switchFlag = new String(getEtString(R.id.et_switch_tms_domain_flag_switch_flag));
            boolean result = getData().switchTmsDomainFlag(switchFlag);
            log("switchTmsDomainFlag: switchFlag=" + switchFlag + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("switchTmsDomainFlag error!", view);
        }
    }
}