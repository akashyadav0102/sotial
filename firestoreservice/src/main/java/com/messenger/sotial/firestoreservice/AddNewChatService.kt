package com.messenger.sotial.firestoreservice

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object AddNewChatService {

    private val user = FirebaseUser

    fun addChatToList(uid2 : String, chatID : String){
        CoroutineScope(Dispatchers.IO).launch {
            val database = FirebaseFirestore.getInstance()

            val data = hashMapOf(
                "name" to user.userName,
                "photoURL" to "default",
                "chatID" to chatID
            )

            database.collection("userChats")
                .document(uid2)
                .collection("chatList")
                .add(data)
                .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot written with ID: ${it.id}") }
                .addOnFailureListener { Log.w(TAG, "Error adding document", it) }
        }
    }

    fun addFriendChatToList(uid1 : String, chatID: String, friendName : String){
        CoroutineScope(Dispatchers.IO).launch {
            val database = FirebaseFirestore.getInstance()

            val data = hashMapOf(
                "name" to friendName,
                "photoURL" to "default",
                "chatID" to chatID
            )

            database.collection("userChats")
                .document(uid1)
                .collection("chatList")
                .add(data)
                .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot written with ID: ${it.id}") }
                .addOnFailureListener { Log.w(TAG, "Error adding document", it) }
        }
    }

    private const val TAG = "ADD_NEW_CHAT_SERVICE"

}