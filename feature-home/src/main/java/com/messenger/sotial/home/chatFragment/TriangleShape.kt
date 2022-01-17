package com.messenger.sotial.home.chatFragment

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection

class TriangleShape() : Shape{
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val trianglePath = Path().apply {
            moveTo(x = 0f, y = size.height-10)
            lineTo(x = 0f, y = size.height)
            lineTo(x = 0f + 15, y = size.height)
            close()
        }

        return Outline.Generic(trianglePath)
    }
}