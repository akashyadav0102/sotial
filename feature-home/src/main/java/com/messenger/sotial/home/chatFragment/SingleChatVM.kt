package com.messenger.sotial.home.chatFragment

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.*
import com.messenger.sotial.firestoreservice.FirebaseUser
import com.messenger.sotial.firestoreservice.ReceiveChatRoomService
import com.messenger.sotial.firestoreservice.SendChatRoomService
import com.messenger.sotial.firestoreservice.data.ChatMessages
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class SingleChatVM @Inject constructor() : ViewModel(){

    private val chatId = MutableLiveData("")

    fun setChatId(it : String){
        chatId.value = it

        viewModelScope.launch {
            startChatRetrieval()
        }

    }

    val allChatMessages : MutableState<List<ChatMessages>> = mutableStateOf(listOf())

    private suspend fun startChatRetrieval(){
        if(chatId.value!!.isNotEmpty()){
            viewModelScope.launch {
                ReceiveChatRoomService.getChatRoomData(chatId.value!!).collect {
                    allChatMessages.value = it
                }
            }
        }
    }

    var text = MutableLiveData("")

    fun getChatAndUpload(it : String, chatID : String){
        text.value = it
        SendChatRoomService.sendChats(text.value!!,chatID)
    }

}