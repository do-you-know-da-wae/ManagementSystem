package com.example.managementsystem.ManagementModule

import com.example.managementsystem.Data.Work

sealed interface WorkEvent {
    object SaveWork:WorkEvent
    data class SetTitle(val title: String): WorkEvent
    data class SetDescription(val description: String): WorkEvent
    object ShowDialog: WorkEvent
    object HideDialog: WorkEvent
    data class SortWork(val sortType: SortType): WorkEvent
    data class DeleteWork(val work: Work): WorkEvent
}