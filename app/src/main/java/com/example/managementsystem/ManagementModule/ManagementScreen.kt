package com.example.managementsystem.ManagementModule

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.managementsystem.Data.WorkListData
import com.example.managementsystem.R
import com.example.managementsystem.ui.theme.ManagementSystemTheme

enum class ManagementScreen(@StringRes val title: Int) {
    managementMain(title = R.string.app_name),
    ruleSet(title = R.string.rules),
    displayWork(title = R.string.displayWorkList),
    workAssign(title = R.string.workAssign),
    detailedWork(title = R.string.workDetail),
    modifyWork(title = R.string.modifyWork)
}

@Composable
fun naviagtionBar(
    goSetRulesButtonClicked: () -> Unit = {},
    goShowWorkButtonClicked: () -> Unit = {},
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
            IconButton(onClick = goShowWorkButtonClicked) {
                Icon(
                    Icons.Filled.Edit,
                    contentDescription = "Add Work"
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

    val workList = remember { mutableStateListOf(*WorkListData.workList.toTypedArray()) }
    var selectedWork by remember { mutableStateOf<WorkListData.Work?>(null) }

    val onDeleteWork: (WorkListData.Work) -> Unit = { work ->
        workList.remove(work)
    }

    Scaffold(
        bottomBar = {
            naviagtionBar(
                currentScreen = currentScreen,
                navigateUp = { navController.navigateUp() },
                goSetRulesButtonClicked = {navController.navigate(ManagementScreen.ruleSet.name)},
                goShowWorkButtonClicked = {navController.navigate(ManagementScreen.displayWork.name)}
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
                AddWorkScreen { title, id, description ->
                    val newWork = WorkListData.Work(title, id, description)
                    workList.add(newWork)
                    navController.popBackStack()
                }
            }
            composable(route = ManagementScreen.displayWork.name) {
                DisplayWorkListScreen(
                    workList = workList,
                    onNextButtonPress = {
                        navController.navigate(ManagementScreen.workAssign.name)
                    },
                    onWorkSelected = { work ->
                        selectedWork = work
                        navController.navigate(ManagementScreen.detailedWork.name)
                    },
                    onDeleteWork = onDeleteWork
                )
            }
            composable(route = ManagementScreen.detailedWork.name) {
                selectedWork?.let { work ->
                    WorkDetailScreen(
                        workDetail = WorkDetail(
                            title = work.title,
                            id = work.id,
                            description = work.description
                        ),
                        onEditClick = {
                            navController.navigate(ManagementScreen.modifyWork.name)
                        },
                        onWorkDetailsChange = {}
                    )
                }
            }
            composable(route = ManagementScreen.modifyWork.name) {
                selectedWork?.let {work ->
                    ModifyWorkScreen(
                        initialTitleField = work.title,
                        initialDescriptionField = work.description
                    ) { updatedTitle, updatedDescription ->
                        val updatedWork = WorkListData.Work(
                            title = updatedTitle,
                            id = work.id,
                            description = updatedDescription
                        )
                        val index = workList.indexOfFirst { it.id == work.id }
                        if (index != -1) {
                            workList[index] = updatedWork
                        }
                        navController.popBackStack()
                    }
                }
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