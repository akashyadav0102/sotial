package com.messenger.sotial

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.google.firebase.FirebaseApp
import com.google.firebase.appcheck.FirebaseAppCheck
import com.google.firebase.appcheck.safetynet.SafetyNetAppCheckProviderFactory
import com.google.firebase.auth.FirebaseAuth
import com.messenger.sotial.PhoneLogin.LoginUIActivity
import com.messenger.sotial.home.HomeNavigation
import com.messenger.sotial.ui.theme.SotialTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        FirebaseApp.initializeApp(this)
        val firebaseAppCheck = FirebaseAppCheck.getInstance()

        firebaseAppCheck.installAppCheckProviderFactory(
            SafetyNetAppCheckProviderFactory.getInstance()
        )

        val user = FirebaseAuth.getInstance().currentUser
//        user?.delete()

        super.onCreate(savedInstanceState)
        setContent {
            SotialTheme {
                if (user == null){
                    val intent = Intent(this,LoginUIActivity::class.java)
                    startActivity(intent)
                }
                HomeNavigation()
            }
        }
    }
}