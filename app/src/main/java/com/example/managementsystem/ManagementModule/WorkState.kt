package com.example.managementsystem.ManagementModule

import com.example.managementsystem.Data.Work

data class WorkState(
    val workList: List<Work> = emptyList(),
    val workTitle: String = "",
    val workDescription: String = "",
    val isAddingWork: Boolean = false,
    val sortType: SortType = SortType.WORK_TITLE
)
