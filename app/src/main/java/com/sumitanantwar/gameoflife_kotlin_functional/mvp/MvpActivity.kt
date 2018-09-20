package com.sumitanantwar.gameoflife_kotlin_functional.mvp

import android.os.Bundle
import butterknife.ButterKnife
import butterknife.Unbinder
import com.sumitanantwar.gameoflife_kotlin_functional.mvp.BaseActivity

/**
 * Created by Sumit Anantwar on 3/14/18.
 */
@Suppress("UNCHECKED_CAST")
abstract class MvpActivity<V:MvpViewInterface, P:MvpPresenterInterface<V>> : BaseActivity() {

    protected abstract val presenter: P

    // ButterKnife Unbinder handle
    private lateinit var unbinder: Unbinder;

    // ======= Activity Lifecycle =======
    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)

        unbinder = ButterKnife.bind(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        // ButterKnife Unbind
        unbinder.unbind()
        // Destroy the Presenter
        presenter.destroy()
    }
}