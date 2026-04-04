package com.rwmobi.xlaunchericons

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(private val iconManager: IconManager) : ViewModel() {
    private val _activeIconComponent = MutableStateFlow<String?>(null)
    val activeIconComponent: StateFlow<String?> = _activeIconComponent.asStateFlow()

    init {
        refreshActiveIcon()
    }

    private fun refreshActiveIcon() {
        _activeIconComponent.value = iconManager.getActiveIconComponent()
    }

    fun setIcon(componentName: String) {
        viewModelScope.launch {
            iconManager.setIcon(componentName)
            _activeIconComponent.value = componentName
        }
    }

    companion object {
        fun provideFactory(iconManager: IconManager): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T = MainViewModel(iconManager) as T
        }
    }
}
