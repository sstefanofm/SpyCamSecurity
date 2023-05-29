package com.example.spycamsecurity.domain

interface IInstanceDataStorage {
    suspend fun updateInstance(instance: CamWrapper): InstanceStorageResult
    suspend fun updateCamera(url: String): InstanceStorageResult
    suspend fun getCurrentInstance(): InstanceStorageResult
}

sealed class InstanceStorageResult {
    data class OnSuccess(val currentInstance: CamWrapper): InstanceStorageResult()
    data class OnError(val exception: Exception): InstanceStorageResult()
}
