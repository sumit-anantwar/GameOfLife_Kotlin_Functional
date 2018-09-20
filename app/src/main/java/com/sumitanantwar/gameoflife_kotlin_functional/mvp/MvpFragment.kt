package com.sumitanantwar.gameoflife_kotlin_functional.mvp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import butterknife.Unbinder

/**
 * Created by Sumit Anantwar on 3/18/18.
 */
@Suppress("UNCHECKED_CAST")
abstract class MvpFragment<V:MvpViewInterface, P:MvpPresenterInterface<V>> : BaseFragment() {

    // Abstract
    protected abstract val presenter    :P
    protected abstract val layoutId     :Int

    // ButterKnife Unbinder handle
    private lateinit var unbinder:Unbinder;

    // ======= Fragment Lifecycle =======
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Retain Instance so that the Fragment survives Device Rotation
        retainInstance = true
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(layoutId, container, false)

        // ButterKnife Binder
        unbinder = ButterKnife.bind(this, v)

        // Return Inflated View
        return v;
    }

    override fun onDestroy() {
        super.onDestroy()
        // ButterKnife Unbind
        unbinder.unbind()
        // Destroy the Presenter
        presenter.destroy()
    }
}