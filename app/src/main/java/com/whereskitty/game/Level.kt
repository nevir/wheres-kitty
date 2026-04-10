package com.whereskitty.game

import androidx.compose.ui.graphics.Color

enum class Difficulty(val label: String, val stars: Int) {
    EASY("Easy", 1),
    MEDIUM("Medium", 2),
    HARD("Hard", 3)
}

data class CatTarget(
    val name: String,
    val description: String,
    val clue: String,
    val previewColor: Color,
    val previewStripeColor: Color? = null,
    val hasHat: Boolean = false,
    val hasBowTie: Boolean = false,
    val hasCollar: Boolean = false,
    // Hit region in scene coordinates (scene is SCENE_W x SCENE_H units)
    val hitX: Float,
    val hitY: Float,
    val hitRadius: Float
)

data class Level(
    val id: Int,
    val name: String,
    val subtitle: String,
    val difficulty: Difficulty,
    val target: CatTarget,
    val sceneWidth: Float = 2000f,
    val sceneHeight: Float = 1400f
) {
    fun isHit(sceneX: Float, sceneY: Float): Boolean {
        val dx = sceneX - target.hitX
        val dy = sceneY - target.hitY
        return dx * dx + dy * dy <= target.hitRadius * target.hitRadius
    }
}
