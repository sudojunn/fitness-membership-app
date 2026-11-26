package ru.university.molchanov.mobile.fitness

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import ru.university.molchanov.mobile.fitness.ui.dashboard.DashboardRoute
import ru.university.molchanov.mobile.fitness.ui.theme.FitnessCenterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FitnessCenterTheme {
                Scaffold { padding ->
                    DashboardRoute(Modifier.padding(padding))
                }
            }
        }
    }
}