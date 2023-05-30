package com.example.spycamsecurity.persistence

import com.example.spycamsecurity.domain.CamWrapper
import com.example.spycamsecurity.domain.IInstanceDataStorage
import com.example.spycamsecurity.domain.InstanceStorageResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.*

private const val FILE_NAME = "instance_state.txt"

class LocalInstanceStorageImpl(
    fileStorageDirectory: String,
    private val pathToStorageFile: File = File(fileStorageDirectory, FILE_NAME)
) : IInstanceDataStorage {

    override suspend fun updateInstance(
        instance: CamWrapper
    ): InstanceStorageResult = withContext(Dispatchers.IO) {
        try {
            updateInstanceData(instance)
            InstanceStorageResult.OnSuccess(instance)
        } catch (e: Exception) {
            InstanceStorageResult.OnError(e)
        }
    }

    private fun updateInstanceData(instance: CamWrapper) {
        val fileOutputStream = FileOutputStream(pathToStorageFile)
        val objectOutputStream = ObjectOutputStream(fileOutputStream)
        objectOutputStream.writeObject(instance)
        objectOutputStream.close()
    }

    override suspend fun updateCamera(
        url: String
    ): InstanceStorageResult = withContext(Dispatchers.IO) {
        try {
            val instance = getInstance()
            instance.url = url
            updateInstanceData(instance)
            InstanceStorageResult.OnSuccess(instance)
        } catch (e: Exception) {
            InstanceStorageResult.OnError(e)
        }
    }

    override suspend fun getCurrentInstance(): InstanceStorageResult = withContext(Dispatchers.IO) {
        try {
            InstanceStorageResult.OnSuccess(getInstance())
        } catch (e: Exception) {
            InstanceStorageResult.OnError(e)
        }
    }

    private fun getInstance(): CamWrapper {
        var instance: CamWrapper

        val fileInputStream = FileInputStream(pathToStorageFile)
        val objectInputStream = ObjectInputStream(fileInputStream)
        instance = objectInputStream.readObject() as CamWrapper
        objectInputStream.close()

        return (instance)
    }
}