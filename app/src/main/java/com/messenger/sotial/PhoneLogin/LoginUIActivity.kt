package com.messenger.sotial.PhoneLogin

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.messenger.sotial.ui.theme.SotialTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit

class LoginUIActivity : AppCompatActivity() {

//    val user_ = Datab

    lateinit var viewModel : LoginSharedViewModel
    private lateinit var auth: FirebaseAuth
    private var storedVerificationId: String? = ""
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var callbacks : PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private var phNoWithCode = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(LoginSharedViewModel::class.java)
        auth = Firebase.auth

        //Disable For Testing
//        FirebaseAuth.getInstance().firebaseAuthSettings.setAppVerificationDisabledForTesting(true)

        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                signInWithPhoneAuthCredential(credential)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                if (e is FirebaseAuthInvalidCredentialsException) {
                    Toast.makeText(
                        this@LoginUIActivity,
                        "Invalid Phone Number or Verification code",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (e is FirebaseTooManyRequestsException) {
                    Toast.makeText(
                        this@LoginUIActivity,
                        "Unable to process verification. Try again later",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                storedVerificationId = verificationId
                resendToken = token

                viewModel.otpCode.observe(this@LoginUIActivity,{
                    if(it.isNotEmpty()){
                        val credential = PhoneAuthProvider.getCredential(
                            storedVerificationId!!,
                            it
                        )
                        signInWithPhoneAuthCredential(credential)
                    }
                })

                viewModel.resendBoolean.observe(this@LoginUIActivity,{
                    if(it == true){
                        resendCode()
                    }
                })
            }
        }

        setContent {
            SotialTheme {
                navigationClass(viewModel)
            }
        }

        viewModel.phoneNo.observe(this, { phoneNumber ->
            if(phoneNumber.isNotEmpty()){
                viewModel.countryCode.observe(this,{ ccp->
                    phNoWithCode = ccp + phoneNumber

                    if(ccp.isNotEmpty()){
                        val options = PhoneAuthOptions.newBuilder(auth)
                            .setPhoneNumber(phNoWithCode)
                            .setTimeout(60L, TimeUnit.SECONDS)
                            .setActivity(this)
                            .setCallbacks(callbacks)
                            .build()
                        PhoneAuthProvider.verifyPhoneNumber(options)
                    }
                })
            }
        })

        viewModel.userName.observe(this,{
            if(it.isNotEmpty()){
                val user = FirebaseAuth.getInstance().currentUser
                val updateName = UserProfileChangeRequest.Builder()
                    .setDisplayName(it)
                    .build()

                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        user?.updateProfile(updateName)
                        val db = FirebaseFirestore.getInstance()

                        val data = hashMapOf(
                            "name" to user?.displayName,
                            "phone_no" to user?.phoneNumber,
                            "photoURL" to "default",
                            "uId" to user?.uid
                        )

                        db.collection("allUsers")
                            .document(user?.phoneNumber!!)
                            .set(data)

                        Log.i("UPDATESUCCESS","UPDATED")

                    } catch (e : Exception){
                        withContext(Dispatchers.Main){
                            Log.i("NameUpdate",e.message!!)
                        }
                    }
                    finish()
                }
            }
        })
    }

    private fun resendCode(){
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phNoWithCode)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(callbacks)
            .setForceResendingToken(resendToken)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }


    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    Toast.makeText(
                        this@LoginUIActivity,
                        "Success!",
                        Toast.LENGTH_SHORT
                    ).show()

                    viewModel.stopTimer()

                    viewModel.setStackStatus(true)

                } else {
                    if (task.exception is FirebaseAuthInvalidCredentialsException){
                        Toast.makeText(
                            this@LoginUIActivity,
                            "Sign In failed. Incorrect verification code",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
    }

}