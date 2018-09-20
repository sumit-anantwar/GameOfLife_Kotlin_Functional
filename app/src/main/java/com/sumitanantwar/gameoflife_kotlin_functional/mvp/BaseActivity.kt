package com.sumitanantwar.gameoflife_kotlin_functional.mvp

import android.support.annotation.StringRes
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import timber.log.Timber

/**
 * Created by Sumit Anantwar on 3/21/18.
 */
abstract class BaseActivity : AppCompatActivity() {


    // Helper Methods
    fun showProgress(show: Boolean) {
        Timber.i(if (show) "Progress Show" else "Progreee Hide")
    }

    fun showMessage(message: String) {
        showToast(message)
    }

    private fun showToast(@StringRes stringResourceId: Int) {
        showToast(this.getString(stringResourceId))
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}