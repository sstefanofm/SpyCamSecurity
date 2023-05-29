package com.example.spycamsecurity.ui.activeinstance

sealed class ActiveInstanceEvent {
    object OnNewInstanceClicked : ActiveInstanceEvent()
    object OnStart : ActiveInstanceEvent()
    object OnStop : ActiveInstanceEvent()
}