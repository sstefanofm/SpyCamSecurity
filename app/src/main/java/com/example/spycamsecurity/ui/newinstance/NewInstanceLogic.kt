package com.example.spycamsecurity.ui.newinstance

import com.example.spycamsecurity.common.BaseLogic
import com.example.spycamsecurity.common.DispatcherProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class NewInstanceLogic(
    private val container: NewInstanceContainer?,
    private val viewModel: NewInstanceViewModel,
    private val appRepo: IGameRepository,
    private val dispatcher: DispatcherProvider
) : BaseLogic<NewInstanceEvent>(),
    CoroutineScope {

    init {
        jobTracker = Job()
    }

    override val coroutineContext: CoroutineContext
        get() = dispatcher.provideUIContext() + jobTracker

    override fun onEvent(event: NewInstanceEvent) {
        when (event) {
            is NewInstanceEvent.OnStart -> onStart()
            is NewInstanceEvent.OnDonePressed -> {
                viewModel.loadingState = true
                onDonePressed()
            }
            is NewInstanceEvent.OnUserTypeChanged -> viewModel.settingsState =
                viewModel.settingsState.copy(userType = event.userType)
        }
    }

    //write to both repos
    private fun onDonePressed() {
        launch {

            gameRepo.updateSettings(
                viewModel.settingsState,
                {
                    createNewGame(viewModel.settingsState.boundary)
                },
                {
                    container?.showError()
                }
            )
        }
    }

    private fun createNewInstance() = launch {
        appRepo.createNewInstance(viewModel.settingsState,
            {
                jobTracker.cancel()
                container?.onDoneClick()
            },
            {
                container?.showError()
                jobTracker.cancel()
                container?.onDoneClick()
            }
        )
    }

    private fun onStart() {
        launch {
            appRepo.getSettings(
                {
                    viewModel.settingsState = it
                },
                {
                    jobTracker.cancel()
                    container?.showError()
                    container?.onDoneClick()
                }
            )
        }
    }
}