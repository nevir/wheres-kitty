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
// Target: Captain (tuxedo, sailor hat) at (355, 390)

fun DrawScope.drawBusyHarborScene(showFoundHighlight: Boolean) {

    // ── Sky ───────────────────────────────────────────────────────────────────
    drawRect(
        brush = Brush.verticalGradient(
            colorStops = arrayOf(
                0f to Color(0xFF4A90D9),
                0.45f to Color(0xFF87CEEB),
                0.7f to Color(0xFFB0D8F0)
            ),
            startY = 0f, endY = 650f
        ),
        size = Size(2000f, 650f)
    )

    // ── Distant city ─────────────────────────────────────────────────────────
    for (i in 0..8) {
        val bx = i * 240f + 20f
        val bh = 80f + (i % 4) * 60f
        drawRect(Color(0xFF6B8CAE).copy(alpha = 0.5f),
            topLeft = Offset(bx, 480f - bh), size = Size(190f, bh))
        for (j in 0..2) {
            for (k in 0..3) {
                drawRect(Color(0xFFFFF9C4).copy(alpha = 0.4f),
                    topLeft = Offset(bx + 20f + j * 50f, 480f - bh + 10f + k * 18f),
                    size = Size(20f, 10f))
            }
        }
    }

    // ── Ocean / Harbor water ──────────────────────────────────────────────────
    drawRect(
        brush = Brush.verticalGradient(
            colorStops = arrayOf(
                0f to Color(0xFF2E6B9A),
                0.4f to Color(0xFF1E5580),
                1f to Color(0xFF154060)
            ),
            startY = 500f, endY = 800f
        ),
        topLeft = Offset(0f, 500f), size = Size(2000f, 300f)
    )
    // Wave lines
    for (i in 0..5) {
        val wy = 530f + i * 38f
        val wavePath = Path().apply {
            moveTo(0f, wy)
            for (w in 0..10) {
                cubicTo(w * 200f + 50f, wy - 8f, w * 200f + 150f, wy + 8f, w * 200f + 200f, wy)
            }
        }
        drawPath(wavePath, Color(0xFF5A9BC2).copy(alpha = 0.35f), style = Stroke(width = 3f))
    }

    // ── Wooden dock ───────────────────────────────────────────────────────────
    drawRect(
        brush = Brush.verticalGradient(
            colorStops = arrayOf(0f to Color(0xFF9C7248), 1f to Color(0xFF7A5530)),
            startY = 790f, endY = 1000f
        ),
        topLeft = Offset(0f, 790f), size = Size(2000f, 210f)
    )
    // Dock planks
    for (i in 0..16) {
        val px = i * 125f
        drawLine(Color(0xFF6B4A28).copy(alpha = 0.5f), Offset(px, 790f), Offset(px, 1000f), strokeWidth = 3f)
    }
    for (i in 0..4) {
        val py = 820f + i * 40f
        drawLine(Color(0xFF6B4A28).copy(alpha = 0.35f), Offset(0f, py), Offset(2000f, py), strokeWidth = 2f)
    }

    // ── Ground / Market area ──────────────────────────────────────────────────
    drawRect(
        brush = Brush.verticalGradient(
            colorStops = arrayOf(0f to Color(0xFFB8A080), 1f to Color(0xFF9A8060)),
            startY = 1000f, endY = 1400f
        ),
        topLeft = Offset(0f, 1000f), size = Size(2000f, 400f)
    )

    // ── FISHING BOAT 1 (left, where Captain lives) ────────────────────────────
    drawFishingBoat(250f, 640f, 1)

    // ── FISHING BOAT 2 (center-right) ─────────────────────────────────────────
    drawFishingBoat(1100f, 660f, -1)

    // ── FISHING BOAT 3 (far right, smaller) ───────────────────────────────────
    drawFishingBoatSmall(1700f, 670f)

    // ── Market stalls ─────────────────────────────────────────────────────────
    drawMarketStall(200f, 1000f, Color(0xFFE74C3C), Color(0xFFC0392B))
    drawMarketStall(550f, 1000f, Color(0xFF3498DB), Color(0xFF2980B9))
    drawMarketStall(900f, 1000f, Color(0xFF2ECC71), Color(0xFF27AE60))
    drawMarketStall(1250f, 1000f, Color(0xFFE67E22), Color(0xFFD35400))
    drawMarketStall(1600f, 1000f, Color(0xFF9B59B6), Color(0xFF8E44AD))

    // Fish on display
    for (i in 0..4) {
        drawFish(160f + i * 350f, 1010f, 40f + (i % 3) * 10f)
    }

    // ── Crates and barrels ────────────────────────────────────────────────────
    drawCrate(820f, 995f)
    drawCrate(840f, 960f)
    drawBarrel(960f, 995f)
    drawBarrel(1020f, 995f)
    drawCrate(1460f, 990f)
    drawBarrel(1540f, 990f)

    // ── Nets/ropes ────────────────────────────────────────────────────────────
    drawFishingNet(580f, 820f)
    drawFishingNet(1380f, 830f)

    // ── Seagulls ─────────────────────────────────────────────────────────────
    drawBird(500f, 180f, 16f, Color(0xFFEEEEEE))
    drawBird(1100f, 140f, 14f, Color(0xFFEEEEEE))
    drawBird(1550f, 200f, 12f, Color(0xFFDDDDDD))
    drawBird(750f, 290f, 11f, Color(0xFFEEEEEE))
    drawBird(1850f, 160f, 13f, Color(0xFFDDDDDD))
    // Seagulls on dock/stalls
    drawSeagullStanding(630f, 1000f, 18f)
    drawSeagullStanding(1350f, 990f, 16f)
    drawSeagullStanding(1780f, 1005f, 14f)

    // ── Dock cats (decoys) ────────────────────────────────────────────────────
    drawCat(420f, 875f, 48f, Color(0xFF888888), stripe = Color(0xFF555555), facing = 1, pose = CatPose.SITTING)
    drawCat(640f, 885f, 42f, Color(0xFFCC8844), stripe = Color(0xFFAA6633), facing = -1, pose = CatPose.WALKING)
    drawCat(780f, 870f, 50f, Color(0xFF222222), facing = 1, pose = CatPose.LOAF, eye = Color(0xFFFFAA00))
    drawCat(950f, 880f, 38f, Color(0xFFDDDDCC), facing = -1, pose = CatPose.SITTING)

    // Market cats (swarming the fish)
    drawCat(180f, 1010f, 45f, Color(0xFF554433), stripe = Color(0xFF332211), facing = 1, pose = CatPose.SITTING)
    drawCat(320f, 1005f, 40f, Color(0xFF777777), facing = -1, pose = CatPose.WALKING)
    drawCat(500f, 1015f, 52f, Color(0xFFBB8855), stripe = Color(0xFF886633), facing = 1, pose = CatPose.SITTING)
    drawCat(700f, 1000f, 38f, Color(0xFFEEEEDD), facing = -1, pose = CatPose.LOAF)
    drawCat(1100f, 1010f, 44f, Color(0xFF333333), facing = 1, pose = CatPose.WALKING, eye = Color(0xFFDDAA00))
    drawCat(1450f, 1005f, 46f, Color(0xFF997755), stripe = Color(0xFF664422), facing = -1, pose = CatPose.SITTING)
    drawCat(1700f, 1015f, 42f, Color(0xFF555555), facing = 1, pose = CatPose.LOAF)
    drawCat(1880f, 1000f, 36f, Color(0xFFCCAA88), facing = -1, pose = CatPose.SITTING)

    // On boat 2 deck
    drawCat(1080f, 590f, 38f, Color(0xFF222222), facing = 1, pose = CatPose.SITTING, eye = Color(0xFFEECC00))

    // ── TARGET: Captain on boat 1 deck ────────────────────────────────────────
    drawCat(
        355f, 400f, 56f,
        body = Color(0xFF1A1A1A),
        eye = Color(0xFFEECC44),
        facing = 1,
        pose = CatPose.SITTING,
        hatColor = Color(0xFF1A3A6A)
    )
    // White chest marking for tuxedo
    drawOval(Color(0xFFF5F5F0).copy(alpha = 0.9f),
        topLeft = Offset(344f, 380f), size = Size(22f, 30f))

    if (showFoundHighlight) {
        drawCircle(Color(0xFFFFD700).copy(alpha = 0.35f), 80f, Offset(355f, 390f))
        drawCircle(Color(0xFFFFD700).copy(alpha = 0.7f), 80f, Offset(355f, 390f),
            style = Stroke(width = 5f))
    }

    // ── Lanterns ──────────────────────────────────────────────────────────────
    drawLantern(1240f, 830f)
    drawLantern(730f, 820f)
}

private fun DrawScope.drawFishingBoat(cx: Float, waterlineY: Float, facing: Int) {
    val bw = 450f; val bh = 200f

    // Hull
    val hull = Path().apply {
        moveTo(cx - bw / 2 + 40f, waterlineY)
        lineTo(cx - bw / 2, waterlineY - bh * 0.4f)
        lineTo(cx - bw / 2, waterlineY - bh * 0.8f)
        lineTo(cx + bw / 2 - 30f, waterlineY - bh * 0.8f)
        cubicTo(cx + bw / 2 + 30f, waterlineY - bh * 0.8f, cx + bw / 2 + 40f, waterlineY - bh * 0.3f, cx + bw / 2 + 30f, waterlineY)
        close()
    }
    drawPath(hull, Color(0xFF5C3D1E))
    drawPath(hull, Color(0xFF4A2C10), style = Stroke(width = 5f))

    // Deck
    drawRect(Color(0xFF8B6340),
        topLeft = Offset(cx - bw / 2, waterlineY - bh * 0.8f),
        size = Size(bw - 30f, 12f))

    // Cabin
    drawRect(Color(0xFFD4A96A),
        topLeft = Offset(cx - 80f, waterlineY - bh * 1.5f),
        size = Size(180f, bh * 0.7f))
    drawRect(Color(0xFFC09860),
        topLeft = Offset(cx - 80f, waterlineY - bh * 1.5f),
        size = Size(180f, 15f))
    // Windows
    drawRect(Color(0xFF87CEEB).copy(alpha = 0.8f),
        topLeft = Offset(cx - 60f, waterlineY - bh * 1.35f),
        size = Size(45f, 35f))
    drawRect(Color(0xFF87CEEB).copy(alpha = 0.8f),
        topLeft = Offset(cx + 15f, waterlineY - bh * 1.35f),
        size = Size(45f, 35f))

    // Mast
    val mastX = cx - 50f * facing
    drawLine(Color(0xFF4A2C10),
        Offset(mastX, waterlineY - bh * 0.8f),
        Offset(mastX, waterlineY - bh * 2.8f),
        strokeWidth = 9f)
    // Boom
    drawLine(Color(0xFF6B4226),
        Offset(mastX, waterlineY - bh * 1.8f),
        Offset(mastX + facing * 200f, waterlineY - bh * 1.5f),
        strokeWidth = 6f)
    // Flag
    val flagPath = Path().apply {
        moveTo(mastX, waterlineY - bh * 2.8f)
        lineTo(mastX + facing * 60f, waterlineY - bh * 2.65f)
        lineTo(mastX, waterlineY - bh * 2.5f)
        close()
    }
    drawPath(flagPath, Color(0xFFCC2222))
}

private fun DrawScope.drawFishingBoatSmall(cx: Float, waterlineY: Float) {
    val bw = 280f; val bh = 130f
    val hull = Path().apply {
        moveTo(cx - bw / 2 + 25f, waterlineY)
        lineTo(cx - bw / 2, waterlineY - bh * 0.35f)
        lineTo(cx - bw / 2, waterlineY - bh * 0.75f)
        lineTo(cx + bw / 2 - 20f, waterlineY - bh * 0.75f)
        cubicTo(cx + bw / 2 + 20f, waterlineY - bh * 0.75f, cx + bw / 2 + 25f, waterlineY - bh * 0.25f, cx + bw / 2 + 20f, waterlineY)
        close()
    }
    drawPath(hull, Color(0xFF6B4A28))
    drawRect(Color(0xFF9C7248), topLeft = Offset(cx - bw / 2, waterlineY - bh * 0.75f), size = Size(bw - 20f, 10f))
    drawLine(Color(0xFF4A2C10), Offset(cx, waterlineY - bh * 0.75f), Offset(cx, waterlineY - bh * 2.2f), strokeWidth = 7f)
}

private fun DrawScope.drawMarketStall(cx: Float, bottomY: Float, awningColor: Color, darkColor: Color) {
    val w = 280f
    // Frame
    drawLine(Color(0xFF5C3D1E), Offset(cx - w / 2, bottomY), Offset(cx - w / 2, bottomY - 200f), strokeWidth = 10f)
    drawLine(Color(0xFF5C3D1E), Offset(cx + w / 2, bottomY), Offset(cx + w / 2, bottomY - 200f), strokeWidth = 10f)
    // Awning
    val awningPath = Path().apply {
        moveTo(cx - w / 2 - 20f, bottomY - 180f)
        lineTo(cx + w / 2 + 20f, bottomY - 180f)
        lineTo(cx + w / 2, bottomY - 120f)
        lineTo(cx - w / 2, bottomY - 120f)
        close()
    }
    drawPath(awningPath, awningColor)
    // Awning stripes
    for (i in 0..5) {
        val sx = cx - w / 2 - 10f + i * (w + 30f) / 5f
        drawLine(darkColor.copy(alpha = 0.4f), Offset(sx, bottomY - 180f), Offset(sx - 6f, bottomY - 120f), strokeWidth = 12f)
    }
    // Counter
    drawRect(Color(0xFF9C7248),
        topLeft = Offset(cx - w / 2, bottomY - 120f),
        size = Size(w, 25f))
}

private fun DrawScope.drawFish(cx: Float, cy: Float, size: Float) {
    val fishPath = Path().apply {
        moveTo(cx + size, cy)
        cubicTo(cx + size * 0.3f, cy - size * 0.4f, cx - size * 0.5f, cy - size * 0.4f, cx - size, cy)
        cubicTo(cx - size * 0.5f, cy + size * 0.4f, cx + size * 0.3f, cy + size * 0.4f, cx + size, cy)
        close()
    }
    val tailPath = Path().apply {
        moveTo(cx - size, cy)
        lineTo(cx - size * 1.5f, cy - size * 0.4f)
        lineTo(cx - size * 1.5f, cy + size * 0.4f)
        close()
    }
    val fishColor = Color(0xFF4488CC)
    drawPath(fishPath, fishColor)
    drawPath(tailPath, fishColor)
    drawCircle(Color(0xFF222222), size * 0.1f, Offset(cx + size * 0.6f, cy - size * 0.1f))
    drawCircle(Color(0xFF88BBDD).copy(alpha = 0.6f), size * 0.3f, Offset(cx + size * 0.2f, cy - size * 0.15f))
}

private fun DrawScope.drawCrate(cx: Float, cy: Float) {
    drawRect(Color(0xFF9C7248),
        topLeft = Offset(cx, cy - 60f), size = Size(80f, 60f))
    drawLine(Color(0xFF6B4A28), Offset(cx, cy - 60f), Offset(cx + 80f, cy), strokeWidth = 3f)
    drawLine(Color(0xFF6B4A28), Offset(cx + 80f, cy - 60f), Offset(cx, cy), strokeWidth = 3f)
    drawLine(Color(0xFF6B4A28), Offset(cx + 40f, cy - 60f), Offset(cx + 40f, cy), strokeWidth = 3f)
    drawLine(Color(0xFF6B4A28), Offset(cx, cy - 30f), Offset(cx + 80f, cy - 30f), strokeWidth = 3f)
}

private fun DrawScope.drawBarrel(cx: Float, cy: Float) {
    drawOval(Color(0xFF7A5535),
        topLeft = Offset(cx, cy - 75f), size = Size(55f, 75f))
    for (i in 0..2) {
        val by = cy - 62f + i * 25f
        drawLine(Color(0xFF4A2C10), Offset(cx - 3f, by), Offset(cx + 58f, by), strokeWidth = 4f)
    }
    drawOval(Color(0xFF9C7248),
        topLeft = Offset(cx + 5f, cy - 75f), size = Size(45f, 12f))
}

private fun DrawScope.drawFishingNet(cx: Float, cy: Float) {
    for (i in 0..5) {
        for (j in 0..4) {
            drawLine(Color(0xFFBB9944).copy(alpha = 0.5f),
                Offset(cx + i * 35f, cy), Offset(cx + j * 42f + 10f, cy + 120f), strokeWidth = 2f)
        }
        drawLine(Color(0xFFBB9944).copy(alpha = 0.5f),
            Offset(cx, cy + i * 24f), Offset(cx + 200f, cy + i * 22f), strokeWidth = 2f)
    }
}

private fun DrawScope.drawSeagullStanding(cx: Float, cy: Float, size: Float) {
    drawCircle(Color(0xFFEEEEEE), size * 0.5f, Offset(cx, cy - size * 1.1f))
    drawOval(Color(0xFFEEEEEE),
        topLeft = Offset(cx - size * 0.6f, cy - size * 0.9f),
        size = Size(size * 1.2f, size * 0.7f))
    drawLine(Color(0xFFFFAA44), Offset(cx + size * 0.4f, cy - size * 1.1f), Offset(cx + size * 0.9f, cy - size * 1.0f),
        strokeWidth = size * 0.2f, cap = StrokeCap.Round)
    drawLine(Color(0xFFFFAA44), Offset(cx - size * 0.3f, cy), Offset(cx + size * 0.4f, cy), strokeWidth = size * 0.15f)
}

private fun DrawScope.drawLantern(cx: Float, cy: Float) {
    drawLine(Color(0xFF5C3D1E), Offset(cx, cy - 60f), Offset(cx, cy - 110f), strokeWidth = 6f)
    drawCircle(Color(0xFFFFF9C4).copy(alpha = 0.5f), 30f, Offset(cx, cy - 30f))
    drawRect(Color(0xFF8B6340),
        topLeft = Offset(cx - 22f, cy - 60f), size = Size(44f, 40f))
    drawRect(Color(0xFFFFF176).copy(alpha = 0.8f),
        topLeft = Offset(cx - 16f, cy - 55f), size = Size(32f, 30f))
}
