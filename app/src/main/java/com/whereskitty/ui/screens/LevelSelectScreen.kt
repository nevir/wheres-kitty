package com.whereskitty.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.whereskitty.game.Difficulty
import com.whereskitty.game.GameViewModel
import com.whereskitty.game.Level
import com.whereskitty.game.LevelRepository
import com.whereskitty.scenes.*
import com.whereskitty.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LevelSelectScreen(
    gameViewModel: GameViewModel,
    onLevelSelected: (Int) -> Unit,
    onBack: () -> Unit
) {
    val state by gameViewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Choose a Level",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold)
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF5C3D1E),
                    titleContentColor = Color(0xFFFFF0D8),
                    navigationIconContentColor = Color(0xFFFFF0D8)
                )
            )
        },
        containerColor = Color(0xFFFFF8F0)
    ) { padding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(1),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            items(LevelRepository.levels) { level ->
                val stars = state.levelStars[level.id] ?: 0
                LevelCard(
                    level = level,
                    stars = stars,
                    onClick = { onLevelSelected(level.id) }
                )
            }
        }
    }
}

@Composable
private fun LevelCard(level: Level, stars: Int, onClick: () -> Unit) {
    val difficultyColor = when (level.difficulty) {
        Difficulty.EASY -> Color(0xFF4CAF50)
        Difficulty.MEDIUM -> Color(0xFFFF9800)
        Difficulty.HARD -> Color(0xFFE53935)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .shadow(6.dp, RoundedCornerShape(20.dp))
            .clip(RoundedCornerShape(20.dp))
            .clickable(onClick = onClick)
    ) {
        // Scene preview (clipped to card)
        Canvas(modifier = Modifier.fillMaxSize()) {
            val scaleX = size.width / 2000f
            val scaleY = size.height / 1400f
            val previewScale = maxOf(scaleX, scaleY) * 1.1f
            withTransform({
                scale(previewScale, previewScale, Offset(size.width / 2f, size.height / 2f))
            }) {
                when (level.id) {
                    1 -> drawGardenPartyScene(false)
                    2 -> drawCozyLibraryScene(false)
                    3 -> drawBusyHarborScene(false)
                }
            }
        }

        // Overlay gradient for text readability
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    androidx.compose.ui.graphics.Brush.verticalGradient(
                        listOf(Color.Transparent, Color(0xFF2C1A06).copy(alpha = 0.75f))
                    )
                )
        )

        // Level info
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Top row: difficulty badge + stars
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Box(
                    modifier = Modifier
                        .background(difficultyColor.copy(alpha = 0.9f), RoundedCornerShape(20.dp))
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        level.difficulty.label,
                        style = MaterialTheme.typography.labelLarge,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
                if (stars > 0) {
                    Row {
                        repeat(3) { i ->
                            Text(
                                if (i < stars) "★" else "☆",
                                color = if (i < stars) Color(0xFFFFD700) else Color.White.copy(alpha = 0.5f),
                                fontSize = 22.sp
                            )
                        }
                    }
                }
            }

            // Bottom: level name and subtitle
            Column {
                Text(
                    "Level ${level.id}: ${level.name}",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    level.subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White.copy(alpha = 0.85f)
                )
            }
        }
    }
}
