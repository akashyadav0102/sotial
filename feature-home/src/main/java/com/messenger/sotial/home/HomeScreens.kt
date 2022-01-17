package com.messenger.sotial.home

sealed class HomeScreens(val route : String) {

    object HomeMainScreen : HomeScreens("home_screen")
    object SearchScreen : HomeScreens("search_screen")
    object SingleChatScreen : HomeScreens("chat_screen")

    fun withArgs(vararg args : String) : String{
        return buildString {
            append(route)
            args.forEach { arg->
                append("/$arg")
            }
        }
    }

}