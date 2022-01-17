package com.messenger.sotial.home.chatFragment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.messenger.sotial.firestoreservice.data.ChatMessages

@Composable
fun LeftChat(message : ChatMessages){
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start
    ) {
        Row(
            modifier = Modifier
                .height(IntrinsicSize.Max)
        ) {

            Column(
                modifier = Modifier.background(
                    MaterialTheme.colors.secondaryVariant,
                    TriangleShapeLeft()
                ).width(8.dp).fillMaxHeight()
            ){}

            Column(
                modifier = Modifier
                    .background(
                        MaterialTheme.colors.primaryVariant,
                        RoundedCornerShape(5.dp, 5.dp, 5.dp, 0.dp)
                    ).widthIn(10.dp,250.dp)
                    .padding(8.dp)
            ) {
                Text(text = message.message)
            }
        }
    }
}