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

    val sessions = viewModel.sessions.collectAsState(initial = emptyList()).value

    Column(modifier = Modifier.padding(16.dp)) {
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

        LazyColumn {
            items(sessions) { session->
                Column(modifier = Modifier.padding(8.dp)) {
                    Text(text = "${session.title} - date${session.date} - session id: ${session.id}")
                }
            }
        }

        // Navigate to the Mark Attendance Screen
        Button(onClick = { navController.navigate("mark_attendance") }) {
            Text(text = "Take Attendance")
        }
        //navigate to the attendance change screen
        Button(onClick = { navController.navigate("session_detail") }) {
            Text(text = "Make changes in attendandce sheet")
        }
        Button(onClick = { navController.navigate("manage_sessions") }) {
            Text("Manage Sessions")
        }

    }
}





