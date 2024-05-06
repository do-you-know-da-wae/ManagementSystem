package com.example.managementsystem.Screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun modifyWorkScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Row {
            Text("This is work modification screen")
        }
        Row {
            Text("You can modify the work detail here")
        }
    }
}