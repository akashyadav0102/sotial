package com.messenger.sotial.home.util

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition

@Composable
fun loadPicture(
    url : String,
    @DrawableRes defaultImage : Int
) : MutableState<Bitmap?>{

    val bitMapState : MutableState<Bitmap?> = remember { mutableStateOf(value = null) }

    //Nullpointer error even when url is default
//    val img = if(url == "default")defaultImage else url

    val img = defaultImage

    Glide.with(LocalContext.current)
        .asBitmap()
        .load(img)
        .into(object : CustomTarget<Bitmap>(){
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                bitMapState.value = resource
            }

            override fun onLoadCleared(placeholder: Drawable?) {
            }

        })
    return bitMapState
}
