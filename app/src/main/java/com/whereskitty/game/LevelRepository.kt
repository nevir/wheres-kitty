package com.whereskitty.game

import androidx.compose.ui.graphics.Color

object LevelRepository {

    val levels = listOf(
        Level(
            id = 1,
            name = "The Garden Party",
            subtitle = "A sunny afternoon tea among the blooms",
            difficulty = Difficulty.EASY,
            target = CatTarget(
                name = "Marmalade",
                description = "An orange tabby wearing a pink bow tie",
                clue = "Look for the fanciest guest at the tea table — she dressed up just for this occasion!",
                previewColor = Color(0xFFE07B39),
                previewStripeColor = Color(0xFFBB5500),
                hasBowTie = true,
                hitX = 1155f,
                hitY = 665f,
                hitRadius = 75f
            )
        ),
        Level(
            id = 2,
            name = "The Cozy Library",
            subtitle = "A rainy day among towers of books",
            difficulty = Difficulty.MEDIUM,
            target = CatTarget(
                name = "Whisper",
                description = "A tiny gray kitten with a blue collar, curled up on the bookshelf",
                clue = "Whisper loves to sleep between books on the middle shelf. She's very small!",
                previewColor = Color(0xFF9A9A9A),
                hasCollar = true,
                hitX = 820f,
                hitY = 435f,
                hitRadius = 60f
            )
        ),
        Level(
            id = 3,
            name = "The Busy Harbor",
            subtitle = "A chaotic fish market at the waterfront",
            difficulty = Difficulty.HARD,
            target = CatTarget(
                name = "Captain",
                description = "A tuxedo cat wearing a tiny sailor's hat, aboard the fishing boat",
                clue = "Captain guards his catch from the boat deck. Look for the hat on the water!",
                previewColor = Color(0xFF1A1A1A),
                hasHat = true,
                hitX = 355f,
                hitY = 390f,
                hitRadius = 65f
            )
        )
    )

    fun getLevel(id: Int): Level = levels.first { it.id == id }
}
