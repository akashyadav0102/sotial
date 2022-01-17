package com.messenger.sotial.home.searchscreen

import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.messenger.sotial.home.HomeScreens

@Composable
fun ShowDialog(
    title : String,
    dialogState : Boolean,
    onDismiss : () -> Unit,
    onSuccess : () -> Unit
){

//    val localContext = LocalContext.current

    if(dialogState){
        AlertDialog(
            onDismissRequest = onDismiss,
            title = {
                Text(text = title)
            },
            text = {
                Text(text = "Would you like to start a chat with $title?")
            },
            confirmButton = {
                Button(
                    onClick = onSuccess,
                ) {
                    Text(text = "Yes")    
                }
            },
            dismissButton = {
                Button(
                    onClick = onDismiss
                ) {
                    Text(text = "No")
                }
            }
        )
    }

}