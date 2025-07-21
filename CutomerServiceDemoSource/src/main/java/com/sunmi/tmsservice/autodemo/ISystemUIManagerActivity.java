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
import com.sunmi.tmsmaster.aidl.systemuimanager.ISystemUIManager;
import com.sunmi.tmsservice.apidemo.TMSMaster;

public class ISystemUIManagerActivity extends AppCompatActivity {

    private static final String TAG = ISystemUIManagerActivity.class.getName();

    private LinearLayout layout;
    private TextView tvLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_i_system_u_i_manager);
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
            runOnUiThread(() -> Toast.makeText(ISystemUIManagerActivity.this, text, Toast.LENGTH_SHORT).show());
        }
    }

    public ISystemUIManager getData() {
        return TMSMaster.getInstance().getSystemUiManager();
    }

    public void enableNavigationBar(View view) {
        try {
            byte flag = 1;
            getData().enableNavigationBar(flag);
            log("enableNavigationBar: , success=true", view);
        } catch (Exception e) {
            e.printStackTrace();
            log("enableNavigationBar error!", view);
        }
    }

    public void enableLockScreen(View view) {
        try {
            boolean flag = isCheckBoxSelected(R.id.cb_enable_lock_screen_flag);
            getData().enableLockScreen(flag);
            log("enableLockScreen: flag=" + flag + ", success=true", view);
        } catch (Exception e) {
            e.printStackTrace();
            log("enableLockScreen error!", view);
        }
    }

    public void enableNotification(View view) {
        try {
            String packagename = new String(getEtString(R.id.et_enable_notification_packagename));
            boolean flag = isCheckBoxSelected(R.id.cb_enable_notification_flag);
            getData().enableNotification(packagename, flag);
            log("enableNotification: packagename=" + packagename + ", flag=" + flag + ", success=true", view);
        } catch (Exception e) {
            e.printStackTrace();
            log("enableNotification error!", view);
        }
    }

    public void enablePoweLockScreen(View view) {
        try {
            boolean flag = isCheckBoxSelected(R.id.cb_enable_powe_lock_screen_flag);
            getData().enablePoweLockScreen(flag);
            log("enablePoweLockScreen: flag=" + flag + ", success=true", view);
        } catch (Exception e) {
            e.printStackTrace();
            log("enablePoweLockScreen error!", view);
        }
    }

    public void showNavigationBarBackButton(View view) {
        try {
            boolean show = isCheckBoxSelected(R.id.cb_show_navigation_bar_back_button_show);
            getData().showNavigationBarBackButton(show);
            log("showNavigationBarBackButton: show=" + show + ", success=true", view);
        } catch (Exception e) {
            e.printStackTrace();
            log("showNavigationBarBackButton error!", view);
        }
    }

    public void showNavigationBarHomeButton(View view) {
        try {
            boolean show = isCheckBoxSelected(R.id.cb_show_navigation_bar_home_button_show);
            getData().showNavigationBarHomeButton(show);
            log("showNavigationBarHomeButton: show=" + show + ", success=true", view);
        } catch (Exception e) {
            e.printStackTrace();
            log("showNavigationBarHomeButton error!", view);
        }
    }

    public void showNavigationBarRecentsButton(View view) {
        try {
            boolean show = isCheckBoxSelected(R.id.cb_show_navigation_bar_recents_button_show);
            getData().showNavigationBarRecentsButton(show);
            log("showNavigationBarRecentsButton: show=" + show + ", success=true", view);
        } catch (Exception e) {
            e.printStackTrace();
            log("showNavigationBarRecentsButton error!", view);
        }
    }

    public void showNotificationPanel(View view) {
        try {
            boolean show = isCheckBoxSelected(R.id.cb_show_notification_panel_show);
            getData().showNotificationPanel(show);
            log("showNotificationPanel: show=" + show + ", success=true", view);
        } catch (Exception e) {
            e.printStackTrace();
            log("showNotificationPanel error!", view);
        }
    }

    public void showNavigationBar(View view) {
        try {
            boolean show = isCheckBoxSelected(R.id.cb_show_navigation_bar_show);
            getData().showNavigationBar(show);
            log("showNavigationBar: show=" + show + ", success=true", view);
        } catch (Exception e) {
            e.printStackTrace();
            log("showNavigationBar error!", view);
        }
    }

    public void showStatusBar(View view) {
        try {
            boolean show = isCheckBoxSelected(R.id.cb_show_status_bar_show);
            getData().showStatusBar(show);
            log("showStatusBar: show=" + show + ", success=true", view);
        } catch (Exception e) {
            e.printStackTrace();
            log("showStatusBar error!", view);
        }
    }

    public void enableNavigationBarBackButton(View view) {
        try {
            boolean enable = isCheckBoxSelected(R.id.cb_enable_navigation_bar_back_button_enable);
            getData().enableNavigationBarBackButton(enable);
            log("enableNavigationBarBackButton: enable=" + enable + ", success=true", view);
        } catch (Exception e) {
            e.printStackTrace();
            log("enableNavigationBarBackButton error!", view);
        }
    }

    public void isNavigationBarBackButtonEnabled(View view) {
        try {
            boolean result = getData().isNavigationBarBackButtonEnabled();
            log("isNavigationBarBackButtonEnabled: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("isNavigationBarBackButtonEnabled error!", view);
        }
    }

    public void enableNavigationBarHomeButton(View view) {
        try {
            boolean enable = isCheckBoxSelected(R.id.cb_enable_navigation_bar_home_button_enable);
            getData().enableNavigationBarHomeButton(enable);
            log("enableNavigationBarHomeButton: enable=" + enable + ", success=true", view);
        } catch (Exception e) {
            e.printStackTrace();
            log("enableNavigationBarHomeButton error!", view);
        }
    }

    public void isNavigationBarHomeButtonEnabled(View view) {
        try {
            boolean result = getData().isNavigationBarHomeButtonEnabled();
            log("isNavigationBarHomeButtonEnabled: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("isNavigationBarHomeButtonEnabled error!", view);
        }
    }

    public void enableNavigationBarRecentsButton(View view) {
        try {
            boolean enable = isCheckBoxSelected(R.id.cb_enable_navigation_bar_recents_button_enable);
            getData().enableNavigationBarRecentsButton(enable);
            log("enableNavigationBarRecentsButton: enable=" + enable + ", success=true", view);
        } catch (Exception e) {
            e.printStackTrace();
            log("enableNavigationBarRecentsButton error!", view);
        }
    }

    public void isNavigationBarRecentsButtonEnabled(View view) {
        try {
            boolean result = getData().isNavigationBarRecentsButtonEnabled();
            log("isNavigationBarRecentsButtonEnabled: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("isNavigationBarRecentsButtonEnabled error!", view);
        }
    }

    public void enableStatusBar(View view) {
        try {
            boolean enable = isCheckBoxSelected(R.id.cb_enable_status_bar_enable);
            getData().enableStatusBar(enable);
            log("enableStatusBar: enable=" + enable + ", success=true", view);
        } catch (Exception e) {
            e.printStackTrace();
            log("enableStatusBar error!", view);
        }
    }

    public void enableNotificationPanel(View view) {
        try {
            boolean enable = isCheckBoxSelected(R.id.cb_enable_notification_panel_enable);
            getData().enableNotificationPanel(enable);
            log("enableNotificationPanel: enable=" + enable + ", success=true", view);
        } catch (Exception e) {
            e.printStackTrace();
            log("enableNotificationPanel error!", view);
        }
    }

    public void clickableNavigationBar(View view) {
        try {
            boolean clickable = isCheckBoxSelected(R.id.cb_clickable_navigation_bar_clickable);
            getData().clickableNavigationBar(clickable);
            log("clickableNavigationBar: clickable=" + clickable + ", success=true", view);
        } catch (Exception e) {
            e.printStackTrace();
            log("clickableNavigationBar error!", view);
        }
    }

    public void setWifiBarClickable(View view) {
        try {
            boolean clickable = isCheckBoxSelected(R.id.cb_set_wifi_bar_clickable_clickable);
            boolean result = getData().setWifiBarClickable(clickable);
            log("setWifiBarClickable: clickable=" + clickable + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("setWifiBarClickable error!", view);
        }
    }

    public void setAirplaneModeBarClickable(View view) {
        try {
            boolean clickable = isCheckBoxSelected(R.id.cb_set_airplane_mode_bar_clickable_clickable);
            boolean result = getData().setAirplaneModeBarClickable(clickable);
            log("setAirplaneModeBarClickable: clickable=" + clickable + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("setAirplaneModeBarClickable error!", view);
        }
    }

    public void setScreenBrightness(View view) {
        try {
            boolean automatic = isCheckBoxSelected(R.id.cb_set_screen_brightness_automatic);
            int value = new Integer(getEtString(R.id.et_set_screen_brightness_value));
            getData().setScreenBrightness(automatic, value);
            log("setScreenBrightness: automatic=" + automatic + ", value=" + value + ", success=true", view);
        } catch (Exception e) {
            e.printStackTrace();
            log("setScreenBrightness error!", view);
        }
    }

    public void showBatteryPercent(View view) {
        try {
            boolean show = isCheckBoxSelected(R.id.cb_show_battery_percent_show);
            getData().showBatteryPercent(show);
            log("showBatteryPercent: show=" + show + ", success=true", view);
        } catch (Exception e) {
            e.printStackTrace();
            log("showBatteryPercent error!", view);
        }
    }

    public void isNavigationBarEnabled(View view) {
        try {
            boolean result = getData().isNavigationBarEnabled();
            log("isNavigationBarEnabled: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("isNavigationBarEnabled error!", view);
        }
    }

    public void isNavigationKeyEnabled(View view) {
        try {
            int type = new Integer(getEtString(R.id.et_is_navigation_key_enabled_type));
            boolean result = getData().isNavigationKeyEnabled(type);
            log("isNavigationKeyEnabled: type=" + type + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("isNavigationKeyEnabled error!", view);
        }
    }

    public void resetStatusBar(View view) {
        try {
            getData().resetStatusBar();
            log("resetStatusBar: , success=true", view);
        } catch (Exception e) {
            e.printStackTrace();
            log("resetStatusBar error!", view);
        }
    }

    public void isStatusBarEnabled(View view) {
        try {
            boolean result = getData().isStatusBarEnabled();
            log("isStatusBarEnabled: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("isStatusBarEnabled error!", view);
        }
    }

    public void isStatusBarVisible(View view) {
        try {
            boolean result = getData().isStatusBarVisible();
            log("isStatusBarVisible: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("isStatusBarVisible error!", view);
        }
    }

    public void isNavigationBarVisible(View view) {
        try {
            boolean result = getData().isNavigationBarVisible();
            log("isNavigationBarVisible: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("isNavigationBarVisible error!", view);
        }
    }

    public void setAPNVisible(View view) {
        try {
            boolean enable = isCheckBoxSelected(R.id.cb_set_a_p_n_visible_enable);
            boolean result = getData().setAPNVisible(enable);
            log("setAPNVisible: enable=" + enable + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("setAPNVisible error!", view);
        }
    }

    public void setNavigationBarButtonLocation(View view) {
        try {
            int left = new Integer(getEtString(R.id.et_set_navigation_bar_button_location_left));
            int center = new Integer(getEtString(R.id.et_set_navigation_bar_button_location_center));
            int right = new Integer(getEtString(R.id.et_set_navigation_bar_button_location_right));
            boolean result = getData().setNavigationBarButtonLocation(left, center, right);
            log("setNavigationBarButtonLocation: left=" + left + ", center=" + center + ", right=" + right + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("setNavigationBarButtonLocation error!", view);
        }
    }

    public void setSettingsUiInstallWifiCertificate(View view) {
        try {
            int value = new Integer(getEtString(R.id.et_set_settings_ui_install_wifi_certificate_value));
            boolean result = getData().setSettingsUiInstallWifiCertificate(value);
            log("setSettingsUiInstallWifiCertificate: value=" + value + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("setSettingsUiInstallWifiCertificate error!", view);
        }
    }

    public void getSettingsUiInstallWifiCertificate(View view) {
        try {
            int result = getData().getSettingsUiInstallWifiCertificate();
            log("getSettingsUiInstallWifiCertificate: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getSettingsUiInstallWifiCertificate error!", view);
        }
    }
}