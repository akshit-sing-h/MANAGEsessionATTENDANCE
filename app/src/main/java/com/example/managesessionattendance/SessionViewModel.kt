package com.example.managesessionattendance



import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch



class SessionViewModel(private val repository: AppRepository) : ViewModel() {

    val sessions: Flow<List<Session>> = repository.allSessions

    fun addSession(title: String, date: String, facilitator: String) {
        viewModelScope.launch {
            repository.insertSession(Session(title = title, date = date, facilitator = facilitator))
        }
    }

    fun searchSessions(query: String): Flow<List<Session>> {
        return repository.searchSessions(query)
    }

    fun updateSession(session: Session) {
        viewModelScope.launch {
            println("Updating session with ID: ${session.id}") // Debugging
            repository.updateSession(session)
        }
    }


    fun deleteSession(session: Session) {
        viewModelScope.launch {
            repository.deleteSession(session)
        }
    }


    fun markAttendance(sessionId: Int, studentName: String, isPresent: Boolean) {
        viewModelScope.launch {
            repository.insertAttendance(Attendance(sessionId = sessionId, studentName = studentName, isPresent = isPresent))
        }
    }


    fun getAttendance(sessionId: Int): Flow<List<Attendance>> {
        return repository.getAttendance(sessionId)
    }
    fun updateAttendanceStatus(attendanceId: Int, isPresent: Boolean) {
        viewModelScope.launch {
            val currentAttendance = repository.getAttendanceById(attendanceId).firstOrNull()

            if (currentAttendance != null) {
                val updatedAttendance = currentAttendance.copy(isPresent = isPresent)
                repository.updateAttendance(updatedAttendance)
            }
        }
    }

    fun deleteAttendance(attendanceId: Int) {
        viewModelScope.launch {
            repository.deleteAttendance(attendanceId)
        }
    }




}
