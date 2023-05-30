package com.example.spycamsecurity.persistence

// import com.bracketcove.graphsudoku.computationlogic.puzzleIsComplete
import com.example.spycamsecurity.domain.*

class InstanceRepositoryImpl(
    private val instanceStorage: IInstanceDataStorage,
    private val settingsStorage: ISettingsStorage
) : IInstanceRepository {

    override suspend fun saveInstance(
        onSuccess: (Unit) -> Unit,
        onError: (Exception) -> Unit
    ) {
        when (val getCurrentInstanceResult = instanceStorage.getCurrentInstance()) {

            is InstanceStorageResult.OnSuccess -> {
                instanceStorage.updateInstance(
                    getCurrentInstanceResult.currentInstance.copy()
                )

                onSuccess.invoke(Unit)
            }

            is InstanceStorageResult.OnError -> {
                onError.invoke(getCurrentInstanceResult.exception)
            }
        }
    }

    override suspend fun updateInstance(
        camInstance: CamWrapper,
        onSuccess: (Unit) -> Unit,
        onError: (Exception) -> Unit
    ) {
        when (val updateInstanceResult: InstanceStorageResult = instanceStorage.updateInstance(camInstance)) {

            is InstanceStorageResult.OnSuccess -> onSuccess(Unit)

            is InstanceStorageResult.OnError -> onError(updateInstanceResult.exception)
        }
    }

    override suspend fun updateCamera(
        onSuccess: (isWatching: Boolean) -> Unit,
        onError: (Exception) -> Unit
    ) {
        when (val result = instanceStorage.updateCamera("url string")) {

            is InstanceStorageResult.OnSuccess -> onSuccess(
                true
            )

            is InstanceStorageResult.OnError -> onError(
                result.exception
            )
        }
    }

    override suspend fun getCurrentInstance(
        onSuccess: (currentInstance: CamWrapper, isWatching: Boolean) -> Unit,
        onError: (Exception) -> Unit
    ) {
        when (val getCurrentInstanceResult = instanceStorage.getCurrentInstance()) {

            is InstanceStorageResult.OnSuccess -> onSuccess(
                getCurrentInstanceResult.currentInstance, true
            )

            is InstanceStorageResult.OnError -> {
                when (val getSettingsResult = settingsStorage.getSettings()) {

                    is SettingsStorageResult.OnSuccess -> {
                        when (val updateInstanceResult =
                            createAndWriteNewInstance(getSettingsResult.settings)) {

                            is InstanceStorageResult.OnSuccess -> onSuccess(
                                updateInstanceResult.currentInstance, true
                            )

                            is InstanceStorageResult.OnError -> onError(updateInstanceResult.exception)
                        }
                    }

                    is SettingsStorageResult.OnError -> onError(getSettingsResult.exception)
                }
            }
        }
    }

    override suspend fun createNewInstance(
        settings: Settings,
        onSuccess: (Unit) -> Unit,
        onError: (Exception) -> Unit
    ) {
        when (val updateSettingsResult = settingsStorage.updateSettings(settings)) {
            is SettingsStorageResult.OnComplete -> {
                when (val updateInstanceResult = createAndWriteNewInstance(settings)) {
                    is InstanceStorageResult.OnSuccess -> onSuccess()
                    is InstanceStorageResult.OnError -> onError(updateInstanceResult.exception)
                }
            }
            is SettingsStorageResult.OnError -> onError(updateSettingsResult.exception)
        }
    }

    private suspend fun createAndWriteNewInstance(settings: Settings): InstanceStorageResult {
        return instanceStorage.updateInstance(
            CamWrapper(
                "url default string",
                settings.userType
            )
        )
    }

    override suspend fun getSettings(
        onSuccess: (Settings) -> Unit,
        onError: (Exception) -> Unit
    ) {
        when (val getSettingsResult = settingsStorage.getSettings()) {
            is SettingsStorageResult.OnSuccess -> onSuccess(getSettingsResult.settings)
            is SettingsStorageResult.OnError -> onError(getSettingsResult.exception)
        }
    }

    override suspend fun updateSettings(
        settings: Settings,
        onSuccess: (Unit) -> Unit,
        onError: (Exception) -> Unit
    ) {
        settingsStorage.updateSettings(settings)
        onSuccess(Unit)
    }
}