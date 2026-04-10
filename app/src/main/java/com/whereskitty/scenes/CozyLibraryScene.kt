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
// Target: Whisper (tiny gray kitten, blue collar) at (820, 435)

private val bookColors = listOf(
    Color(0xFFE74C3C), Color(0xFF3498DB), Color(0xFF2ECC71), Color(0xFFF39C12),
    Color(0xFF9B59B6), Color(0xFF1ABC9C), Color(0xFFE67E22), Color(0xFF27AE60),
    Color(0xFF8E44AD), Color(0xFF2980B9), Color(0xFFC0392B), Color(0xFF16A085),
    Color(0xFFD35400), Color(0xFF7F8C8D), Color(0xFF2C3E50), Color(0xFF795548),
    Color(0xFF607D8B), Color(0xFFFF5722), Color(0xFF009688), Color(0xFF4CAF50)
)

fun DrawScope.drawCozyLibraryScene(showFoundHighlight: Boolean) {

    // ── Floor ─────────────────────────────────────────────────────────────────
    drawRect(
        brush = Brush.verticalGradient(
            colorStops = arrayOf(0f to Color(0xFF8D6E47), 1f to Color(0xFF6D4E27)),
            startY = 900f, endY = 1400f
        ),
        topLeft = Offset(0f, 900f), size = Size(2000f, 500f)
    )
    // Floor planks
    for (i in 0..8) {
        val py = 920f + i * 55f
        drawLine(Color(0xFF5D4037).copy(alpha = 0.5f), Offset(0f, py), Offset(2000f, py), strokeWidth = 2f)
    }
    for (i in 0..9) {
        val px = i * 220f + 80f
        drawLine(Color(0xFF5D4037).copy(alpha = 0.3f), Offset(px, 900f), Offset(px + 30f, 1400f), strokeWidth = 2f)
    }

    // ── Walls ─────────────────────────────────────────────────────────────────
    drawRect(
        brush = Brush.verticalGradient(
            colorStops = arrayOf(0f to Color(0xFFD4A96A), 1f to Color(0xFFB8895A)),
            startY = 0f, endY = 900f
        ),
        size = Size(2000f, 900f)
    )
    // Wall paneling
    for (i in 0..5) {
        val wx = i * 340f + 20f
        drawLine(Color(0xFFA07040).copy(alpha = 0.3f), Offset(wx, 0f), Offset(wx, 900f), strokeWidth = 3f)
    }
    for (i in 0..3) {
        val wy = i * 220f + 50f
        drawLine(Color(0xFFA07040).copy(alpha = 0.2f), Offset(0f, wy), Offset(2000f, wy), strokeWidth = 2f)
    }

    // ── Window (top center) ───────────────────────────────────────────────────
    drawRect(Color(0xFF5C3D1E),
        topLeft = Offset(870f, 80f), size = Size(260f, 340f))
    // Window glow
    drawRect(
        brush = Brush.verticalGradient(
            colorStops = arrayOf(0f to Color(0xFFFFF9C4), 1f to Color(0xFFFFE0B2)),
            startY = 88f, endY = 412f
        ),
        topLeft = Offset(878f, 88f), size = Size(244f, 324f)
    )
    // Window cross
    drawLine(Color(0xFF5C3D1E), Offset(1000f, 88f), Offset(1000f, 412f), strokeWidth = 10f)
    drawLine(Color(0xFF5C3D1E), Offset(878f, 250f), Offset(1122f, 250f), strokeWidth = 10f)
    // Light rays on floor
    drawRect(Color(0xFFFFF9C4).copy(alpha = 0.15f),
        topLeft = Offset(900f, 900f), size = Size(200f, 500f))

    // ── Bookshelf - Left wall (large) ─────────────────────────────────────────
    drawBookshelf(30f, 100f, 560f, 800f)

    // ── Bookshelf - Right wall (large) ────────────────────────────────────────
    drawBookshelf(1410f, 100f, 560f, 800f)

    // ── Bookshelf - Back wall center ──────────────────────────────────────────
    drawBookshelf(600f, 100f, 800f, 550f)

    // ── Fireplace (right) ─────────────────────────────────────────────────────
    drawRect(Color(0xFF4A2C0A),
        topLeft = Offset(1640f, 600f), size = Size(320f, 300f))
    drawRect(Color(0xFF2C1A06),
        topLeft = Offset(1660f, 640f), size = Size(280f, 220f))
    // Flames
    drawFlame(1760f, 840f, 60f, Color(0xFFFF6B00))
    drawFlame(1800f, 840f, 50f, Color(0xFFFFAA00))
    drawFlame(1830f, 840f, 45f, Color(0xFFFF8C00))
    // Mantle
    drawRect(Color(0xFF5C3D1E),
        topLeft = Offset(1620f, 590f), size = Size(360f, 30f))
    // Fireplace glow on floor
    drawRect(Color(0xFFFF8C00).copy(alpha = 0.08f),
        topLeft = Offset(1550f, 900f), size = Size(450f, 500f))

    // ── Floor lamp (left) ────────────────────────────────────────────────────
    drawLine(Color(0xFF8B6340), Offset(520f, 900f), Offset(520f, 450f), strokeWidth = 8f)
    drawCircle(Color(0xFFFFF9C4).copy(alpha = 0.3f), 100f, Offset(520f, 430f))
    // Lampshade
    val shadeL = Path().apply {
        moveTo(460f, 430f); lineTo(450f, 380f); lineTo(590f, 380f); lineTo(580f, 430f); close()
    }
    drawPath(shadeL, Color(0xFFE8C87A))
    drawPath(shadeL, Color(0xFFF5DFA0), style = Stroke(width = 4f))

    // ── Armchairs ─────────────────────────────────────────────────────────────
    drawArmchair(220f, 900f, Color(0xFF8D5524), Color(0xFF6D3A14))
    drawArmchair(1280f, 900f, Color(0xFF546E7A), Color(0xFF37474F))

    // ── Books stacked on floor ────────────────────────────────────────────────
    for (i in 0..3) {
        drawRect(bookColors[(i + 5) % bookColors.size],
            topLeft = Offset(650f + i * 3f, 900f - i * 28f - 28f),
            size = Size(60f, 28f))
        drawLine(Color.White.copy(alpha = 0.3f),
            Offset(660f + i * 3f, 900f - i * 28f - 20f),
            Offset(700f + i * 3f, 900f - i * 28f - 20f), strokeWidth = 2f)
    }

    // ── Side table with teacup ────────────────────────────────────────────────
    drawRect(Color(0xFF8B6340),
        topLeft = Offset(490f, 845f), size = Size(120f, 55f))
    drawRect(Color(0xFF7A5530),
        topLeft = Offset(510f, 840f), size = Size(12f, 60f))
    drawRect(Color(0xFF7A5530),
        topLeft = Offset(580f, 840f), size = Size(12f, 60f))
    // Teacup
    drawOval(Color(0xFFF5ECD0),
        topLeft = Offset(530f, 810f), size = Size(50f, 35f))
    drawLine(Color(0xFFF5ECD0), Offset(580f, 820f), Offset(598f, 835f), strokeWidth = 5f, cap = StrokeCap.Round)
    // Steam
    drawSteam(555f, 810f)

    // ── Background decoy cats ─────────────────────────────────────────────────
    // Big white fluffy on armchair (left)
    drawCat(220f, 860f, 68f, Color(0xFFF5F5F0), eye = Color(0xFF6644BB), facing = 1, pose = CatPose.LOAF)
    // Orange sprawl on rug
    drawCat(400f, 945f, 55f, Color(0xFFCC7733), stripe = Color(0xFFAA5520), facing = 1, pose = CatPose.SLEEPING)
    // Tuxedo on floor
    drawCat(700f, 930f, 50f, Color(0xFF1A1A1A), facing = -1, pose = CatPose.SITTING, eye = Color(0xFFEECC00))
    // Gray cat (bigger than Whisper) on top bookshelf
    drawCat(1200f, 355f, 55f, Color(0xFF7A7A7A), stripe = Color(0xFF555555), facing = 1, pose = CatPose.LOAF)
    // Calico on window sill
    drawCat(990f, 418f, 42f, Color(0xFFCC8844), stripe = Color(0xFF221100), facing = -1, pose = CatPose.LOAF)
    // Siamese in armchair (right)
    drawCat(1280f, 855f, 60f, Color(0xFFF0DEB5), facing = -1, pose = CatPose.LOAF, eye = Color(0xFF4499CC))
    // Tortoiseshell near fireplace
    drawCat(1580f, 920f, 48f, Color(0xFF8B4513), stripe = Color(0xFF3D1F00), facing = 1, pose = CatPose.SITTING)
    // Brown tabby on books
    drawCat(1750f, 860f, 44f, Color(0xFF9C7245), stripe = Color(0xFF6B4A28), facing = -1, pose = CatPose.SITTING)
    // Small black cat peeking from behind chair
    drawCat(380f, 885f, 30f, Color(0xFF222222), facing = 1, pose = CatPose.SITTING, eye = Color(0xFFFFAA00))

    // ── TARGET: Whisper on the center bookshelf ────────────────────────────────
    // She's tiny (35f size) tucked between books, 3rd shelf, slightly right of center
    drawCat(
        820f, 455f, 35f,
        body = Color(0xFF9A9A9A),
        eye = Color(0xFF88AAEE),
        facing = 1,
        pose = CatPose.LOAF,
        collarColor = Color(0xFF4488FF)
    )

    if (showFoundHighlight) {
        drawCircle(Color(0xFFFFD700).copy(alpha = 0.35f), 65f, Offset(820f, 435f))
        drawCircle(Color(0xFFFFD700).copy(alpha = 0.7f), 65f, Offset(820f, 435f),
            style = Stroke(width = 5f))
    }

    // ── Decorative items ──────────────────────────────────────────────────────
    // Globe on mantle
    drawCircle(Color(0xFF4A9D8F), 28f, Offset(1680f, 563f))
    drawLine(Color(0xFF8B6340), Offset(1680f, 563f - 28f), Offset(1680f, 563f + 28f), strokeWidth = 2f)
    // Clock on mantle
    drawCircle(Color(0xFFD4A96A), 22f, Offset(1740f, 568f))
    drawCircle(Color(0xFF2C1A06), 18f, Offset(1740f, 568f))

    // ── Rug ───────────────────────────────────────────────────────────────────
    val rugPath = Path().apply {
        moveTo(300f, 1380f); lineTo(1700f, 1360f)
        lineTo(1680f, 1400f); lineTo(320f, 1400f); close()
    }
    drawPath(rugPath, Color(0xFF8B2500).copy(alpha = 0.7f))
    drawOval(Color(0xFFAA3310).copy(alpha = 0.6f),
        topLeft = Offset(400f, 1370f), size = Size(1200f, 30f))
}

private fun DrawScope.drawBookshelf(left: Float, top: Float, width: Float, height: Float) {
    val shelfColor = Color(0xFF6B4226)
    val shelfDark = Color(0xFF4A2C10)
    val shelfCount = 5
    val shelfH = height / shelfCount

    // Back panel
    drawRect(Color(0xFF4A2C10).copy(alpha = 0.8f),
        topLeft = Offset(left, top), size = Size(width, height))

    // Side panels
    drawRect(shelfColor, topLeft = Offset(left, top), size = Size(18f, height))
    drawRect(shelfColor, topLeft = Offset(left + width - 18f, top), size = Size(18f, height))

    // Shelf boards + books
    for (s in 0..shelfCount) {
        val sy = top + s * shelfH
        drawRect(shelfColor, topLeft = Offset(left, sy - 10f), size = Size(width, 18f))

        if (s < shelfCount) {
            drawBooksOnShelf(left + 18f, sy, width - 36f, shelfH - 10f)
        }
    }
}

private fun DrawScope.drawBooksOnShelf(left: Float, shelfY: Float, shelfWidth: Float, bookH: Float) {
    var x = left + 4f
    var idx = (left / 30f).toInt()
    while (x < left + shelfWidth - 20f) {
        val bookW = 18f + (idx % 5) * 5f
        val bH = bookH * (0.75f + (idx % 3) * 0.08f)
        val bColor = bookColors[idx % bookColors.size]
        val bookTop = shelfY - bH

        drawRect(bColor, topLeft = Offset(x, bookTop), size = Size(bookW, bH))
        // Spine decoration
        drawLine(Color.White.copy(alpha = 0.3f),
            Offset(x + bookW / 2, bookTop + 5f),
            Offset(x + bookW / 2, bookTop + bH * 0.7f),
            strokeWidth = 1.5f)
        if (bookW > 25f) {
            drawRect(bColor.copy(alpha = 0f).let { Color(1f - bColor.red * 0.3f, 1f - bColor.green * 0.3f, 1f - bColor.blue * 0.3f, 0.2f) },
                topLeft = Offset(x + 3f, bookTop + bH * 0.2f),
                size = Size(bookW - 6f, bH * 0.15f))
        }

        x += bookW + 2f
        idx++
    }
}

private fun DrawScope.drawArmchair(cx: Float, bottomY: Float, seatColor: Color, darkColor: Color) {
    val w = 220f; val h = 180f
    // Back
    drawRect(seatColor,
        topLeft = Offset(cx - w / 2, bottomY - h),
        size = Size(w, h * 0.7f))
    // Seat
    drawRect(seatColor,
        topLeft = Offset(cx - w / 2, bottomY - h * 0.4f),
        size = Size(w, h * 0.35f))
    // Arms
    drawRect(darkColor,
        topLeft = Offset(cx - w / 2 - 18f, bottomY - h * 0.55f),
        size = Size(18f, h * 0.5f))
    drawRect(darkColor,
        topLeft = Offset(cx + w / 2, bottomY - h * 0.55f),
        size = Size(18f, h * 0.5f))
    // Legs
    drawRect(darkColor, topLeft = Offset(cx - w / 2 + 15f, bottomY - 20f), size = Size(16f, 20f))
    drawRect(darkColor, topLeft = Offset(cx + w / 2 - 31f, bottomY - 20f), size = Size(16f, 20f))
    // Cushion highlight
    drawOval(seatColor.copy(alpha = 0.5f).let { Color(it.red + 0.1f, it.green + 0.1f, it.blue + 0.1f, it.alpha) },
        topLeft = Offset(cx - w * 0.35f, bottomY - h * 0.35f),
        size = Size(w * 0.7f, h * 0.2f))
}

private fun DrawScope.drawFlame(cx: Float, baseY: Float, height: Float, color: Color) {
    val flamePath = Path().apply {
        moveTo(cx, baseY)
        cubicTo(cx - height * 0.4f, baseY - height * 0.5f, cx - height * 0.2f, baseY - height, cx, baseY - height)
        cubicTo(cx + height * 0.2f, baseY - height, cx + height * 0.4f, baseY - height * 0.5f, cx, baseY)
        close()
    }
    drawPath(flamePath, color.copy(alpha = 0.9f))
}

private fun DrawScope.drawSteam(cx: Float, topY: Float) {
    val steamPath1 = Path().apply {
        moveTo(cx - 8f, topY)
        cubicTo(cx - 12f, topY - 15f, cx, topY - 25f, cx - 4f, topY - 40f)
    }
    val steamPath2 = Path().apply {
        moveTo(cx + 8f, topY)
        cubicTo(cx + 12f, topY - 15f, cx, topY - 25f, cx + 4f, topY - 40f)
    }
    val steamStyle = Stroke(width = 3f, cap = StrokeCap.Round)
    drawPath(steamPath1, Color(0xFFCCCCCC).copy(alpha = 0.5f), style = steamStyle)
    drawPath(steamPath2, Color(0xFFCCCCCC).copy(alpha = 0.5f), style = steamStyle)
}
