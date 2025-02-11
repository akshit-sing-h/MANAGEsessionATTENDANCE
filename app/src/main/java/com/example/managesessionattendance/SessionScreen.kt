package com.example.managesessionattendance



import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController


@Composable
fun SessionScreen(viewModel: SessionViewModel, navController: NavHostController) {
    var title by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var facilitator by remember { mutableStateOf("") }

    val sessions = viewModel.sessions.collectAsState(initial = emptyList()).value

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Input Fields
        TextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = date,
            onValueChange = { date = it },
            label = { Text("Date") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = facilitator,
            onValueChange = { facilitator = it },
            label = { Text("Facilitator") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        // Add Session Button
        Button(
            onClick = {
                viewModel.addSession(title, date, facilitator)
                title = ""
                date = ""
                facilitator = ""
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Session")
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Session List
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(sessions) { session ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    elevation = CardDefaults.elevatedCardElevation(4.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(text = "📌 ${session.title}", fontWeight = FontWeight.Bold)
                        Text(text = "📅 Date: ${session.date}")
                        Text(text = "🆔 Session ID: ${session.id}")
                    }
                }
            }
        }

        // Navigation Buttons
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = { navController.navigate("mark_attendance") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Take Attendance")
            }
            Button(
                onClick = { navController.navigate("session_detail") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Make Changes in Attendance Sheet")
            }
            Button(
                onClick = { navController.navigate("manage_sessions") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Manage Sessions")
            }
        }
    }
}






