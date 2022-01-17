package com.messenger.sotial.home

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.messenger.sotial.home.chatFragment.SingleChat
import com.messenger.sotial.home.mainscreen.MainScreen
import com.messenger.sotial.home.searchscreen.SearchScreen
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@ExperimentalCoroutinesApi
@FlowPreview
@Composable
fun HomeNavigation(){

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = HomeScreens.HomeMainScreen.route){

        composable(route = HomeScreens.HomeMainScreen.route){
            MainScreen(navController)
        }
        composable(route = HomeScreens.SearchScreen.route){
            SearchScreen(navController)
        }
        composable(
            route = HomeScreens.SingleChatScreen.route + "/{chatID}/{chatName}/{chatURL}",
            arguments = listOf(
                navArgument("chatID"){
                    type = NavType.StringType
                    defaultValue = ""
                    nullable = false
                },
                navArgument("chatName"){
                    type = NavType.StringType
                    defaultValue = ""
                    nullable = false
                },
                navArgument("chatURL"){
                    type = NavType.StringType
                    defaultValue = ""
                    nullable = false
                }
            )
        ){backStackEntry->
            SingleChat(
                navController,
                backStackEntry.arguments?.getString("chatID")!!,
                backStackEntry.arguments?.getString("chatName")!!,
                backStackEntry.arguments?.getString("chatURL")!!
            )
        }

    }

}