package com.messenger.sotial.firestoreservice

import android.util.Log
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object SendChatRoomService {

    private val firebaseUser = FirebaseUser.Companion

    //RETURN TRUE OR FALSE FOR MESSAGES SEND OR NOT

    fun sendChats(text : String, chatID : String){

        CoroutineScope(Dispatchers.IO).launch{
            val database = FirebaseFirestore.getInstance()

            val data = hashMapOf(
                "timestamp" to FieldValue.serverTimestamp(),
                "user_id" to firebaseUser.uid,
                "message" to text
            )

            database.collection("chatRooms")
                .document(chatID)
                .collection("messages").add(data)
                .addOnSuccessListener {
                    Log.d(TAG, "DocumentSnapshot written with ID: ${it.id}")
                }
                .addOnFailureListener {
                    Log.w(TAG, "Error adding document", it)
                }
        }
    }

    private const val TAG = "CHAT_ROOM_SERVICE_SEND_DATA"

}