package com.sunmi.tmsservice.apidemo;

import android.content.Context;

import com.sunmi.tmsmaster.aidl.IDeviceService;
import com.sunmi.tmsmaster.aidl.certificatemanager.ICertificateManager;
import com.sunmi.tmsmaster.aidl.deviceinfo.IDeviceInfo;
import com.sunmi.tmsmaster.aidl.devicemanager.IDeviceManager;
import com.sunmi.tmsmaster.aidl.devicerunninginfo.IDeviceRunningInfo;
import com.sunmi.tmsmaster.aidl.host.IHostManager;
import com.sunmi.tmsmaster.aidl.iot.IIotInfo;
import com.sunmi.tmsmaster.aidl.kioskmanager.IKioskManager;
import com.sunmi.tmsmaster.aidl.networkmanager.INetworkManager;
import com.sunmi.tmsmaster.aidl.pm.IPackageCTPA;
import com.sunmi.tmsmaster.aidl.pm.IServicePreference;
import com.sunmi.tmsmaster.aidl.settingsuimanager.ISettingsUIManager;
import com.sunmi.tmsmaster.aidl.softwaremanager.ISoftwareManager;
import com.sunmi.tmsmaster.aidl.systemmanager.ISystemManager;
import com.sunmi.tmsmaster.aidl.systemuimanager.ISystemUIManager;
import com.sunmi.tmsmaster.aidl.telephonemanager.ITelephoneManager;

public class TMSMaster {

    private static TMSMaster instance = null;

    private TMSMaster() {
    }

    private Context applicationContext;
    private IDeviceService deviceService;
    private ISoftwareManager softwareManager;
    private IDeviceManager deviceManager;
    private IDeviceInfo deviceInfo;
    private ISystemManager systemManager;
    private IHostManager host;
    private ISystemUIManager systemUiManager;
    private IKioskManager kioskManager;
    private IDeviceRunningInfo deviceRunningInfo;
    private INetworkManager networkManager;
    private IServicePreference servicePreference;
    private ICertificateManager certificateManager;
    private IPackageCTPA iPackageCTPA;
    private IIotInfo iotInfo;
    private ISettingsUIManager iSettingsUIManager;
    private ITelephoneManager iTelephoneManager;

    public static TMSMaster getInstance() {
        if (instance == null) {
            synchronized (TMSMaster.class) {
                if (instance == null) {
                    instance = new TMSMaster();
                }
            }
        }
        return instance;
    }

    public void init(Context context, IDeviceService deviceService) {
        if (context == null) {
            throw new NullPointerException("Context = null!");
        }
        applicationContext = context.getApplicationContext();
        this.deviceService = deviceService;

        try {
            softwareManager = deviceService.getSoftwareManagerBinder();
            deviceManager = deviceService.getDeviceManagerBinder();
            deviceInfo = deviceService.getDeviceInfoBinder();
            systemManager = deviceService.getSystemManagerBinder();
            systemUiManager = deviceService.getSystemUIManagerBinder();
            kioskManager = deviceService.getKioskManagerBinder();
            deviceRunningInfo = deviceService.getDeviceRunningInfoBinder();
            networkManager = deviceService.getNetworkManagerBinder();
            servicePreference = deviceService.getPreference();
            certificateManager = deviceService.getCertificateManagerBinder();
            iPackageCTPA = deviceService.getAllPackageInfo();
            iotInfo = deviceService.getIotInfoBinder();
            host = deviceService.getHostManager();
            iSettingsUIManager = deviceService.getSettingsUIManagerBinder();
            iTelephoneManager = deviceService.getTelephoneManager();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clear() {
        deviceService = null;
        softwareManager = null;
        deviceManager = null;
        deviceInfo = null;
        systemManager = null;
        systemUiManager = null;
        kioskManager = null;
        deviceRunningInfo = null;
        networkManager = null;
        servicePreference = null;
        iPackageCTPA = null;
        iotInfo = null;
        host = null;
        iSettingsUIManager = null;
        iTelephoneManager = null;
    }

    public Context getApplicationContext() {
        return applicationContext;
    }

    public ISoftwareManager getSoftwareManager() {
        return softwareManager;
    }

    public IDeviceManager getDeviceManager() {
        return deviceManager;
    }

    public IDeviceInfo getDeviceInfo() {
        return deviceInfo;
    }

    public ISystemManager getSystemManager() {
        return systemManager;
    }

    public IHostManager getHost() {
        return host;
    }

    public ISystemUIManager getSystemUiManager() {
        return systemUiManager;
    }

    public IKioskManager getKioskManager() {
        return kioskManager;
    }

    public IDeviceRunningInfo getDeviceRunningInfo() {
        return deviceRunningInfo;
    }

    public INetworkManager getNetworkManager() {
        return networkManager;
    }

    public IServicePreference getServicePreference() {
        return servicePreference;
    }

    public ICertificateManager getCertificateManager() {
        return certificateManager;
    }

    public IPackageCTPA getiPackageCTPA() {
        return iPackageCTPA;
    }

    public IIotInfo getIotInfo() {
        return iotInfo;
    }

    public ISettingsUIManager getSettingsUIManager() {
        return iSettingsUIManager;
    }

    public ITelephoneManager getTelephoneManager() {
        return iTelephoneManager;
    }
}
