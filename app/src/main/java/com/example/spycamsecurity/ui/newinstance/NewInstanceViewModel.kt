package com.example.spycamsecurity.ui.newinstance

import com.example.spycamsecurity.domain.Settings

class NewInstanceViewModel {
    //values don't change while this feature is active, so there's no need for pub/sub
    internal lateinit var settingsState: Settings
    internal var loadingState: Boolean = true
        set(value) {
            field = value
            subLoadingState?.invoke(field)
        }

    internal var subLoadingState: ((Boolean) -> Unit)? = null
}