package com.example.spycamsecurity.domain

interface ISettingsDataStorage {
    suspend fun getSettings(): SettingsStorageResult
    suspend fun updateSettings(settings: Settings): SettingsStorageResult
}

/* restricted set of types with values;
    return object from particular function of ISettingsDataStorage;
    object capable of representing multiple different states:
        onSuccess
        onError
 */
sealed class SettingsStorageResult {
    data class OnSuccess(val settings: Settings): SettingsStorageResult()
    data class OnError(val exception: Exception): SettingsStorageResult()
}