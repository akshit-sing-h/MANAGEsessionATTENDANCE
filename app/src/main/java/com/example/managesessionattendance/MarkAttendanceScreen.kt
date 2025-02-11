package com.example.managesessionattendance



import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun MarkAttendanceScreen(viewModel: SessionViewModel, navController: NavHostController) {
    var studentName by remember { mutableStateOf("") }
    var selectedSessionId by remember { mutableStateOf<Int?>(null) }

    val sessions = viewModel.sessions.collectAsState(initial = emptyList()).value
    val selectedSession = sessions.find { it.id == selectedSessionId }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Mark Attendance")

        // Select Session Dropdown
        LazyColumn {
            items(sessions) { session ->
                Button(onClick = { selectedSessionId = session.id }) {
                    Text("Select Session: ${session.id} - ${session.title}")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (selectedSessionId != null && selectedSession != null) {
            Text("Mark Attendance for Session: ${selectedSession.id} - ${selectedSession.title}")

            TextField(value = studentName, onValueChange = { studentName = it }, label = { Text("Student Name") })

            Row {
                Button(onClick = {
                    viewModel.markAttendance(selectedSession.id, studentName, true)
                    studentName = "" // Reset input
                }) {
                    Text("Present")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = {
                    viewModel.markAttendance(selectedSession.id, studentName, false)
                    studentName = "" // Reset input
                }) {
                    Text("Absent")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Display Marked Attendance
            val attendanceList = viewModel.getAttendance(selectedSession.id).collectAsState(initial = emptyList()).value
            LazyColumn {
                items(attendanceList) { attendance ->
                    Text("Session ${attendance.sessionId}: ${attendance.studentName} - ${if (attendance.isPresent) "Present" else "Absent"}")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Button to go back
        Button(onClick = { navController.popBackStack() }) {
            Text("Back to Sessions")
        }
    }
}


