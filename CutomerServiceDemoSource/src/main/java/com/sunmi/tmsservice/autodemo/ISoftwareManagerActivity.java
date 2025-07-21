package com.sunmi.tmsservice.autodemo;

import android.os.Bundle;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSON;
import com.sunmi.cutomerservicedemosource.R;
import com.sunmi.tmsmaster.aidl.networkmanager.IUnifiedCallback;
import com.sunmi.tmsmaster.aidl.softwaremanager.ISoftwareManager;
import com.sunmi.tmsmaster.aidl.softwaremanager.OnInstallAppListener;
import com.sunmi.tmsmaster.aidl.softwaremanager.OnUninstallAppListener;
import com.sunmi.tmsservice.apidemo.TMSMaster;
import com.sunmi.tmsservice.autodemo.util.Util;

import java.util.Arrays;
import java.util.List;

public class ISoftwareManagerActivity extends AppCompatActivity {

    private static final String TAG = ISoftwareManagerActivity.class.getName();

    private LinearLayout layout;
    private TextView tvLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_i_software_manager);
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
            runOnUiThread(() -> Toast.makeText(ISoftwareManagerActivity.this, text, Toast.LENGTH_SHORT).show());
        }
    }

    public ISoftwareManager getData() {
        return TMSMaster.getInstance().getSoftwareManager();
    }

    public void installApp(View view) {
        try {
            String appFilePath = new String(getEtString(R.id.et_install_app_app_file_path));

            List<String> list = Arrays.asList(appFilePath.split(","));

            for (String path : list) {
                if (TextUtils.isEmpty(path)) {
                    continue;
                }
                getData().installApp(path, new OnInstallAppListener.Stub() {
                    @Override
                    public void onInstallFinished() throws RemoteException {
                        log("installApp: appFilePath=" + appFilePath + ", onInstallFinished success=true", view);

                    }

                    @Override
                    public void onInstallError(int errorId) throws RemoteException {
                        log("installApp: appFilePath=" + appFilePath + ", onInstallError errorId=" + errorId, view);

                    }

                    @Override
                    public void onInstallSuccess(String packagename) throws RemoteException {
                        log("installApp: appFilePath=" + appFilePath + ", onInstallSuccess success=true", view);

                    }

                    @Override
                    public void onInstallFail(String packagename, int errorId) throws RemoteException {
                        log("installApp: appFilePath=" + appFilePath + ", onInstallError errorId=" + errorId, view);

                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            log("installApp error!", view);
        }
    }

    public void uninstallApp(View view) {
        try {
            String packageName = new String(getEtString(R.id.et_uninstall_app_package_name));
            getData().uninstallApp(packageName, new OnUninstallAppListener.Stub() {
                @Override
                public void onUnInstallFinished() throws RemoteException {
                    log("uninstallApp: onUnInstallFinished packageName=" + packageName, view);

                }

                @Override
                public void onUnInstallError(int errorId) throws RemoteException {
                    log("uninstallApp: onUnInstallError packageName=" + packageName + ", errorId=" + errorId, view);

                }

                @Override
                public void onUnInstallSuccess(String packagename) throws RemoteException {
                    log("uninstallApp: onUnInstallSuccess packageName=" + packageName, view);
                }

                @Override
                public void onUnInstallFail(String packagename, int errorId) throws RemoteException {
                    log("uninstallApp: onUnInstallFail packageName=" + packageName + ", errorId=" + errorId, view);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            log("uninstallApp error!", view);
        }
    }

    public void killApp(View view) {
        try {
            String packageName = new String(getEtString(R.id.et_kill_app_package_name));
            getData().killApp(packageName);
            log("killApp: packageName=" + packageName + ", success=true", view);
        } catch (Exception e) {
            e.printStackTrace();
            log("killApp error!", view);
        }
    }

    public void restartApp(View view) {
        try {
            String packageName = new String(getEtString(R.id.et_restart_app_package_name));
            getData().restartApp(packageName);
            log("restartApp: packageName=" + packageName + ", success=true", view);
        } catch (Exception e) {
            e.printStackTrace();
            log("restartApp error!", view);
        }
    }

    public void setAppWhiteList(View view) {
        try {
            String whiteList = new String(getEtString(R.id.et_set_app_white_list_white_list));
            getData().setAppWhiteList(whiteList);
            log("setAppWhiteList: whiteList=" + whiteList + ", success=true", view);
        } catch (Exception e) {
            e.printStackTrace();
            log("setAppWhiteList error!", view);
        }
    }

    public void getAppWhiteList(View view) {
        try {
            String result = getData().getAppWhiteList();
            log("getAppWhiteList: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getAppWhiteList error!", view);
        }
    }

    public void addLauncherShortcut(View view) {
        try {
            String packageName = new String(getEtString(R.id.et_add_launcher_shortcut_package_name));
            getData().addLauncherShortcut(packageName);
            log("addLauncherShortcut: packageName=" + packageName + ", success=true", view);
        } catch (Exception e) {
            e.printStackTrace();
            log("addLauncherShortcut error!", view);
        }
    }

    public void removeLauncherShortcut(View view) {
        try {
            String packageName = new String(getEtString(R.id.et_remove_launcher_shortcut_package_name));
            getData().removeLauncherShortcut(packageName);
            log("removeLauncherShortcut: packageName=" + packageName + ", success=true", view);
        } catch (Exception e) {
            e.printStackTrace();
            log("removeLauncherShortcut error!", view);
        }
    }

    public void setBatteryOptimizationWhitelist(View view) {
        try {
            String whitelist = new String(getEtString(R.id.et_set_battery_optimization_whitelist_whitelist));
            boolean result = getData().setBatteryOptimizationWhitelist(whitelist);
            log("setBatteryOptimizationWhitelist: whitelist=" + whitelist + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("setBatteryOptimizationWhitelist error!", view);
        }
    }

    public void setAppEnabled(View view) {
        try {
            String packageName = new String(getEtString(R.id.et_set_app_enabled_package_name));
            boolean enabled = isCheckBoxSelected(R.id.cb_set_app_enabled_enabled);
            boolean result = getData().setAppEnabled(packageName, enabled);
            log("setAppEnabled: packageName=" + packageName + ", enabled=" + enabled + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("setAppEnabled error!", view);
        }
    }

    public void getRequestPermissions(View view) {
        try {
            String packageName = new String(getEtString(R.id.et_get_request_permissions_package_name));
            String result = getData().getRequestPermissions(packageName);
            log("getRequestPermissions: packageName=" + packageName + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getRequestPermissions error!", view);
        }
    }

    public void grantAppPermissions(View view) {
        try {
            String packageName = new String(getEtString(R.id.et_grant_app_permissions_package_name));
            String permissions = new String(getEtString(R.id.et_grant_app_permissions_permissions));
            boolean result = getData().grantAppPermissions(packageName, permissions);
            log("grantAppPermissions: packageName=" + packageName + ", permissions=" + permissions + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("grantAppPermissions error!", view);
        }
    }

    public void checkAppPermissionsByType(View view) {
        try {
            String packageName = new String(getEtString(R.id.et_check_app_permissions_package_name));
            String type = new String(getEtString(R.id.et_check_app_permissions_type));
            boolean result = getData().checkAppPermissionsByType(packageName, type);
            log("checkAppPermissionsByType: packageName=" + packageName + ", type=" + type + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("checkAppPermissionsByType error!", view);
        }
    }

    public void allowAlertWindowPermission(View view) {
        try {
            String packageName = new String(getEtString(R.id.et_allow_alert_window_permission_package_name));
            boolean result = getData().allowAlertWindowPermission(packageName);
            log("allowAlertWindowPermission: packageName=" + packageName + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("allowAlertWindowPermission error!", view);
        }
    }

    public void prohibitUninstall(View view) {
        try {
            String pkgName = new String(getEtString(R.id.et_prohibit_uninstall_pkg_name));
            boolean allowUninstall = isCheckBoxSelected(R.id.cb_prohibit_uninstall_allow_uninstall);
            getData().prohibitUninstall(pkgName, allowUninstall);
            log("prohibitUninstall: pkgName=" + pkgName + ", allowUninstall=" + allowUninstall + ", success=true", view);
        } catch (Exception e) {
            e.printStackTrace();
            log("prohibitUninstall error!", view);
        }
    }

    public void getProhibitUninstallList(View view) {
        try {
            String result = getData().getProhibitUninstallList();
            log("getProhibitUninstallList: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getProhibitUninstallList error!", view);
        }
    }

    public void initPasswordForUninstallAppLock(View view) {
        try {
            String password = new String(getEtString(R.id.et_init_password_for_uninstall_app_lock_password));
            boolean result = getData().initPasswordForUninstallAppLock(password);
            log("initPasswordForUninstallAppLock: password=" + password + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("initPasswordForUninstallAppLock error!", view);
        }
    }

    public void modifyPasswordForUninstallAppLock(View view) {
        try {
            String currentPwd = new String(getEtString(R.id.et_modify_password_for_uninstall_app_lock_current_pwd));
            String newPwd = new String(getEtString(R.id.et_modify_password_for_uninstall_app_lock_new_pwd));
            boolean result = getData().modifyPasswordForUninstallAppLock(currentPwd, newPwd);
            log("modifyPasswordForUninstallAppLock: currentPwd=" + currentPwd + ", newPwd=" + newPwd + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("modifyPasswordForUninstallAppLock error!", view);
        }
    }

    public void clearApplicationUserData(View view) {
        try {
            String packageName = new String(getEtString(R.id.et_clear_application_user_data_package_name));
            boolean result = getData().clearApplicationUserData(packageName);
            log("clearApplicationUserData: packageName=" + packageName + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("clearApplicationUserData error!", view);
        }
    }

    public void enableHideNavigationBarPackageWhiteList(View view) {
        try {
            boolean enable = isCheckBoxSelected(R.id.cb_enable_hide_navigation_bar_package_white_list_enable);
            boolean result = getData().enableHideNavigationBarPackageWhiteList(enable);
            log("enableHideNavigationBarPackageWhiteList: enable=" + enable + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("enableHideNavigationBarPackageWhiteList error!", view);
        }
    }

    public void getHideNavigationBarPackageWhiteListStatus(View view) {
        try {
            boolean result = getData().getHideNavigationBarPackageWhiteListStatus();
            log("getHideNavigationBarPackageWhiteListStatus: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getHideNavigationBarPackageWhiteListStatus error!", view);
        }
    }

    public void addPkgToHideNavigationBarPkgWhiteList(View view) {
        try {
            String packageName = new String(getEtString(R.id.et_add_pkg_to_hide_navigation_bar_pkg_white_list_package_name));
            boolean result = getData().addPkgToHideNavigationBarPkgWhiteList(packageName);
            log("addPkgToHideNavigationBarPkgWhiteList: packageName=" + packageName + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("addPkgToHideNavigationBarPkgWhiteList error!", view);
        }
    }

    public void getHideNavigationBarPackageWhiteList(View view) {
        try {
            String result = getData().getHideNavigationBarPackageWhiteList();
            log("getHideNavigationBarPackageWhiteList: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getHideNavigationBarPackageWhiteList error!", view);
        }
    }

    public void clearHideNavigationBarPackageWhiteList(View view) {
        try {
            boolean result = getData().clearHideNavigationBarPackageWhiteList();
            log("clearHideNavigationBarPackageWhiteList: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("clearHideNavigationBarPackageWhiteList error!", view);
        }
    }

    public void enableAutoStartApp(View view) {
        try {
            boolean enable = isCheckBoxSelected(R.id.cb_enable_auto_start_app_enable);
            boolean result = getData().enableAutoStartApp(enable);
            log("enableAutoStartApp: enable=" + enable + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("enableAutoStartApp error!", view);
        }
    }

    public void isAutoStartAppEnabled(View view) {
        try {
            boolean result = getData().isAutoStartAppEnabled();
            log("isAutoStartAppEnabled: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("isAutoStartAppEnabled error!", view);
        }
    }

    public void setAutoStartApp(View view) {
        try {
            String packageName = new String(getEtString(R.id.et_set_auto_start_app_package_name));
            boolean result = getData().setAutoStartApp(packageName);
            log("setAutoStartApp: packageName=" + packageName + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("setAutoStartApp error!", view);
        }
    }

    public void getAutoStartApp(View view) {
        try {
            String result = getData().getAutoStartApp();
            log("getAutoStartApp: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getAutoStartApp error!", view);
        }
    }

    public void clearAutoStartApp(View view) {
        try {
            boolean result = getData().clearAutoStartApp();
            log("clearAutoStartApp: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("clearAutoStartApp error!", view);
        }
    }

    public void clearApplicationUserDataWithResult(View view) {
        try {
            String packageName = new String(getEtString(R.id.et_clear_application_user_data_with_result_package_name));
            getData().clearApplicationUserDataWithResult(packageName, new IUnifiedCallback.Stub() {
                @Override
                public void onCall(String result) throws RemoteException {
                    log("clearApplicationUserDataWithResult: packageName=" + packageName + ", result=" + result, view);

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            log("clearApplicationUserDataWithResult error!", view);
        }
    }

    public void deleteApplicationCacheFiles(View view) {
        try {
            String packageName = new String(getEtString(R.id.et_delete_application_cache_files_package_name));
            getData().deleteApplicationCacheFiles(packageName, new IUnifiedCallback.Stub() {
                @Override
                public void onCall(String result) throws RemoteException {

                    log("deleteApplicationCacheFiles: packageName=" + packageName + ", result=" + result, view);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            log("deleteApplicationCacheFiles error!", view);
        }
    }

    public void revokeAppPermission(View view) {
        try {
            String packageName = new String(getEtString(R.id.et_revoke_app_permission_package_name));
            String permissions = new String(getEtString(R.id.et_revoke_app_permission_permissions));
            getData().revokeAppPermission(packageName, permissions);
            log("revokeAppPermission: packageName=" + packageName + ", permissions=" + permissions + ", success=true", view);
        } catch (Exception e) {
            e.printStackTrace();
            log("revokeAppPermission error!", view);
        }
    }

    public void setForbiddenUninstallPackageStatus(View view) {
        try {
            int type = new Integer(getEtString(R.id.et_set_forbidden_uninstall_package_status_type));
            boolean result = getData().setForbiddenUninstallPackageStatus(type);
            log("setForbiddenUninstallPackageStatus: type=" + type + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("setForbiddenUninstallPackageStatus error!", view);
        }
    }

    public void setNotificationsEnabledForPackage(View view) {
        try {
            String packageName = new String(getEtString(R.id.et_set_notifications_enabled_for_package_package_name));
            boolean enabled = isCheckBoxSelected(R.id.cb_set_notifications_enabled_for_package_enabled);
            boolean result = getData().setNotificationsEnabledForPackage(packageName, enabled);
            log("setNotificationsEnabledForPackage: packageName=" + packageName + ", enabled=" + enabled + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("setNotificationsEnabledForPackage error!", view);
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

    public void isForeground(View view) {
        try {
            String packageName = new String(getEtString(R.id.et_is_foreground_package_name));
            boolean result = getData().isForeground(packageName);
            log("isForeground: packageName=" + packageName + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("isForeground error!", view);
        }
    }

    public void switchAppEnable(View view) {
        try {
            String packageName = new String(getEtString(R.id.et_switch_app_enable_package_name));
            boolean enable = isCheckBoxSelected(R.id.cb_switch_app_enable_enable);
            boolean result = getData().switchAppEnable(packageName, enable);
            log("switchAppEnable: packageName=" + packageName + ", enable=" + enable + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("switchAppEnable error!", view);
        }
    }

    public void getUndeletableAppList(View view) {
        try {
            java.util.List<String> result = getData().getUndeletableAppList();
            log("getUndeletableAppList: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getUndeletableAppList error!", view);
        }
    }

    public void setLauncherApp(View view) {
        try {
            String packageName = new String(getEtString(R.id.et_set_launcher_app_package_name));
            String activityName = new String(getEtString(R.id.et_set_launcher_app_activity_name));
            int result = getData().setLauncherApp(packageName, activityName);
            log("setLauncherApp: packageName=" + packageName + ", activityName=" + activityName + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("setLauncherApp error!", view);
        }
    }

    public void restoreFactorySettings(View view) {
        try {
            int result = getData().restoreFactorySettings();
            log("restoreFactorySettings: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("restoreFactorySettings error!", view);
        }
    }

    public void enableOptimizeBatteryPackageWhiteList(View view) {
        try {
            boolean enable = isCheckBoxSelected(R.id.cb_enable_optimize_battery_package_white_list_enable);
            boolean result = getData().enableOptimizeBatteryPackageWhiteList(enable);
            log("enableOptimizeBatteryPackageWhiteList: enable=" + enable + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("enableOptimizeBatteryPackageWhiteList error!", view);
        }
    }

    public void isOptimizeBatteryPackageWhiteListEnabled(View view) {
        try {
            boolean result = getData().isOptimizeBatteryPackageWhiteListEnabled();
            log("isOptimizeBatteryPackageWhiteListEnabled: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("isOptimizeBatteryPackageWhiteListEnabled error!", view);
        }
    }

    public void addPackageToOptimizeBatteryPackageWhiteList(View view) {
        try {
            String data = getEtString(R.id.et_add_package_to_optimize_battery_package_white_list);
            List<String> list = Arrays.asList(data.split(","));
            boolean result = getData().addPackageToOptimizeBatteryPackageWhiteList(list);
            log("addPackageToOptimizeBatteryPackageWhiteList: list=" + list + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("addPackageToOptimizeBatteryPackageWhiteList error!", view);
        }
    }

    public void removePkgFromOptimizeBatteryPkgWhiteList(View view) {
        try {
            String data = getEtString(R.id.et_remove_pkg_from_optimize_battery_pkg_white_list);
            List<String> list = Arrays.asList(data.split(","));
            boolean result = getData().removePkgFromOptimizeBatteryPkgWhiteList(list);
            log("removePkgFromOptimizeBatteryPkgWhiteList: list=" + list + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("removePkgFromOptimizeBatteryPkgWhiteList error!", view);
        }
    }

    public void getOptimizeBatteryPackageWhiteList(View view) {
        try {
            java.util.List<String> result = getData().getOptimizeBatteryPackageWhiteList();
            log("getOptimizeBatteryPackageWhiteList: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getOptimizeBatteryPackageWhiteList error!", view);
        }
    }

    public void addAppToCommonAppLockList(View view) {
        try {
            String data = getEtString(R.id.et_add_app_to_common_app_lock_list);
            List<String> list = Arrays.asList(data.split(","));
            int type = new Integer(getEtString(R.id.et_add_app_to_common_app_lock_list_type));
            String password = new String(getEtString(R.id.et_add_app_to_common_app_lock_list_password));
            boolean result = getData().addAppToCommonAppLockList(list, type, type == -1 ? password : Util.md5(password));

            log("addAppToCommonAppLockList: type=" + type + ", list=" + Util.object2json(list) + ", password=" + password + ", md5=" + Util.md5(password)+ ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("addAppToCommonAppLockList error!", view);
        }
    }

    public void removeAppFromCommonAppLockList(View view) {
        try {
            String data = getEtString(R.id.et_remove_app_from_common_app_lock_list);
            List<String> list = Arrays.asList(data.split(","));
            boolean result = getData().removeAppFromCommonAppLockList(list);
            log("removeAppFromCommonAppLockList: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("removeAppFromCommonAppLockList error!", view);
        }
    }

    public void getCommonAppLockList(View view) {
        try {
            java.util.List<String> result = getData().getCommonAppLockList();
            log("getCommonAppLockList: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getCommonAppLockList error!", view);
        }
    }

    public void isCommonAppLock(View view) {
        try {
            String packageName = new String(getEtString(R.id.et_is_common_app_lock_package_name));
            boolean result = getData().isCommonAppLock(packageName);
            log("isCommonAppLock: packageName=" + packageName + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("isCommonAppLock error!", view);
        }
    }

    public void getAppNetworkStats(View view) {
        try {
            String packageName = new String(getEtString(R.id.et_get_app_network_stats_package_name));
            com.sunmi.tmsmaster.aidl.softwaremanager.AppNetworkStats result = getData().getAppNetworkStats(packageName);
            log("getAppNetworkStats: packageName=" + packageName + ", result=" + JSON.toJSONString(result), view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getAppNetworkStats error!", view);
        }
    }

    public void installAppV2(View view) {
        try {
            String appFilePath = new String(getEtString(R.id.et_install_app_v2_app_file_path));
            boolean autoStart = isCheckBoxSelected(R.id.cb_install_app_v2_auto_start);
            getData().installAppV2(appFilePath, autoStart, new OnInstallAppListener.Stub() {
                @Override
                public void onInstallFinished() throws RemoteException {
                    log("installAppV2: onInstallFinished appFilePath=" + appFilePath + ", autoStart=" + autoStart + ", success=true", view);
                }

                @Override
                public void onInstallError(int errorId) throws RemoteException {
                    log("installAppV2: onInstallError appFilePath=" + appFilePath + ", autoStart=" + autoStart + ", errorId=" + errorId, view);
                }

                @Override
                public void onInstallSuccess(String packagename) throws RemoteException {
                    log("installAppV2: onInstallSuccess appFilePath=" + appFilePath + ", autoStart=" + autoStart + ", packagename=" + packagename, view);
                }

                @Override
                public void onInstallFail(String packagename, int errorId) throws RemoteException {
                    log("installAppV2: onInstallFail appFilePath=" + appFilePath + ", autoStart=" + autoStart + ", packagename=" + packagename + ", errorId=" + errorId, view);

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            log("installApp error!", view);
        }
    }
}