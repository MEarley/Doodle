package com.example.doodle.ui.drawing

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import com.example.doodle.data.Line
import androidx.compose.ui.graphics.StrokeCap



@Composable
fun DrawCanvas(){
    val strokeHistory = remember { mutableStateListOf<Line>() }
    var currentColor by remember { mutableStateOf(Color.Black) }
    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(true) {
                detectDragGestures { change, dragAmount ->
                    change.consume()

                    val stroke = Line(
                        start = change.position - dragAmount,
                        end = change.position,
                        color = currentColor
                    )

                    strokeHistory.add(stroke)
                }
            }
    ) {

        strokeHistory.forEach { stroke ->
            drawLine(
                start = stroke.start,
                end = stroke.end,
                color = stroke.color,
                strokeWidth = 2.0f,
                cap = StrokeCap.Round
            )
        }

        val canvasQuadrantSize = size / 3F
        drawRect(
            color = Color.Magenta,
            size = canvasQuadrantSize
        )
    }
}