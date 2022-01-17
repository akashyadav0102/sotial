package com.messenger.sotial.home.mainscreen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import com.messenger.sotial.firestoreservice.ChatListService
import com.messenger.sotial.firestoreservice.data.ChatListUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@FlowPreview
@HiltViewModel
class MainScreenVM @Inject constructor() : ViewModel() {

    var userChatList : MutableState<List<ChatListUser>> = mutableStateOf(listOf())

    init{
        viewModelScope.launch {
            ChatListService.getChatList()
                .collect {
                userChatList.value = it
            }
        }
    }
}