package com.whereskitty.scenes

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke

// Scene: 2000 x 1400 units
// Target: Marmalade (orange tabby, pink bow tie) at (1155, 665)

fun DrawScope.drawGardenPartyScene(showFoundHighlight: Boolean) {

    // ── Sky ───────────────────────────────────────────────────────────────────
    drawRect(
        brush = Brush.verticalGradient(
            colorStops = arrayOf(
                0.0f to Color(0xFF6BB8E8),
                0.4f to Color(0xFF9FD0EE),
                0.65f to Color(0xFFD6EEF8)
            ),
            startY = 0f, endY = 900f
        ),
        size = Size(2000f, 900f)
    )

    // ── Sun ──────────────────────────────────────────────────────────────────
    drawCircle(Color(0xFFFFF176).copy(alpha = 0.95f), 68f, Offset(1780f, 110f))
    drawCircle(Color(0xFFFFF9C4).copy(alpha = 0.4f), 90f, Offset(1780f, 110f))

    // ── Clouds ────────────────────────────────────────────────────────────────
    drawCloud(260f, 80f, 110f, alpha = 0.9f)
    drawCloud(680f, 45f, 140f, alpha = 0.85f)
    drawCloud(1120f, 90f, 95f, alpha = 0.88f)
    drawCloud(1480f, 55f, 125f, alpha = 0.82f)

    // ── Far hills ─────────────────────────────────────────────────────────────
    val hill1 = Path().apply {
        moveTo(0f, 680f)
        cubicTo(300f, 560f, 600f, 620f, 900f, 580f)
        cubicTo(1100f, 550f, 1300f, 590f, 1600f, 560f)
        cubicTo(1800f, 545f, 1950f, 575f, 2000f, 565f)
        lineTo(2000f, 900f); lineTo(0f, 900f); close()
    }
    drawPath(hill1, Color(0xFF8DC86E).copy(alpha = 0.6f))

    val hill2 = Path().apply {
        moveTo(0f, 720f)
        cubicTo(250f, 650f, 500f, 690f, 750f, 660f)
        cubicTo(1000f, 635f, 1250f, 670f, 1500f, 650f)
        cubicTo(1700f, 635f, 1900f, 660f, 2000f, 650f)
        lineTo(2000f, 900f); lineTo(0f, 900f); close()
    }
    drawPath(hill2, Color(0xFF7CBF5C).copy(alpha = 0.7f))

    // ── Main ground ───────────────────────────────────────────────────────────
    drawRect(
        brush = Brush.verticalGradient(
            colorStops = arrayOf(
                0.0f to Color(0xFF6BAF4A),
                0.5f to Color(0xFF5A9C3D),
                1.0f to Color(0xFF4A8530)
            ),
            startY = 750f, endY = 1400f
        ),
        topLeft = Offset(0f, 750f), size = Size(2000f, 650f)
    )

    // Grass texture streaks
    for (i in 0..30) {
        val gx = i * 68f + 20f
        drawLine(Color(0xFF7DC455).copy(alpha = 0.4f),
            Offset(gx, 780f + (i % 5) * 15f),
            Offset(gx + 15f, 820f + (i % 4) * 12f),
            strokeWidth = 4f, cap = StrokeCap.Round)
    }

    // ── Trees ─────────────────────────────────────────────────────────────────
    drawTree(120f, 840f, 28f, 310f, Color(0xFF5BA045), Color(0xFF6B4A28))
    drawTree(280f, 820f, 22f, 250f, Color(0xFF6DB84E), Color(0xFF7A5535))
    drawTree(1820f, 830f, 35f, 360f, Color(0xFF4E9040), Color(0xFF5C3F22))
    drawTree(1950f, 850f, 20f, 220f, Color(0xFF65A84A), Color(0xFF7A5535))

    // ── White picket fence ────────────────────────────────────────────────────
    val fenceY = 820f
    for (i in 0..22) {
        val fx = i * 90f + 30f
        // Post
        drawRect(Color(0xFFF8F4EE),
            topLeft = Offset(fx, fenceY - 95f),
            size = Size(14f, 105f))
        // Pointed top
        val postTopPath = Path().apply {
            moveTo(fx, fenceY - 95f)
            lineTo(fx + 7f, fenceY - 118f)
            lineTo(fx + 14f, fenceY - 95f)
            close()
        }
        drawPath(postTopPath, Color(0xFFF8F4EE))
    }
    // Horizontal rails
    drawLine(Color(0xFFF0ECE5), Offset(0f, fenceY - 70f), Offset(2000f, fenceY - 70f), strokeWidth = 11f)
    drawLine(Color(0xFFF0ECE5), Offset(0f, fenceY - 30f), Offset(2000f, fenceY - 30f), strokeWidth = 11f)

    // ── Flower beds ───────────────────────────────────────────────────────────
    drawFlowerBed(180f, 870f, 220f)
    drawFlowerBed(760f, 880f, 190f)
    drawFlowerBed(1380f, 865f, 230f)
    drawFlowerBed(1850f, 875f, 160f)

    // ── Garden path ───────────────────────────────────────────────────────────
    val pathW = 130f
    drawRect(
        brush = Brush.verticalGradient(
            colorStops = arrayOf(0f to Color(0xFFD4B896), 1f to Color(0xFFB89870)),
            startY = 840f, endY = 1400f
        ),
        topLeft = Offset(1060f, 840f), size = Size(pathW, 560f)
    )

    // ── Garden table (center-right) ────────────────────────────────────────────
    // Tablecloth
    drawOval(Color(0xFFFFF8F0),
        topLeft = Offset(1000f, 695f), size = Size(320f, 80f))
    // Checkered pattern
    for (i in 0..3) {
        for (j in 0..1) {
            val tileX = 1010f + i * 70f
            val tileY = 698f + j * 30f
            if ((i + j) % 2 == 0)
                drawRect(Color(0xFFE8D4B8).copy(alpha = 0.6f),
                    topLeft = Offset(tileX, tileY), size = Size(70f, 30f))
        }
    }
    // Table legs
    drawLine(Color(0xFF8B6340), Offset(1030f, 745f), Offset(1010f, 870f), strokeWidth = 12f)
    drawLine(Color(0xFF8B6340), Offset(1290f, 745f), Offset(1310f, 870f), strokeWidth = 12f)

    // ── Tea set ───────────────────────────────────────────────────────────────
    // Teapot
    drawCircle(Color(0xFFE8C87A), 28f, Offset(1080f, 688f))
    drawLine(Color(0xFFD4A850), Offset(1080f, 660f), Offset(1080f, 670f), strokeWidth = 8f)
    // Spout
    val spoutPath = Path().apply {
        moveTo(1107f, 688f)
        cubicTo(1120f, 680f, 1130f, 682f, 1135f, 690f)
    }
    drawPath(spoutPath, Color(0xFFD4A850), style = Stroke(width = 7f))
    // Cups
    drawRoundedCup(1185f, 700f, 18f, Color(0xFFF0E8D8))
    drawRoundedCup(1240f, 698f, 18f, Color(0xFFF0E8D8))
    // Small cakes/treats
    drawCircle(Color(0xFFFFB3BA), 12f, Offset(1270f, 700f))
    drawCircle(Color(0xFFFFD700).copy(alpha = 0.8f), 10f, Offset(1130f, 702f))

    // ── Picnic blanket (bottom-left) ──────────────────────────────────────────
    val blanketPath = Path().apply {
        moveTo(80f, 1050f); lineTo(380f, 1020f)
        lineTo(400f, 1120f); lineTo(60f, 1150f); close()
    }
    drawPath(blanketPath, Color(0xFFE8A08A))
    // Blanket pattern
    for (i in 0..3) {
        drawLine(Color(0xFFC07060).copy(alpha = 0.5f),
            Offset(80f + i * 80f, 1050f), Offset(60f + i * 85f, 1150f), strokeWidth = 6f)
    }

    // ── Background cats (decoys, drawn before target) ─────────────────────────
    // Far background (small, less detail)
    drawCat(1900f, 775f, 32f, Color(0xFF888888), facing = -1, pose = CatPose.LOAF, alpha = 0.85f)
    drawCat(130f, 795f, 30f, Color(0xFF554433), stripe = Color(0xFF3A2818), facing = 1, pose = CatPose.SITTING, alpha = 0.8f)

    // Mid-ground cats
    drawCat(350f, 775f, 42f, Color(0xFF222222), facing = 1, pose = CatPose.SITTING, eye = Color(0xFFDDAA00))
    drawCat(520f, 780f, 38f, Color(0xFFCCA877), stripe = Color(0xFFAA8855), facing = -1, pose = CatPose.LOAF)
    drawCat(680f, 770f, 45f, Color(0xFFF5F5F0), facing = 1, pose = CatPose.WALKING, eye = Color(0xFF4488FF))
    drawCat(870f, 785f, 40f, Color(0xFF8B6B4A), stripe = Color(0xFF5C3F22), facing = -1, pose = CatPose.SITTING)

    // Near the table (potential decoys)
    drawCat(950f, 800f, 35f, Color(0xFF555555), facing = 1, pose = CatPose.SLEEPING)  // Gray sleeper under table area
    drawCat(1370f, 780f, 48f, Color(0xFFCC8844), stripe = Color(0xFFAA6622), facing = -1, pose = CatPose.SITTING)  // Orange-ish, no bow tie
    drawCat(1530f, 770f, 38f, Color(0xFF1A1A1A), facing = 1, pose = CatPose.LOAF)  // Tuxedo loaf (white chest)
    drawCat(1690f, 775f, 44f, Color(0xFFBBBBBB), facing = -1, pose = CatPose.WALKING)

    // On picnic blanket
    drawCat(230f, 1045f, 52f, Color(0xFF9B7050), stripe = Color(0xFF6B4A28), facing = 1, pose = CatPose.LOAF)

    // ── TARGET CAT: Marmalade on table ────────────────────────────────────────
    drawCat(
        1155f, 690f, 62f,
        body = Color(0xFFE07B39),
        stripe = Color(0xFFBB5500),
        eye = Color(0xFF44BB44),
        facing = -1,
        pose = CatPose.SITTING,
        bowTieColor = Color(0xFFFF85A1)
    )

    // Found highlight (drawn last so it's on top)
    if (showFoundHighlight) {
        drawCircle(Color(0xFFFFD700).copy(alpha = 0.35f), 90f, Offset(1155f, 660f))
        drawCircle(Color(0xFFFFD700).copy(alpha = 0.7f), 90f, Offset(1155f, 660f),
            style = Stroke(width = 5f))
    }

    // ── Butterflies ───────────────────────────────────────────────────────────
    drawButterfly(430f, 560f, 18f, Color(0xFFFFD700))
    drawButterfly(1230f, 480f, 14f, Color(0xFFFF8C94))
    drawButterfly(1650f, 510f, 16f, Color(0xFF9B59B6))
    drawButterfly(880f, 530f, 12f, Color(0xFF5DADE2))

    // ── Birds ─────────────────────────────────────────────────────────────────
    drawBird(700f, 210f, 13f)
    drawBird(1350f, 165f, 11f)
    drawBird(1560f, 240f, 9f)
}

private fun DrawScope.drawFlowerBed(cx: Float, cy: Float, width: Float) {
    val flowerColors = listOf(
        Color(0xFFFF6B9D), Color(0xFFFFD700), Color(0xFFFF8C42),
        Color(0xFF9B59B6), Color(0xFF3498DB), Color(0xFFE74C3C)
    )
    // Green base
    drawOval(Color(0xFF5A9C3D).copy(alpha = 0.7f),
        topLeft = Offset(cx - width / 2, cy - 30f),
        size = Size(width, 45f))

    val count = (width / 32f).toInt().coerceAtLeast(4)
    for (i in 0 until count) {
        val fx = cx - width / 2 + 18f + i * (width - 36f) / (count - 1)
        val fy = cy - 10f + (i % 3) * 8f - 8f
        drawFlower(fx, fy, 11f, flowerColors[i % flowerColors.size])
    }
}

private fun DrawScope.drawRoundedCup(cx: Float, cy: Float, size: Float, color: Color) {
    drawOval(color, topLeft = Offset(cx - size, cy - size * 0.6f), size = Size(size * 2f, size * 1.2f))
    drawLine(color, Offset(cx + size * 0.8f, cy - size * 0.2f), Offset(cx + size * 1.4f, cy + size * 0.2f),
        strokeWidth = size * 0.3f, cap = StrokeCap.Round)
}
