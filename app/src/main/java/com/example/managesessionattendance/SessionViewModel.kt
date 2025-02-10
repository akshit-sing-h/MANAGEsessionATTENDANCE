package com.example.managesessionattendance



import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.managesessionattendance.AppRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class SessionViewModel(private val repository: AppRepository) : ViewModel() {

    val sessions: Flow<List<Session>> = repository.allSessions
    fun addSession(title: String, date: String, facilitator: String) {
        viewModelScope.launch {
            repository.insertSession(Session(title = title, date = date, facilitator = facilitator))
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

}
