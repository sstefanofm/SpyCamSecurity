package com.example.spycamsecurity.persistence

import androidx.datastore.core.DataStore
import com.example.spycamsecurity.AppSettings
import com.example.spycamsecurity.domain.Settings
import com.example.spycamsecurity.domain.ISettingsStorage
import com.example.spycamsecurity.domain.SettingsStorageResult
import com.example.spycamsecurity.domain.UserType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

/**
 * Note: AppSettings is a class generated for Proto Datastore
 */
class LocalSettingsStorageImpl(
    private val dataStore: DataStore<AppSettings>
) : ISettingsStorage {
    override suspend fun getSettings(): SettingsStorageResult =
        withContext(Dispatchers.IO) {
            try {
                val gameSettings = dataStore.data.first()
                SettingsStorageResult.OnSuccess(gameSettings.toSettings)
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
    get() = when (this) {
        UserType.SERVER -> AppSettings.ProtoUserType.SERVER
        UserType.CLIENT -> AppSettings.ProtoUserType.CLIENT
    }
