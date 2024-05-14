package com.example.managementsystem.ManagementModule

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.example.managementsystem.Data.Work
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkDao {

    @Upsert
    suspend fun upsertWork(work: Work)

    @Delete
    suspend fun deleteWork(work: Work)

    @Update
    suspend fun updateWork(work: Work)

    @Query("SELECT * FROM Work ORDER BY workTitle ASC")
    fun getWorkListOrderByTitle(): Flow<List<Work>>
}