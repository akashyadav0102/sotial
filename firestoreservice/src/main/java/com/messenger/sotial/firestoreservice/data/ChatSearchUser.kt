package com.messenger.sotial.firestoreservice.data

import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import java.lang.Exception

data class ChatSearchUser(

    val userName : String,
    val phoneNo : String,
    val photoURL : String,
    val uId : String
){

    companion object{

        fun DocumentSnapshot.toChatSearchUser() : ChatSearchUser?{
            return try {
                val name = getString("name")!!
                val phone_no = getString("phone_no")!!
                val photoURL = getString("photoURL")!!
                val uId = getString("uId")!!

                ChatSearchUser(name,phone_no,photoURL,uId)
            } catch (e : Exception){
                Log.e(TAG,"Error finding User",e)
                null
            }
        }

        private const val TAG = "SearchUserError"

    }

}
