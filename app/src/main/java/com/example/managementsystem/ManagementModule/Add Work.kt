package com.example.managementsystem.ManagementModule

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun addWorkScreen(
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Row {
            Text("This is add work screen")
        }
    }
}