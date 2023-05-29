package com.example.spycamsecurity.persistence

import android.util.Log
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import com.example.spycamsecurity.AppSettings
import com.example.spycamsecurity.domain.ISettingsStorage
import com.example.spycamsecurity.domain.Settings
import com.example.spycamsecurity.domain.SettingsStorageResult
import com.example.spycamsecurity.domain.UserType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.google.protobuf.InvalidProtocolBufferException
import kotlinx.coroutines.flow.first
import java.io.InputStream
import java.io.OutputStream

/* AppSettings is a class created for Proto Datastore */
class LocalSettingsStorageImpl(
    private val dataStore: DataStore<AppSettings>
) : ISettingsStorage {
    override suspend fun getSettings(): SettingsStorageResult =
        withContext(Dispatchers.IO) {
            try {
                val appSettings = dataStore.data.first()
                SettingsStorageResult.OnSuccess(appSettings.toSettings)
            } catch (e: Exception) {
                SettingsStorageResult.OnError(e)
            }
        }

    override suspend fun updateSettings(settings: Settings): SettingsStorageResult =
        withContext(Dispatchers.IO) {
            try {
                dataStore.updateData { appSettings ->
                    appSettings.toBuilder()
                        .setUserType(settings.userType.toProto)
                        .build()
                }

                SettingsStorageResult.OnComplete

            } catch (e: Exception) {
                SettingsStorageResult.OnError(e)
            }
        }
}

private val AppSettings.toSettings: Settings
    get() = Settings(
        this.userType.toDomain
    )

private val AppSettings.ProtoUserType.toDomain: UserType
    get() = when (this.number) {
        0 -> UserType.SERVER
        1 -> UserType.CLIENT
        else -> UserType.CLIENT
    }

private val UserType.toProto: AppSettings.ProtoUserType
    get() =  when (this) {
        UserType.SERVER -> AppSettings.ProtoUserType.SERVER
        UserType.CLIENT -> AppSettings.ProtoUserType.CLIENT
    }