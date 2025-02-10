package com.example.managesessionattendance

import kotlinx.coroutines.flow.Flow


class AppRepository(private val sessionDao: SessionDao, private val attendanceDao: AttendanceDao) {
    val allSessions: Flow<List<Session>> = sessionDao.getAllSessions()

    suspend fun insertSession(session: Session) {
        sessionDao.insertSession(session)
    }

    suspend fun deleteSession(session: Session) {
        sessionDao.deleteSession(session)
    }

    fun getAttendance(sessionId: Int) = attendanceDao.getAttendanceForSession(sessionId)

    suspend fun insertAttendance(attendance: Attendance) {
        attendanceDao.insertAttendance(attendance)
    }
}
