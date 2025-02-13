package com.example.managesessionattendance

import kotlinx.coroutines.flow.Flow


class AppRepository(private val sessionDao: SessionDao, private val attendanceDao: AttendanceDao) {
    val allSessions: Flow<List<Session>> = sessionDao.getAllSessions()


    suspend fun insertSession(session: Session) {
        sessionDao.insertSession(session)
    }

    fun searchSessions(query: String): Flow<List<Session>> {
        return sessionDao.searchSessions(query)
    }

    suspend fun updateSession(session: Session) {
        sessionDao.updateSession(session)
    }

    suspend fun deleteSession(session: Session) {
        sessionDao.deleteSession(session)
    }



    // Get attendance for a session
    fun getAttendance(sessionId: Int) = attendanceDao.getAttendanceForSession(sessionId)

    // Insert attendance
    suspend fun insertAttendance(attendance: Attendance) {
        attendanceDao.insertAttendance(attendance)
    }
    fun getAttendanceById(attendanceId: Int): Flow<Attendance> {
        return attendanceDao.getAttendanceById(attendanceId)
    }


    // Update attendance
    suspend fun updateAttendance(attendance: Attendance) {
        attendanceDao.updateAttendance(attendance)
    }

    suspend fun deleteAttendance(attendanceId: Int) {
        attendanceDao.deleteAttendance(attendanceId)
    }

}

