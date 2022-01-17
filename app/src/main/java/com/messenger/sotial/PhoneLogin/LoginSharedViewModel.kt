package com.messenger.sotial.PhoneLogin

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import java.util.*
import java.util.concurrent.TimeUnit
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginSharedViewModel : ViewModel(){

    private var _phoneNo = MutableLiveData("")
    private var _otpCode = MutableLiveData("")
    private var _countryCode = MutableLiveData("")
    private var _userName = MutableLiveData("")
    private var _resendBoolean = MutableLiveData(false)
    private var _isStackPopped = MutableLiveData(false)

    var phoneNo : LiveData<String> = _phoneNo
    var countryCode : LiveData<String> = _countryCode
    var otpCode : LiveData<String> = _otpCode
    var userName : LiveData<String> = _userName
    var resendBoolean : LiveData<Boolean> = _resendBoolean
    var isStackPopped : LiveData<Boolean> = _isStackPopped

    fun setStackStatus(it : Boolean){
        _isStackPopped.value = it
    }

    fun setPhoneNo(setNumber : String){
        _phoneNo.value = setNumber
        timer.start()
    }

    fun setOtpCode(setOtp : String){
        _otpCode.value = setOtp
    }

    fun setUserName(setName : String){
        _userName.value = setName
    }

    fun setCountryCode(setCCP : String){
        _countryCode.value = setCCP
    }

    fun setResendBool(){
        viewModelScope.launch {
            _resendBoolean.value = true
            delay(1000L)
            _resendBoolean.value = false
        }
    }

    fun returnPhoneNo() : String?{
        return phoneNo.value
    }

    fun returnCountryCode() : String?{
        return countryCode.value
    }

    fun stopTimer(){
        timer.cancel()
    }

    var ctntimer = MutableLiveData("")

    val _ctntimer : LiveData<String> = ctntimer

    val duration = TimeUnit.SECONDS.toMillis(60)
    var timer = object : CountDownTimer(duration,1000){
        override fun onTick(p0: Long) {
            val sDuration = String.format(
                Locale.ENGLISH,
                "%02d",
                TimeUnit.MILLISECONDS.toSeconds(p0)
            )
            ctntimer.value = sDuration
        }

        override fun onFinish() {
            ctntimer.value = "Resend OTP Code"
        }
    }

}