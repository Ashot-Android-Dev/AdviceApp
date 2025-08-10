package com.example.advice.view.navHost

sealed class Route(val route: String){
    object MainScreen: Route("Main_Screen")
    object AdviceScreen: Route("AdviceScreen")
    object FavoriteScreen: Route("FavoriteScreen")
}