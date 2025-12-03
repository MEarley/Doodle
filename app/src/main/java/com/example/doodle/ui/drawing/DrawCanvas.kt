package com.example.doodle.ui.drawing

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.sp
import com.example.doodle.data.Line
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

private val BUTTON_WIDTH = 20.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawCanvas() {
    val strokeHistory = remember { mutableStateListOf<Line>() }
    var currentColor by remember { mutableStateOf(Color.Black) }
    var sliderPosition by remember { mutableFloatStateOf(0f) }

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = {
//                        Text(
//                            "Select",
//                            color = Color.White,
//                            fontSize = 12.sp
//                        )
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    // Buttons for Colors and Undo/Clear Options
                    actions = {
                        // Select Black
                        Button(
                            colors = ButtonDefaults.buttonColors(Color.Black),
                            onClick = { currentColor = Color.Black },
                            modifier = Modifier.padding(3.dp).width(BUTTON_WIDTH),
                            border = BorderStroke(1.dp, Color.White)
                        ) {
                        }
                        // Select Red
                        Button(
                            colors = ButtonDefaults.buttonColors(Color.Red),
                            onClick = { currentColor = Color.Red },
                            modifier = Modifier.padding(3.dp).width(BUTTON_WIDTH),
                            border = BorderStroke(1.dp, Color.Black)
                        ) {
                        }
                        // Select Green
                        Button(
                            colors = ButtonDefaults.buttonColors(Color.Green),
                            onClick = { currentColor = Color.Green },
                            modifier = Modifier.padding(3.dp).width(BUTTON_WIDTH),
                            border = BorderStroke(1.dp, Color.Black)
                        ) {
                        }
                        // Select Blue
                        Button(
                            colors = ButtonDefaults.buttonColors(Color.Blue),
                            onClick = { currentColor = Color.Blue },
                            modifier = Modifier.padding(3.dp).width(BUTTON_WIDTH),
                            border = BorderStroke(1.dp, Color.Black)
                        ) {
                        }
                        // Select Cyan
                        Button(
                            colors = ButtonDefaults.buttonColors(Color.Cyan),
                            onClick = { currentColor = Color.Cyan },
                            modifier = Modifier.padding(3.dp).width(BUTTON_WIDTH),
                            border = BorderStroke(1.dp, Color.Black)
                        ) {
                        }
                        // Select Gray
                        Button(
                            colors = ButtonDefaults.buttonColors(Color.Gray),
                            onClick = { currentColor = Color.Gray },
                            modifier = Modifier.padding(3.dp).width(BUTTON_WIDTH),
                            border = BorderStroke(1.dp, Color.Black)
                        ) {
                        }
                        // Select White (Eraser)
                        Button(
                            colors = ButtonDefaults.buttonColors(Color.White),
                            onClick = { currentColor = Color.White },
                            modifier = Modifier.padding(3.dp).width(BUTTON_WIDTH),
                            border = BorderStroke(1.dp, Color.Black)
                        ) {
                        }
                        // Undo Button
                        Button(
                            colors = ButtonDefaults.buttonColors(Color.DarkGray),
                            onClick = {
                                if (strokeHistory.isNotEmpty()) {
                                    strokeHistory.removeAt(strokeHistory.lastIndex)
                                }
                            },
                            modifier = Modifier.padding(3.dp)
                        ) {
                            Text(
                                text = "Undo",
                                fontWeight = FontWeight.Bold
                            )
                        }
                        // Clear Button
                        Button(
                            colors = ButtonDefaults.buttonColors(Color.Black),
                            onClick = { strokeHistory.clear() },
                            modifier = Modifier.padding(3.dp)
                        ) {
                            Text(
                                text = "Clear",
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                )
                // Row for slider
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Brush Size: ${sliderPosition.toInt()}",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 8.dp,end = 8.dp)
                    )
                    // Slider for Stroke Width
                    Slider(
                        value = sliderPosition,
                        onValueChange = { sliderPosition = it },
                        valueRange = 1.0f..50.0f,
                        colors = SliderDefaults.colors(
                            thumbColor = Color.DarkGray,
                            activeTrackColor = currentColor,
                            inactiveTrackColor = Color.LightGray
                        ),
                        modifier = Modifier.padding(end = 8.dp)
                    )
                }
            }
        }
    ) { innerPadding ->
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .pointerInput(true) {
                    detectDragGestures { change, dragAmount ->
                        change.consume()

                        // Create a line utilizing cursor drag
                        val stroke = Line(
                            start = change.position - dragAmount,
                            end = change.position,
                            color = currentColor,
                            strokeWidth = sliderPosition

                        )

                        strokeHistory.add(stroke)
                    }
                }
        ) {
            // Draw lines from the stroke history
            strokeHistory.forEach { stroke ->
                drawLine(
                    start = stroke.start,
                    end = stroke.end,
                    color = stroke.color,
                    strokeWidth = stroke.strokeWidth,
                    cap = StrokeCap.Round
                )
            }
        }
    }
}
