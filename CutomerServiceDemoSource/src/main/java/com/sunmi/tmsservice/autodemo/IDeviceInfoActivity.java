package com.sunmi.tmsservice.autodemo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.sunmi.cutomerservicedemosource.R;
import com.sunmi.tmsmaster.aidl.deviceinfo.IDeviceInfo;
import com.sunmi.tmsservice.apidemo.TMSMaster;

public class IDeviceInfoActivity extends AppCompatActivity {

    private static final String TAG = IDeviceInfoActivity.class.getName();

    private LinearLayout layout;
    private TextView tvLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_i_device_info);
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
            runOnUiThread(() -> Toast.makeText(IDeviceInfoActivity.this, text, Toast.LENGTH_SHORT).show());
        }
    }

    public IDeviceInfo getData() {
        return TMSMaster.getInstance().getDeviceInfo();
    }

    public void getSerialNo(View view) {
        try {
            String result = getData().getSerialNo();
            log("getSerialNo: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getSerialNo error!", view);
        }
    }

    public void getIMSI(View view) {
        try {
            String result = getData().getIMSI();
            log("getIMSI: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getIMSI error!", view);
        }
    }

    public void getIMEI(View view) {
        try {
            String result = getData().getIMEI();
            log("getIMEI: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getIMEI error!", view);
        }
    }

    public void getICCID(View view) {
        try {
            String result = getData().getICCID();
            log("getICCID: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getICCID error!", view);
        }
    }

    public void getManufacture(View view) {
        try {
            String result = getData().getManufacture();
            log("getManufacture: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getManufacture error!", view);
        }
    }

    public void getModel(View view) {
        try {
            String result = getData().getModel();
            log("getModel: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getModel error!", view);
        }
    }

    public void getAndroidOSVersion(View view) {
        try {
            String result = getData().getAndroidOSVersion();
            log("getAndroidOSVersion: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getAndroidOSVersion error!", view);
        }
    }

    public void getAndroidKernelVersion(View view) {
        try {
            String result = getData().getAndroidKernelVersion();
            log("getAndroidKernelVersion: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getAndroidKernelVersion error!", view);
        }
    }

    public void getROMVersion(View view) {
        try {
            String result = getData().getROMVersion();
            log("getROMVersion: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getROMVersion error!", view);
        }
    }

    public void getROMVersionCode(View view) {
        try {
            String result = getData().getRomVersionCode();
            log("getROMVersionCode: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getROMVersionCode error!", view);
        }
    }

    public void getFirmwareVersion(View view) {
        try {
            String result = getData().getFirmwareVersion();
            log("getFirmwareVersion: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getFirmwareVersion error!", view);
        }
    }

    public void getHardwareVersion(View view) {
        try {
            String result = getData().getHardwareVersion();
            log("getHardwareVersion: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getHardwareVersion error!", view);
        }
    }

    public void getSDKVersion(View view) {
        try {
            String result = getData().getSDKVersion();
            log("getSDKVersion: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getSDKVersion error!", view);
        }
    }

    public void getMemoryOccupation(View view) {
        try {
            long result = getData().getMemoryOccupation();
            log("getMemoryOccupation: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getMemoryOccupation error!", view);
        }
    }

    public void getMac(View view) {
        try {
            String result = getData().getMac();
            log("getMac: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getMac error!", view);
        }
    }

    public void getBrand(View view) {
        try {
            String result = getData().getBrand();
            log("getBrand: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getBrand error!", view);
        }
    }

    public void getSystemProperty(View view) {
        try {
            String key = new String(getEtString(R.id.et_get_system_property_key));
            String result = getData().getSystemProperty(key);
            log("getSystemProperty: key=" + key + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getSystemProperty error!", view);
        }
    }

    public void getIMSIBySlotIndex(View view) {
        try {
            int slotIndex = new Integer(getEtString(R.id.et_get_i_m_s_i_by_slot_index_slot_index));
            String result = getData().getIMSIBySlotIndex(slotIndex);
            log("getIMSIBySlotIndex: slotIndex=" + slotIndex + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getIMSIBySlotIndex error!", view);
        }
    }

    public void getIMEIBySlotIndex(View view) {
        try {
            int slotIndex = new Integer(getEtString(R.id.et_get_i_m_e_i_by_slot_index_slot_index));
            String result = getData().getIMEIBySlotIndex(slotIndex);
            log("getIMEIBySlotIndex: slotIndex=" + slotIndex + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getIMEIBySlotIndex error!", view);
        }
    }

    public void getICCIDBySlotIndex(View view) {
        try {
            int slotIndex = new Integer(getEtString(R.id.et_get_i_c_c_i_d_by_slot_index_slot_index));
            String result = getData().getICCIDBySlotIndex(slotIndex);
            log("getICCIDBySlotIndex: slotIndex=" + slotIndex + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getICCIDBySlotIndex error!", view);
        }
    }

    public void getFullICCID(View view) {
        try {
            int subId = new Integer(getEtString(R.id.et_get_full_i_c_c_i_d_sub_id));
            String result = getData().getFullICCID(subId);
            log("getFullICCID: subId=" + subId + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getFullICCID error!", view);
        }
    }

    public void getDevicesFlashID(View view) {
        try {
            String result = getData().getDevicesFlashID();
            log("getDevicesFlashID: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getDevicesFlashID error!", view);
        }
    }

    public void getSPHS(View view) {
        try {
            String result = getData().getSPHS();
            log("getSPHS: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getSPHS error!", view);
        }
    }

    public void getSV(View view) {
        try {
            String result = getData().getSV();
            log("getSV: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getSV error!", view);
        }
    }

    public void getBatteryModelName(View view) {
        try {
            String result = getData().getBatteryModelName();
            log("getBatteryModelName: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getBatteryModelName error!", view);
        }
    }

    public void getBatterySerialNo(View view) {
        try {
            String result = getData().getBatterySerialNo();
            log("getBatterySerialNo: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getBatterySerialNo error!", view);
        }
    }

    public void getBatteryPartNumber(View view) {
        try {
            String result = getData().getBatteryPartNumber();
            log("getBatteryPartNumber: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getBatteryPartNumber error!", view);
        }
    }

    public void getBatteryManufactureDate(View view) {
        try {
            long result = getData().getBatteryManufactureDate();
            log("getBatteryManufactureDate: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getBatteryManufactureDate error!", view);
        }
    }

    public void getBatteryFirstUseTime(View view) {
        try {
            long result = getData().getBatteryFirstUseTime();
            log("getBatteryFirstUseTime: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getBatteryFirstUseTime error!", view);
        }
    }

    public void getBatteryMaxCapacity(View view) {
        try {
            int result = getData().getBatteryMaxCapacity();
            log("getBatteryMaxCapacity: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getBatteryMaxCapacity error!", view);
        }
    }

    public void getSunmiOsVersion(View view) {
        try {
            String result = getData().getSunmiOsVersion();
            log("getSunmiOsVersion: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getSunmiOsVersion error!", view);
        }
    }

    public void getIpAddresses(View view) {
        try {
            String result = getData().getIpAddresses();
            log("getIpAddresses: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getIpAddresses error!", view);
        }
    }

    public void getDeviceBluetoothMacAddress(View view) {
        try {
            String result = getData().getDeviceBluetoothMacAddress();
            log("getDeviceBluetoothMacAddress: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getDeviceBluetoothMacAddress error!", view);
        }
    }

    public void isFinancialDevice(View view) {
        try {
            boolean result = getData().isFinancialDevice();
            log("isFinancialDevice: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("isFinancialDevice error!", view);
        }
    }

    public void verifyCrp(View view) {
        try {
            String filePath = new String(getEtString(R.id.et_verify_crp_file_path));
            int result = getData().verifyCrp(filePath);
            log("verifyCrp: filePath=" + filePath + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("verifyCrp error!", view);
        }
    }

    public void installCrp(View view) {
        try {
            String filePath = new String(getEtString(R.id.et_install_crp_file_path));
            int result = getData().installCrp(filePath);
            log("installCrp: filePath=" + filePath + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("installCrp error!", view);
        }
    }

    public void getCrpType(View view) {
        try {
            String filePath = new String(getEtString(R.id.et_get_crp_type_file_path));
            int result = getData().getCrpType(filePath);
            log("getCrpType: filePath=" + filePath + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getCrpType error!", view);
        }
    }

    public void getCrpVersion(View view) {
        try {
            int crpType = new Integer(getEtString(R.id.et_get_crp_version_crp_type));
            String result = getData().getCrpVersion(crpType);
            log("getCrpVersion: crpType=" + crpType + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getCrpVersion error!", view);
        }
    }

    public void getGmsFlag(View view) {
        try {
            int result = getData().getGmsFlag();
            log("getGmsFlag: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getGmsFlag error!", view);
        }
    }

    public void isInnerScannerSupported(View view) {
        try {
            boolean result = getData().isInnerScannerSupported();
            log("isInnerScannerSupported: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("isInnerScannerSupported error!", view);
        }
    }

    public void isInnerPrinterSupported(View view) {
        try {
            boolean result = getData().isInnerPrinterSupported();
            log("isInnerPrinterSupported: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("isInnerPrinterSupported error!", view);
        }
    }

    public void getDisplayId(View view) {
        try {
            String result = getData().getDisplayId();
            log("getDisplayId: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getDisplayId error!", view);
        }
    }

    public void getEtnMac(View view) {
        try {
            String result = getData().getEtnMac();
            log("getEtnMac: , result = " + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getEtnMac error", view);
        }
    }
}
