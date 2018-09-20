package com.sumitanantwar.gameoflife_kotlin_functional.mvp

import android.support.v4.app.Fragment
import android.support.annotation.StringRes
import android.widget.Toast
import timber.log.Timber

/**
 * Created by Sumit Anantwar on 4/26/18.
 */
abstract class BaseFragment : Fragment() {

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
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}