package com.messenger.sotial.firestoreservice

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import com.messenger.sotial.firestoreservice.data.ChatSearchUser
import com.messenger.sotial.firestoreservice.data.ChatSearchUser.Companion.toChatSearchUser
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
object UserSearchService {

    //TODO: Pagination for limiting no of queried items

    private val firebaseUser = FirebaseUser.Companion

    @Singleton
    @Provides
    @ExperimentalCoroutinesApi
    suspend fun getSearchUser(searchQuery : String) : Flow<List<ChatSearchUser>> {
        val database = FirebaseFirestore.getInstance()

        return callbackFlow {
            val listenerRegistration = database
                .collection("allUsers")
                .whereGreaterThanOrEqualTo("phone_no",searchQuery)
                .whereLessThanOrEqualTo("phone_no",searchQuery + '\uf8ff')
                .addSnapshotListener{
                        querySnapshot : QuerySnapshot?,
                        firebaseFirestoreException: FirebaseFirestoreException? ->

                    if(firebaseFirestoreException != null){
                        cancel(
                            message = "Error fetching Users",
                            cause = firebaseFirestoreException
                        )
                        return@addSnapshotListener
                    }

                    val map = querySnapshot!!.documents.mapNotNull { it.toChatSearchUser() }
                    offer(map)
                }
            awaitClose {
                listenerRegistration.remove()
            }
        }
    }
}