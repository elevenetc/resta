package com.elevenetc.android.resta.features.rests

import androidx.lifecycle.ViewModelProvider
import com.elevenetc.android.resta.features.rests.vm.ViewModel
import com.elevenetc.android.resta.features.rests.vm.ViewModelImpl
import dagger.Module
import dagger.Provides

@Module
class RestsModule {
    @Provides
    fun viewModel(inst: ViewModelImpl): ViewModel = inst

    @Provides
    fun viewModelFactory(vm: ViewModel): ViewModelFactory {
        return ViewModelFactory(vm)
    }

    @Suppress("UNCHECKED_CAST")
    class ViewModelFactory constructor(val vm: ViewModel) : ViewModelProvider.Factory {
        override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
            return vm as T
        }
    }
}

