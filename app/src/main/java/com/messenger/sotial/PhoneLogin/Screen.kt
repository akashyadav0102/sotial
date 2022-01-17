package com.messenger.sotial.PhoneLogin

sealed class Screen(val route : String){
    object LoginUIPage : Screen("login_page")
    object SmsVerificationUI : Screen("sms_page")
    object NameAndDpUI : Screen("name_page")
}
