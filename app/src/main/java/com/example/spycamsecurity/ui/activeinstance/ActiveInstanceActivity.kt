package com.example.spycamsecurity.ui.activeinstance

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.example.spycamsecurity.R
import com.example.spycamsecurity.ui.SpyCamSecurityTheme
// import com.example.spycamsecurity.ui.newinstance.NewInstanceActivity

class ActiveInstanceActivity : AppCompatActivity(), ActiveInstanceContainer {
    private lateinit var logic: ActiveInstanceLogic ?????

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel = ActiveInstanceViewModel()

        setContent {
            SpyCamSecurityTheme {
                ActiveAppScreen(
                    onEventHandler = logic::onEvent,
                    viewModel
                )
            }
        }

        logic = buildActiveInstanceLogic(this, viewModel, applicationContext)
    }

    override fun onStart() {
        super.onStart()
        logic.onEvent(ActiveInstanceEvent.OnStart)
    }

    -- - -- - --
    override fun onStop() {
        super.onStop()
        logic.onEvent(ActiveGameEvent.OnStop)

        //guarantee that onRestart not called

        finish()
    }

    override fun onNewGameClick() {
        startActivity(
            Intent(
                this,
                NewGameActivity::class.java
            )
        )
    }

    override fun showError() = makeToast(getString(R.string.generic_error))
}