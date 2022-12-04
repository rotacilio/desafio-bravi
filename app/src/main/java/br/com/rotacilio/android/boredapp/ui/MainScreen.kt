package br.com.rotacilio.android.boredapp.ui

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun MainScreen() {

    val startDestination = "home"
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable("home") {
            HomeScreen(
                onBack = {
                    navController.navigate(
                        startDestination,
                        NavOptions.Builder()
                            .setPopUpTo(navController.graph.findStartDestination().id, true)
                            .build()
                    )
                },
                goToPendingActivities = {
                    navController.navigate("pendingActivities")
                },
                goToDoneActivities = {
                    navController.navigate("doneActivities")
                },
            )
        }
        composable("pendingActivities") {
            PendingActivitiesScreen(
                onBack = {
                    navController.navigate(
                        startDestination,
                        NavOptions.Builder()
                            .setPopUpTo(navController.graph.findStartDestination().id, true)
                            .build()
                    )
                }
            )
        }
        composable("doneActivities") {
            Text(text = "My Activities Screen")
        }
    }
}