package com.sunmi.tmsservice.autodemo

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import com.sunmi.cutomerservicedemosource.R
import com.sunmi.tmsmaster.aidl.networkmanager.APNConfigInfo
import com.sunmi.tmsservice.apidemo.TMSMaster
import com.sunmi.tmsservice.autodemo.util.Util

class ApnInsertActivity : AppCompatActivity() {
    companion object {
        const val TAG = "ApnInsertActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_apn_insert2)

        findViewById<Button>(R.id.bt_add_apn).setOnClickListener {
            try {
                val addAPN = TMSMaster.getInstance().networkManager.addAPN(getApnInfo())
                log("addAPN: result=$addAPN")
                val intent = Intent()
                intent.putExtra("log", "addAPN: apn = ${Util.object2json(getApnInfo())}, result=$addAPN")
                setResult(RESULT_OK, intent)
                finish()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        findViewById<Button>(R.id.bt_update_apn).setOnClickListener {
            try {
                val updateAPN = TMSMaster.getInstance().networkManager.updateAPN(getApnInfo())
                val intent = Intent()
                intent.putExtra("log", "updateAPN: apn = ${Util.object2json(getApnInfo())}, result=$updateAPN")
                setResult(RESULT_OK, intent)
                finish()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun log(text: String) {
        Log.d(TAG, text)
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    private fun getApnInfo(): APNConfigInfo {
        return APNConfigInfo().also {
            it.name = findViewById<AppCompatEditText>(R.id.et_name).editableText.toString()
            it.apn = findViewById<AppCompatEditText>(R.id.et_apn).editableText.toString()
            it.type = findViewById<AppCompatEditText>(R.id.et_type).editableText.toString()
            it.proxy = findViewById<AppCompatEditText>(R.id.et_proxy).editableText.toString()
            it.server = findViewById<AppCompatEditText>(R.id.et_server).editableText.toString()
            it.port = findViewById<AppCompatEditText>(R.id.et_port).editableText.toString()
            val authType = findViewById<AppCompatEditText>(R.id.sp_auth_type).editableText.toString()
            if (!TextUtils.isEmpty(authType)) {
                it.authType = authType.toInt()
            } else {
                it.authType = -1
            }
            it.user = findViewById<AppCompatEditText>(R.id.et_user).editableText.toString()
            it.password = findViewById<AppCompatEditText>(R.id.et_password).editableText.toString()
            it.mmsproxy = findViewById<AppCompatEditText>(R.id.et_mms_proxy).editableText.toString()
            it.mmsport = findViewById<AppCompatEditText>(R.id.et_mms_port).editableText.toString()
            it.mmsc = findViewById<AppCompatEditText>(R.id.et_mmsc).editableText.toString()
            it.mcc = findViewById<AppCompatEditText>(R.id.et_mcc).editableText.toString()
            it.mnc = findViewById<AppCompatEditText>(R.id.et_mnc).editableText.toString()
        }
    }
}