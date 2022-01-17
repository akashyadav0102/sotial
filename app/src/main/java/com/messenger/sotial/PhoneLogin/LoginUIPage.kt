package com.messenger.sotial.PhoneLogin

import android.view.LayoutInflater
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.hbb20.CountryCodePicker
import com.messenger.sotial.R
import com.messenger.sotial.ui.theme.mattRed

@Composable
fun LoginScreen(navController: NavController,viewModel: LoginSharedViewModel){

    var text by rememberSaveable { mutableStateOf("") }
    var countryCode by rememberSaveable { mutableStateOf("") }
    val context = LocalContext.current

    Box(modifier = Modifier
        .background(mattRed)
        .fillMaxSize()){

        Column(modifier = Modifier
            .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally)
        {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {

                AndroidView(factory = {
                    val view = LayoutInflater.from(it)
                        .inflate(R.layout.countrycodepicker,null,false)
                    view

                }, update = {
                    var ccp : CountryCodePicker = it.findViewById(R.id.ccPicker)
                    countryCode = ccp.selectedCountryCodeWithPlus

                    ccp.setOnCountryChangeListener {
                        countryCode = ccp.selectedCountryCodeWithPlus
                    }

                })

                OutlinedTextField(value = text,
                    onValueChange = {text = it},
                    placeholder = { Text(text = "Enter Phone Number")},
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done),
                    modifier = Modifier
                        .padding(16.dp, 0.dp)
                        .fillMaxWidth(),
                    textStyle = TextStyle(Color.White),
                    singleLine = true,
                    maxLines = 1,
                    shape = RoundedCornerShape(10.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = Color.Yellow,
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.Transparent,
                        disabledBorderColor = Color.Transparent
                    )
                )
            }

            Button(onClick = {
                                if(text.isNotEmpty()){
                                    viewModel.setPhoneNo(text)
                                    viewModel.setCountryCode(countryCode)
                                    navController.navigate(Screen.SmsVerificationUI.route)

                                }else{
                                    Toast
                                        .makeText(context,"Enter a valid Phone Number",Toast.LENGTH_SHORT)
                                        .show()
                                }

                             },
                border = BorderStroke(1.dp,Color.DarkGray),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .size(40.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black)
            ) {
                Text(text = "SEND OTP",color = Color.White)
            }

        }

    }
}
