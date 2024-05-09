package com.example.managementsystem.ManagementModule

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.managementsystem.ui.theme.ManagementSystemTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowRulesScreen() {
    var startTime by remember { mutableStateOf("08:30AM") }
    var endTime by remember { mutableStateOf("5:30PM") }

    val timePickerState = rememberTimePickerState()

    var showStartTimePicker by remember { mutableStateOf(false) }
    var showEndTimePicker by remember { mutableStateOf(false) }
    var progress by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row {
            Text("Current time range: ")
        }
        Row {
            Text("$startTime-$endTime")
        }
        Row {
            Button(onClick = {
                showStartTimePicker = true
            }) {
                Text("Set Start Time")
            }
            Button(onClick = {
                showEndTimePicker = true
            }) {
                Text("Set End Time")
            }
        }
    }

    if (showStartTimePicker) {
        TimePickerDialog(
            onDismissRequest = {  },
            confirmButton = {
                TextButton(
                    onClick = {
                        var hour = timePickerState.hour
                        var minute = timePickerState.minute
                        var am_pm = ""
                        var hour_string = ""
                        var minute_string = ""
                        when {hour == 0 -> { hour += 12
                            am_pm = "AM"
                        }
                            hour == 12 -> am_pm = "PM"
                            hour > 12 -> { hour -= 12
                                am_pm = "PM"
                            }
                            else -> am_pm = "AM"
                        }
                        if (hour < 10) {
                            hour_string = "0$hour"
                        } else {
                            hour_string = "$hour"
                        }
                        if (minute < 10) {
                            minute_string = "0$minute"
                        } else {
                            minute_string = "$minute"
                        }
                        startTime = "$hour_string:$minute_string $am_pm"
                        showStartTimePicker = false
                    }) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showStartTimePicker = false
                    }
                ) { Text("Cancel") }
            }
        )
        {
            TimePicker(
                state = timePickerState
            )
        }
    }
    if(showEndTimePicker) {
        TimePickerDialog(
            onDismissRequest = {  },
            confirmButton = {
                TextButton(
                    onClick = {
                        var hour = timePickerState.hour
                        var minute = timePickerState.minute
                        var am_pm = ""
                        var hour_string = ""
                        var minute_string = ""
                        when {hour == 0 -> { hour += 12
                            am_pm = "AM"
                        }
                            hour == 12 -> am_pm = "PM"
                            hour > 12 -> { hour -= 12
                                am_pm = "PM"
                            }
                            else -> am_pm = "AM"
                        }
                        if (hour < 10) {
                            hour_string = "0$hour"
                        } else {
                            hour_string = "$hour"
                        }
                        if (minute < 10) {
                            minute_string = "0$minute"
                        } else {
                            minute_string = "$minute"
                        }
                        endTime = "$hour_string:$minute_string $am_pm"
                        showEndTimePicker = false
                    }) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showEndTimePicker = false
                    }
                ) { Text("Cancel") }
            }
        )
        {
            TimePicker(
                state = timePickerState
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTime(): String {
    var time by remember { mutableStateOf("08:30AM") }

    val timePickerState = rememberTimePickerState()
    TimePickerDialog(
        onDismissRequest = {  },
        confirmButton = {
            TextButton(
                onClick = {
                    var hour = timePickerState.hour
                    var minute = timePickerState.minute
                    var am_pm = ""
                    var hour_string = ""
                    var minute_string = ""
                    when {hour == 0 -> { hour += 12
                        am_pm = "AM"
                    }
                        hour == 12 -> am_pm = "PM"
                        hour > 12 -> { hour -= 12
                            am_pm = "PM"
                        }
                        else -> am_pm = "AM"
                    }
                    if (hour < 10) {
                        hour_string = "0$hour"
                    } else {
                        hour_string = "$hour"
                    }
                    if (minute < 10) {
                        minute_string = "0$minute"
                    } else {
                        minute_string = "$minute"
                    }
                    time = "$hour_string:$minute_string $am_pm"
                }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                }
            ) { Text("Cancel") }
        }
    )
    {
        TimePicker(
            state = timePickerState
        )
    }

    return time
}

@Composable
fun TimePickerDialog(
    title: String = "Select Time",
    onDismissRequest: () -> Unit,
    confirmButton: @Composable (() -> Unit),
    dismissButton: @Composable (() -> Unit)? = null,
    containerColor: androidx.compose.ui.graphics.Color = MaterialTheme.colorScheme.surface,
    content: @Composable () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        ),
    ) {
        Surface(
            shape = MaterialTheme.shapes.extraLarge,
            tonalElevation = 6.dp,
            modifier = Modifier
                .width(IntrinsicSize.Min)
                .height(IntrinsicSize.Min)
                .background(
                    shape = MaterialTheme.shapes.extraLarge,
                    color = containerColor
                ),
            color = containerColor
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp),
                    text = title,
                    style = MaterialTheme.typography.labelMedium
                )
                content()
                Row(
                    modifier = Modifier
                        .height(40.dp)
                        .fillMaxWidth()
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    dismissButton?.invoke()
                    confirmButton()
                }
            }
        }
    }
}



@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun RulesModifyPreview() {
    ManagementSystemTheme {
        ShowRulesScreen()
    }
}