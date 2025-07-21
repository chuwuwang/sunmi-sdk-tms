package com.sunmi.tmsservice.autodemo;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;
import android.provider.Telephony;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.alibaba.fastjson.JSON;
import com.sunmi.cutomerservicedemosource.R;
import com.sunmi.tmsmaster.aidl.networkmanager.INetworkManager;
import com.sunmi.tmsmaster.aidl.networkmanager.IUnifiedCallback;
import com.sunmi.tmsmaster.aidl.networkmanager.WifiConfigurationInfo;
import com.sunmi.tmsservice.apidemo.TMSMaster;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public class INetworkManagerActivity extends AppCompatActivity {

    private static final String TAG = INetworkManagerActivity.class.getName();

    private LinearLayout layout;
    private TextView tvLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_i_network_manager);
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
            runOnUiThread(() -> Toast.makeText(INetworkManagerActivity.this, text, Toast.LENGTH_SHORT).show());
        }
    }

    public INetworkManager getData() {
        return TMSMaster.getInstance().getNetworkManager();
    }

    public void enableMobileNetwork(View view) {
        try {
            int slotIdx = new Integer(getEtString(R.id.et_enable_mobile_network_slot_idx));
            boolean enable = isCheckBoxSelected(R.id.cb_enable_mobile_network_enable);
            getData().enableMobileNetwork(slotIdx, enable);
            log("enableMobileNetwork: slotIdx=" + slotIdx + ", enable=" + enable + ", success=true", view);
        } catch (Exception e) {
            e.printStackTrace();
            log("enableMobileNetwork error!", view);
        }
    }

    public void checkAPN(View view) {
        try {
            String apnInfo = new String(getEtString(R.id.et_check_a_p_n_apn_info));
            boolean result = getData().checkAPN(apnInfo);
            log("checkAPN: apnInfo=" + apnInfo + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("checkAPN error!", view);
        }
    }

    public void setAPN(View view) {
        try {
            int apnId = new Integer(getEtString(R.id.et_set_a_p_n_apn_id));
            boolean result = getData().setAPN(apnId);
            log("setAPN: apnId=" + apnId + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("setAPN error!", view);
        }
    }

    public void addAPN(View view) {
        startActivityForResult(new Intent(this, ApnInsertActivity.class), 101);
    }

    private String getCurrentNumeric(Context context) throws Exception {
        String numeric = "";

        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1 && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            SubscriptionManager subscriptionManager = (SubscriptionManager) context.getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE);
            @SuppressLint("MissingPermission") SubscriptionInfo info = subscriptionManager.getActiveSubscriptionInfoForSimSlotIndex(0);
            int subId = SubscriptionManager.INVALID_SUBSCRIPTION_ID;

            if (info != null) {
                subId = info.getSubscriptionId();
                Method method = TelephonyManager.class.getDeclaredMethod("getSimOperator", int.class);

                numeric = (String) method.invoke(telephonyManager, subId);
            }
        } else {
            numeric = telephonyManager.getSimOperator();
        }

        return numeric;
    }

    public void getAPNList(View view) {
        try {
            String numeric = getCurrentNumeric(this);

            String[] projection = {Telephony.Carriers._ID, Telephony.Carriers.NAME, Telephony.Carriers.APN, Telephony.Carriers.NUMERIC, Telephony.Carriers.SERVER, Telephony.Carriers.PASSWORD, Telephony.Carriers.AUTH_TYPE, Telephony.Carriers.TYPE, Telephony.Carriers.PROTOCOL, Telephony.Carriers.PROXY};
            String selection = numeric;
            String[] selectionArgs = {};
            if (!TextUtils.isEmpty(numeric)) {
                selection = Telephony.Carriers.NUMERIC + "=?";
                selectionArgs = new String[]{numeric};
            }

            String result = getData().getAPNList(projection, selection, selectionArgs, Telephony.Carriers.DEFAULT_SORT_ORDER + " LIMIT 10");
            log("getAPNList: result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getAPNList error!", view);
        }
    }

    public void getCurrentAPN(View view) {
        try {
            int slotIdx = new Integer(getEtString(R.id.et_get_current_a_p_n_slot_idx));
            String result = getData().getCurrentAPN(slotIdx);
            log("getCurrentAPN: slotIdx=" + slotIdx + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getCurrentAPN error!", view);
        }
    }

    public void setSimcardDataEnabled(View view) {
        try {
            int subId = new Integer(getEtString(R.id.et_set_simcard_data_enabled_sub_id));
            boolean enable = isCheckBoxSelected(R.id.cb_set_simcard_data_enabled_enable);
            getData().setSimcardDataEnabled(subId, enable);
            log("setSimcardDataEnabled: subId=" + subId + ", enable=" + enable + ", success=true", view);
        } catch (Exception e) {
            e.printStackTrace();
            log("setSimcardDataEnabled error!", view);
        }
    }

    public void queryTrafficUsageSummary(View view) {
        try {
            int networkType = new Integer(getEtString(R.id.et_query_traffic_usage_summary_network_type));
            long startTime = new Long(getEtString(R.id.et_query_traffic_usage_summary_start_time));
            long endTime = new Long(getEtString(R.id.et_query_traffic_usage_summary_end_time));
            com.sunmi.tmsmaster.aidl.networkmanager.DataBucket result = getData().queryTrafficUsageSummary(networkType, startTime, endTime);
            log("queryTrafficUsageSummary: networkType=" + networkType + ", startTime=" + startTime + ", endTime=" + endTime + ", result=" + JSON.toJSONString(result), view);
        } catch (Exception e) {
            e.printStackTrace();
            log("queryTrafficUsageSummary error!", view);
        }
    }

    public void getDefaultDataSlotIndex(View view) {
        try {
            int result = getData().getDefaultDataSlotIndex();
            log("getDefaultDataSlotIndex: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getDefaultDataSlotIndex error!", view);
        }
    }

    public void updateAPN(View view) {
        startActivityForResult(new Intent(this, ApnInsertActivity.class), 102);
    }

    public void deleteAPN(View view) {
        try {
            String apn = new String(getEtString(R.id.et_delete_a_p_n_apn));
            String name = new String(getEtString(R.id.et_delete_a_p_n_name));
            boolean result = getData().deleteAPN(apn, name);
            log("deleteAPN: apn=" + apn + ", name=" + name + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("deleteAPN error!", view);
        }
    }

    public void addNetwork(View view) {
        try {
            String ssid = getEtString(R.id.et_add_network_ssid);
            String pwd = getEtString(R.id.et_add_network_pwd);
            Bundle bundle = new Bundle();
            bundle.putString("SSID", ssid);
            bundle.putString("preSharedKey", pwd);
            int result = getData().addNetwork(bundle);
            log("addNetwork: ssid=" + ssid + ", pwd=" + pwd + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("addNetwork error!", view);
        }
    }

    public void updateNetwork(View view) {
        try {
            String ssid = getEtString(R.id.et_update_network_ssid);
            String pwd = getEtString(R.id.et_update_network_pwd);
            int networkId = Integer.parseInt(getEtString(R.id.et_update_network_id));
            Bundle bundle = new Bundle();
            bundle.putString("SSID", ssid);
            bundle.putString("preSharedKey", pwd);
            bundle.putInt("networkId", networkId);
            int result = getData().updateNetwork(bundle);
            log("updateNetwork: ssid=" + ssid + ", pwd=" + pwd + ", networkId=" + networkId + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("updateNetwork error!", view);
        }
    }

    public void removeNetwork(View view) {
        try {
            int networkId = new Integer(getEtString(R.id.et_remove_network_network_id));
            boolean result = getData().removeNetwork(networkId);
            log("removeNetwork: networkId=" + networkId + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("removeNetwork error!", view);
        }
    }

    public void getConfiguredNetworks(View view) {
        try {
            java.util.List<Bundle> result = getData().getConfiguredNetworks();
            log("getConfiguredNetworks: , result=" + JSON.toJSONString(result), view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getConfiguredNetworks error!", view);
        }
    }

    public void enableMobileData(View view) {
        try {
            boolean enable = isCheckBoxSelected(R.id.cb_enable_mobile_data_enable);
            boolean result = getData().enableMobileData(enable);
            log("enableMobileData: enable=" + enable + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("enableMobileData error!", view);
        }
    }

    public void setMobileDataLocked(View view) {
        try {
            boolean locked = isCheckBoxSelected(R.id.cb_set_mobile_data_locked_locked);
            boolean result = getData().setMobileDataLocked(locked);
            log("setMobileDataLocked: locked=" + locked + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("setMobileDataLocked error!", view);
        }
    }

    public void setAppRestrictMobile(View view) {
        try {
            boolean restrict = isCheckBoxSelected(R.id.cb_set_app_restrict_mobile_restrict);
            String packageName = new String(getEtString(R.id.et_set_app_restrict_mobile_package_name));
            boolean result = getData().setAppRestrictMobile(restrict, packageName);
            log("setAppRestrictMobile: restrict=" + restrict + ", packageName=" + packageName + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("setAppRestrictMobile error!", view);
        }
    }

    public void setAppRestrictWlan(View view) {
        try {
            boolean restrict = isCheckBoxSelected(R.id.cb_set_app_restrict_wlan_restrict);
            String packageName = new String(getEtString(R.id.et_set_app_restrict_wlan_package_name));
            boolean result = getData().setAppRestrictWlan(restrict, packageName);
            log("setAppRestrictWlan: restrict=" + restrict + ", packageName=" + packageName + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("setAppRestrictWlan error!", view);
        }
    }

    public void getAppRestrictMobile(View view) {
        try {
            java.util.List<String> result = getData().getAppRestrictMobile();
            log("getAppRestrictMobile: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getAppRestrictMobile error!", view);
        }
    }

    public void getAppRestrictWlan(View view) {
        try {
            java.util.List<String> result = getData().getAppRestrictWlan();
            log("getAppRestrictWlan: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getAppRestrictWlan error!", view);
        }
    }

    public void deny(View view) {
        try {
            String ip = new String(getEtString(R.id.et_deny_ip));
            boolean result = getData().deny(ip);
            log("deny: ip=" + ip + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("deny error!", view);
        }
    }

    public void clearDenial(View view) {
        try {
            String ip = new String(getEtString(R.id.et_clear_denial_ip));
            boolean result = getData().clearDenial(ip);
            log("clearDenial: ip=" + ip + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("clearDenial error!", view);
        }
    }

    public void clearDenialAll(View view) {
        try {
            boolean result = getData().clearDenialAll();
            log("clearDenialAll: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("clearDenialAll error!", view);
        }
    }

    public void getDenialList(View view) {
        try {
            java.util.List<String> result = getData().getDenialList();
            log("getDenialList: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getDenialList error!", view);
        }
    }

    public void switchPortableHotspot(View view) {
        try {
            boolean enable = isCheckBoxSelected(R.id.cb_switch_portable_hotspot_enable);
            boolean result = getData().switchPortableHotspot(enable);
            log("switchPortableHotspot: enable=" + enable + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("switchPortableHotspot error!", view);
        }
    }

    public void clearDnsCache(View view) {
        try {
            boolean result = getData().clearDnsCache();
            log("clearDnsCache: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("clearDnsCache error!", view);
        }
    }

    public void addToWhiteList(View view) {
        try {
            String ip = new String(getEtString(R.id.et_add_to_white_list_ip));
            boolean result = getData().addToWhiteList(ip);
            log("addToWhiteList: ip=" + ip + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("addToWhiteList error!", view);
        }
    }

    public void removeFromWhiteList(View view) {
        try {
            String ip = new String(getEtString(R.id.et_remove_from_white_list_ip));
            boolean result = getData().removeFromWhiteList(ip);
            log("removeFromWhiteList: ip=" + ip + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("removeFromWhiteList error!", view);
        }
    }

    public void getWhiteList(View view) {
        try {
            java.util.List<String> result = getData().getWhiteList();
            log("getWhiteList: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getWhiteList error!", view);
        }
    }

    public void isActiveSlotIndex(View view) {
        try {
            int slotIndex = new Integer(getEtString(R.id.et_is_active_slot_index_slot_index));
            boolean result = getData().isActiveSlotIndex(slotIndex);
            log("isActiveSlotIndex: slotIndex=" + slotIndex + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("isActiveSlotIndex error!", view);
        }
    }

    public void setDefaultDataSlotIndex(View view) {
        try {
            int slotIndex = new Integer(getEtString(R.id.et_set_default_data_slot_index_slot_index));
            boolean result = getData().setDefaultDataSlotIndex(slotIndex);
            log("setDefaultDataSlotIndex: slotIndex=" + slotIndex + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("setDefaultDataSlotIndex error!", view);
        }
    }

    public void getTrafficOfEachApp(View view) {
        try {
            int networkType = new Integer(getEtString(R.id.et_get_traffic_of_each_app_network_type));
            long startTime = new Long(getEtString(R.id.et_get_traffic_of_each_app_start_time));
            long endTime = new Long(getEtString(R.id.et_get_traffic_of_each_app_end_time));
            java.util.List<com.sunmi.tmsmaster.aidl.networkmanager.AppDataBucket> result = getData().getTrafficOfEachApp(networkType, startTime, endTime);
            log("getTrafficOfEachApp: networkType=" + networkType + ", startTime=" + startTime + ", endTime=" + endTime + ", result=" + JSON.toJSONString(result), view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getTrafficOfEachApp error!", view);
        }
    }

    public void getWebsiteWhiteList(View view) {
        try {
            java.util.List<String> result = getData().getWebsiteWhiteList();
            log("getWebsiteWhiteList: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getWebsiteWhiteList error!", view);
        }
    }

    public void setWifiStaticIp(View view) {
        try {
            String ipAddr = new String(getEtString(R.id.et_set_wifi_static_ip_ip_addr));
            String gateway = new String(getEtString(R.id.et_set_wifi_static_ip_gateway));
            int networkPrefixLength = new Integer(getEtString(R.id.et_set_wifi_static_ip_network_prefix_length));
            String dns1 = new String(getEtString(R.id.et_set_wifi_static_ip_dns1));
            String dns2 = new String(getEtString(R.id.et_set_wifi_static_ip_dns2));
            boolean reconnect = isCheckBoxSelected(R.id.cb_set_wifi_static_ip_reconnect);
            getData().setWifiStaticIp(ipAddr, gateway, networkPrefixLength, dns1, dns2, reconnect);
            log("setWifiStaticIp: ipAddr=" + ipAddr + ", gateway=" + gateway + ", networkPrefixLength=" + networkPrefixLength + ", dns1=" + dns1 + ", dns2=" + dns2 + ", reconnect=" + reconnect + ", success=true", view);
        } catch (Exception e) {
            e.printStackTrace();
            log("setWifiStaticIp error!", view);
        }
    }

    public void addWifiSsid(View view) {
        try {
            String ssid = new String(getEtString(R.id.et_add_wifi_ssid_ssid));
            String password = new String(getEtString(R.id.et_add_wifi_ssid_password));
            int type = new Integer(getEtString(R.id.et_add_wifi_ssid_type));
            getData().addWifiSsid(ssid, password, type, new IUnifiedCallback.Stub() {
                @Override
                public void onCall(String result) throws RemoteException {
                    log("addWifiSsid: onCall ssid=" + ssid + ", password=" + password + ", type=" + type + ", result=" + result, view);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            log("addWifiSsid error!", view);
        }
    }

    public void setEthernetStaticIp(View view) {
        try {
            boolean status = isCheckBoxSelected(R.id.cb_set_ethernet_static_ip_status);
            String ipAddr = new String(getEtString(R.id.et_set_ethernet_static_ip_ip_addr));
            String gateway = new String(getEtString(R.id.et_set_ethernet_static_ip_gateway));
            int networkPrefixLength = new Integer(getEtString(R.id.et_set_ethernet_static_ip_network_prefix_length));
            String dns1 = new String(getEtString(R.id.et_set_ethernet_static_ip_dns1));
            String dns2 = new String(getEtString(R.id.et_set_ethernet_static_ip_dns2));
            boolean reconnect = isCheckBoxSelected(R.id.cb_set_ethernet_static_ip_reconnect);
            boolean result = getData().setEthernetStaticIp(status, ipAddr, gateway, networkPrefixLength, dns1, dns2, reconnect);
            log("setEthernetStaticIp: status=" + status + ", ipAddr=" + ipAddr + ", gateway=" + gateway + ", networkPrefixLength=" + networkPrefixLength + ", dns1=" + dns1 + ", dns2=" + dns2 + ", reconnect=" + reconnect + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("setEthernetStaticIp error!", view);
        }
    }

    public void getMobileDataStatusBySlot(View view) {
        try {
            int slotIndex = new Integer(getEtString(R.id.et_get_mobile_data_status_by_slot_slot_index));
            boolean result = getData().getMobileDataStatusBySlot(slotIndex);
            log("getMobileDataStatusBySlot: slotIndex=" + slotIndex + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getMobileDataStatusBySlot error!", view);
        }
    }

    public void getMobileDataStatus(View view) {
        try {
            boolean result = getData().getMobileDataStatus();
            log("getMobileDataStatus: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getMobileDataStatus error!", view);
        }
    }

    public void getDataRoamingEnabled(View view) {
        try {
            int slotIndex = new Integer(getEtString(R.id.et_get_data_roaming_enabled_slot_index));
            boolean result = getData().getDataRoamingEnabled(slotIndex);
            log("getDataRoamingEnabled: slotIndex=" + slotIndex + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getDataRoamingEnabled error!", view);
        }
    }

    public void setDataRoamingEnabled(View view) {
        try {
            int slotIndex = new Integer(getEtString(R.id.et_set_data_roaming_enabled_slot_index));
            boolean enable = isCheckBoxSelected(R.id.cb_set_data_roaming_enabled_enable);
            getData().setDataRoamingEnabled(slotIndex, enable);
            log("setDataRoamingEnabled: slotIndex=" + slotIndex + ", enable=" + enable + ", success=true", view);
        } catch (Exception e) {
            e.printStackTrace();
            log("setDataRoamingEnabled error!", view);
        }
    }

    public void setCustomNtpServer(View view) {
        try {
            String server = new String(getEtString(R.id.et_set_custom_ntp_server_server));
            boolean result = getData().setCustomNtpServer(server);
            log("setCustomNtpServer: server=" + server + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("setCustomNtpServer error!", view);
        }
    }

    public void getCustomNtpServer(View view) {
        try {
            String result = getData().getCustomNtpServer();
            log("getCustomNtpServer: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getCustomNtpServer error!", view);
        }
    }

    public void addNtpServers(View view) {
        try {
            String data = getEtString(R.id.et_add_ntp_servers);
            List<String> list = Arrays.asList(data.split(","));
            boolean result = getData().addNtpServers(list);
            log("addNtpServers: list=" + list + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("addNtpServers error!", view);
        }
    }

    public void removeNtpServers(View view) {
        try {
            String data = getEtString(R.id.et_remove_ntp_servers);
            List<String> list = Arrays.asList(data.split(","));
            boolean result = getData().removeNtpServers(list);
            log("removeNtpServers: list=" + list + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("removeNtpServers error!", view);
        }
    }

    public void getNtpServers(View view) {
        try {
            java.util.List<String> result = getData().getNtpServers();
            log("getNtpServers: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getNtpServers error!", view);
        }
    }

    public void getBestNtpServer(View view) {
        try {
            String result = getData().getBestNtpServer();
            log("getBestNtpServer: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getBestNtpServer error!", view);
        }
    }

    public void setWifiStaticIpV2(View view) {
        try {
            boolean status = isCheckBoxSelected(R.id.cb_set_wifi_static_ip_v2_status);
            String ipAddr = new String(getEtString(R.id.et_set_wifi_static_ip_v2_ip_addr));
            String gateway = new String(getEtString(R.id.et_set_wifi_static_ip_v2_gateway));
            int networkPrefixLength = new Integer(getEtString(R.id.et_set_wifi_static_ip_v2_network_prefix_length));
            String dns1 = new String(getEtString(R.id.et_set_wifi_static_ip_v2_dns1));
            String dns2 = new String(getEtString(R.id.et_set_wifi_static_ip_v2_dns2));
            boolean reconnect = isCheckBoxSelected(R.id.cb_set_wifi_static_ip_v2_reconnect);
            getData().setWifiStaticIpV2(status, ipAddr, gateway, networkPrefixLength, dns1, dns2, reconnect, new IUnifiedCallback.Stub() {
                @Override
                public void onCall(String result) throws RemoteException {
                    log("setWifiStaticIpV2: status=" + status + ", ipAddr=" + ipAddr + ", gateway=" + gateway + ", networkPrefixLength=" + networkPrefixLength + ", dns1=" + dns1 + ", dns2=" + dns2 + ", reconnect=" + reconnect + ", result=" + result, view);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            log("setWifiStaticIpV2 error!", view);
        }
    }

    public void getApnList(View view) {
        try {
            java.util.List<com.sunmi.tmsmaster.aidl.networkmanager.ApnModel> result = getData().getApnList();
            log("getApnList: , result=" + JSON.toJSONString(result), view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getApnList error!", view);
        }
    }

    public void forgetSavedWifi(View view) {
        try {
            getData().forgetSavedWifi();
            log("forgetSavedWifi: , success=true", view);
        } catch (Exception e) {
            e.printStackTrace();
            log("forgetSavedWifi error!", view);
        }
    }

    public void getActiveSimCardCount(View view) {
        try {
            int result = getData().getActiveSimCardCount();
            log("getActiveSimCardCount: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getActiveSimCardCount error!", view);
        }
    }

    public void getApnList_V2(View view) {
        try {
            int slotIdx = new Integer(getEtString(R.id.et_get_apn_list__v2_slot_idx));
            String result = getData().getApnList_V2(slotIdx);
            log("getApnList_V2: slotIdx=" + slotIdx + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getApnList_V2 error!", view);
        }
    }

    public void getTrafficTotal(View view) {
        try {
            int networkType = new Integer(getEtString(R.id.et_get_traffic_total_network_type));
            long startTime = new Long(getEtString(R.id.et_get_traffic_total_start_time));
            long endTime = new Long(getEtString(R.id.et_get_traffic_total_end_time));
            long result = getData().getTrafficTotal(networkType, startTime, endTime);
            log("getTrafficTotal: networkType=" + networkType + ", startTime=" + startTime + ", endTime=" + endTime + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getTrafficTotal error!", view);
        }
    }

    public void getTrafficOfEachAppMap(View view) {
        try {
            int networkType = new Integer(getEtString(R.id.et_get_traffic_of_each_app_map_network_type));
            long startTime = new Long(getEtString(R.id.et_get_traffic_of_each_app_map_start_time));
            long endTime = new Long(getEtString(R.id.et_get_traffic_of_each_app_map_end_time));
            java.util.Map result = getData().getTrafficOfEachAppMap(networkType, startTime, endTime);
            log("getTrafficOfEachAppMap: networkType=" + networkType + ", startTime=" + startTime + ", endTime=" + endTime + ", result=" + JSON.toJSONString(result), view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getTrafficOfEachAppMap error!", view);
        }
    }

    public void getCurrentNetworkSlot(View view) {
        try {
            int result = getData().getCurrentNetworkSlot();
            log("getCurrentNetworkSlot: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getCurrentNetworkSlot error!", view);
        }
    }

    public void setDataWarningPolicy(View view) {
        try {
            String value = new String(getEtString(R.id.et_set_data_warning_policy_value));
            boolean result = getData().setDataWarningPolicy(value);
            log("setDataWarningPolicy: value=" + value + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("setDataWarningPolicy error!", view);
        }
    }

    public void resetNetworkSettings(View view) {
        try {
            getData().resetNetworkSettings();
            log("resetNetworkSettings: , success=true", view);
        } catch (Exception e) {
            e.printStackTrace();
            log("resetNetworkSettings error!", view);
        }
    }

    public void removeAPN(View view) {
        try {
            String apnName = new String(getEtString(R.id.et_remove_a_p_n_apn_name));
            boolean result = getData().removeAPN(apnName);
            log("removeAPN: apnName=" + apnName + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("removeAPN error!", view);
        }
    }

    public void setDnsWhiteName(View view) {
        try {
            String dnsServerName = new String(getEtString(R.id.et_set_dns_white_name_dns_server_name));
            getData().setDnsWhiteName(dnsServerName);
            log("setDnsWhiteName: dnsServerName=" + dnsServerName + ", success=true", view);
        } catch (Exception e) {
            e.printStackTrace();
            log("setDnsWhiteName error!", view);
        }
    }

    public void clearDnsWhiteNameList(View view) {
        try {
            getData().clearDnsWhiteNameList();
            log("clearDnsWhiteNameList: , success=true", view);
        } catch (Exception e) {
            e.printStackTrace();
            log("clearDnsWhiteNameList error!", view);
        }
    }

    public void setDnsWhiteNameEnable(View view) {
        try {
            boolean enable = isCheckBoxSelected(R.id.cb_set_dns_white_name_enable_enable);
            getData().setDnsWhiteNameEnable(enable);
            log("setDnsWhiteNameEnable: enable=" + enable + ", success=true", view);
        } catch (Exception e) {
            e.printStackTrace();
            log("setDnsWhiteNameEnable error!", view);
        }
    }

    public void installWlanCertificate(View view) {
        try {
            String name = new String(getEtString(R.id.et_install_wlan_certificate_name));
            String filePath = new String(getEtString(R.id.et_install_wlan_certificate_path));
            String password = new String(getEtString(R.id.et_install_wlan_certificate_password));

            String certDataStr = readFileJson(new File(filePath));
            byte[] certData = certDataStr.getBytes();

            boolean result = getData().installWlanCertificate(name, certData, password);
            log("installWlanCertificate: name=" + name + ", filePath=" + filePath + ", password=" + password + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("installWlanCertificate error!", view);
        }
    }

    /**
     * 解析文件
     */
    private String readFileJson(File file) {
        //将json数据变成字符串
        StringBuilder stringBuilder = new StringBuilder();
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            BufferedReader bf = new BufferedReader(new InputStreamReader(fileInputStream));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException ignore) {
                }
            }
        }
        return stringBuilder.toString();
    }

    public void enableWifi(View view) {
        try {
            boolean enable = isCheckBoxSelected(R.id.cb_enable_wifi_enable);
            boolean result = getData().enableWifi(enable);
            log("enableWifi: enable=" + enable + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("enableWifi error!", view);
        }
    }

    public void getPackageRestrictStatus(View view) {
        try {
            String packageName = new String(getEtString(R.id.et_get_package_restrict_status_package_name));
            int result = getData().getPackageRestrictStatus(packageName);
            log("getPackageRestrictStatus: packageName=" + packageName + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getPackageRestrictStatus error!", view);
        }
    }

    public void connectWifiSsid(View view) {
        try {
            String ssid = new String(getEtString(R.id.et_connect_wifi_ssid_ssid));
            getData().connectWifiSsid(ssid, new IUnifiedCallback.Stub() {
                @Override
                public void onCall(String result) throws RemoteException {
                    log("connectWifiSsid: ssid=" + ssid + ", result=" + result, view);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            log("connectWifiSsid error!", view);
        }
    }

    public void removeWifiSsid(View view) {
        try {
            String ssid = new String(getEtString(R.id.et_remove_wifi_ssid_ssid));
            getData().removeWifiSsid(ssid, new IUnifiedCallback.Stub() {
                @Override
                public void onCall(String result) throws RemoteException {
                    log("removeWifiSsid: ssid=" + ssid + ", result=" + result, view);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            log("removeWifiSsid error!", view);
        }
    }

    public void addWifiSsidByWifiConfiguration(View view) {
        try {
            String ssid = new String(getEtString(R.id.et_add_wifi_ssid_by_wifi_configuration_ssid));
            String password = new String(getEtString(R.id.et_add_wifi_ssid_by_wifi_configuration_password));
            WifiConfigurationInfo wifiConfigurationInfo = new WifiConfigurationInfo();
            wifiConfigurationInfo.ssid = ssid;
            wifiConfigurationInfo.pwd = password;
            getData().addWifiSsidByWifiConfiguration(wifiConfigurationInfo, new IUnifiedCallback.Stub() {
                @Override
                public void onCall(String result) throws RemoteException {
                    log("addWifiSsidByWifiConfiguration: addWifiSsidByWifiConfiguration=" + ssid + ", result=" + result, view);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            log("removeWifiSsid error!", view);
        }
    }

    public void turnOffWifiHotspot(View view) {
        try {
            getData().turnOffWifiHotspot();
            log("turnOffWifiHotspot: result=true", view);
        } catch (Exception e) {
            e.printStackTrace();
            log("turnOffWifiHotspot error!", view);
        }
    }

    public void showWifiHotspotSettings(View view) {
        try {
            getData().showWifiHotspotSettings();
            log("showWifiHotspotSettings: result=true", view);
        } catch (Exception e) {
            e.printStackTrace();
            log("showWifiHotspotSettings error!", view);
        }
    }

    public void disableWifiHotspotAndHideSettings(View view) {
        try {
            getData().disableWifiHotspotAndHideSettings();
            log("disableWifiHotspotAndHideSettings: result=true", view);
        } catch (Exception e) {
            e.printStackTrace();
            log("disableWifiHotspotAndHideSettings error!", view);
        }
    }

    public void turnOnWifiHotspot(View view) {
        try {
            String ssid = new String(getEtString(R.id.et_turn_on_wifi_hotspot_ssid));
            String preShareKey = new String(getEtString(R.id.et_turn_on_wifi_hotspot_preShareKey));
            getData().turnOnWifiHotspot(ssid, preShareKey, 1);
            log("turnOnWifiHotspot: ssid=" + ssid + ", preShareKey=" + preShareKey + ", result=true", view);
        } catch (Exception e) {
            e.printStackTrace();
            log("turnOnWifiHotspot error!", view);
        }
    }

    public void isWifiHotspotEnable(View view) {
        try {
            boolean result = getData().isWifiHotspotEnable();
            log("isWifiHotspotEnable: result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("isWifiHotspotEnable error!", view);
        }
    }


    public void getSupportNetworkType(View view) {
        try {
            int slotIndex = new Integer(getEtString(R.id.et_get_support_network_type_sub_index));
            String result = getData().getSupportNetworkType(slotIndex);
            log("getSupportNetworkType: slotIndex=" + slotIndex + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getSupportNetworkType error!", view);
        }
    }

    public void setPreferredNetworkMode(View view) {
        try {
            int mode = new Integer(getEtString(R.id.et_set_preferred_network_mode_mode));
            int slotIndex = new Integer(getEtString(R.id.et_set_preferred_network_mode_subIndex));
            getData().setPreferredNetworkMode(mode, slotIndex);
            log("setPreferredNetworkMode: mode=" + mode + ", slotIndex=" + slotIndex + ", result=true", view);
        } catch (Exception e) {
            e.printStackTrace();
            log("setPreferredNetworkMode error!", view);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == 101) {
                String log = data.getStringExtra("log");
                log(log, findViewById(R.id.bt_add_a_p_n));
            } else if (requestCode == 102) {
                String log = data.getStringExtra("log");
                log(log, findViewById(R.id.bt_update_a_p_n));
            }
        }
    }

}