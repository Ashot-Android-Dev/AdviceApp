package com.example.advice.view.navHost

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.advice.R
import com.example.advice.view.screens.AdviceScreen
import com.example.advice.view.screens.FavoriteScreen
import com.example.advice.view.screens.MainScreen

@Composable
fun NavigationStack(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(navController=navController,startDestination = Route.MainScreen.route){
        composable (route = Route.MainScreen.route){
            MainScreen(
                textAdvice = stringResource(R.string.adviseText),
                buttonText = stringResource(R.string.buttonText),
                buttonColor = null,
                textColor = null,
                navController = navController

            )
        }
        composable (route = Route.AdviceScreen.route){
            AdviceScreen(
                alertDialogText = stringResource(R.string.dialogText),
                alertDialogTitleText = stringResource(R.string.dialogTitle),
                refreshText = stringResource(R.string.refreshText),
                dialogMessage = stringResource(R.string.deletedAdvice),
                navController = navController
            )
        }
        composable (route= Route.FavoriteScreen.route){
            FavoriteScreen(navController=navController)
        }
    }
}