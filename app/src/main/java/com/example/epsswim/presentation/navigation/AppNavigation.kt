package com.example.epsswim.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.epsswim.presentation.ui.common.screens.LoginScreen
import com.example.epsswim.presentation.ui.common.screens.ParticipationDetailsScreen
import com.example.epsswim.presentation.ui.common.screens.SplashScreen
import com.example.epsswim.presentation.ui.common.screens.SwimmerProfile
import com.example.epsswim.presentation.ui.parent.screens.HomeScreen
import com.example.epsswim.presentation.ui.trainer.screens.AbsenceScreen
import com.example.epsswim.presentation.ui.trainer.screens.CompetitionsScreen
import com.example.epsswim.presentation.ui.trainer.screens.LevelScreen
import com.example.epsswim.presentation.ui.trainer.screens.TrainerProfile

@Composable
fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier,
    isTrainer :Boolean
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Screen.ParticipationDetails
    ){
        composable<Screen.Splash>{
            SplashScreen(
                navController = navController
            )
        }
        composable<Screen.Login>{
            LoginScreen(
                navController = navController,
                isTrainer = isTrainer
            )
        }
        composable<Screen.ParentHome>{
            HomeScreen(
                navController = navController,
            )
        }
        composable<Screen.SwimmerProfile>{
            SwimmerProfile(
                navController = navController,
            )
        }
        composable<Screen.ParticipationDetails>{
            ParticipationDetailsScreen(
                navController = navController,
                isTrainer = isTrainer
            )
        }
        composable<Screen.AbsenceScreen>{
            AbsenceScreen(
                navController = navController,
            )
        }
        composable<Screen.CompetitionScreen>{
            CompetitionsScreen(
                navController = navController,
            )
        }
        composable<Screen.LevelScreen>{
            LevelScreen(
                navController = navController,
            )
        }
        composable<Screen.TrainerProfile>{
            TrainerProfile(
                navController = navController,
            )
        }
    }

}