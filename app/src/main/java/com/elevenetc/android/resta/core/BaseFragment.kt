package com.elevenetc.android.resta.core

import androidx.fragment.app.Fragment
import com.elevenetc.android.resta.App
import com.elevenetc.android.resta.core.di.AppComponent
import io.reactivex.disposables.CompositeDisposable

open class BaseFragment : Fragment() {
    /**
     * Available after [Fragment.onAttach] called
     */
    val appComponent: AppComponent by lazy { (context!!.applicationContext as App).appComponent }

    protected var subs = CompositeDisposable()

    override fun onDestroyView() {
        subs.dispose()
        super.onDestroyView()
    }

    fun showRetry() {

    }
}