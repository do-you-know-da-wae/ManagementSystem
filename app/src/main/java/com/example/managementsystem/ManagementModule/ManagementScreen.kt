package com.example.managementsystem.ManagementModule

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.managementsystem.R
import com.example.managementsystem.ui.theme.ManagementSystemTheme

enum class ManagementScreen(@StringRes val title: Int) {
    managementMain(title = R.string.app_name),
    ruleSet(title = R.string.rules),
    workModify(title = R.string.workModify),
    workAssign(title = R.string.workAssign)
}

@Composable
fun naviagtionBar(
    goSetRulesButtonClicked: () -> Unit = {},
    goAddWorkButtonClicked: () -> Unit = {},
    goModifyWorkButtonClicked: () -> Unit = {},
    currentScreen: ManagementScreen,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    BottomAppBar(
        modifier = modifier
            .wrapContentSize(Alignment.Center)
            .fillMaxWidth(),
        actions = {
            IconButton(onClick = goSetRulesButtonClicked) {
                Icon(
                    Icons.Outlined.DateRange,
                    contentDescription = "Rule Set"
                )
            }
            IconButton(onClick = goAddWorkButtonClicked) {
                Icon(
                    Icons.Filled.Add,
                    contentDescription = "Add Work"
                )
            }
            IconButton(onClick = goModifyWorkButtonClicked) {
                Icon(
                    Icons.Filled.Edit,
                    contentDescription = "Edit Work"
                )
            }
        }
    )
}

@Composable
fun ManagementApp(
    navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()

    val currentScreen = ManagementScreen.valueOf(
        backStackEntry?.destination?.route ?: ManagementScreen.managementMain.name
    )

    Scaffold(
        bottomBar = {
            naviagtionBar(
                currentScreen = currentScreen,
                navigateUp = { navController.navigateUp() },
                goSetRulesButtonClicked = {navController.navigate(ManagementScreen.ruleSet.name)},
                goAddWorkButtonClicked = {navController.navigate(ManagementScreen.workAssign.name)},
                goModifyWorkButtonClicked = {navController.navigate(ManagementScreen.workModify.name)}
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = ManagementScreen.managementMain.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = ManagementScreen.managementMain.name) {
                MainScreen()
            }
            composable(route = ManagementScreen.ruleSet.name) {
                ShowRulesScreen()
            }
            composable(route = ManagementScreen.workAssign.name) {
                addWorkScreen()
            }
            composable(route = ManagementScreen.workModify.name) {
                modifyWorkScreen()
            }
        }
    }


}

@Preview
@Composable
fun managementSystemPreview(){
    ManagementSystemTheme{
        ManagementApp()
    }
}