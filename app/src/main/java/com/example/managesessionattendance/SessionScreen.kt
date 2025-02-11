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
fun SessionScreen(viewModel: SessionViewModel, navController: NavHostController) {
    var title by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var facilitator by remember { mutableStateOf("") }
    var studentName by remember { mutableStateOf("") }
    var selectedSessionId by remember { mutableStateOf<Int?>(null) }

    // Collect the sessions from the ViewModel
    val sessions = viewModel.sessions.collectAsState(initial = emptyList()).value

    Column(modifier = Modifier.padding(16.dp)) {
        // If no session is selected, show the session creation UI
        if (selectedSessionId == null) {
            TextField(value = title, onValueChange = { title = it }, label = { Text("Title") })
            TextField(value = date, onValueChange = { date = it }, label = { Text("Date") })
            TextField(value = facilitator, onValueChange = { facilitator = it }, label = { Text("Facilitator") })

            Button(onClick = {
                viewModel.addSession(title, date, facilitator)
                title = ""
                date = ""
                facilitator = ""
            }) {
                Text("Add Session")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Display list of sessions
            LazyColumn {
                items(sessions) { session ->
                    Column(modifier = Modifier.padding(8.dp)) {
                        Text(text = "${session.title} - ${session.date}")

                        // "Mark Attendance" button
                        Button(onClick = { selectedSessionId = session.id }) {
                            Text("Mark Attendance")
                        }
                    }
                }
            }
        } else {
            // Attendance marking UI
            Column {
                Text("Mark Attendance for Session ID: $selectedSessionId")
                TextField(value = studentName, onValueChange = { studentName = it }, label = { Text("Student Name") })

                Row {
                    Button(onClick = {
                        viewModel.markAttendance(selectedSessionId!!, studentName, true)
                        studentName = "" // Reset input
                    }) {
                        Text("Present")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = {
                        viewModel.markAttendance(selectedSessionId!!, studentName, false)
                        studentName = "" // Reset input
                    }) {
                        Text("Absent")
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // "Done" button to reset back to session creation
                Button(onClick = {
                    selectedSessionId = null  // Reset selection
                    studentName = ""         // Clear student name input
                }) {
                    Text("Done")
                }
            }
        }

        Button(onClick = { navController.navigate("session_detail") }) {
            Text(text = "Go to Session Detail")
        }
    }
}




