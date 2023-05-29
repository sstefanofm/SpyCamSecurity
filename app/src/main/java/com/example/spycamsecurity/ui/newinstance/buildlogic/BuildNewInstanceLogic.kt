package com.example.spycamsecurity.ui.newinstance.buildlogic

import android.content.Context
import com.example.spycamsecurity.common.ProductionDispatcherProvider
import com.example.spycamsecurity.persistence.*
import com.example.spycamsecurity.ui.newinstance.NewInstanceContainer
import com.example.spycamsecurity.ui.newinstance.NewInstanceLogic
import com.example.spycamsecurity.ui.newinstance.NewInstanceViewModel

internal fun buildNewInstanceLogic(
    container: NewInstanceContainer,
    viewModel: NewInstanceViewModel,
    context: Context
): NewInstanceLogic {
    return NewInstanceLogic(
        container,
        viewModel,
        AppRepositoryImpl(
            LocalAppRepositoryImpl(context.filesDir.path),
            LocalSettingsStorageImpl(context.settingsDataStore)
        ),
        ProductionDispatcherProvider
    )
}