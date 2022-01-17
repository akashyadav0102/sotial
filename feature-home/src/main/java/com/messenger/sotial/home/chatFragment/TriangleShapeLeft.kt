package com.messenger.sotial.home.chatFragment

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection

class TriangleShapeLeft() : Shape{
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val trianglePath = Path().apply {

            moveTo(x = size.width, y = size.height-10)
            lineTo(x = 0f+5, y = size.height)
            lineTo(x = size.width, y = size.height)
            close()
        }

        return Outline.Generic(trianglePath)
    }
}