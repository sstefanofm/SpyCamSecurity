package com.example.spycamsecurity.ui.newinstance

import com.example.spycamsecurity.domain.UserType

sealed class NewInstanceEvent {
    object OnStart: NewInstanceEvent()
    data class OnUserTypeChanged(val userType: UserType): NewInstanceEvent()
    object OnDonePressed: NewInstanceEvent()
}