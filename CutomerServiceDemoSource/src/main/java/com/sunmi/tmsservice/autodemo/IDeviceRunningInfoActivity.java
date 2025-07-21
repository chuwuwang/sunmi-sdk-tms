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
import com.sunmi.tmsmaster.aidl.devicerunninginfo.IDeviceRunningInfo;
import com.sunmi.tmsmaster.aidl.devicerunninginfo.listener.GPSLocationListener;
import com.sunmi.tmsmaster.aidl.devicerunninginfo.listener.NetworkLocationListener;
import com.sunmi.tmsservice.apidemo.TMSMaster;
import com.alibaba.fastjson.JSON;

import java.util.Map;

public class IDeviceRunningInfoActivity extends AppCompatActivity {

    private static final String TAG = IDeviceRunningInfoActivity.class.getName();

    private LinearLayout layout;
    private TextView tvLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_i_device_running_info);
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
            runOnUiThread(() -> Toast.makeText(IDeviceRunningInfoActivity.this, text, Toast.LENGTH_SHORT).show());
        }
    }

    public IDeviceRunningInfo getData() {
        return TMSMaster.getInstance().getDeviceRunningInfo();
    }

    public void getConnectionInfo(View view) {
        try {
            java.util.Map result = getData().getConnectionInfo();
            log("getConnectionInfo: , result=" + JSON.toJSONString(result), view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getConnectionInfo error!", view);
        }
    }

    public void getGPSLocation(View view) {
        try {
            java.util.Map result = getData().getGPSLocation();
            log("getGPSLocation: , result=" + JSON.toJSONString(result), view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getGPSLocation error!", view);
        }
    }

    public void getDeviceStatus(View view) {
        try {
            byte result = getData().getDeviceStatus();
            log("getDeviceStatus: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getDeviceStatus error!", view);
        }
    }

    public void getDeviceUsingData(View view) {
        try {
            java.util.Map result = getData().getDeviceUsingData();
            log("getDeviceUsingData: , result=" + JSON.toJSONString(result), view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getDeviceUsingData error!", view);
        }
    }

    public void getBatteryCapacity(View view) {
        try {
            String result = getData().getBatteryCapacity();
            log("getBatteryCapacity: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getBatteryCapacity error!", view);
        }
    }

    public void getCpuUsage(View view) {
        try {
            float result = getData().getCpuUsage();
            log("getCpuUsage: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getCpuUsage error!", view);
        }
    }

    public void isCharging(View view) {
        try {
            boolean result = getData().isCharging();
            log("isCharging: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("isCharging error!", view);
        }
    }

    public void getGPSLocationWithTimeout(View view) {
        try {
            long timeout = new Long(getEtString(R.id.et_get_g_p_s_location_with_timeout_timeout));
            getData().getGPSLocationWithTimeout(new GPSLocationListener.Stub() {
                @Override
                public void onGPSLocationChanged(Map location) throws RemoteException {
                    log("getGPSLocationWithTimeout: onGPSLocationChanged location=" + location, view);
                }
            }, timeout);
        } catch (Exception e) {
            e.printStackTrace();
            log("getGPSLocationWithTimeout error!", view);
        }
    }

    public void getNetworkLocation(View view) {
        try {
            getData().getNetworkLocation(new NetworkLocationListener.Stub() {
                @Override
                public void onLocationChanged(Map location) throws RemoteException {
                    log("getNetworkLocation: onLocationChanged location=" + location, view);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            log("getNetworkLocation error!", view);
        }
    }

    public void getRamTotalSize(View view) {
        try {
            long result = getData().getRamTotalSize();
            log("getRamTotalSize: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getRamTotalSize error!", view);
        }
    }

    public void getRamUsedSize(View view) {
        try {
            long result = getData().getRamUsedSize();
            log("getRamUsedSize: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getRamUsedSize error!", view);
        }
    }

    public void getRomTotalSize(View view) {
        try {
            long result = getData().getRomTotalSize();
            log("getRomTotalSize: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getRomTotalSize error!", view);
        }
    }

    public void getRomUsedSize(View view) {
        try {
            long result = getData().getRomUsedSize();
            log("getRomUsedSize: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getRomUsedSize error!", view);
        }
    }

    public void getBatteryLevelPercentage(View view) {
        try {
            String result = getData().getBatteryLevelPercentage();
            log("getBatteryLevelPercentage: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getBatteryLevelPercentage error!", view);
        }
    }

    public void getBatteryHealth(View view) {
        try {
            int result = getData().getBatteryHealth();
            log("getBatteryHealth: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getBatteryHealth error!", view);
        }
    }

    public void getBatteryCurrentCapacity(View view) {
        try {
            int result = getData().getBatteryCurrentCapacity();
            log("getBatteryCurrentCapacity: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getBatteryCurrentCapacity error!", view);
        }
    }

    public void getBatteryCurrentElectricity(View view) {
        try {
            int result = getData().getBatteryCurrentElectricity();
            log("getBatteryCurrentElectricity: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getBatteryCurrentElectricity error!", view);
        }
    }

    public void getBatteryChargeCycles(View view) {
        try {
            int result = getData().getBatteryChargeCycles();
            log("getBatteryChargeCycles: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getBatteryChargeCycles error!", view);
        }
    }

    public void getCpuName(View view) {
        try {
            String result = getData().getCpuName();
            log("getCpuName: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getCpuName error!", view);
        }
    }

    public void getTurnOnMills(View view) {
        try {
            long result = getData().getTurnOnMills();
            log("getTurnOnMills: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getTurnOnMills error!", view);
        }
    }

    public void getCpuTemperature(View view) {
        try {
            String result = getData().getCpuTemperature();
            log("getCpuTemperature: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getCpuTemperature error!", view);
        }
    }

    public void getCpuSleepInfo(View view) {
        try {
            String result = getData().getCpuSleepInfo();
            log("getCpuSleepInfo: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getCpuSleepInfo error!", view);
        }
    }

    public void getDeviceUnits(View view) {
        try {
            String result = getData().getDeviceUnits();
            log("getDeviceUnits: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getDeviceUnits error!", view);
        }
    }

    public void getForegroundPackage(View view) {
        try {
            String result = getData().getForegroundPackage();
            log("getForegroundPackage: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getForegroundPackage error!", view);
        }
    }

    public void getMainboardTemperature(View view) {
        try {
            String result = getData().getMainboardTemperature();
            log("getMainboardTemperature: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getMainboardTemperature error!", view);
        }
    }

    public void getBatteryTemperature(View view) {
        try {
            String result = getData().getBatteryTemperature();
            log("getBatteryTemperature: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getBatteryTemperature error!", view);
        }
    }

    public void getBatteryState(View view) {
        try {
            int result = getData().getBatteryStatus();
            log("getBatteryTemperature: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getBatteryTemperature error!", view);
        }
    }

    public void getCpuModel(View view) {
        try {
            String result = getData().getCpuModel();
            log("getCpuModel: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getCpuModel error!", view);
        }
    }

}