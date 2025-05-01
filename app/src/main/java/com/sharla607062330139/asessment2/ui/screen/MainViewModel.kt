package com.sharla607062330139.asessment2.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sharla607062330139.asessment2.database.KontakDao
import com.sharla607062330139.asessment2.model.Kontak
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class MainViewModel(dao: KontakDao) : ViewModel() {
    val data: StateFlow<List<Kontak>> = dao.getKontak().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = emptyList()
    )

}
