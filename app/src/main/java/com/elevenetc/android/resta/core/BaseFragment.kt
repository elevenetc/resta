package com.elevenetc.android.resta.core

import androidx.fragment.app.Fragment
import com.elevenetc.android.resta.core.di.AppComponent
import io.reactivex.disposables.Disposable

open class BaseFragment : Fragment() {
    /**
     * Available after [Fragment.onAttach] called
     */
    val appComponent: AppComponent by lazy { (context!!.applicationContext as App).appComponent }

    protected var sub: Disposable? = null

    override fun onDestroyView() {
        sub?.dispose()
        super.onDestroyView()
    }
}