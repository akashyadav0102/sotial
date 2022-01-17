package com.messenger.sotial.home.searchscreen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.messenger.sotial.firestoreservice.AddNewChatService
import com.messenger.sotial.firestoreservice.UserSearchService
import com.messenger.sotial.firestoreservice.data.ChatSearchUser
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchScreenVM @Inject constructor() : ViewModel() {

    //mutableStateList

    private var _inputNumber = MutableLiveData("")
    var inputNumber : LiveData<String> = _inputNumber

    var usersFound : MutableState<List<ChatSearchUser>> = mutableStateOf(listOf())


    @ExperimentalCoroutinesApi
    fun onNoInput(it : String){
        _inputNumber.value = it
        callRepoForSearch(inputNumber.value!!)
    }

    @ExperimentalCoroutinesApi
    private fun callRepoForSearch(searchQuery : String){
        viewModelScope.launch {
            UserSearchService.getSearchUser(searchQuery).collect {
                usersFound.value = it
            }
        }
    }

    fun addNewChatToLists(
        uid1: String,
        uid2: String,
        chatID: String,
        friendName : String
    ){
        AddNewChatService.addChatToList(uid2,chatID)
        AddNewChatService.addFriendChatToList(uid1,chatID, friendName)
    }

}