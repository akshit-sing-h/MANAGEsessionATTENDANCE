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
fun EditSessionScreen(sessionId: Int, viewModel: SessionViewModel, navController: NavHostController) {
    val sessions by viewModel.sessions.collectAsState(initial = emptyList())
    val session = sessions.find { it.id == sessionId }

    var title by remember { mutableStateOf(session?.title ?: "") }
    var date by remember { mutableStateOf(session?.date ?: "") }
    var facilitator by remember { mutableStateOf(session?.facilitator ?: "") }

    if (session == null) {
        Text("Session not found!")
        return
    }

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(value = title, onValueChange = { title = it }, label = { Text("Title") })
        TextField(value = date, onValueChange = { date = it }, label = { Text("Date") })
        TextField(value = facilitator, onValueChange = { facilitator = it }, label = { Text("Facilitator") })

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            viewModel.updateSession(session.copy(id = session.id, title = title, date = date, facilitator = facilitator))
            navController.popBackStack()
        }) {
            Text("Save Changes")
        }
    }
}
