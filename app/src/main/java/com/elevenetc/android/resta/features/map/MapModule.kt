package com.elevenetc.android.resta.features.map

import androidx.lifecycle.ViewModelProvider
import com.elevenetc.android.resta.features.map.vm.ViewModel
import com.elevenetc.android.resta.features.map.vm.ViewModelFactory
import com.elevenetc.android.resta.features.map.vm.ViewModelImpl
import dagger.Module
import dagger.Provides

@Module
class MapModule {
    @Provides
    fun viewModel(inst: ViewModelImpl): ViewModel = inst

    @Provides
    fun viewModelFactory(vm: ViewModel): ViewModelFactory {
        return ViewModelFactory(vm)
    }
}

