package com.messenger.sotial.home.chatFragment

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.NavController
import com.messenger.sotial.home.R
import com.messenger.sotial.home.util.loadPicture
import kotlinx.coroutines.ExperimentalCoroutinesApi

//@Preview
@ExperimentalCoroutinesApi
@Composable
fun SingleChat(navController: NavController,chatID : String, chatName : String, chatURL : String){

    val viewModelOwner = LocalViewModelStoreOwner.current!!
    val viewModel = ViewModelProvider(viewModelOwner).get(SingleChatVM::class.java)

    viewModel.setChatId(chatID)

    val listState = rememberLazyListState()
    var chatMsg by rememberSaveable{ mutableStateOf("")}

    val image = loadPicture(
        url = chatURL,
        defaultImage = R.drawable.ic_person_default
    ).value

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.primary)
    ) {
        Scaffold(
            topBar = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colors.primary)
                        .padding(4.dp)
                ) {
                    IconButton(
                        onClick = { navController.popBackStack() }
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBack, 
                            contentDescription = "Back"
                        )    
                    }

                    Spacer(modifier = Modifier.size(16.dp))

                    Image(
                        bitmap = image!!.asImageBitmap(),
                        contentDescription = "",
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(Color.White)
                            .border(2.dp, MaterialTheme.colors.primary, CircleShape)
                    )

                    Spacer(modifier = Modifier.size(16.dp))

                    Text(
                        text = chatName,
                        fontFamily = MaterialTheme.typography.h1.fontFamily,
                        fontSize = 25.sp,
                        color = MaterialTheme.colors.onPrimary
                    )
                }
            }

        ) {
            Column() {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(0.dp, 0.dp, 30.dp, 30.dp))
                        .background(MaterialTheme.colors.primary)
                        .weight(1f)
                ) {
                    LazyColumn(
                        state = listState,
                        modifier = Modifier
                            .padding(20.dp, 5.dp)
                            .fillMaxHeight(),
                        reverseLayout = true,
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        val chatItem = viewModel.allChatMessages.value
                        items(chatItem) { messages ->
                            if (messages.right) {
                                RightChat(messages)
                            } else {
                                LeftChat(messages)
                            }
                        }
                    }
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    OutlinedTextField(
                        value = chatMsg,
                        onValueChange = { chatMsg = it },
                        placeholder = { Text(text = "Message") },
                        modifier = Modifier
                            .fillMaxWidth(0.85F)
                            .heightIn(60.dp,150.dp)
                            .padding(4.dp)
                            .scrollable(
                                state = scrollState,
                                orientation = Orientation.Vertical,
                                enabled = true
                            ),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            textColor = Color.Black,
                            focusedBorderColor = Color.Black,
                            unfocusedBorderColor = Color.Gray,
                            disabledBorderColor = Color.Blue
                        )

                    )

                    IconButton(
                        onClick = {
                            viewModel.getChatAndUpload(chatMsg, chatID)
                            chatMsg = ""
                        },
                        modifier = Modifier.size(50.dp)
                    ) {
                        Icon(
                            painterResource(R.drawable.ic_send_button),
                            contentDescription = "Send",
                            tint = Color.Unspecified,
                            modifier = Modifier.size(50.dp)
                                .border(1.dp,MaterialTheme.colors.primary, CircleShape)
                        )
                    }
                }
            }
        }
    }
}
