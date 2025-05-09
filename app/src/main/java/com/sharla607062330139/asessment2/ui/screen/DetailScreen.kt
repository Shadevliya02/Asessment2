package com.sharla607062330139.asessment2.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.sharla607062330139.asessment2.util.ViewModelFactory
import com.sharla607062330139.asessment2.R
import com.sharla607062330139.asessment2.database.KontakDb
import com.sharla607062330139.asessment2.ui.theme.Asessment2Theme

const val KEY_ID_KONTAK = "idKontak"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(navController: NavHostController, id: Long? = null) {
    val context = LocalContext.current
    val db = KontakDb.getInstance(context)
    val factory = ViewModelFactory(db.dao)
    val viewModel: DetailViewModel = viewModel(factory = factory)

    val kontak by viewModel.kontak

    var nama by remember { mutableStateOf("") }
    var nomorTelepon by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("She/Her") }
    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(id) {
        if (id != null) {
            viewModel.getKontak(id)
        }
    }

    LaunchedEffect(kontak) {
        kontak?.let {
            nama = it.nama
            nomorTelepon = it.nomorTelepon
            gender = it.gender
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.kembali),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                title = {
                    if (id == null)
                        Text(text = stringResource(id = R.string.tambah_catatan))
                    else
                        Text(text = stringResource(id = R.string.edit_catatan))
                },
                actions = {
                    IconButton(onClick = {
                        if (nama == "" || nomorTelepon == "") {
                            Toast.makeText(context, R.string.invalid, Toast.LENGTH_LONG).show()
                            return@IconButton
                        }
                        if (id == null) {
                            viewModel.insert(nama, nomorTelepon, gender)
                        } else {
                            viewModel.update(id, nama, nomorTelepon, gender)
                        }
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.Check,
                            contentDescription = stringResource(R.string.simpan),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    if (id != null) {
                        DeleteAction {
                            showDialog = true
                        }
                    }
                }
            )
        }
    ) { padding ->
        FormKontak(
            nama = nama,
            onNamaChange = { nama = it },
            nomorTelepon = nomorTelepon,
            onNomorTeleponChange = { nomorTelepon = it },
            gender = gender,
            onGenderChange = { gender = it },
            modifier = Modifier.padding(padding)
        )
        if (id!= null && showDialog){
            DisplayAlertDialog(
                onDismissRequest = {showDialog=false}) {
                showDialog=false
                viewModel.softDelete(id)
                navController.popBackStack()
            }
        }
    }
}

@Composable
fun DeleteAction(delete: () -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    IconButton(onClick = { expanded = true }) {
        Icon(
            imageVector = Icons.Filled.MoreVert,
            contentDescription = stringResource(R.string.lainnya),
            tint = MaterialTheme.colorScheme.primary
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text(text = stringResource(id = R.string.hapus)) },
                onClick = {
                    expanded = false
                    delete()
                }
            )
        }
    }
}

@Composable
fun FormKontak(
    nama: String, onNamaChange: (String) -> Unit,
    nomorTelepon: String, onNomorTeleponChange: (String) -> Unit,
    gender: String, onGenderChange: (String) -> Unit,
    modifier: Modifier
) {
    val genderList = listOf("She/Her", "He/Him", "They/Them")

    Column(
        modifier = modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = nama,
            onValueChange = { onNamaChange(it) },
            label = { Text(text = "Name") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = nomorTelepon,
            onValueChange = { onNomorTeleponChange(it) },
            label = { Text(text = "Phone Number") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Phone,
                capitalization = KeyboardCapitalization.Sentences
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Surface (
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.small,
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
            color = MaterialTheme.colorScheme.surface
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                genderList.forEach { genderItem ->
                    Row (
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onGenderChange(genderItem) }
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (gender == genderItem),
                            onClick = { onGenderChange(genderItem) }
                        )
                        Text(
                            text = genderItem,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun DetailScreenPreview() {
    Asessment2Theme {
        DetailScreen(rememberNavController())
    }
}