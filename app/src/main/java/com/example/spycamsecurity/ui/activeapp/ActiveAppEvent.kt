package com.example.spycamsecurity.ui.activeapp

sealed class ActiveAppEvent {
    data class OnInput(val input: Int) : ActiveAppEvent()
    data class OnTileFocused(val x: Int, val y: Int) : ActiveAppEvent()
    object OnNewAppClicked : ActiveAppEvent()
    object OnStart : ActiveAppEvent()
    object OnStop : ActiveAppEvent()
}