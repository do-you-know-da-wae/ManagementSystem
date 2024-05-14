package com.example.managementsystem

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.managementsystem.Data.WorkDatabase
import com.example.managementsystem.ManagementModule.ManagementApp
import com.example.managementsystem.ManagementModule.WorkViewModel
import com.example.managementsystem.ui.theme.ManagementSystemTheme

open class MainActivity : ComponentActivity() {
    private val workdb by lazy {
        Room.databaseBuilder(
            applicationContext,
            WorkDatabase::class.java,
            "workList.db"
        ).build()
    }

    private val workViewModel by viewModels<WorkViewModel> (
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create (modelClass: Class<T>): T {
                    return WorkViewModel(workdb.dao) as T
                }
            }
        }
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ManagementSystemTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val workListState by workViewModel.state.collectAsState()
                    //ManagementScreenTest(state = workListState, onEvent = workViewModel::onEvent)
                    ManagementApp(state = workListState, onEvent = workViewModel::onEvent)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ManagementSystemTheme {
    }
}