package com.sharla607062330139.asessment2.ui.screen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sharla607062330139.asessment2.database.KontakDao
import com.sharla607062330139.asessment2.model.Kontak
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel(private val dao: KontakDao) : ViewModel() {
    private val _kontak = mutableStateOf<Kontak?>(null)
    val kontak = _kontak

    fun getKontak(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val data = dao.getKontakById(id)
            _kontak.value = data
        }
    }

    fun insert(nama: String, nomorTelepon: String, gender: String) {
        val kontak = Kontak(
            nama = nama,
            nomorTelepon = nomorTelepon,
            gender = gender,
            isDeleted = false
        )

        viewModelScope.launch(Dispatchers.IO) {
            dao.insert(kontak)
        }
    }

    fun update(id: Long, nama: String, nomorTelepon: String, gender: String) {
        val kontak = Kontak(
            id = id,
            nama = nama,
            nomorTelepon = nomorTelepon,
            gender = gender,
            isDeleted = _kontak.value?.isDeleted ?: false
        )

        viewModelScope.launch(Dispatchers.IO) {
            dao.update(kontak)
        }
    }

    fun softDelete(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.softDeleteById(id)
        }
    }
}