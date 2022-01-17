package com.messenger.sotial.home.searchscreen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.NavController
import com.messenger.sotial.firestoreservice.FirebaseUser
import com.messenger.sotial.home.HomeScreens
import com.messenger.sotial.home.util.UidBuilder
import kotlinx.coroutines.launch

@Composable
fun SearchScreen(navController: NavController){

    val context = LocalContext.current
    val viewLifecycleOwner = LocalViewModelStoreOwner.current!!
    val lifecycleOwner = LocalLifecycleOwner.current
    val viewModel = ViewModelProvider(viewLifecycleOwner).get(SearchScreenVM::class.java)

    var inputNo by rememberSaveable{mutableStateOf("")}

    val dialogState = rememberSaveable{ mutableStateOf(false) }
    val dialogString = rememberSaveable{ mutableStateOf("")}

    val currUser = FirebaseUser.uid
    val friendUser = rememberSaveable{ mutableStateOf("") }
    val friendName = rememberSaveable{ mutableStateOf("") }
    val friendURL = rememberSaveable{ mutableStateOf("") }

    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    if(dialogState.value){
        ShowDialog(
            dialogString.value,
            dialogState.value,
            {dialogState.value = false}
        ) { dialogState.value = false
            if (currUser!! != friendUser.value){
                val chatID = UidBuilder(currUser,friendUser.value)

                navController.navigate(
                    HomeScreens.SingleChatScreen.withArgs(chatID,friendName.value,friendURL.value))

                viewModel.addNewChatToLists(currUser,friendUser.value,chatID,friendName.value)
            }
            else{
                Toast.makeText(
                        context,
                        "Cannot Start Conversation with the same user",
                        Toast.LENGTH_SHORT
                    ).show()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.secondary)
    ) {

        Scaffold(
            topBar = {
                Row(modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ){

                    IconButton(
                        onClick = {
                                  navController.popBackStack()
                        },
                    ) {
                        Icon(
                            Icons.Rounded.ArrowBack,
                            contentDescription = "back"
                        )
                    }

                    Box(
                        contentAlignment = Alignment.CenterStart
                    ) {
                        OutlinedTextField(
                            value = inputNo,
                            onValueChange = {
                                inputNo = it
                                viewModel.onNoInput(it)
                            },
                            placeholder = { Text(
                                text = "Search for users using their contact number",
                                fontSize = 12.sp
                            )},
                            modifier = Modifier
                                .fillMaxWidth(0.85F)
                                .size(50.dp)
                                .clip(RoundedCornerShape(5.dp)),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Phone
                            )
                        )
                    }
                }
            },
        ) {

            Column(
                Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(30.dp, 30.dp, 0.dp, 0.dp))
                    .background(MaterialTheme.colors.primary)
            ) {

                LazyColumn(
                    state = listState,
                    modifier = Modifier
                        .padding(20.dp,15.dp)
                ) {
                    val searchList = viewModel.usersFound.value
                        itemsIndexed(searchList){index,item ->
                        Row(
                            modifier = Modifier
                                .clickable(
                                enabled = true
                            ){
                                friendUser.value = item.uId
                                friendName.value = item.userName
                                friendURL.value = item.photoURL
                                dialogString.value = item.userName
                                dialogState.value = true
                            }
                        ) {
                                SearchListItem(item = item)
                        }
                    }

                }
            }
        }
    }
}
