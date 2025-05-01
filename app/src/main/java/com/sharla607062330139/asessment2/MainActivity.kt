package com.sharla607062330139.asessment2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.sharla607062330139.asessment2.navigation.SetupNavGraph
import com.sharla607062330139.asessment2.ui.theme.Asessment2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Asessment2Theme {
                SetupNavGraph()
            }
        }
    }
}
