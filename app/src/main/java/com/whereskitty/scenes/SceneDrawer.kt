package com.whereskitty.scenes

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.translate
import kotlin.math.cos
import kotlin.math.sin

enum class CatPose { SITTING, LOAF, SLEEPING, WALKING }

// ── Cat drawing ──────────────────────────────────────────────────────────────

fun DrawScope.drawCat(
    cx: Float, cy: Float,
    size: Float = 80f,
    body: Color = Color(0xFFD4A96A),
    stripe: Color? = null,
    eye: Color = Color(0xFF33BB55),
    facing: Int = 1,
    pose: CatPose = CatPose.SITTING,
    bowTieColor: Color? = null,
    collarColor: Color? = null,
    hatColor: Color? = null,
    alpha: Float = 1f
) {
    when (pose) {
        CatPose.SITTING  -> sittingCat(cx, cy, size, body, stripe, eye, facing, bowTieColor, collarColor, hatColor, alpha)
        CatPose.LOAF     -> loafCat(cx, cy, size, body, stripe, eye, facing, collarColor, alpha)
        CatPose.SLEEPING -> sleepingCat(cx, cy, size, body, stripe, alpha)
        CatPose.WALKING  -> walkingCat(cx, cy, size, body, stripe, eye, facing, hatColor, alpha)
    }
}

private fun DrawScope.sittingCat(
    cx: Float, cy: Float, s: Float,
    body: Color, stripe: Color?, eye: Color,
    facing: Int,
    bowTie: Color?, collar: Color?, hat: Color?,
    alpha: Float
) {
    val bW = s * 0.58f; val bH = s * 0.42f
    val hR = s * 0.28f
    val hX = cx + facing * s * 0.08f
    val hY = cy - bH * 0.6f - hR * 1.1f

    // Tail (behind body)
    val tailPath = Path().apply {
        moveTo(cx + facing * bW * 0.35f, cy - bH * 0.1f)
        cubicTo(
            cx + facing * bW * 0.9f, cy + bH * 0.6f,
            cx + facing * bW * 1.3f, cy - bH * 0.3f,
            cx + facing * bW * 0.8f, cy - bH * 0.5f
        )
    }
    drawPath(tailPath, body.copy(alpha = alpha), style = Stroke(width = s * 0.12f, cap = StrokeCap.Round))

    // Body
    drawOval(body.copy(alpha = alpha),
        topLeft = Offset(cx - bW / 2, cy - bH * 0.55f),
        size = Size(bW, bH * 1.1f))

    // Stripe markings on body
    if (stripe != null) {
        for (i in 0..2) {
            val sx = cx + facing * (bW * 0.05f + i * bW * 0.15f)
            drawLine(stripe.copy(alpha = alpha * 0.6f),
                Offset(sx, cy - bH * 0.4f),
                Offset(sx - facing * bW * 0.05f, cy + bH * 0.3f),
                strokeWidth = s * 0.04f, cap = StrokeCap.Round)
        }
    }

    // Neck
    drawOval(body.copy(alpha = alpha),
        topLeft = Offset(hX - hR * 0.35f, hY + hR * 0.6f),
        size = Size(hR * 0.7f, hR * 0.8f))

    // Head
    drawCircle(body.copy(alpha = alpha), hR, Offset(hX, hY))

    // Ear outer
    val earL = leftEarPath(hX, hY, hR, facing, s)
    val earR = rightEarPath(hX, hY, hR, facing, s)
    drawPath(earL, body.copy(alpha = alpha))
    drawPath(earR, body.copy(alpha = alpha))

    // Ear inner
    val innerScale = 0.5f
    val earLI = leftEarPath(hX, hY, hR * innerScale, facing, s * innerScale, innerEar = true)
    val earRI = rightEarPath(hX, hY, hR * innerScale, facing, s * innerScale, innerEar = true)
    drawPath(earLI, Color(0xFFFFB3B3).copy(alpha = alpha))
    drawPath(earRI, Color(0xFFFFB3B3).copy(alpha = alpha))

    // Stripe on forehead
    if (stripe != null) {
        drawLine(stripe.copy(alpha = alpha * 0.5f),
            Offset(hX, hY - hR * 0.7f), Offset(hX, hY - hR * 0.1f),
            strokeWidth = s * 0.04f, cap = StrokeCap.Round)
        drawLine(stripe.copy(alpha = alpha * 0.4f),
            Offset(hX - hR * 0.25f, hY - hR * 0.5f), Offset(hX - hR * 0.2f, hY - hR * 0.05f),
            strokeWidth = s * 0.03f, cap = StrokeCap.Round)
        drawLine(stripe.copy(alpha = alpha * 0.4f),
            Offset(hX + hR * 0.25f, hY - hR * 0.5f), Offset(hX + hR * 0.2f, hY - hR * 0.05f),
            strokeWidth = s * 0.03f, cap = StrokeCap.Round)
    }

    // Eyes
    val eyeY = hY - hR * 0.08f
    val eyeSpacing = hR * 0.38f
    drawEye(hX - eyeSpacing, eyeY, hR * 0.2f, eye, alpha)
    drawEye(hX + eyeSpacing, eyeY, hR * 0.2f, eye, alpha)

    // Nose + mouth
    drawNoseMouth(hX, hY + hR * 0.18f, hR, alpha)

    // Whiskers
    drawWhiskers(hX, hY + hR * 0.28f, hR, facing, alpha)

    // Accessories
    collar?.let { drawCollar(hX, hY + hR * 0.7f, hR, it, alpha) }
    bowTie?.let { drawBowTie(hX, hY + hR * 1.05f, s * 0.22f, it, alpha) }
    hat?.let { drawSailorHat(hX, hY - hR * 0.85f, hR * 1.3f, it, alpha) }
}

private fun DrawScope.loafCat(
    cx: Float, cy: Float, s: Float,
    body: Color, stripe: Color?, eye: Color,
    facing: Int, collar: Color?, alpha: Float
) {
    val bW = s * 0.65f; val bH = s * 0.35f
    val hR = s * 0.26f
    val hX = cx + facing * bW * 0.2f
    val hY = cy - bH - hR * 0.7f

    // Loaf body
    val loafPath = Path().apply {
        moveTo(cx - bW / 2 + bH * 0.3f, cy)
        lineTo(cx + bW / 2 - bH * 0.3f, cy)
        cubicTo(cx + bW / 2 + bH * 0.2f, cy, cx + bW / 2 + bH * 0.2f, cy - bH, cx + bW / 2 - bH * 0.2f, cy - bH)
        lineTo(cx - bW / 2 + bH * 0.2f, cy - bH)
        cubicTo(cx - bW / 2 - bH * 0.2f, cy - bH, cx - bW / 2 - bH * 0.2f, cy, cx - bW / 2 + bH * 0.3f, cy)
        close()
    }
    drawPath(loafPath, body.copy(alpha = alpha))

    if (stripe != null) {
        for (i in -1..1) {
            drawLine(stripe.copy(alpha = alpha * 0.5f),
                Offset(cx + i * bW * 0.18f, cy - bH * 0.9f),
                Offset(cx + i * bW * 0.12f, cy - bH * 0.1f),
                strokeWidth = s * 0.04f, cap = StrokeCap.Round)
        }
    }

    drawCircle(body.copy(alpha = alpha), hR, Offset(hX, hY))
    drawPath(leftEarPath(hX, hY, hR, facing, s), body.copy(alpha = alpha))
    drawPath(rightEarPath(hX, hY, hR, facing, s), body.copy(alpha = alpha))
    drawPath(leftEarPath(hX, hY, hR * 0.5f, facing, s * 0.5f, innerEar = true), Color(0xFFFFB3B3).copy(alpha = alpha))
    drawPath(rightEarPath(hX, hY, hR * 0.5f, facing, s * 0.5f, innerEar = true), Color(0xFFFFB3B3).copy(alpha = alpha))

    val eyeY = hY - hR * 0.05f
    drawEye(hX - hR * 0.35f, eyeY, hR * 0.19f, eye, alpha)
    drawEye(hX + hR * 0.35f, eyeY, hR * 0.19f, eye, alpha)
    drawNoseMouth(hX, hY + hR * 0.22f, hR, alpha)
    drawWhiskers(hX, hY + hR * 0.32f, hR, facing, alpha)

    collar?.let { drawCollar(hX, hY + hR * 0.7f, hR, it, alpha) }
}

private fun DrawScope.sleepingCat(
    cx: Float, cy: Float, s: Float,
    body: Color, stripe: Color?, alpha: Float
) {
    val bW = s * 0.8f; val bH = s * 0.25f
    val hR = s * 0.22f

    // Curled body ellipse
    drawOval(body.copy(alpha = alpha),
        topLeft = Offset(cx - bW / 2, cy - bH / 2),
        size = Size(bW, bH))

    if (stripe != null) {
        for (i in -2..2) {
            drawLine(stripe.copy(alpha = alpha * 0.4f),
                Offset(cx + i * bW * 0.15f, cy - bH * 0.4f),
                Offset(cx + i * bW * 0.12f, cy + bH * 0.4f),
                strokeWidth = s * 0.04f, cap = StrokeCap.Round)
        }
    }

    // Tail curled on top
    val tailPath = Path().apply {
        moveTo(cx + bW * 0.4f, cy)
        cubicTo(cx + bW * 0.55f, cy - bH * 1.5f, cx - bW * 0.05f, cy - bH * 1.8f, cx - bW * 0.1f, cy - bH * 0.6f)
    }
    drawPath(tailPath, body.copy(alpha = alpha), style = Stroke(width = s * 0.1f, cap = StrokeCap.Round))

    // Head (side view, mostly tucked)
    drawCircle(body.copy(alpha = alpha), hR, Offset(cx - bW * 0.32f, cy - bH * 0.1f))
    // Ear
    val earPath = Path().apply {
        moveTo(cx - bW * 0.32f - hR * 0.5f, cy - bH * 0.1f - hR * 0.8f)
        lineTo(cx - bW * 0.32f - hR * 0.9f, cy - bH * 0.1f - hR * 1.6f)
        lineTo(cx - bW * 0.32f, cy - bH * 0.1f - hR * 1.0f)
        close()
    }
    drawPath(earPath, body.copy(alpha = alpha))
    // Closed eye (zzz line)
    drawLine(Color(0xFF333333).copy(alpha = alpha * 0.7f),
        Offset(cx - bW * 0.38f, cy - bH * 0.15f),
        Offset(cx - bW * 0.26f, cy - bH * 0.15f),
        strokeWidth = s * 0.04f, cap = StrokeCap.Round)
}

private fun DrawScope.walkingCat(
    cx: Float, cy: Float, s: Float,
    body: Color, stripe: Color?, eye: Color,
    facing: Int, hat: Color?, alpha: Float
) {
    val bW = s * 0.7f; val bH = s * 0.3f
    val hR = s * 0.26f
    val hX = cx + facing * bW * 0.35f
    val hY = cy - bH - hR * 0.8f

    // Legs
    val legColor = body.copy(alpha = alpha)
    val legW = s * 0.08f
    drawLine(legColor, Offset(cx - bW * 0.2f, cy - bH * 0.15f), Offset(cx - bW * 0.25f, cy + bH * 0.9f), legW, cap = StrokeCap.Round)
    drawLine(legColor, Offset(cx - bW * 0.05f, cy - bH * 0.15f), Offset(cx, cy + bH * 0.6f), legW, cap = StrokeCap.Round)
    drawLine(legColor, Offset(cx + bW * 0.1f, cy - bH * 0.15f), Offset(cx + bW * 0.1f, cy + bH * 0.9f), legW, cap = StrokeCap.Round)
    drawLine(legColor, Offset(cx + bW * 0.25f, cy - bH * 0.15f), Offset(cx + bW * 0.3f, cy + bH * 0.6f), legW, cap = StrokeCap.Round)

    // Tail up
    val tailPath = Path().apply {
        moveTo(cx - facing * bW * 0.4f, cy - bH * 0.3f)
        cubicTo(
            cx - facing * bW * 0.6f, cy - bH * 1.5f,
            cx - facing * bW * 0.3f, cy - bH * 2.0f,
            cx - facing * bW * 0.1f, cy - bH * 1.8f
        )
    }
    drawPath(tailPath, body.copy(alpha = alpha), style = Stroke(width = s * 0.1f, cap = StrokeCap.Round))

    // Body elongated
    drawOval(body.copy(alpha = alpha),
        topLeft = Offset(cx - bW / 2, cy - bH * 0.9f),
        size = Size(bW, bH))

    if (stripe != null) {
        for (i in -1..2) {
            drawLine(stripe.copy(alpha = alpha * 0.5f),
                Offset(cx + facing * (i * bW * 0.15f - bW * 0.1f), cy - bH * 0.8f),
                Offset(cx + facing * (i * bW * 0.12f - bW * 0.08f), cy - bH * 0.1f),
                strokeWidth = s * 0.04f, cap = StrokeCap.Round)
        }
    }

    // Head
    drawCircle(body.copy(alpha = alpha), hR, Offset(hX, hY))
    drawPath(leftEarPath(hX, hY, hR, facing, s), body.copy(alpha = alpha))
    drawPath(rightEarPath(hX, hY, hR, facing, s), body.copy(alpha = alpha))
    drawPath(leftEarPath(hX, hY, hR * 0.5f, facing, s * 0.5f, innerEar = true), Color(0xFFFFB3B3).copy(alpha = alpha))
    drawPath(rightEarPath(hX, hY, hR * 0.5f, facing, s * 0.5f, innerEar = true), Color(0xFFFFB3B3).copy(alpha = alpha))

    drawEye(hX - hR * 0.35f, hY - hR * 0.08f, hR * 0.19f, eye, alpha)
    drawEye(hX + hR * 0.35f, hY - hR * 0.08f, hR * 0.19f, eye, alpha)
    drawNoseMouth(hX, hY + hR * 0.22f, hR, alpha)
    drawWhiskers(hX, hY + hR * 0.32f, hR, facing, alpha)

    hat?.let { drawSailorHat(hX, hY - hR * 0.85f, hR * 1.3f, it, alpha) }
}

// ── Facial features ──────────────────────────────────────────────────────────

private fun DrawScope.drawEye(
    x: Float, y: Float, r: Float, color: Color, alpha: Float
) {
    drawCircle(Color.White.copy(alpha = alpha), r, Offset(x, y))
    drawCircle(color.copy(alpha = alpha), r * 0.78f, Offset(x, y))
    drawCircle(Color(0xFF111111).copy(alpha = alpha), r * 0.42f, Offset(x, y + r * 0.08f))
    drawCircle(Color.White.copy(alpha = alpha * 0.9f), r * 0.15f, Offset(x + r * 0.2f, y - r * 0.2f))
}

private fun DrawScope.drawNoseMouth(cx: Float, cy: Float, headR: Float, alpha: Float) {
    val noseSize = headR * 0.14f
    val nosePath = Path().apply {
        moveTo(cx, cy)
        lineTo(cx - noseSize, cy - noseSize * 0.7f)
        lineTo(cx + noseSize, cy - noseSize * 0.7f)
        close()
    }
    drawPath(nosePath, Color(0xFFFF9999).copy(alpha = alpha))

    // Mouth
    val mouthPath = Path().apply {
        moveTo(cx - headR * 0.2f, cy + headR * 0.12f)
        cubicTo(cx - headR * 0.05f, cy + headR * 0.22f, cx + headR * 0.05f, cy + headR * 0.22f, cx + headR * 0.2f, cy + headR * 0.12f)
    }
    drawPath(mouthPath, Color(0xFF774444).copy(alpha = alpha), style = Stroke(width = headR * 0.07f, cap = StrokeCap.Round))
}

private fun DrawScope.drawWhiskers(cx: Float, cy: Float, headR: Float, facing: Int, alpha: Float) {
    val wColor = Color(0xFFBBBBBB).copy(alpha = alpha * 0.8f)
    val wW = headR * 0.07f
    // Left whiskers
    drawLine(wColor, Offset(cx - headR * 0.1f, cy), Offset(cx - headR * 1.1f, cy - headR * 0.12f), wW, cap = StrokeCap.Round)
    drawLine(wColor, Offset(cx - headR * 0.1f, cy + headR * 0.08f), Offset(cx - headR * 1.1f, cy + headR * 0.12f), wW, cap = StrokeCap.Round)
    // Right whiskers
    drawLine(wColor, Offset(cx + headR * 0.1f, cy), Offset(cx + headR * 1.1f, cy - headR * 0.12f), wW, cap = StrokeCap.Round)
    drawLine(wColor, Offset(cx + headR * 0.1f, cy + headR * 0.08f), Offset(cx + headR * 1.1f, cy + headR * 0.12f), wW, cap = StrokeCap.Round)
}

// ── Accessories ──────────────────────────────────────────────────────────────

private fun DrawScope.drawCollar(cx: Float, cy: Float, headR: Float, color: Color, alpha: Float) {
    drawLine(color.copy(alpha = alpha),
        Offset(cx - headR * 0.6f, cy),
        Offset(cx + headR * 0.6f, cy),
        strokeWidth = headR * 0.25f, cap = StrokeCap.Round)
    // Small tag
    drawCircle(Color(0xFFFFD700).copy(alpha = alpha), headR * 0.12f, Offset(cx, cy + headR * 0.14f))
}

private fun DrawScope.drawBowTie(cx: Float, cy: Float, size: Float, color: Color, alpha: Float) {
    val halfW = size * 0.55f
    val halfH = size * 0.35f
    // Left wing
    val leftPath = Path().apply {
        moveTo(cx, cy)
        cubicTo(cx - halfW * 0.4f, cy - halfH, cx - halfW, cy - halfH * 0.5f, cx - halfW, cy)
        cubicTo(cx - halfW, cy + halfH * 0.5f, cx - halfW * 0.4f, cy + halfH, cx, cy)
        close()
    }
    // Right wing
    val rightPath = Path().apply {
        moveTo(cx, cy)
        cubicTo(cx + halfW * 0.4f, cy - halfH, cx + halfW, cy - halfH * 0.5f, cx + halfW, cy)
        cubicTo(cx + halfW, cy + halfH * 0.5f, cx + halfW * 0.4f, cy + halfH, cx, cy)
        close()
    }
    drawPath(leftPath, color.copy(alpha = alpha))
    drawPath(rightPath, color.copy(alpha = alpha))
    drawCircle(color.copy(alpha = alpha).let { Color(it.red * 0.8f, it.green * 0.8f, it.blue * 0.8f, it.alpha) },
        size * 0.13f, Offset(cx, cy))
}

private fun DrawScope.drawSailorHat(cx: Float, cy: Float, width: Float, color: Color, alpha: Float) {
    val h = width * 0.5f
    // Hat brim
    drawOval(color.copy(alpha = alpha),
        topLeft = Offset(cx - width / 2, cy - h * 0.15f),
        size = Size(width, h * 0.3f))
    // Hat crown
    drawRect(color.copy(alpha = alpha),
        topLeft = Offset(cx - width * 0.35f, cy - h * 0.95f),
        size = Size(width * 0.7f, h * 0.8f))
    // White band
    drawRect(Color.White.copy(alpha = alpha),
        topLeft = Offset(cx - width * 0.35f, cy - h * 0.35f),
        size = Size(width * 0.7f, h * 0.18f))
}

// ── Ear helpers ──────────────────────────────────────────────────────────────

private fun leftEarPath(cx: Float, cy: Float, hR: Float, facing: Int, s: Float, innerEar: Boolean = false): Path {
    val offset = if (innerEar) hR * 0.05f else 0f
    return Path().apply {
        moveTo(cx - hR * 0.55f + offset, cy - hR * 0.7f + offset)
        lineTo(cx - hR * 0.9f + offset, cy - hR * 1.6f)
        lineTo(cx - hR * 0.1f + offset, cy - hR * 1.1f + offset)
        close()
    }
}

private fun rightEarPath(cx: Float, cy: Float, hR: Float, facing: Int, s: Float, innerEar: Boolean = false): Path {
    val offset = if (innerEar) hR * 0.05f else 0f
    return Path().apply {
        moveTo(cx + hR * 0.55f - offset, cy - hR * 0.7f + offset)
        lineTo(cx + hR * 0.9f - offset, cy - hR * 1.6f)
        lineTo(cx + hR * 0.1f - offset, cy - hR * 1.1f + offset)
        close()
    }
}

// ── Environment helpers ───────────────────────────────────────────────────────

fun DrawScope.drawCloud(cx: Float, cy: Float, scale: Float, alpha: Float = 1f) {
    val c = Color.White.copy(alpha = alpha * 0.92f)
    drawCircle(c, scale * 0.5f, Offset(cx, cy))
    drawCircle(c, scale * 0.38f, Offset(cx - scale * 0.45f, cy + scale * 0.1f))
    drawCircle(c, scale * 0.42f, Offset(cx + scale * 0.45f, cy + scale * 0.1f))
    drawCircle(c, scale * 0.32f, Offset(cx - scale * 0.75f, cy + scale * 0.3f))
    drawCircle(c, scale * 0.32f, Offset(cx + scale * 0.75f, cy + scale * 0.3f))
}

fun DrawScope.drawTree(cx: Float, baseY: Float, trunkW: Float, height: Float, leafColor: Color, trunkColor: Color = Color(0xFF8B6340)) {
    val trunkH = height * 0.35f
    val canopyR = height * 0.45f

    // Trunk
    drawRect(trunkColor,
        topLeft = Offset(cx - trunkW / 2, baseY - trunkH),
        size = Size(trunkW, trunkH))

    // Canopy layers
    drawCircle(leafColor.copy(alpha = 0.8f), canopyR * 0.8f, Offset(cx - canopyR * 0.25f, baseY - trunkH - canopyR * 0.6f))
    drawCircle(leafColor.copy(alpha = 0.85f), canopyR * 0.9f, Offset(cx + canopyR * 0.2f, baseY - trunkH - canopyR * 0.5f))
    drawCircle(leafColor, canopyR, Offset(cx, baseY - trunkH - canopyR * 0.85f))
    drawCircle(leafColor.copy(alpha = 0.7f), canopyR * 0.65f, Offset(cx - canopyR * 0.1f, baseY - trunkH - canopyR * 1.3f))
}

fun DrawScope.drawFlower(cx: Float, cy: Float, r: Float, petalColor: Color, centerColor: Color = Color(0xFFFFEE55)) {
    for (i in 0 until 6) {
        val angle = i * 60f * (Math.PI / 180f).toFloat()
        drawCircle(petalColor.copy(alpha = 0.9f), r,
            Offset(cx + cos(angle) * r * 1.3f, cy + sin(angle) * r * 1.3f))
    }
    drawCircle(centerColor, r * 0.7f, Offset(cx, cy))
}

fun DrawScope.drawButterfly(cx: Float, cy: Float, size: Float, color: Color) {
    val wingPath = Path().apply {
        // Left wings
        moveTo(cx, cy)
        cubicTo(cx - size * 1.5f, cy - size * 1.2f, cx - size * 2f, cy, cx - size * 1.5f, cy + size * 0.8f)
        cubicTo(cx - size * 0.8f, cy + size * 1.0f, cx, cy + size * 0.3f, cx, cy)
        close()
    }
    val wingPathR = Path().apply {
        moveTo(cx, cy)
        cubicTo(cx + size * 1.5f, cy - size * 1.2f, cx + size * 2f, cy, cx + size * 1.5f, cy + size * 0.8f)
        cubicTo(cx + size * 0.8f, cy + size * 1.0f, cx, cy + size * 0.3f, cx, cy)
        close()
    }
    drawPath(wingPath, color.copy(alpha = 0.85f))
    drawPath(wingPathR, color.copy(alpha = 0.85f))
    // Body
    drawLine(Color(0xFF333333), Offset(cx, cy - size * 0.8f), Offset(cx, cy + size * 0.6f),
        strokeWidth = size * 0.2f, cap = StrokeCap.Round)
}

fun DrawScope.drawBird(cx: Float, cy: Float, size: Float, color: Color = Color(0xFF555555)) {
    // Simple bird in flight (M-shape)
    val path = Path().apply {
        moveTo(cx - size * 1.5f, cy)
        cubicTo(cx - size * 0.8f, cy - size, cx - size * 0.2f, cy - size * 0.3f, cx, cy)
        cubicTo(cx + size * 0.2f, cy - size * 0.3f, cx + size * 0.8f, cy - size, cx + size * 1.5f, cy)
    }
    drawPath(path, color, style = Stroke(width = size * 0.25f, cap = StrokeCap.Round))
}

fun DrawScope.drawFoundCircle(cx: Float, cy: Float, radius: Float, progress: Float) {
    // Pulsing success circle
    val r = radius * (1f + progress * 0.3f)
    drawCircle(Color(0xFFFFD700).copy(alpha = (1f - progress) * 0.7f),
        r, Offset(cx, cy), style = Stroke(width = radius * 0.15f))
    drawCircle(Color(0xFF88EE88).copy(alpha = (1f - progress) * 0.5f),
        r * 0.7f, Offset(cx, cy), style = Stroke(width = radius * 0.1f))
}

fun DrawScope.drawWrongTapX(cx: Float, cy: Float, radius: Float) {
    val c = Color(0xFFFF5555).copy(alpha = 0.8f)
    drawLine(c, Offset(cx - radius, cy - radius), Offset(cx + radius, cy + radius),
        strokeWidth = radius * 0.2f, cap = StrokeCap.Round)
    drawLine(c, Offset(cx + radius, cy - radius), Offset(cx - radius, cy + radius),
        strokeWidth = radius * 0.2f, cap = StrokeCap.Round)
}
