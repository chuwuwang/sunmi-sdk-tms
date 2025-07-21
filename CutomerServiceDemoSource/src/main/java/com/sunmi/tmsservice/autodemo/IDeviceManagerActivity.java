package com.sunmi.tmsservice.autodemo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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
import com.sunmi.tmsmaster.aidl.devicemanager.IDeviceManager;
import com.sunmi.tmsmaster.aidl.devicemanager.listener.OnResetAppsListener;
import com.sunmi.tmsservice.apidemo.TMSMaster;

public class IDeviceManagerActivity extends AppCompatActivity {

    private static final String TAG = IDeviceManagerActivity.class.getName();

    private LinearLayout layout;
    private TextView tvLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_i_device_manager);
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
            runOnUiThread(() -> Toast.makeText(IDeviceManagerActivity.this, text, Toast.LENGTH_SHORT).show());
        }
    }

    public IDeviceManager getData() {
        return TMSMaster.getInstance().getDeviceManager();
    }

    public void setSystemTime(View view) {
        try {
            int second = new Integer(getEtString(R.id.et_set_system_time_second));
            int minute = new Integer(getEtString(R.id.et_set_system_time_minute));
            int hour = new Integer(getEtString(R.id.et_set_system_time_hour));
            int day = new Integer(getEtString(R.id.et_set_system_time_day));
            int month = new Integer(getEtString(R.id.et_set_system_time_month));
            int year = new Integer(getEtString(R.id.et_set_system_time_year));
            getData().setSystemTime(second, minute, hour, day, month, year);
            log("setSystemTime: second=" + second + ", minute=" + minute + ", hour=" + hour + ", day=" + day + ", month=" + month + ", year=" + year + ", success=true", view);
        } catch (Exception e) {
            e.printStackTrace();
            log("setSystemTime error!", view);
        }
    }

    public void powerReboot(View view) {
        try {
            getData().powerReboot();
            log("powerReboot: , success=true", view);
        } catch (Exception e) {
            e.printStackTrace();
            log("powerReboot error!", view);
        }
    }

    public void shutdown(View view) {
        try {
            getData().shutdown();
            log("shutdown: , success=true", view);
        } catch (Exception e) {
            e.printStackTrace();
            log("shutdown error!", view);
        }
    }

    public void setTimeZone(View view) {
        try {
            String timeZone = new String(getEtString(R.id.et_set_time_zone_time_zone));
            getData().setTimeZone(timeZone);
            log("setTimeZone: timeZone=" + timeZone + ", success=true", view);
        } catch (Exception e) {
            e.printStackTrace();
            log("setTimeZone error!", view);
        }
    }

    public void factoryReset(View view) {
        try {
            getData().factoryReset();
            log("factoryReset: , success=true", view);
        } catch (Exception e) {
            e.printStackTrace();
            log("factoryReset error!", view);
        }
    }

    public void resetApps(View view) {
        try {
            getData().resetApps(new OnResetAppsListener.Stub() {
                @Override
                public void progress(int progress) throws RemoteException {
                    log("resetApps: progress=" + progress, view);
                }

                @Override
                public void resetAppFail(String packageName, int returnCode) throws RemoteException {
                    log("resetApps: resetAppFail packageName=" + packageName + ", returnCode=" + returnCode, view);

                }

                @Override
                public void resetAppSuccess(String packageName, int returnCode) throws RemoteException {
                    log("resetApps: resetAppSuccess packageName=" + packageName + ", returnCode=" + returnCode, view);

                }

                @Override
                public void resetAppNoPermission() throws RemoteException {
                    log("resetApps: resetAppNoPermission", view);

                }

                @Override
                public void resetNoApps() throws RemoteException {
                    log("resetApps: resetNoApps", view);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            log("resetApps error!", view);
        }
    }

    public void toSleep(View view) {
        try {
            getData().toSleep();
            log("toSleep: , success=true", view);
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    try {
                        log("toWakeUp: , success=true", view);
                        getData().toWakeUp();
                    } catch (Exception e) {
                        e.printStackTrace();
                        log("toWakeUp error!", view);
                    }
                }
            }, 1000L);
        } catch (Exception e) {
            e.printStackTrace();
            log("toSleep error!", view);
        }
    }

    public void toWakeUp(View view) {
        try {
            getData().toWakeUp();
            log("toWakeUp: , success=true", view);
        } catch (Exception e) {
            e.printStackTrace();
            log("toWakeUp error!", view);
        }
    }

    public void setBootAnimation(View view) {
        try {
            String filePath = new String(getEtString(R.id.et_set_boot_animation_file_path));
            getData().setBootAnimation(filePath);
            log("setBootAnimation: filePath=" + filePath + ", success=true", view);
        } catch (Exception e) {
            e.printStackTrace();
            log("setBootAnimation error!", view);
        }
    }

    public void enableBluetooth(View view) {
        try {
            boolean enable = isCheckBoxSelected(R.id.cb_enable_bluetooth_enable);
            getData().enableBluetooth(enable);
            log("enableBluetooth: enable=" + enable + ", success=true", view);
        } catch (Exception e) {
            e.printStackTrace();
            log("enableBluetooth error!", view);
        }
    }

    public void setAutoTimeType(View view) {
        try {
            int type = new Integer(getEtString(R.id.et_set_auto_time_type_type));
            getData().setAutoTimeType(type);
            log("setAutoTimeType: type=" + type + ", success=true", view);
        } catch (Exception e) {
            e.printStackTrace();
            log("setAutoTimeType error!", view);
        }
    }

    public void setCustomKeyFunction(View view) {
        try {
            String key = new String(getEtString(R.id.et_set_custom_key_function_key));
            String type = new String(getEtString(R.id.et_set_custom_key_function_type));
            String value = new String(getEtString(R.id.et_set_custom_key_function_value));
            getData().setCustomKeyFunction(key, type, value);
            log("setCustomKeyFunction: key=" + key + ", type=" + type + ", value=" + value + ", success=true", view);
        } catch (Exception e) {
            e.printStackTrace();
            log("setCustomKeyFunction error!", view);
        }
    }

    public void setPowerButtonEnabled(View view) {
        try {
            boolean enabled = isCheckBoxSelected(R.id.cb_set_power_button_enabled_enabled);
            boolean result = getData().setPowerButtonEnabled(enabled);
            log("setPowerButtonEnabled: enabled=" + enabled + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("setPowerButtonEnabled error!", view);
        }
    }

    public void isPowerButtonEnabled(View view) {
        try {
            boolean result = getData().isPowerButtonEnabled();
            log("isPowerButtonEnabled: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("isPowerButtonEnabled error!", view);
        }
    }

    public void setSystemProperty(View view) {
        try {
            String key = new String(getEtString(R.id.et_set_system_property_key));
            String value = new String(getEtString(R.id.et_set_system_property_value));
            boolean result = getData().setSystemProperty(key, value);
            log("setSystemProperty: key=" + key + ", value=" + value + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("setSystemProperty error!", view);
        }
    }

    public void enableGPS(View view) {
        try {
            boolean isEnable = isCheckBoxSelected(R.id.cb_enable_g_p_s_is_enable);
            boolean result = getData().enableGPS(isEnable);
            log("enableGPS: isEnable=" + isEnable + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("enableGPS error!", view);
        }
    }

    public void enableWIFI(View view) {
        try {
            boolean isEnable = isCheckBoxSelected(R.id.cb_enable_w_i_f_i_is_enable);
            boolean result = getData().enableWIFI(isEnable);
            log("enableWIFI: isEnable=" + isEnable + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("enableWIFI error!", view);
        }
    }

    public void enableLockDevice(View view) {
        try {
            boolean enable = isCheckBoxSelected(R.id.cb_enable_lock_device_enable);
            boolean result = getData().enableLockDevice(enable);
            log("enableLockDevice: enable=" + enable + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("enableLockDevice error!", view);
        }
    }

    public void setWallpaper(View view) {
        try {
            String filePath = new String(getEtString(R.id.et_set_wallpaper_file_path));
            getData().setWallpaper(filePath);
            log("setWallpaper: filePath=" + filePath + ", success=true", view);
        } catch (Exception e) {
            e.printStackTrace();
            log("setWallpaper error!", view);
        }
    }

    public void enableAirplaneMode(View view) {
        try {
            boolean enable = isCheckBoxSelected(R.id.cb_enable_airplane_mode_enable);
            boolean result = getData().enableAirplaneMode(enable);
            log("enableAirplaneMode: enable=" + enable + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("enableAirplaneMode error!", view);
        }
    }

    public void setAirplaneModeLocked(View view) {
        try {
            boolean locked = isCheckBoxSelected(R.id.cb_set_airplane_mode_locked_locked);
            boolean result = getData().setAirplaneModeLocked(locked);
            log("setAirplaneModeLocked: locked=" + locked + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("setAirplaneModeLocked error!", view);
        }
    }

    public void switchDeviceEnable(View view) {
        try {
            boolean enable = isCheckBoxSelected(R.id.cb_switch_device_enable_enable);
            boolean result = getData().switchDeviceEnable(enable);
            log("switchDeviceEnable: enable=" + enable + ", result=" + result, view);

            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    try {
                        boolean result = getData().switchDeviceEnable(true);
                        log("switchDeviceEnable: enable=" + true + ", result=" + result, view);
                    } catch (Exception e) {
                        e.printStackTrace();
                        log("switchDeviceEnable error!", view);
                    }
                }
            }, 3000L);
        } catch (Exception e) {
            e.printStackTrace();
            log("switchDeviceEnable error!", view);
        }
    }

    public void setScreenBrightness(View view) {
        try {
            int value = new Integer(getEtString(R.id.et_set_screen_brightness_value));
            boolean result = getData().setScreenBrightness(value);
            log("setScreenBrightness: value=" + value + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("setScreenBrightness error!", view);
        }
    }

    public void setScreenTimeout(View view) {
        try {
            int timeout = new Integer(getEtString(R.id.et_set_screen_timeout_timeout));
            boolean result = getData().setScreenTimeout(timeout);
            log("setScreenTimeout: timeout=" + timeout + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("setScreenTimeout error!", view);
        }
    }

    public void enableNFC(View view) {
        try {
            boolean enable = isCheckBoxSelected(R.id.cb_enable_n_f_c_enable);
            boolean result = getData().enableNFC(enable);
            log("enableNFC: enable=" + enable + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("enableNFC error!", view);
        }
    }

    public void setBrightnessMode(View view) {
        try {
            int mode = new Integer(getEtString(R.id.et_set_brightness_mode_mode));
            getData().setBrightnessMode(mode);
            log("setBrightnessMode: mode=" + mode + ", success=true", view);
        } catch (Exception e) {
            e.printStackTrace();
            log("setBrightnessMode error!", view);
        }
    }

    public void setSubScreenBrightness(View view) {
        try {
            int brightness = new Integer(getEtString(R.id.et_set_sub_screen_brightness_brightness));
            getData().setSubScreenBrightness(brightness);
            log("setSubScreenBrightness: brightness=" + brightness + ", success=true", view);
        } catch (Exception e) {
            e.printStackTrace();
            log("setSubScreenBrightness error!", view);
        }
    }

    public void getMinimumScreenBrightnessSetting(View view) {
        try {
            int result = getData().getMinimumScreenBrightnessSetting();
            log("getMinimumScreenBrightnessSetting: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getMinimumScreenBrightnessSetting error!", view);
        }
    }

    public void getMaximumScreenBrightnessSetting(View view) {
        try {
            int result = getData().getMaximumScreenBrightnessSetting();
            log("getMaximumScreenBrightnessSetting: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getMaximumScreenBrightnessSetting error!", view);
        }
    }

    public void setSmartChargeLimit(View view) {
        try {
            int minFactoryCapacity = new Integer(getEtString(R.id.et_set_smart_charge_limit_min_factory_capacity));
            int maxFactoryCapacity = new Integer(getEtString(R.id.et_set_smart_charge_limit_max_factory_capacity));
            boolean result = getData().setSmartChargeLimit(minFactoryCapacity, maxFactoryCapacity);
            log("setSmartChargeLimit: minFactoryCapacity=" + minFactoryCapacity + ", maxFactoryCapacity=" + maxFactoryCapacity + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("setSmartChargeLimit error!", view);
        }
    }

    public void openSmartCharge(View view) {
        try {
            boolean open = isCheckBoxSelected(R.id.cb_open_smart_charge_open);
            boolean result = getData().openSmartCharge(open);
            log("openSmartCharge: open=" + open + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("openSmartCharge error!", view);
        }
    }

    public void isDeviceEnabled(View view) {
        try {
            boolean result = getData().isDeviceEnabled();
            log("isDeviceEnabled: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("isDeviceEnabled error!", view);
        }
    }

    public void enableDefaultPrinterService(View view) {
        try {
            boolean enable = isCheckBoxSelected(R.id.cb_enable_default_printer_service_enable);
            boolean result = getData().enableDefaultPrinterService(enable);
            log("enableDefaultPrinterService: enable=" + enable + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("enableDefaultPrinterService error!", view);
        }
    }

    public void setLocationEnabled(View view) {
        try {
            boolean enable = isCheckBoxSelected(R.id.cb_set_location_enabled_enable);
            getData().setLocationEnabled(enable);
            log("setLocationEnabled: enable=" + enable + ", success=true", view);
        } catch (Exception e) {
            e.printStackTrace();
            log("setLocationEnabled error!", view);
        }
    }

    public void setDeviceOwner(View view) {
        try {
            String packName = new String(getEtString(R.id.et_set_device_owner_pack_name));
            String classFullPathName = new String(getEtString(R.id.et_set_device_owner_class_full_path_name));
            boolean result = getData().setDeviceOwner(packName, classFullPathName);
            log("setDeviceOwner: packName=" + packName + ", classFullPathName=" + classFullPathName + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("setDeviceOwner error!", view);
        }
    }

    public void forbiddenCamera(View view) {
        try {
            boolean disable = isCheckBoxSelected(R.id.cb_forbidden_camera_disable);
            boolean result = getData().forbiddenCamera(disable);
            log("forbiddenCamera: disable=" + disable + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("forbiddenCamera error!", view);
        }
    }

    public void setScreenSaverPicture(View view) {
        try {
            String filePath = new String(getEtString(R.id.et_set_screen_saver_picture_file_path));
            boolean result = getData().setScreenSaverPicture(filePath);
            log("setScreenSaverPicture: filePath=" + filePath + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("setScreenSaverPicture error!", view);
        }
    }

    public void enableScreenSaver(View view) {
        try {
            boolean enable = isCheckBoxSelected(R.id.cb_enable_screen_saver_enable);
            boolean result = getData().enableScreenSaver(enable);
            log("enableScreenSaver: enable=" + enable + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("enableScreenSaver error!", view);
        }
    }

    public void setScreenSaverTime(View view) {
        try {
            int second = new Integer(getEtString(R.id.et_set_screen_saver_time_second));
            getData().setScreenSaverTime(second);
            log("setScreenSaverTime: second=" + second + ", success=true", view);
        } catch (Exception e) {
            e.printStackTrace();
            log("setScreenSaverTime error!", view);
        }
    }

    public void getScreenSaverTime(View view) {
        try {
            int result = getData().getScreenSaverTime();
            log("getScreenSaverTime: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getScreenSaverTime error!", view);
        }
    }

    public void setScreenSaverTimeWithResult(View view) {
        try {
            int millisecond = new Integer(getEtString(R.id.et_set_screen_saver_time_with_result_millisecond));
            int result = getData().setScreenSaverTimeWithResult(millisecond);
            log("setScreenSaverTimeWithResult: millisecond=" + millisecond + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("setScreenSaverTimeWithResult error!", view);
        }
    }

    public void switchBTModuleEnable(View view) {
        try {
            boolean enable = isCheckBoxSelected(R.id.cb_switch_b_t_module_enable_enable);
            boolean result = getData().switchBTModuleEnable(enable);
            log("switchBTModuleEnable: enable=" + enable + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("switchBTModuleEnable error!", view);
        }
    }

    public void isBTModuleEnabled(View view) {
        try {
            boolean result = getData().isBTModuleEnabled();
            log("isBTModuleEnabled: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("isBTModuleEnabled error!", view);
        }
    }

    public void setBrightness(View view) {
        try {
            int value = new Integer(getEtString(R.id.et_set_brightness_value));
            boolean result = getData().setBrightness(value);
            log("setBrightness: value=" + value + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("setBrightness error!", view);
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

    public void enableAutoTime(View view) {
        try {
            boolean enable = isCheckBoxSelected(R.id.cb_enable_auto_time_enable);
            boolean result = getData().enableAutoTime(enable);
            log("enableAutoTime: enable=" + enable + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("enableAutoTime error!", view);
        }
    }

    public void enableAutoTimeZone(View view) {
        try {
            boolean enable = isCheckBoxSelected(R.id.cb_enable_auto_time_zone_enable);
            boolean result = getData().enableAutoTimeZone(enable);
            log("enableAutoTimeZone: enable=" + enable + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("enableAutoTimeZone error!", view);
        }
    }

    public void enableKeyEvent(View view) {
        try {
            boolean enable = isCheckBoxSelected(R.id.cb_enable_key_event_enable);
            getData().enableKeyEvent(enable);
            log("enableKeyEvent: enable=" + enable + ", success=true", view);
        } catch (Exception e) {
            e.printStackTrace();
            log("enableKeyEvent error!", view);
        }
    }

    public void enableLocation(View view) {
        try {
            boolean enable = isCheckBoxSelected(R.id.cb_enable_location_enable);
            getData().enableLocation(enable);
            log("enableLocation: enable=" + enable + ", success=true", view);
        } catch (Exception e) {
            e.printStackTrace();
            log("enableLocation error!", view);
        }
    }

    public void set24Hour(View view) {
        try {
            boolean is24Hour = isCheckBoxSelected(R.id.cb_set24_hour_is24_hour);
            getData().set24Hour(is24Hour);
            log("set24Hour: is24Hour=" + is24Hour + ", success=true", view);
        } catch (Exception e) {
            e.printStackTrace();
            log("set24Hour error!", view);
        }
    }

    public void enablePowerKey(View view) {
        try {
            boolean enable = isCheckBoxSelected(R.id.cb_enable_power_key_enable);
            getData().enablePowerKey(enable);
            log("enablePowerKey: enable=" + enable + ", success=true", view);
        } catch (Exception e) {
            e.printStackTrace();
            log("enablePowerKey error!", view);
        }
    }

    public void isPowerKeyEnabled(View view) {
        try {
            boolean result = getData().isPowerKeyEnabled();
            log("isPowerKeyEnabled: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("isPowerKeyEnabled error!", view);
        }
    }

    public void enableShortPressPowerKey(View view) {
        try {
            boolean enable = isCheckBoxSelected(R.id.cb_enable_short_press_power_key_enable);
            getData().enableShortPressPowerKey(enable);
            log("enableShortPressPowerKey: enable=" + enable + ", success=true", view);
        } catch (Exception e) {
            e.printStackTrace();
            log("enableShortPressPowerKey error!", view);
        }
    }

    public void enableUsbPermissionDialog(View view) {
        try {
            boolean enable = isCheckBoxSelected(R.id.cb_enable_usb_permission_dialog_enable);
            getData().enableUsbPermissionDialog(enable);
            log("enableUsbPermissionDialog: enable=" + enable + ", success=true", view);
        } catch (Exception e) {
            e.printStackTrace();
            log("enableUsbPermissionDialog error!", view);
        }
    }

    public void getScreenTimeout(View view) {
        try {
            int result = getData().getScreenTimeout();
            log("getScreenTimeout: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getScreenTimeout error!", view);
        }
    }

    public void setBatteryWorkMode(View view) {
        try {
            int mode = new Integer(getEtString(R.id.et_set_battery_work_mode_mode));
            boolean result = getData().setBatteryWorkMode(mode);
            log("setBatteryWorkMode: mode=" + mode + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("setBatteryWorkMode error!", view);
        }
    }

    public void getBatteryWorkMode(View view) {
        try {
            int result = getData().getBatteryWorkMode();
            log("getBatteryWorkMode: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getBatteryWorkMode error!", view);
        }
    }

    public void enableQSPanelIcon(View view) {
        try {
            String icon = new String(getEtString(R.id.et_enable_q_s_panel_icon_icon));
            boolean enable = isCheckBoxSelected(R.id.cb_enable_q_s_panel_icon_enable);
            boolean result = getData().enableQSPanelIcon(icon, enable);
            log("enableQSPanelIcon: icon=" + icon + ", enable=" + enable + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("enableQSPanelIcon error!", view);
        }
    }

    public void setAirplaneMode(View view) {
        try {
            boolean open = isCheckBoxSelected(R.id.cb_set_airplane_mode_open);
            boolean result = getData().setAirplaneMode(open);
            log("setAirplaneMode: open=" + open + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("setAirplaneMode error!", view);
        }
    }

    public void clearDeviceOwner(View view) {
        try {
            boolean result = getData().clearDeviceOwner();
            log("clearDeviceOwner: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("clearDeviceOwner error!", view);
        }
    }

    public void enableBootOrShutdownRegularly(View view) {
        try {
            int type = new Integer(getEtString(R.id.et_enable_boot_or_shutdown_regularly_type));
            int hour = new Integer(getEtString(R.id.et_enable_boot_or_shutdown_regularly_hour));
            int minutes = new Integer(getEtString(R.id.et_enable_boot_or_shutdown_regularly_minutes));
            int repeatMode = new Integer(getEtString(R.id.et_enable_boot_or_shutdown_regularly_repeat_mode));
            boolean result = getData().enableBootOrShutdownRegularly(type, hour, minutes, repeatMode);
            log("enableBootOrShutdownRegularly: type=" + type + ", hour=" + hour + ", minutes=" + minutes + ", repeatMode=" + repeatMode + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("enableBootOrShutdownRegularly error!", view);
        }
    }

    public void disableBootOrShutdownRegularly(View view) {
        try {
            int type = new Integer(getEtString(R.id.et_disable_boot_or_shutdown_regularly_type));
            boolean result = getData().disableBootOrShutdownRegularly(type);
            log("disableBootOrShutdownRegularly: type=" + type + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("disableBootOrShutdownRegularly error!", view);
        }
    }

    public void disableNitzTimeZone(View view) {
        try {
            boolean disable = isCheckBoxSelected(R.id.cb_disable_nitz_time_zone_disable);
            boolean result = getData().disableNitzTimeZone(disable);
            log("disableNitzTimeZone: disable=" + disable + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("disableNitzTimeZone error!", view);
        }
    }

    public void enableTalkback(View view) {
        try {
            boolean enable = isCheckBoxSelected(R.id.cb_enable_talkback_enable);
            boolean result = getData().enableTalkback(enable);
            log("enableTalkback: enable=" + enable + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("enableTalkback error!", view);
        }
    }

    public void exeTopCmd(View view) {
        try {
            int count = new Integer(getEtString(R.id.et_exe_top_cmd_count));
            String result = getData().exeTopCmd(count);
            log("exeTopCmd: count=" + count + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("exeTopCmd error!", view);
        }
    }
}