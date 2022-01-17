package com.messenger.sotial.home.mainscreen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.NavController
import com.messenger.sotial.home.HomeScreens
import com.messenger.sotial.home.R
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

//@Preview
@FlowPreview
@ExperimentalCoroutinesApi
@Composable
fun MainScreen(navController: NavController){

    val viewLifecycleOwner = LocalViewModelStoreOwner.current!!
    val viewModel = ViewModelProvider(viewLifecycleOwner).get(MainScreenVM::class.java)
    val lifecycleOwner = LocalLifecycleOwner.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.secondary)
    ){
        Scaffold(
            topBar = {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.secondary),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(
                        onClick = { /*TODO*/ },
                        ) {
                        Icon(
                            Icons.Rounded.Settings,
                            contentDescription = "Settings"
                        )
                    }

                    Text(
                        text = "Sotial",
                        fontFamily = MaterialTheme.typography.h2.fontFamily,
                        fontSize = 30.sp,
                        color = MaterialTheme.colors.onSecondary
                    )

                    IconButton(
                        onClick = { /*TODO*/ },
                    ) {
                        Icon(
                            Icons.Rounded.Search,
                            contentDescription = "Search"
                        )
                    }
                }
            },
            drawerContent = {

            },
            floatingActionButton = {
                IconButton(
                    onClick = {
                              navController.navigate(HomeScreens.SearchScreen.route)
                    },
                    modifier = Modifier
                        .padding(0.dp)
                        .size(60.dp)
                ) {
                    Icon(
                        painterResource(id = R.drawable.ic_splusrounded),
                        contentDescription = "Add",
                        tint = Color.Unspecified
                    )
                }
            },
            floatingActionButtonPosition = FabPosition.End

        ) {
            Column(
                Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(30.dp, 30.dp, 0.dp, 0.dp))
                    .background(MaterialTheme.colors.primary)
            ) {

                LazyColumn(
                    modifier = Modifier.padding(20.dp,15.dp)
                ) {
                    val chatList = viewModel.userChatList.value
                    itemsIndexed(chatList){index,chat->
                        Row(
                            modifier = Modifier.clickable(
                                enabled = true
                            ){
                                navController.navigate(
                                    HomeScreens.SingleChatScreen.withArgs(
                                        chat.chatID,chat.chatName,chat.photoURL))
                            }
                        ) {
                            ChatListItem(item = chat)
                        }
                    }
                }
            }
        }
    }
}