package com.messenger.sotial.firestoreservice.data

import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentSnapshot
import com.messenger.sotial.firestoreservice.FirebaseUser
import java.lang.Exception

data class ChatMessages(

    val message : String,
    val timestamp : Timestamp,
    val userID : String,
    val right : Boolean

){
    companion object{
        fun DocumentSnapshot.toChatMessage() : ChatMessages?{

            val uid = FirebaseUser.uid

            return try {
                val message = getString("message")!!
                val timestamp = getTimestamp("timestamp")!!
                val userID = getString("user_id")!!
                val isRight = (userID==uid)

                ChatMessages(message,timestamp,userID,isRight)

            }catch (e : Exception){
                Log.e(TAG,"Error converting List User",e)
                null
            }
        }
        private const val TAG = "ChatMessagesError"
    }


}
