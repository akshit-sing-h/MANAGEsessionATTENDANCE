package com.example.managesessionattendance


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController


@Composable
fun ManageSessionsScreen(viewModel: SessionViewModel, navController: NavHostController) {
    var searchQuery by remember { mutableStateOf("") }
    val sessions by viewModel.searchSessions(searchQuery).collectAsState(initial = emptyList())

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Search Sessions") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(sessions) { session ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Title: ${session.title}")
                        Text("Date: ${session.date}")
                        Text("Facilitator: ${session.facilitator}")

                        Spacer(modifier = Modifier.height(8.dp))

                        Row {
                            Button(onClick = { navController.navigate("edit_session/${session.id}") }) {
                                Text("Edit")
                            }


                            Spacer(modifier = Modifier.width(8.dp))
                            Button(onClick = { viewModel.deleteSession(session) }) {
                                Text("Delete")
                            }
                        }
                    }
                }
            }
        }
    }
}
