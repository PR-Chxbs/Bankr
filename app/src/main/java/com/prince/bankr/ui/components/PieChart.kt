package com.prince.bankr.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.drawArc
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.*
import androidx.compose.ui.unit.*
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun PieChart(
    data: List<Pair<String, Int>>,
    modifier: Modifier = Modifier,
    colors: List<Color> = defaultChartColors(),
    labelTextSize: TextUnit = 12.sp,
) {
    val total = data.sumOf { it.second }.takeIf { it != 0 } ?: 1

    Canvas(modifier = modifier) {
        val size = size.minDimension
        val radius = size / 2
        val rect = Rect(Offset.Zero, size, size)
        var startAngle = -90f

        val density = LocalDensity.current

        data.forEachIndexed { index, (label, value) ->
            val sweepAngle = (value.toFloat() / total) * 360f
            val color = colors[index % colors.size]

            drawArc(
                color = color,
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                useCenter = true,
                size = this.size
            )

            // Draw labels
            val angleRad = Math.toRadians((startAngle + sweepAngle / 2).toDouble())
            val labelX = radius + cos(angleRad) * radius * 0.7
            val labelY = radius + sin(angleRad) * radius * 0.7

            drawIntoCanvas { canvas ->
                val paint = androidx.compose.ui.graphics.Paint().asFrameworkPaint().apply {
                    isAntiAlias = true
                    textSize = with(density) { labelTextSize.toPx() }
                    color = android.graphics.Color.BLACK
                }
                canvas.nativeCanvas.drawText(
                    label,
                    labelX.toFloat(),
                    labelY.toFloat(),
                    paint
                )
            }

            startAngle += sweepAngle
        }
    }
}

fun defaultChartColors(): List<Color> = listOf(
    Color(0xFF4CAF50), // Green
    Color(0xFFF44336), // Red
    Color(0xFF2196F3), // Blue
    Color(0xFFFFC107), // Amber
    Color(0xFF9C27B0), // Purple
    Color(0xFF009688), // Teal
)
