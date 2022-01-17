package com.messenger.sotial.PhoneLogin

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun NameScreen (navController: NavController, viewModel: LoginSharedViewModel
){

    var inputText by rememberSaveable{ mutableStateOf("") }

    Column(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.White)) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(450.dp)
                .background(color = Color.Cyan)
                .clip(RoundedCornerShape(0.dp, 0.dp, 10.dp, 10.dp)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){

            Image(
                imageVector = Icons.Default.Person,
                contentDescription = "",
                modifier = Modifier
                    .size(250.dp)
                    .clip(CircleShape)
                    .background(color = Color.White)
            )

        }

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Enter Your Name",
                fontSize = 25.sp,
                modifier = Modifier.padding(8.dp)
            )
            OutlinedTextField(
                value = inputText,
                onValueChange = {inputText = manageLength(it)},
                modifier = Modifier.padding(8.dp),
                maxLines = 1,
                singleLine = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Red,
                    disabledBorderColor = Color.Transparent,
                    textColor = Color.Black
                )
            )
            
            Button(
                onClick = {
                    viewModel.setUserName(inputText)
                },
                modifier = Modifier
                    .padding(16.dp)
                    .size(120.dp,50.dp)) {
                Text(text = "Submit",
                    fontSize = 18.sp)
            }
        }
    }
}

private fun manageLength(it : String) = if(it.length>25) it.substring(0..24)else it