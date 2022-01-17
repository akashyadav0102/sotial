package com.messenger.sotial.firestoreservice.data

import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import java.lang.Exception


data class ChatListUser(
    val chatID : String,
    val chatName : String,
    val photoURL : String
){
    companion object{

        fun DocumentSnapshot.toChatListUser() : ChatListUser?{
            return try{
                val chat_ID = getString("chatID")!!
                val chat_Name = getString("name")!!
                val photo_URL = getString("photoURL")!!

                ChatListUser(chat_ID,chat_Name,photo_URL)

            }catch (e : Exception){
                Log.e(TAG,"Error converting List User",e)
                null
            }
        }
        private const val TAG = "ChatListUserError"
    }

}
