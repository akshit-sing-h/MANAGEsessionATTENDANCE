package com.example.managesessionattendance

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState

import androidx.compose.ui.unit.dp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun SessionDetailScreen(viewModel: SessionViewModel,navController: NavHostController) {
    var sessionId by remember { mutableStateOf("") }
    var showAttendance by remember { mutableStateOf(false) }
    var currentSessionId by remember { mutableStateOf<Int?>(null) } // Stores the last session ID searched

    val attendanceList = viewModel.getAttendance(currentSessionId ?: -1).collectAsState(initial = emptyList()).value

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Enter Session ID:", style = MaterialTheme.typography.headlineSmall)

        TextField(
            value = sessionId,
            onValueChange = { sessionId = it },
            label = { Text("Session ID") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            currentSessionId = sessionId.toIntOrNull() // Only update session ID when button is clicked
            showAttendance = true
        }) {
            Text("Show Attendance")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (showAttendance) {
            val sessionIdInt = currentSessionId

            if (sessionIdInt != null && sessionIdInt > 0) {
                if (attendanceList.isNotEmpty()) {
                    LazyColumn {
                        items(attendanceList) { attendance ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Text("Student: ${attendance.studentName}")
                                    Text("Status: ${if (attendance.isPresent) "Present" else "Absent"}")

                                    Spacer(modifier = Modifier.height(8.dp))
                                    IconButton(onClick = {
                                        viewModel.deleteAttendance(attendance.id)
                                    }) {
                                        Icon(
                                            imageVector = Icons.Default.Delete,
                                            contentDescription = "Delete",
                                            tint = MaterialTheme.colorScheme.error
                                        )
                                    }
                                    Row {
                                        Button(onClick = {
                                            viewModel.updateAttendanceStatus(attendance.id, true)
                                        }) {
                                            Text("Mark Present")
                                        }

                                        Spacer(modifier = Modifier.width(8.dp))

                                        Button(onClick = {
                                            viewModel.updateAttendanceStatus(attendance.id, false)
                                        }) {
                                            Text("Mark Absent")
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(8.dp))
                                }
                            }
                        }
                    }
                } else {
                    Text("No attendance records found for this session.")
                }
            } else {
                Text("Please enter a valid Session ID.")
            }
            Button(onClick = { navController.popBackStack() }) {
                Text("Back to Sessions")
            }
        }

    }
}



