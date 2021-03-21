package com.erdemtsynduev.dtmfsos

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.redmadrobot.inputmask.MaskedTextChangedListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val APP_PREFERENCES = "APP_PREFERENCES"
        const val APP_PREFERENCES_PHONE = "APP_PREFERENCES_PHONE"
    }

    private var sharedPreferences: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)

        btn_sos.isEnabled = false

        val maskedTextChangedListener = MaskedTextChangedListener.installOn(
            phone_number,
            "+7 ([000]) [000]-[00]-[00]",
            object : MaskedTextChangedListener.ValueListener {
                override fun onTextChanged(maskFilled: Boolean, extractedValue: String) {
                    btn_sos.isEnabled = maskFilled
                    if (maskFilled) {
                        savePhoneNumber(extractedValue)
                    }
                }
            }
        )

        phone_number.hint = maskedTextChangedListener.placeholder()

        btn_sos.setOnClickListener {
            getSharedPreferensCallNumber()
        }

        initPhoneNumber()
    }

    private fun savePhoneNumber(phoneString: String) {
        sharedPreferences?.edit()?.putString(APP_PREFERENCES_PHONE, phoneString)?.apply()
    }

    private fun initPhoneNumber() {
        if(sharedPreferences?.contains(APP_PREFERENCES_PHONE)!!) {
            val textTemp = sharedPreferences?.getString(APP_PREFERENCES_PHONE, "")
            phone_number.setText(textTemp)
        }
    }

    private fun getSharedPreferensCallNumber() {
        if(sharedPreferences?.contains(APP_PREFERENCES_PHONE)!!) {
            startCaller(sharedPreferences?.getString(APP_PREFERENCES_PHONE, ""))
        }
    }

    private fun startCaller(phoneNumber: String?) {
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            Toast.makeText(this, phoneNumber, Toast.LENGTH_LONG).show()
            return
        }
        val numberString = "+7" + phoneNumber + getString(R.string.sos_dtmf_signal)
        val number: Uri = Uri.parse(getString(R.string.prefix_phone_call) + numberString)
        val dial = Intent(Intent.ACTION_CALL, number)
        startActivity(dial)
    }
}