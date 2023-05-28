package com.example.spycamsecurity.domain

interface ISettingsRepository {
    suspend fun getSettings(
        onSuccess: (settings: Settings) -> Unit,
        onError: (Exception) -> Unit
    )

    suspend fun updateSettings(
        userType: Settings,
        onSuccess: (Unit) -> Unit,
        onError: (Exception) -> Unit
    )
}