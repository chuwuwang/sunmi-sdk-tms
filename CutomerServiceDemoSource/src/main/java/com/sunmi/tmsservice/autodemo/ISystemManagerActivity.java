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

import com.alibaba.fastjson.JSON;
import com.sunmi.cutomerservicedemosource.R;
import com.sunmi.tmsmaster.aidl.networkmanager.IUnifiedCallback;
import com.sunmi.tmsmaster.aidl.systemmanager.ISystemManager;
import com.sunmi.tmsmaster.aidl.systemmanager.listener.OnSystemUpdateListener;
import com.sunmi.tmsservice.apidemo.TMSMaster;

public class ISystemManagerActivity extends AppCompatActivity {

    private static final String TAG = ISystemManagerActivity.class.getName();

    private LinearLayout layout;
    private TextView tvLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_i_system_manager);
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
            runOnUiThread(() -> Toast.makeText(ISystemManagerActivity.this, text, Toast.LENGTH_SHORT).show());
        }
    }

    public com.sunmi.tmsmaster.aidl.systemmanager.ISystemManager getData() {
        return TMSMaster.getInstance().getSystemManager();
    }

    public void updateSystem(View view) {
        try {
            String systemPath = new String(getEtString(R.id.et_update_system_system_path));
            getData().updateSystem(systemPath, new OnSystemUpdateListener.Stub() {
                @Override
                public void progress(int progress) throws RemoteException {

                }

                @Override
                public void verifyPackageFail(String info) throws RemoteException {
                    log("updateSystem: verifyPackageFail systemPath=" + systemPath + ", info=" + info, view);
                }

                @Override
                public void updateSystemFail(String info) throws RemoteException {
                    log("updateSystem: updateSystemFail systemPath=" + systemPath + ", info=" + info, view);

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            log("updateSystem error!", view);
        }
    }

    public void enableGoogleMobileServices(View view) {
        try {
            boolean flag = isCheckBoxSelected(R.id.cb_enable_google_mobile_services_flag);
            getData().enableGoogleMobileServices(flag);
            log("enableGoogleMobileServices: flag=" + flag + ", success=true", view);
        } catch (Exception e) {
            e.printStackTrace();
            log("enableGoogleMobileServices error!", view);
        }
    }

    public void getPCIRebootTime(View view) {
        try {
            int[] result = getData().getPCIRebootTime();
            log("getPCIRebootTime: , result=" + JSON.toJSONString(result), view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getPCIRebootTime error!", view);
        }
    }

    public void settingPCIRebootTime(View view) {
        try {
            int hour = new Integer(getEtString(R.id.et_setting_p_c_i_reboot_time_hour));
            int minute = new Integer(getEtString(R.id.et_setting_p_c_i_reboot_time_minute));
            int second = new Integer(getEtString(R.id.et_setting_p_c_i_reboot_time_second));
            boolean result = getData().settingPCIRebootTime(hour, minute, second);
            log("settingPCIRebootTime: hour=" + hour + ", minute=" + minute + ", second=" + second + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("settingPCIRebootTime error!", view);
        }
    }

    public void clearPCIRebootTime(View view) {
        try {
            getData().clearPCIRebootTime();
            log("clearPCIRebootTime: , success=true", view);
        } catch (Exception e) {
            e.printStackTrace();
            log("clearPCIRebootTime error!", view);
        }
    }

    public void setSystemResetPassword(View view) {
        try {
            String password = new String(getEtString(R.id.et_set_system_reset_password_password));
            boolean result = getData().setSystemResetPassword(password);
            log("setSystemResetPassword: password=" + password + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("setSystemResetPassword error!", view);
        }
    }

    public void updateLocale(View view) {
        try {
            String language = new String(getEtString(R.id.et_update_locale_language));
            boolean result = getData().updateLocale(language);
            log("updateLocale: language=" + language + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("updateLocale error!", view);
        }
    }

    public void updateLocaleWithCountry(View view) {
        try {
            String language = new String(getEtString(R.id.et_update_locale_with_country_language));
            String country = new String(getEtString(R.id.et_update_locale_with_country_country));
            boolean result = getData().updateLocaleWithCountry(language, country);
            log("updateLocaleWithCountry: language=" + language + ", country=" + country + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("updateLocaleWithCountry error!", view);
        }
    }

    public void setDefaultLauncher(View view) {
        try {
            String packageName = new String(getEtString(R.id.et_set_default_launcher_package_name));
            boolean result = getData().setDefaultLauncher(packageName);
            log("setDefaultLauncher: packageName=" + packageName + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("setDefaultLauncher error!", view);
        }
    }

    public void setAdbEnabled(View view) {
        try {
            boolean enabled = isCheckBoxSelected(R.id.cb_set_adb_enabled_enabled);
            boolean result = getData().setAdbEnabled(enabled);
            log("setAdbEnabled: enabled=" + enabled + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("setAdbEnabled error!", view);
        }
    }

    public void isAdbEnabled(View view) {
        try {
            boolean result = getData().isAdbEnabled();
            log("isAdbEnabled: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("isAdbEnabled error!", view);
        }
    }

    public void doScreenshot(View view) {
        try {
            android.os.ParcelFileDescriptor result = getData().doScreenshot();
            log("doScreenshot: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("doScreenshot error!", view);
        }
    }

    public void getBatteryUsageInfos(View view) {
        try {
            com.sunmi.tmsmaster.aidl.systemmanager.BatteryUsageInfo result = getData().getBatteryUsageInfos();
            log("getBatteryUsageInfos: , result=" + JSON.toJSONString(result), view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getBatteryUsageInfos error!", view);
        }
    }

    public void calculateBatteryLastFullChargeTime(View view) {
        try {
            long result = getData().calculateBatteryLastFullChargeTime();
            log("calculateBatteryLastFullChargeTime: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("calculateBatteryLastFullChargeTime error!", view);
        }
    }

    public void calculateBatteryUsagePercent(View view) {
        try {
            long result = getData().calculateBatteryUsagePercent();
            log("calculateBatteryUsagePercent: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("calculateBatteryUsagePercent error!", view);
        }
    }

    public void clearKeyguardPwdAndResetZPattern(View view) {
        try {
            boolean result = getData().clearKeyguardPwdAndResetZPattern();
            log("clearKeyguardPwdAndResetZPattern: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("clearKeyguardPwdAndResetZPattern error!", view);
        }
    }

    public void getNoFilterBatteryUsageInfos(View view) {
        try {
            java.util.List<com.sunmi.tmsmaster.aidl.systemmanager.BatteryEntry> result = getData().getNoFilterBatteryUsageInfos();
            log("getNoFilterBatteryUsageInfos: , result=" + JSON.toJSONString(result), view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getNoFilterBatteryUsageInfos error!", view);
        }
    }

    public void setUsbTaxControlState(View view) {
        try {
            int state = new Integer(getEtString(R.id.et_set_usb_tax_control_state_state));
            boolean result = getData().setUsbTaxControlState(state);
            log("setUsbTaxControlState: state=" + state + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("setUsbTaxControlState error!", view);
        }
    }

    public void getUsbTaxControlState(View view) {
        try {
            int result = getData().getUsbTaxControlState();
            log("getUsbTaxControlState: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getUsbTaxControlState error!", view);
        }
    }

    public void showStreamVolumeDialog(View view) {
        try {
            int type = new Integer(getEtString(R.id.et_show_stream_volume_dialog_type));
            boolean result = getData().showStreamVolumeDialog(type);
            log("showStreamVolumeDialog: type=" + type + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("showStreamVolumeDialog error!", view);
        }
    }

    public void isDebug(View view) {
        try {
            boolean result = getData().isDebug();
            log("isDebug: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("isDebug error!", view);
        }
    }

    public void getLogcatPath(View view) {
        try {
            getData().getLogcatPath(new IUnifiedCallback.Stub() {
                @Override
                public void onCall(String result) throws RemoteException {
                    log("getLogcatPath: , result=" + result, view);

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            log("getLogcatPath error!", view);
        }
    }

    public void setSettingsNeedPassword(View view) {
        try {
            String packageName = new String(getEtString(R.id.et_set_settings_need_password_package_name));
            String password = new String(getEtString(R.id.et_set_settings_need_password_password));
            getData().setSettingsNeedPassword(packageName, password);
            log("setSettingsNeedPassword: packageName=" + packageName + ", password=" + password + ", success=true", view);
        } catch (Exception e) {
            e.printStackTrace();
            log("setSettingsNeedPassword error!", view);
        }
    }

    public void queryAppUsageState(View view) {
        try {
            int bucketType = new Integer(getEtString(R.id.et_query_app_usage_state_bucket_type));
            long startTime = new Long(getEtString(R.id.et_query_app_usage_state_start_time));
            long endTime = new Long(getEtString(R.id.et_query_app_usage_state_end_time));
            java.util.List<com.sunmi.tmsmaster.aidl.systemmanager.AppUsageState> result = getData().queryAppUsageState(bucketType, startTime, endTime);
            log("queryAppUsageState: bucketType=" + bucketType + ", startTime=" + startTime + ", endTime=" + endTime + ", result=" + JSON.toJSONString(result), view);
        } catch (Exception e) {
            e.printStackTrace();
            log("queryAppUsageState error!", view);
        }
    }

    public void getSystemPackageList(View view) {
        try {
            java.util.List<String> result = getData().getSystemPackageList();
            log("getSystemPackageList: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getSystemPackageList error!", view);
        }
    }

    public void enableCommonAppLock(View view) {
        try {
            boolean enable = isCheckBoxSelected(R.id.cb_enable_common_app_lock_enable);
            boolean result = getData().enableCommonAppLock(enable);
            log("enableCommonAppLock: enable=" + enable + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("enableCommonAppLock error!", view);
        }
    }

    public void enableAdbWithResult(View view) {
        try {
            boolean enable = isCheckBoxSelected(R.id.cb_enable_adb_with_result_enable);
            boolean result = getData().enableAdbWithResult(enable);
            log("enableAdbWithResult: enable=" + enable + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("enableAdbWithResult error!", view);
        }
    }

    public void setDefaultInputMethod(View view) {
        try {
            String packageName = new String(getEtString(R.id.et_set_default_input_method_package_name));
            boolean result = getData().setDefaultInputMethod(packageName);
            log("setDefaultInputMethod: packageName=" + packageName + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("setDefaultInputMethod error!", view);
        }
    }

    public void setSystemLanguage(View view) {
        try {
            String language = new String(getEtString(R.id.et_set_system_language_language));
            boolean result = getData().setSystemLanguage(language);
            log("setSystemLanguage: language=" + language + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("setSystemLanguage error!", view);
        }
    }

    public void getBatteryUsageOfEachApp(View view) {
        try {
            java.util.Map result = getData().getBatteryUsageOfEachApp();
            log("getBatteryUsageOfEachApp: , result=" + JSON.toJSONString(result), view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getBatteryUsageOfEachApp error!", view);
        }
    }

    public void queryAppUsageStats(View view) {
        try {
            int intervalType = new Integer(getEtString(R.id.et_query_app_usage_stats_interval_type));
            long beginTime = new Long(getEtString(R.id.et_query_app_usage_stats_begin_time));
            long endTime = new Long(getEtString(R.id.et_query_app_usage_stats_end_time));
            java.util.List<android.app.usage.UsageStats> result = getData().queryAppUsageStats(intervalType, beginTime, endTime);
            log("queryAppUsageStats: intervalType=" + intervalType + ", beginTime=" + beginTime + ", endTime=" + endTime + ", result=" + JSON.toJSONString(result), view);
        } catch (Exception e) {
            e.printStackTrace();
            log("queryAppUsageStats error!", view);
        }
    }

    public void enableSystemOTA(View view) {
        try {
            boolean enable = isCheckBoxSelected(R.id.cb_enable_system_o_t_a_enable);
            getData().enableSystemOTA(enable);
            log("enableSystemOTA: enable=" + enable + ", success=true", view);
        } catch (Exception e) {
            e.printStackTrace();
            log("enableSystemOTA error!", view);
        }
    }

    public void isSystemOTAEnable(View view) {
        try {
            boolean result = getData().isSystemOTAEnable();
            log("isSystemOTAEnable: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("isSystemOTAEnable error!", view);
        }
    }

    public void enableGPSLocation(View view) {
        try {
            boolean enabled = isCheckBoxSelected(R.id.cb_enable_g_p_s_location_enabled);
            getData().enableGPSLocation(enabled);
            log("enableGPSLocation: enabled=" + enabled + ", success=true", view);
        } catch (Exception e) {
            e.printStackTrace();
            log("enableGPSLocation error!", view);
        }
    }

    public void setGooglePlayEnabled(View view) {
        try {
            boolean enabled = isCheckBoxSelected(R.id.cb_set_google_play_enabled_enabled);
            getData().setGooglePlayEnabled(enabled);
            log("setGooglePlayEnabled: enabled=" + enabled + ", success=true", view);
        } catch (Exception e) {
            e.printStackTrace();
            log("setGooglePlayEnabled error!", view);
        }
    }

    public void setGoogleMapEnabled(View view) {
        try {
            boolean enabled = isCheckBoxSelected(R.id.cb_set_google_map_enabled_enabled);
            getData().setGoogleMapEnabled(enabled);
            log("setGoogleMapEnabled: enabled=" + enabled + ", success=true", view);
        } catch (Exception e) {
            e.printStackTrace();
            log("setGoogleMapEnabled error!", view);
        }
    }

    public void setGMSEnabled(View view) {
        try {
            boolean enabled = isCheckBoxSelected(R.id.cb_set_g_m_s_enabled_enabled);
            getData().setGMSEnabled(enabled);
            log("setGMSEnabled: enabled=" + enabled + ", success=true", view);
        } catch (Exception e) {
            e.printStackTrace();
            log("setGMSEnabled error!", view);
        }
    }

    public void setGmailEnabled(View view) {
        try {
            boolean enabled = isCheckBoxSelected(R.id.cb_set_gmail_enabled_enabled);
            getData().setGmailEnabled(enabled);
            log("setGmailEnabled: enabled=" + enabled + ", success=true", view);
        } catch (Exception e) {
            e.printStackTrace();
            log("setGmailEnabled error!", view);
        }
    }

    public void getGmsAppEnabled(View view) {
        try {
            java.util.Map result = getData().getGmsAppEnabled();
            log("getGmsAppEnabled: , result=" + JSON.toJSONString(result), view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getGmsAppEnabled error!", view);
        }
    }

    public void isCommonAppLockEnabled(View view) {
        try {
            boolean result = getData().isCommonAppLockEnabled();
            log("isCommonAppLockEnabled: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("isCommonAppLockEnabled error!", view);
        }
    }

    public void disableSecurityPCI24HoursReboot(View view) {
        try {
            boolean disable = isCheckBoxSelected(R.id.cb_disable_security_p_c_i24_hours_reboot_disable);
            boolean result = getData().disableSecurityPCI24HoursReboot(disable);
            log("disableSecurityPCI24HoursReboot: disable=" + disable + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("disableSecurityPCI24HoursReboot error!", view);
        }
    }

    public void setLockScreenPasswordByType(View view) {
        try {
            int type = new Integer(getEtString(R.id.et_set_lock_screenpassword_by_type_type));
            String password = new String(getEtString(R.id.et_set_lock_screenpassword_by_type_password));
            boolean result = getData().setLockScreenPasswordByType(type, password);
            log("setLockScreenPasswordByType: type=" + type + ", password=" + password + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("setLockScreenPasswordByType error!", view);
        }
    }

}