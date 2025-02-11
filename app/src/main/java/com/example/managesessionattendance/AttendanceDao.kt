package com.example.managesessionattendance



import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao
interface AttendanceDao {
    @Insert
    suspend fun insertAttendance(attendance: Attendance)

    @Query("SELECT * FROM Attendance WHERE sessionId = :sessionId")
    fun getAttendanceForSession(sessionId: Int): Flow<List<Attendance>>

    @Query("DELETE FROM attendance WHERE id = :attendanceId")
    suspend fun deleteAttendance(attendanceId: Int)


    // Update attendance status for a specific student
    @Update
    suspend fun updateAttendance(attendance: Attendance)

    @Query("SELECT * FROM Attendance WHERE id = :attendanceId LIMIT 1")
    fun getAttendanceById(attendanceId: Int): Flow<Attendance>

}
