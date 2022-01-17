package com.messenger.sotial.home.searchscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.messenger.sotial.firestoreservice.data.ChatSearchUser
import com.messenger.sotial.home.R
import com.messenger.sotial.home.util.loadPicture

//@Preview
@Composable
fun SearchListItem(item : ChatSearchUser){

//  val context = LocalContext.current

    Row(modifier = Modifier
        .fillMaxWidth()
        .size(64.dp)
        .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        
        val image = loadPicture(
            url = item.photoURL,
            defaultImage = R.drawable.ic_person_default
        ).value

        image?.let {img->
            Image(
                bitmap = img.asImageBitmap(),
                contentDescription = "",
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .border(2.dp, MaterialTheme.colors.primaryVariant, CircleShape)
            )
        }

        Box(
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = item.userName,
                fontFamily = MaterialTheme.typography.h1.fontFamily,
                fontSize = 15.sp,
                color = MaterialTheme.colors.onPrimary
            )
        }
    }
}