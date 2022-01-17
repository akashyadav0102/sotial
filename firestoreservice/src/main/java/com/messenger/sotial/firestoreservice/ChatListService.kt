package com.messenger.sotial.firestoreservice

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import com.messenger.sotial.firestoreservice.data.ChatListUser
import com.messenger.sotial.firestoreservice.data.ChatListUser.Companion.toChatListUser
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ChatListService{

    private val currentUser = FirebaseUser.Companion

    @Singleton
    @Provides
    @ExperimentalCoroutinesApi
    suspend fun getChatList() : Flow<List<ChatListUser>> {
        val database = FirebaseFirestore.getInstance()
        return callbackFlow{

            val listenerRegistration = database.collection("userChats")
                .document(currentUser.uid!!)
                .collection("chatList")
                .addSnapshotListener{
                        querySnapshot : QuerySnapshot?,
                        firebaseFirestoreException: FirebaseFirestoreException? ->

                    if(firebaseFirestoreException != null){
                        cancel(
                            message = "Error fetching Chats",
                            cause = firebaseFirestoreException
                        )
                        return@addSnapshotListener
                    }

                    val map = querySnapshot!!.documents.mapNotNull { it.toChatListUser() }
                    offer(map)
                }
                awaitClose {
                    listenerRegistration.remove()
                }
        }
    }
}