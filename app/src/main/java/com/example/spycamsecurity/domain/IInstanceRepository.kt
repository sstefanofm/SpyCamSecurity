package com.example.spycamsecurity.domain

interface IInstanceRepository {
    suspend fun saveInstance(
        onSuccess: (Unit) -> Unit,
        onError: (Exception) -> Unit
    )

    suspend fun updateInstance(
        instance: CamWrapper,
        onSuccess: (Unit) -> Unit,
        onError: (Exception) -> Unit
    )

    suspend fun updateCamera(
        // things for camera
        onSuccess: (isWatching: Boolean) -> Unit,
        onError: (Exception) -> Unit
    )

    suspend fun getCurrentInstance(
        onSuccess: (currentInstance: CamWrapper, isWatching: Boolean) -> Unit,
        onError: (Exception) -> Unit
    )

    suspend fun createNewInstance(
        settings: Settings,
        onSuccess: (Unit) -> Unit,
        onError: (Exception) -> Unit
    )

    suspend fun getSettings(
        onSuccess: (Settings) -> Unit,
        onError: (Exception) -> Unit
    )

    suspend fun updateSettings(
        settings: Settings,
        onSuccess: (Unit) -> Unit,
        onError: (Exception) -> Unit
    )
}
