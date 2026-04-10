package com.whereskitty.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.whereskitty.scenes.*
import com.whereskitty.ui.theme.*

@Composable
fun HomeScreen(onPlayClicked: () -> Unit) {
    val infiniteTransition = rememberInfiniteTransition(label = "home_anim")
    val tailSwing by infiniteTransition.animateFloat(
        initialValue = -15f, targetValue = 15f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = EaseInOut),
            repeatMode = RepeatMode.Reverse
        ), label = "tail"
    )
    val eyeBlink by infiniteTransition.animateFloat(
        initialValue = 1f, targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 4000
                1f at 0
                1f at 3700
                0.05f at 3850
                1f at 4000
            },
            repeatMode = RepeatMode.Restart
        ), label = "blink"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFF87CEEB), Color(0xFF6BAF4A), Color(0xFF4A8530))
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier.padding(32.dp)
        ) {
            // Title
            Text(
                text = "Where's\nKitty?",
                style = MaterialTheme.typography.displayLarge,
                color = Color.White,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                lineHeight = 52.sp,
                modifier = Modifier
                    .background(
                        Color(0xFF5C3D1E).copy(alpha = 0.55f),
                        RoundedCornerShape(20.dp)
                    )
                    .padding(horizontal = 28.dp, vertical = 18.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Animated cat illustration
            Canvas(
                modifier = Modifier
                    .size(180.dp)
                    .background(Color(0xFFFFF0D8).copy(alpha = 0.5f), RoundedCornerShape(50))
            ) {
                val cx = size.width / 2
                val cy = size.height * 0.6f
                val s = size.height * 0.55f

                // Ground
                drawCircle(Color(0xFF6BAF4A).copy(alpha = 0.4f), size.width / 2, Offset(cx, cy + s * 0.05f))

                // Draw the hero cat
                drawCat(
                    cx = cx, cy = cy,
                    size = s,
                    body = Color(0xFFE07B39),
                    stripe = Color(0xFFBB5500),
                    eye = Color(0xFF44BB44),
                    facing = 1,
                    pose = CatPose.SITTING,
                    bowTieColor = Color(0xFFFF85A1)
                )

                // Animate eyes slightly with blink
                if (eyeBlink < 0.5f) {
                    drawLine(
                        Color(0xFF33BB44),
                        Offset(cx - s * 0.108f, cy - s * 0.14f),
                        Offset(cx - s * 0.108f + s * 0.036f, cy - s * 0.14f),
                        strokeWidth = s * 0.06f
                    )
                    drawLine(
                        Color(0xFF33BB44),
                        Offset(cx + s * 0.072f, cy - s * 0.14f),
                        Offset(cx + s * 0.072f + s * 0.036f, cy - s * 0.14f),
                        strokeWidth = s * 0.06f
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Play button
            Button(
                onClick = onPlayClicked,
                modifier = Modifier
                    .fillMaxWidth(0.72f)
                    .height(58.dp),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF5C3D1E),
                    contentColor = Color(0xFFFFF0D8)
                ),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
            ) {
                Text(
                    text = "Play",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            Text(
                text = "Find the hidden cat in each scene!",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White.copy(alpha = 0.9f),
                textAlign = TextAlign.Center
            )
        }
    }
}
