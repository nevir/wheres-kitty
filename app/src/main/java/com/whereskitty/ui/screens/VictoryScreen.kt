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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.whereskitty.game.LevelRepository
import com.whereskitty.scenes.CatPose
import com.whereskitty.scenes.drawCat
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun VictoryScreen(
    levelId: Int,
    stars: Int,
    onNextLevel: (Int) -> Unit,
    onLevelSelect: () -> Unit
) {
    val level = remember(levelId) { LevelRepository.getLevel(levelId) }
    val hasNextLevel = LevelRepository.levels.any { it.id == levelId + 1 }

    val infiniteTransition = rememberInfiniteTransition(label = "victory")
    val confettiAngle by infiniteTransition.animateFloat(
        initialValue = 0f, targetValue = 360f,
        animationSpec = infiniteRepeatable(tween(8000, easing = LinearEasing)),
        label = "confetti"
    )
    val starScale by infiniteTransition.animateFloat(
        initialValue = 0.9f, targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            tween(600, easing = EaseInOut), RepeatMode.Reverse
        ), label = "star_scale"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFF5C3D1E), Color(0xFF8B5E3C), Color(0xFF5C3D1E))
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        // Confetti canvas
        Canvas(modifier = Modifier.fillMaxSize()) {
            val confettiColors = listOf(
                Color(0xFFFFD700), Color(0xFFFF85A1), Color(0xFF87CEEB),
                Color(0xFF7CB87A), Color(0xFFE07B39), Color(0xFFB39DDB)
            )
            for (i in 0..40) {
                val angle = (confettiAngle + i * 9f) * (Math.PI / 180f).toFloat()
                val r = (i % 4 + 1) * size.width * 0.12f
                val cx = size.width / 2 + cos(angle) * r
                val cy = size.height / 2 + sin(angle) * r
                val confettiColor = confettiColors[i % confettiColors.size]
                val sz = 8f + (i % 4) * 5f

                // Different confetti shapes
                when (i % 3) {
                    0 -> drawCircle(confettiColor.copy(alpha = 0.7f), sz / 2, Offset(cx, cy))
                    1 -> drawRect(confettiColor.copy(alpha = 0.7f),
                        topLeft = Offset(cx - sz / 2, cy - sz / 4),
                        size = androidx.compose.ui.geometry.Size(sz, sz / 2))
                    else -> {
                        val triPath = Path().apply {
                            moveTo(cx, cy - sz / 2)
                            lineTo(cx + sz / 2, cy + sz / 2)
                            lineTo(cx - sz / 2, cy + sz / 2)
                            close()
                        }
                        drawPath(triPath, confettiColor.copy(alpha = 0.7f))
                    }
                }
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(32.dp)
        ) {
            Text(
                text = "You found ${level.target.name}!",
                style = MaterialTheme.typography.displayMedium,
                color = Color(0xFFFFF0D8),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )

            // Cat preview
            Canvas(
                modifier = Modifier
                    .size(120.dp)
                    .background(Color(0xFFFFF0D8).copy(alpha = 0.15f), RoundedCornerShape(50))
            ) {
                drawCat(
                    cx = size.width / 2,
                    cy = size.height * 0.65f,
                    size = size.height * 0.55f,
                    body = level.target.previewColor,
                    stripe = level.target.previewStripeColor,
                    facing = 1,
                    pose = CatPose.SITTING,
                    bowTieColor = if (level.target.hasBowTie) Color(0xFFFF85A1) else null,
                    collarColor = if (level.target.hasCollar) Color(0xFF4488FF) else null,
                    hatColor = if (level.target.hasHat) Color(0xFF1A3A6A) else null
                )
            }

            // Stars
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.scale(starScale)
            ) {
                repeat(3) { i ->
                    Text(
                        text = if (i < stars) "★" else "☆",
                        fontSize = 48.sp,
                        color = if (i < stars) Color(0xFFFFD700) else Color.White.copy(alpha = 0.3f)
                    )
                }
            }

            Text(
                text = when (stars) {
                    3 -> "Amazing! First try!"
                    2 -> "Well done!"
                    else -> "You got there!"
                },
                style = MaterialTheme.typography.titleLarge,
                color = Color(0xFFFFF0D8)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Buttons
            if (hasNextLevel) {
                Button(
                    onClick = { onNextLevel(levelId + 1) },
                    modifier = Modifier.fillMaxWidth(0.75f).height(52.dp),
                    shape = RoundedCornerShape(26.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFE07B39),
                        contentColor = Color.White
                    )
                ) {
                    Text("Next Level", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                }
            }

            OutlinedButton(
                onClick = onLevelSelect,
                modifier = Modifier.fillMaxWidth(0.75f).height(52.dp),
                shape = RoundedCornerShape(26.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFFFFF0D8))
            ) {
                Text("Level Select", style = MaterialTheme.typography.titleLarge)
            }
        }
    }
}

