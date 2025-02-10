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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SessionDetailsScreen(title: String, date: String, facilitator: String, viewModel: SessionViewModel) {
    val attendanceList = viewModel.getAttendance(1).collectAsState(initial = emptyList()).value  // Replace `1` with actual session ID

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Session Details", style = MaterialTheme.typography.headlineSmall)
        Text("Title: $title")
        Text("Date: $date")
        Text("Facilitator: $facilitator")

        Spacer(modifier = Modifier.height(16.dp))

        Text("Attendance List", style = MaterialTheme.typography.headlineSmall)
        LazyColumn {
            items(attendanceList) { attendance ->
                Text("${attendance.studentName} - ${if (attendance.isPresent) "Present" else "Absent"}")
            }
        }
    }
}
