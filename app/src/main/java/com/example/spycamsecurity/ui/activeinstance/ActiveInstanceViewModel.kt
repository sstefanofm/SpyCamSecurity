package com.example.spycamsecurity.ui.activeinstance

import com.example.spycamsecurity.domain.UserType

class ActiveInstanceViewModel {
    internal var subTimerState: ((Long) -> Unit)? = null

    internal fun updateTimerState() {
        timerState++
        subTimerState?.invoke(timerState)
    }

    internal var subIsWatchingState: ((Boolean) -> Unit)? = null

    internal var timerState: Long = 0L

    internal val userType = UserType.CLIENT

    internal var isWatchingState: Boolean = true

    fun initializeCamState(isWatching: Boolean) {
        val contentState: ActiveAppScreenState
    }
}