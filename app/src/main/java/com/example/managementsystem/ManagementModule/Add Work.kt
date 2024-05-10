package com.example.managementsystem.ManagementModule

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.managementsystem.Data.WorkListData

@Composable
fun AddWorkScreen(
    onAddButtonClick: (String, String, String) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    val nextId = remember {
        mutableStateOf(
            calculateNextId(WorkListData.workList.map { it.id })
        )
    }

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Row(modifier = Modifier.padding(vertical = 8.dp)) {
            Text(text = "ID : E${nextId.value.toString().padStart(3, '0')}",
                fontSize = 18.sp
            ) // Display the next available ID
        }

        Row(modifier = Modifier.padding(vertical = 8.dp)) {
            Text(text = "Name:")
            Spacer(modifier = Modifier.width(16.dp))
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                modifier = Modifier.weight(1f)
            )
        }

        Row(modifier = Modifier.padding(vertical = 8.dp)) {
            Text(text = "Email:")
            Spacer(modifier = Modifier.width(16.dp))
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                modifier = Modifier.weight(1f)
            )
        }
        Button(
            onClick = {
                val newId = "E${nextId.value.toString().padStart(3, '0')}"
                val newEmployee = WorkListData.addWork(title, newId, description) // Add the new employee
                onAddButtonClick(title, newId, description) // Pass data to callback (optional)
                // Potentially trigger UI update in EmployeeListScreen (explained later)
                title = "" // Clear fields after adding employee
                description = ""
                nextId.value++ // Increment the next available ID for the next employee
            },
            modifier = Modifier.padding(vertical = 16.dp)
        ) {
            Text("Add Employee")
        }
    }
}

fun calculateNextId(usedIds: List<String>): Int {
    val maxId = usedIds.mapNotNull { it.removePrefix("E").toIntOrNull() }.maxOrNull() ?: 0
    return maxId + 1
}