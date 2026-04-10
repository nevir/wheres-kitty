package com.whereskitty.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.whereskitty.game.GameViewModel
import com.whereskitty.game.LevelRepository
import com.whereskitty.scenes.*
import com.whereskitty.ui.components.CatHintPanel
import kotlin.math.max
import kotlin.math.min

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen(
    levelId: Int,
    gameViewModel: GameViewModel,
    onLevelComplete: (Int) -> Unit,
    onBack: () -> Unit
) {
    val state by gameViewModel.state.collectAsState()
    val level = remember(levelId) { LevelRepository.getLevel(levelId) }

    var containerSize by remember { mutableStateOf(IntSize.Zero) }
    var scale by remember { mutableFloatStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    var initialized by remember { mutableStateOf(false) }

    val sceneW = level.sceneWidth
    val sceneH = level.sceneHeight

    // Initialize transform to fit scene
    LaunchedEffect(containerSize, levelId) {
        if (containerSize.width > 0 && containerSize.height > 0 && !initialized) {
            val fitScale = min(
                containerSize.width / sceneW,
                containerSize.height / sceneH
            )
            scale = fitScale
            offset = Offset(
                (containerSize.width - sceneW * fitScale) / 2f,
                (containerSize.height - sceneH * fitScale) / 2f
            )
            initialized = true
        }
    }

    // Navigate away when cat is found
    LaunchedEffect(state.found) {
        if (state.found) {
            kotlinx.coroutines.delay(1200)
            val stars = state.levelStars[levelId] ?: 1
            onLevelComplete(stars)
        }
    }

    fun constrainOffset(newOffset: Offset, newScale: Float): Offset {
        val cW = containerSize.width.toFloat()
        val cH = containerSize.height.toFloat()
        val scaledW = sceneW * newScale
        val scaledH = sceneH * newScale
        return Offset(
            x = if (scaledW <= cW) (cW - scaledW) / 2f
                else newOffset.x.coerceIn(cW - scaledW, 0f),
            y = if (scaledH <= cH) (cH - scaledH) / 2f
                else newOffset.y.coerceIn(cH - scaledH, 0f)
        )
    }

    val minScale = remember(containerSize, sceneW, sceneH) {
        if (containerSize.width == 0) 0.2f
        else min(containerSize.width / sceneW, containerSize.height / sceneH) * 0.95f
    }
    val maxScale = minScale * 4f

    // Hint overlay
    val showHintDialog = state.showHint && state.level?.id == levelId

    Box(modifier = Modifier.fillMaxSize()) {
        // Game canvas
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .onSizeChanged { containerSize = it }
                .pointerInput(levelId) {
                    detectTransformGestures { centroid, pan, zoom, _ ->
                        val newScale = (scale * zoom).coerceIn(minScale, maxScale)
                        val scaleChange = newScale / scale
                        val newOffset = Offset(
                            centroid.x - scaleChange * (centroid.x - offset.x) + pan.x,
                            centroid.y - scaleChange * (centroid.y - offset.y) + pan.y
                        )
                        scale = newScale
                        offset = constrainOffset(newOffset, newScale)
                    }
                }
                .pointerInput(levelId) {
                    detectTapGestures { tap ->
                        if (state.found) return@detectTapGestures
                        val sceneX = (tap.x - offset.x) / scale
                        val sceneY = (tap.y - offset.y) / scale
                        gameViewModel.onTap(sceneX, sceneY)
                    }
                }
        ) {
            withTransform({
                translate(offset.x, offset.y)
                scale(scale, scale, Offset.Zero)
            }) {
                // Draw the appropriate scene
                when (levelId) {
                    1 -> drawGardenPartyScene(state.found)
                    2 -> drawCozyLibraryScene(state.found)
                    3 -> drawBusyHarborScene(state.found)
                }

                // Wrong tap feedback
                state.feedback?.let { fb ->
                    if (!fb.isHit) {
                        drawWrongTapX(fb.position.x, fb.position.y, 30f)
                    }
                }
            }
        }

        // Top HUD
        Column(modifier = Modifier.fillMaxWidth()) {
            // Top bar
            TopAppBar(
                title = { Text(level.name, style = MaterialTheme.typography.titleLarge) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back",
                            tint = Color(0xFFFFF0D8))
                    }
                },
                actions = {
                    IconButton(onClick = { gameViewModel.toggleHint() }) {
                        Icon(Icons.Default.Search, "Hint",
                            tint = if (state.hintUsed) Color(0xFFFFD700) else Color(0xFFFFF0D8))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF5C3D1E).copy(alpha = 0.9f),
                    titleContentColor = Color(0xFFFFF0D8)
                ),
                modifier = Modifier
            )

            // Cat hint panel
            CatHintPanel(
                target = level.target,
                tapCount = state.tapCount,
                elapsedSeconds = state.elapsedSeconds
            )
        }

        // Wrong tap feedback message
        val feedback = state.feedback
        if (feedback != null && !feedback.isHit) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 120.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = Color(0xFFCC3333).copy(alpha = 0.9f),
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = listOf(
                            "Not there! Keep looking...",
                            "Nope! Try another spot.",
                            "That's not the one!",
                            "Hmm, look more carefully...",
                            "Not quite! Keep searching."
                        )[state.tapCount % 5],
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
                        color = Color.White,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }

        // Found! overlay
        AnimatedVisibility(
            visible = state.found,
            enter = fadeIn(tween(300)),
            exit = fadeOut()
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Surface(
                    shape = RoundedCornerShape(24.dp),
                    color = Color(0xFF4CAF50).copy(alpha = 0.95f),
                    modifier = Modifier.padding(32.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(32.dp)
                    ) {
                        Text("Found!", style = MaterialTheme.typography.displayMedium,
                            color = Color.White)
                        Text("You found ${level.target.name}!",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.White.copy(alpha = 0.9f))
                    }
                }
            }
        }

        // Hint overlay
        if (showHintDialog) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.55f))
                    .pointerInput(Unit) { detectTapGestures { gameViewModel.dismissHint() } },
                contentAlignment = Alignment.Center
            ) {
                Surface(
                    shape = RoundedCornerShape(24.dp),
                    color = Color(0xFFFFF8F0),
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text("Hint", style = MaterialTheme.typography.headlineLarge,
                            color = Color(0xFF5C3D1E))
                        Text(level.target.clue,
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color(0xFF5C3D1E))
                        TextButton(onClick = { gameViewModel.dismissHint() }) {
                            Text("Got it!", color = Color(0xFF8B5E3C))
                        }
                    }
                }
            }
        }

        // Zoom hint (first visit)
        if (initialized && scale == minScale) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
                    .align(Alignment.BottomCenter),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "Pinch to zoom  •  Tap to guess",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White.copy(alpha = 0.75f),
                    modifier = Modifier
                        .background(Color.Black.copy(alpha = 0.35f), RoundedCornerShape(12.dp))
                        .padding(horizontal = 16.dp, vertical = 6.dp)
                )
            }
        }
    }
}
