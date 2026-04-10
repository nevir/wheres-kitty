package com.whereskitty.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Canvas
import com.whereskitty.game.CatTarget
import com.whereskitty.scenes.CatPose
import com.whereskitty.scenes.drawCat

@Composable
fun CatHintPanel(
    target: CatTarget,
    tapCount: Int,
    elapsedSeconds: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                Color(0xFF5C3D1E).copy(alpha = 0.92f),
                RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
            )
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Cat portrait
        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
                .background(Color(0xFFFFF0D8))
                .border(2.dp, Color(0xFFE07B39), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Canvas(modifier = Modifier.size(56.dp)) {
                val cx = size.width / 2
                val cy = size.height * 0.62f
                drawCat(
                    cx = cx, cy = cy,
                    size = size.height * 0.58f,
                    body = target.previewColor,
                    stripe = target.previewStripeColor,
                    facing = 1,
                    pose = CatPose.SITTING,
                    bowTieColor = if (target.hasBowTie) Color(0xFFFF85A1) else null,
                    collarColor = if (target.hasCollar) Color(0xFF4488FF) else null,
                    hatColor = if (target.hasHat) Color(0xFF1A3A6A) else null
                )
            }
        }

        // Target info
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "Find: ${target.name}",
                style = MaterialTheme.typography.titleLarge,
                color = Color(0xFFFFF0D8)
            )
            Text(
                text = target.description,
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFFD4B896),
                maxLines = 2
            )
        }

        // Stats
        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = formatTime(elapsedSeconds),
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFFFFF0D8),
                fontSize = 13.sp
            )
            Text(
                text = "$tapCount taps",
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFFD4B896),
                fontSize = 11.sp
            )
        }
    }
}

private fun formatTime(seconds: Int): String {
    val m = seconds / 60
    val s = seconds % 60
    return "%d:%02d".format(m, s)
}
