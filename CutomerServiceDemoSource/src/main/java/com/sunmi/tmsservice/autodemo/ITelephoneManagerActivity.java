package com.sunmi.tmsservice.autodemo;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.CallLog;
import android.provider.ContactsContract;
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
import com.sunmi.tmsmaster.aidl.telephonemanager.ITelephoneManager;
import com.sunmi.tmsmaster.aidl.telephonemanager.SimInfoConstant;
import com.sunmi.tmsservice.apidemo.TMSMaster;

import android.content.ContentProviderOperation;

import java.util.ArrayList;
import java.util.Set;

public class ITelephoneManagerActivity extends AppCompatActivity {

    private static final String TAG = ITelephoneManagerActivity.class.getName();

    private LinearLayout layout;
    private TextView tvLog;

    private static final String SEND_ACTION = "current.send.sms";
    private static final String DELIVERY_ACTION = "current.delivery.sms";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_i_telephone_manager);
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
            runOnUiThread(() -> Toast.makeText(ITelephoneManagerActivity.this, text, Toast.LENGTH_SHORT).show());
        }
    }

    public ITelephoneManager getData() {
        return TMSMaster.getInstance().getTelephoneManager();
    }

    public void getCarrierInfo(View view) {
        try {
            int slotIndex = new Integer(getEtString(R.id.et_get_carrier_info_slot_index));
            String result = getData().getCarrierInfo(slotIndex);
            log("getCarrierInfo: slotIndex=" + slotIndex + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getCarrierInfo error!", view);
        }
    }

    public void getPhoneNumberBySlotIndex(View view) {
        try {
            int slotIndex = new Integer(getEtString(R.id.et_get_phone_number_by_slot_index_slot_index));
            String result = getData().getPhoneNumberBySlotIndex(slotIndex);
            log("getPhoneNumberBySlotIndex: slotIndex=" + slotIndex + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("getPhoneNumberBySlotIndex error!", view);
        }
    }

    public void sendTextMessage(View view) {
        try {
            String address = new String(getEtString(R.id.et_send_text_message_address));
            String body = new String(getEtString(R.id.et_send_text_message_body));
            boolean result = getData().sendTextMessage(address, body);
            log("sendTextMessage: address=" + address + ", body=" + body + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("sendTextMessage error!", view);
        }
    }

    public void queryMessages(View view) {
        try {
            java.util.List<Bundle> result = getData().queryMessages();
            log("queryMessages: , result=" + JSON.toJSONString(result), view);
        } catch (Exception e) {
            e.printStackTrace();
            log("queryMessages error!", view);
        }
    }

    public void queryCallRecords(View view) {
        try {
            java.util.List<Bundle> result = getData().queryCallRecords();
            log("queryCallRecords: , result=" + JSON.toJSONString(result), view);
        } catch (Exception e) {
            e.printStackTrace();
            log("queryCallRecords error!", view);
        }
    }

    public void insertCallLogs(View view) {
        try {
            String phoneNumber = new String(getEtString(R.id.et_phone_number));
            ContentProviderOperation operation = ContentProviderOperation.newInsert(CallLog.Calls.CONTENT_URI).withValue(CallLog.Calls.NUMBER, phoneNumber).withValue(CallLog.Calls.TYPE, CallLog.Calls.OUTGOING_TYPE).withValue(CallLog.Calls.DATE, System.currentTimeMillis()).withValue(CallLog.Calls.DURATION, 30).withValue("sub_id", 0).build();

            ArrayList<ContentProviderOperation> list = new ArrayList<>();
            list.add(operation);

            boolean result = getData().operationCallLogs(list);
            log("operationCallLogs: 添加一条通话记录, result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("operationCallLogs 添加一条通话记录 error!", view);
        }
    }

    public void deletedOneCallLogs(View view) {
        try {
            String phoneNumber = new String(getEtString(R.id.et_phone_number));
            ContentProviderOperation operation = ContentProviderOperation.newDelete(CallLog.Calls.CONTENT_URI).withSelection(CallLog.Calls.NUMBER + "=?", new String[]{phoneNumber}).build();

            ArrayList<ContentProviderOperation> list = new ArrayList<>();
            list.add(operation);

            boolean result = getData().operationCallLogs(list);
            log("operationCallLogs: 删除指定号码所有通话记录, result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("operationCallLogs 删除指定号码所有通话记录 error!", view);
        }
    }

    public void queryContacts(View view) {
        try {
            java.util.List<Bundle> result = getData().queryContacts();
            log("queryContacts: , result=" + JSON.toJSONString(result), view);
        } catch (Exception e) {
            e.printStackTrace();
            log("queryContacts error!", view);
        }
    }

    public void operationContacts(View view) {
        try {
            String contactName = new String(getEtString(R.id.et_contact_name));
            String contactNumber = new String(getEtString(R.id.et_contact_number));

            ArrayList<ContentProviderOperation> list = new ArrayList<>();
            int rawContactInsertIndex = 0;
            list.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI).withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null).withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null).withYieldAllowed(true).build());

            //添加姓名
            list.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI).withValueBackReference(ContactsContract.RawContacts.Data.RAW_CONTACT_ID, rawContactInsertIndex).withValue(ContactsContract.RawContacts.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE).withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, contactName).withYieldAllowed(true).build());

            // 添加号码
            list.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI).withValueBackReference(ContactsContract.RawContacts.Data.RAW_CONTACT_ID, rawContactInsertIndex).withValue(ContactsContract.RawContacts.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE).withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, contactNumber).withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE).withValue(ContactsContract.CommonDataKinds.Phone.LABEL, "").withYieldAllowed(true).build());

            boolean result = getData().operationContacts(list);
            log("operationContacts: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("operationContacts error!", view);
        }
    }

    public void deleteAllContacts(View view) {
        try {
            boolean result = getData().deleteAllContacts();
            log("deleteAllContacts: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("deleteAllContacts error!", view);
        }
    }

    public void addMessage(View view) {
        try {
            boolean result = getData().addMessage(null);
            log("addMessage: , result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("addMessage error!", view);
        }
    }

    public void sendTextMessageWithCallBack(View view) {
        try {
            String address = new String(getEtString(R.id.et_send_text_message_with_call_back_address));
            String body = new String(getEtString(R.id.et_send_text_message_with_call_back_body));

            Intent send = new Intent(SEND_ACTION);
            PendingIntent sendIntent = PendingIntent.getBroadcast(this, 0, send, 0);

            Intent delivery = new Intent(DELIVERY_ACTION);
            PendingIntent deliveryIntent = PendingIntent.getBroadcast(this, 0, delivery, 0);

            boolean result = getData().sendTextMessageWithCallBack(address, body, sendIntent, deliveryIntent);
            log("sendTextMessageWithCallBack: address=" + address + ", body=" + body + ", result=" + result, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("sendTextMessageWithCallBack error!", view);
        }
    }

    public void getSimListInfo(View view) {
        try {
            java.util.List<Bundle> result = getData().getSimListInfo();
            StringBuilder stringBuilder = new StringBuilder();
            for (Bundle bundle: result) {
//                Set<String> keys = bundle.keySet();
//                for (String key : keys) {
//                    Object value = bundle.get(key); // 获取键对应的值
//                    stringBuilder.append("\"");
//                    stringBuilder.append(key);
//                    stringBuilder.append("\"");
//                    stringBuilder.append(":");
//                    stringBuilder.append(value);
//                    stringBuilder.append(",");
//                }
                stringBuilder.append("{");
                stringBuilder.append(SimInfoConstant.SIM_SLOT + ":").append(bundle.getInt(SimInfoConstant.SIM_SLOT)).append(",");
                stringBuilder.append(SimInfoConstant.IMEI + ":").append(bundle.getString(SimInfoConstant.IMEI)).append(",");
                stringBuilder.append(SimInfoConstant.SIM_STATE + ":").append(bundle.getInt(SimInfoConstant.SIM_STATE)).append(",");
                stringBuilder.append(SimInfoConstant.SIM_STATE_DESC + ":").append(bundle.getString(SimInfoConstant.SIM_STATE_DESC)).append(",");
                stringBuilder.append(SimInfoConstant.NET_COUNTRY_ISO + ":").append(bundle.getString(SimInfoConstant.NET_COUNTRY_ISO)).append(",");
                stringBuilder.append(SimInfoConstant.NET_OP + ":").append(bundle.getString(SimInfoConstant.NET_OP)).append(",");
                stringBuilder.append(SimInfoConstant.NET_OP_NAME + ":").append(bundle.getString(SimInfoConstant.NET_OP_NAME)).append(",");
                stringBuilder.append(SimInfoConstant.SIM_SN + ":").append(bundle.getString(SimInfoConstant.SIM_SN));
                stringBuilder.append("},");
            }
            log("getSimListInfo: , result=" + stringBuilder, view);
        } catch (Exception e) {
            e.printStackTrace();
            log("queryCallRecords error!", view);
        }
    }
}