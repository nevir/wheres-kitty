package com.whereskitty.game

import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class TapFeedback(
    val position: Offset,
    val isHit: Boolean
)

data class GameState(
    val level: Level? = null,
    val found: Boolean = false,
    val tapCount: Int = 0,
    val elapsedSeconds: Int = 0,
    val feedback: TapFeedback? = null,
    val showHint: Boolean = false,
    val hintUsed: Boolean = false,
    val completedLevels: Set<Int> = emptySet(),
    val levelStars: Map<Int, Int> = emptyMap()
)

class GameViewModel : ViewModel() {

    private val _state = MutableStateFlow(GameState())
    val state: StateFlow<GameState> = _state.asStateFlow()

    private var timerJob: Job? = null
    private var feedbackJob: Job? = null

    fun startLevel(levelId: Int) {
        timerJob?.cancel()
        _state.update { current ->
            current.copy(
                level = LevelRepository.getLevel(levelId),
                found = false,
                tapCount = 0,
                elapsedSeconds = 0,
                feedback = null,
                showHint = false,
                hintUsed = false
            )
        }
        timerJob = viewModelScope.launch {
            while (true) {
                delay(1000)
                if (!_state.value.found) {
                    _state.update { it.copy(elapsedSeconds = it.elapsedSeconds + 1) }
                }
            }
        }
    }

    fun onTap(sceneX: Float, sceneY: Float) {
        val state = _state.value
        if (state.found || state.level == null) return

        val isHit = state.level.isHit(sceneX, sceneY)

        _state.update {
            it.copy(
                tapCount = it.tapCount + 1,
                feedback = TapFeedback(Offset(sceneX, sceneY), isHit),
                found = isHit,
                showHint = false
            )
        }

        if (isHit) {
            timerJob?.cancel()
            val stars = calculateStars(state.tapCount + 1, state.elapsedSeconds, state.hintUsed)
            _state.update {
                it.copy(
                    completedLevels = it.completedLevels + state.level.id,
                    levelStars = it.levelStars + (state.level.id to stars)
                )
            }
        } else {
            feedbackJob?.cancel()
            feedbackJob = viewModelScope.launch {
                delay(700)
                _state.update { it.copy(feedback = null) }
            }
        }
    }

    fun toggleHint() {
        _state.update { it.copy(showHint = !it.showHint, hintUsed = true) }
    }

    fun dismissHint() {
        _state.update { it.copy(showHint = false) }
    }

    private fun calculateStars(taps: Int, seconds: Int, hintUsed: Boolean): Int {
        if (hintUsed) return 1
        return when {
            taps == 1 && seconds < 30 -> 3
            taps <= 3 && seconds < 60 -> 2
            else -> 1
        }
    }
}
