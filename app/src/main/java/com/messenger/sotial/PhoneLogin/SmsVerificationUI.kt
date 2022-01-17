package com.messenger.sotial.PhoneLogin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.messenger.sotial.ui.theme.mattRed
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.navigation.NavController

@Composable
fun SmsVerificationUI(navController: NavController,viewModel: LoginSharedViewModel){

    var inputText by rememberSaveable{ mutableStateOf("")}
    var timer by rememberSaveable{ mutableStateOf("")}
    val viewLifecycleOwner = LocalLifecycleOwner.current

    viewModel._ctntimer.observe(viewLifecycleOwner,{
        timer = it
    })

    Box(modifier = Modifier
        .fillMaxSize()
        .background(mattRed))
    {
       Column(modifier = Modifier
           .fillMaxSize())
       {
           Row(modifier = Modifier
               .padding(8.dp)
               .fillMaxWidth(),
               horizontalArrangement = Arrangement.Start,
               verticalAlignment = Alignment.CenterVertically)
           {
               IconButton(onClick = {
                   navController.popBackStack()

               }) {
                   Icon(Icons.Rounded.ArrowBack, contentDescription = "Back")
               }
               
               Text(text = "OTP Verification", color = Color.White)
               
           }
       }
       
       Column(modifier = Modifier
           .fillMaxSize(),
           horizontalAlignment = Alignment.CenterHorizontally,
           verticalArrangement = Arrangement.Center)
       {
           Text(text = "We have sent a verification code to",
           modifier = Modifier.padding(8.dp),
           color = Color.White)

           DisplayNumber(viewModel)

           OutlinedTextField(value = inputText,
               onValueChange = {inputText = it},
               placeholder = {Text(text = "Enter the OTP")},
               modifier = Modifier.padding(8.dp),
               keyboardOptions = KeyboardOptions(
                   keyboardType = KeyboardType.Phone),
               shape = RoundedCornerShape(10.dp),
               visualTransformation = PasswordVisualTransformation(),
               colors = TextFieldDefaults.outlinedTextFieldColors(
                   focusedBorderColor = Color.White,
                   unfocusedBorderColor = Color.Transparent,
                   disabledBorderColor = Color.Transparent,
                   textColor = Color.White
               )
           )

           Row(modifier = Modifier.fillMaxWidth(),
               horizontalArrangement = Arrangement.SpaceEvenly)
           {
               Button(
                   onClick = {
                                viewModel.setResendBool()
                             },
                   modifier = Modifier
                       .padding(8.dp),
                   shape = RoundedCornerShape(10.dp),
                   colors = ButtonDefaults.buttonColors(
                       backgroundColor = Color.Black,
                       disabledBackgroundColor = Color.DarkGray
                   ),
                   enabled = viewModel._ctntimer.value == "Resend OTP Code"
               ) {
                   Text(text = timer, color = Color.White)
               }

               Button(onClick = {
                   viewModel.setOtpCode(inputText)

                   viewModel.isStackPopped.observe(viewLifecycleOwner,{
                       if(it == true){
                           navController.navigate(Screen.NameAndDpUI.route){
                               popUpTo(Screen.SmsVerificationUI.route){
                                   inclusive = true
                               }
                           }
                       }
                   })

               },
                   modifier = Modifier
                       .padding(8.dp),
                   shape = RoundedCornerShape(10.dp),
                   colors = ButtonDefaults.buttonColors(
                       backgroundColor = Color.Black,
                       disabledBackgroundColor = Color.DarkGray
                   ),
                   enabled = inputText.isNotEmpty()) {
                   Text(text = "Submit OTP",color = Color.White)
               }
           }
       }
   }
}

@Composable
fun DisplayNumber(viewModel: LoginSharedViewModel){
    Text(text = viewModel.returnCountryCode()!!.plus("-").plus(viewModel.returnPhoneNo()),
        modifier = Modifier.padding(8.dp),
        color = Color.White)
}
