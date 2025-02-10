package com.example.managesessionattendance



import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun SessionScreen(viewModel: SessionViewModel) {
    var title by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var facilitator by remember { mutableStateOf("") }
    var studentName by remember { mutableStateOf("") }
    var selectedSessionId by remember { mutableStateOf<Int?>(null) }

    val sessions = viewModel.sessions.collectAsState(initial = emptyList()).value
    val attendanceList = selectedSessionId?.let { viewModel.getAttendance(it).collectAsState(initial = emptyList()).value } ?: emptyList()

    Column(modifier = Modifier.padding(16.dp)) {
        // Input fields for adding a session
        TextField(value = title, onValueChange = { title = it }, label = { Text("Title") })
        TextField(value = date, onValueChange = { date = it }, label = { Text("Date") })
        TextField(value = facilitator, onValueChange = { facilitator = it }, label = { Text("Facilitator") })

        Button(onClick = { viewModel.addSession(title, date, facilitator) }) {
            Text("Add Session")
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(sessions) { session ->
                Column(modifier = Modifier.padding(8.dp)) {
                    Text(text = "${session.title} - ${session.date}")
                    Button(onClick = { selectedSessionId = session.id }) {
                        Text("Mark Attendance")
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        selectedSessionId?.let { sessionId ->
            Column {
                Text("Mark Attendance for Session ID: $sessionId")
                TextField(value = studentName, onValueChange = { studentName = it }, label = { Text("Student Name") })

                Row {
                    Button(onClick = {
                        viewModel.markAttendance(sessionId, studentName, true)
                        studentName = "" // Reset input
                    }) {
                        Text("Present")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = {
                        viewModel.markAttendance(sessionId, studentName, false)
                        studentName = "" // Reset input
                    }) {
                        Text("Absent")
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text("Attendance List:")
                LazyColumn {
                    items(attendanceList) { attendance ->
                        Text("${attendance.studentName} - ${if (attendance.isPresent) "Present" else "Absent"}")
                    }
                }
            }
        }
    }
}


