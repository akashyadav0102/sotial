package com.messenger.sotial.firestoreservice

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.messenger.sotial.firestoreservice.data.ChatMessages
import com.messenger.sotial.firestoreservice.data.ChatMessages.Companion.toChatMessage
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

object ReceiveChatRoomService {

    @ExperimentalCoroutinesApi
    suspend fun getChatRoomData(chatID : String) : Flow<List<ChatMessages>> {
        val database = FirebaseFirestore.getInstance()

        return callbackFlow {
            val listenerRegistration = database
                .collection("chatRooms")
                .document(chatID)
                .collection("messages")
                .orderBy("timestamp",Query.Direction.DESCENDING)
                .addSnapshotListener{ querySnapshot: QuerySnapshot?,
                                       firebaseFirestoreException: FirebaseFirestoreException? ->

                    if(firebaseFirestoreException != null){
                        cancel(
                            message = "Error fetching Messages",
                            cause = firebaseFirestoreException
                        )
                        return@addSnapshotListener
                    }

                    val map = querySnapshot!!.documents.mapNotNull{it.toChatMessage()}
                    offer(map)
                }
            awaitClose {
                listenerRegistration.remove()
            }
        }
    }

}