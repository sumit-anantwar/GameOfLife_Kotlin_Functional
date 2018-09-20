package com.sumitanantwar.gameoflife_kotlin_functional.ui.activities.main

import android.os.Bundle
import android.os.Handler
import android.widget.Button
import butterknife.BindView
import butterknife.OnClick
import com.sumitanantwar.gameoflife_kotlin_functional.R
import com.sumitanantwar.gameoflife_kotlin_functional.mvp.MvpActivity
import com.sumitanantwar.gameoflife_kotlin_functional.ui.views.WorldView
import com.sumitanantwar.gameoflife_kotlin_functional.utils.disable
import com.sumitanantwar.gameoflife_kotlin_functional.utils.enable
import java.util.*
import kotlin.concurrent.timer

class MainActivity : MvpActivity<MainContract.View, MainContract.Presenter>(), MainContract.View {

    // ButterKnife binders
    @BindView(R.id.world_view)
    lateinit var worldView: WorldView

    @BindView(R.id.play_button)
    lateinit var playButton: Button

    @BindView(R.id.reset_button)
    lateinit var resetButton: Button

    @BindView(R.id.glider_button)
    lateinit var gliderButton: Button

    // Presenter
    override lateinit var presenter: MainPresenter

    // Timer
    lateinit var timer: Timer

    //======= Activity Lifecycle =======
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize the Presenter
        presenter = MainPresenter(this)
    }

    override fun onPause() {
        super.onPause()
        // Pause the UI when the App goes into background
        shouldPause()
    }

    //======= Timers =======
    private fun startTimer() {

        val handler = Handler()

        timer = timer("timer", false, 0, 200) {
            handler.post { worldView.nextStep() }
        }
    }

    private fun stopTimer() {
        timer.cancel()
    }

    //======= OnClick Listeners =======
    @OnClick(R.id.play_button)
    fun onPlayButtonClick() {
        presenter.play()
    }

    @OnClick(R.id.reset_button)
    fun onResetButtonClick() {
        presenter.reset()
    }

    @OnClick(R.id.glider_button)
    fun onGliderButtonClick() {
        presenter.resetForGlider()
    }


    override fun shouldPlay() {
        playButton.setText(R.string.awesome_pause)
        gliderButton.disable()
        resetButton.disable()

        // Start the Timer
        startTimer()
    }

    override fun shouldPause() {
        playButton.setText(R.string.awesome_play)
        gliderButton.enable()
        resetButton.enable()

        // Stop the Timer
        stopTimer()
    }

    override fun reset(forGlider: Boolean) {
        worldView.reset(forGlider)
    }
}
