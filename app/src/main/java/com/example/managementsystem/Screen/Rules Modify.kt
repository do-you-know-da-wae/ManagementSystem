package com.example.managementsystem.Screen

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.managementsystem.R
import com.example.managementsystem.ui.theme.ManagementSystemTheme

enum class RuleModificationScreen(@StringRes val title: Int) {
    showRules(title = R.string.showRules),
    editScreen(title = R.string.editWorkingTime)
}

@Composable
fun RulesModifyScreen(
    navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    
    val currentScreen = RuleModificationScreen.valueOf(
        backStackEntry?.destination?.route ?: RuleModificationScreen.showRules.name
    )

    var currentTime: Int
    
    NavHost(
        navController = navController,
        startDestination = RuleModificationScreen.showRules.name
    ) {
        composable(route = RuleModificationScreen.showRules.name) {
            ShowRulesScreen(
                editTimeButtonClick = {navController.navigate(RuleModificationScreen.editScreen.name)}
            )
        }
        composable(route = RuleModificationScreen.editScreen.name) {
            EditTimeScreen()
        }
    }
}

@Composable
fun ShowRulesScreen(
    editTimeButtonClick: () -> Unit
) {
    Column(
        modifier = Modifier,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row {
            Text("Current time range: ")
        }
        Row {
            Text("9am-6pm")
        }
        Row {
            Button(onClick = editTimeButtonClick) {
                Text("Edit")
            }
        }
    }
}

@Composable
fun EditTimeScreen() {
    var startTime by remember { mutableStateOf("") }
    var endTime by remember { mutableStateOf("") }

    val startHourList = listOf("8","9","10","11","12")
    var startSelectedIndex by rememberSaveable { mutableStateOf(0) }
    val endHourList = listOf("16","17","18","19","20")
    var endSelectedIndex by rememberSaveable { mutableStateOf(0) }

    var startHour = startHourList[startSelectedIndex]
    var startMinute by remember { mutableStateOf("") }
    var endHour = endHourList[endSelectedIndex]
    var endMinute by remember { mutableStateOf("") }

    var startTimeDayStatus = when(startHour) {
        "12" -> "pm"
        else -> "am"
    }
    var endTimeDayStatus = "pm"
    var displayEndHour = endHour.toInt() - 12

    var newStartTime = "$startHour:$startMinute $startTimeDayStatus"
    var newEndTime = "$displayEndHour:$endMinute $endTimeDayStatus"

    Column(
        modifier = Modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row {
            Text("Time Setting: ")
        }
        Row {
            Text("Current Start time: $startTime")
        }
        Row {
            Text("Change hour to ")
            DropDownList(
                itemList = startHourList,
                selectedIndex = startSelectedIndex,
                modifier = Modifier.width(100.dp),
                onItemClick = {startSelectedIndex = it}
                )
        }
        Spacer(modifier = Modifier.height(140.dp))
        Row {
            EditNumberField(
                label = R.string.whatStartMinute,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                value = startMinute,
                onValueChanged = { startMinute = it },
                modifier = Modifier.padding(bottom = 32.dp).fillMaxWidth(),
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row {
            Text("Current End time: $endTime")
        }
        Row {
            Text("Change hour to ")
            DropDownList(
                itemList = endHourList,
                selectedIndex = endSelectedIndex,
                modifier = Modifier.width(100.dp),
                onItemClick = {endSelectedIndex = it}
            )
        }
        Spacer(modifier = Modifier.height(140.dp))
        Row {
            EditNumberField(
                label = R.string.whatStartMinute,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                value = endMinute,
                onValueChanged = { endMinute = it },
                modifier = Modifier.padding(bottom = 32.dp).fillMaxWidth(),
            )
        }
        Row {
            Text("New start time: $newStartTime")
        }
        Row {
            Text("New end time: $newEndTime")
        }
    }
}

@Composable
fun EditNumberField(
    @StringRes label: Int,
    keyboardOptions: KeyboardOptions,
    value: String,
    onValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = value,
        singleLine = true,
        modifier = modifier,
        onValueChange = onValueChanged,
        label = { Text(stringResource(label)) },
        keyboardOptions = keyboardOptions
    )
}

@Composable
fun DropDownList(
    itemList: List<String>,
    selectedIndex: Int,
    modifier: Modifier = Modifier,
    onItemClick: (Int) -> Unit
) {

    var showDropdown by rememberSaveable { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {

        // button
        Box(
            modifier = modifier
                .background(Color.Red)
                .clickable { showDropdown = true },
//            .clickable { showDropdown = !showDropdown },
            contentAlignment = Alignment.Center
        ) {
            Text(text = itemList[selectedIndex], modifier = Modifier.padding(3.dp))
        }

        // dropdown list
        Box() {
            if (showDropdown) {
                Popup(
                    alignment = Alignment.TopCenter,
                    properties = PopupProperties(
                        excludeFromSystemGesture = true,
                    ),
                    // to dismiss on click outside
                    onDismissRequest = { showDropdown = false }
                ) {
                    Column(
                        modifier = modifier
                            .heightIn(max = 120.dp)
                            .verticalScroll(state = scrollState)
                            .border(width = 1.dp, color = Color.Gray)
                    ) {

                        itemList.onEachIndexed { index, item ->
                            if (index != 0) {
                                Divider(thickness = 1.dp, color = Color.LightGray)
                            }
                            Box(
                                modifier = Modifier
                                    .background(Color.Green)
                                    .fillMaxWidth()
                                    .clickable {
                                        onItemClick(index)
                                        showDropdown = !showDropdown
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = item,)
                            }
                        }

                    }
                }
            }
        }
    }

}

@Preview
@Composable
fun RulesModifyPreview() {
    ManagementSystemTheme {
        RulesModifyScreen()
    }
}