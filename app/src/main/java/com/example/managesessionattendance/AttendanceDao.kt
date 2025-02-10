package com.example.managesessionattendance



import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface AttendanceDao {
    @Insert
    suspend fun insertAttendance(attendance: Attendance)

    @Query("SELECT * FROM Attendance WHERE sessionId = :sessionId")
    fun getAttendanceForSession(sessionId: Int): Flow<List<Attendance>>
}
