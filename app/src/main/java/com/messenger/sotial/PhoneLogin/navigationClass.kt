package com.messenger.sotial.PhoneLogin

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun navigationClass(viewModel: LoginSharedViewModel){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.LoginUIPage.route){
        composable(route = Screen.LoginUIPage.route){
            LoginScreen(navController = navController,viewModel = viewModel)
        }
        composable(route = Screen.SmsVerificationUI.route){
            SmsVerificationUI(navController = navController,viewModel = viewModel)
        }
        composable(route = Screen.NameAndDpUI.route){
            NameScreen(navController = navController,viewModel = viewModel)
        }
    }

}