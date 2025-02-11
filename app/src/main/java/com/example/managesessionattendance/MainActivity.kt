package com.example.managesessionattendance

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.managesessionattendance.ui.theme.ManageSESSIONattendanceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Database
        val database = AppDatabase.getDatabase(this)

        // Pass both DAOs to the repository
        val repository = AppRepository(database.sessionDao(), database.attendanceDao())

        val viewModel = SessionViewModel(repository)

        setContent {
            ManageSESSIONattendanceTheme {
                SessionApp(viewModel)
            }
        }
    }
}

@Composable
fun SessionApp(viewModel: SessionViewModel) {
    val navController = rememberNavController()

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "session_screen",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("session_screen") {
                SessionScreen(viewModel, navController)
            }

            // Session Detail screen without requiring a sessionId initially
            composable("session_detail") {
                SessionDetailScreen(viewModel)
            }
            composable("mark_attendance") { MarkAttendanceScreen(viewModel,navController) }
            composable("manage_sessions") { ManageSessionsScreen(viewModel,navController) }
            composable(
                "edit_session/{sessionId}",
                arguments = listOf(navArgument("sessionId") { type = NavType.IntType })
            ) { backStackEntry ->
                val sessionId = backStackEntry.arguments?.getInt("sessionId") ?: 0
                EditSessionScreen(sessionId,viewModel,navController)
            }
        }

    }
}
