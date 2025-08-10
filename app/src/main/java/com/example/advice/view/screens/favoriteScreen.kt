package com.example.advice.view.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.advice.ui.theme.Blue
import com.example.advice.view.navHost.Route
import com.example.advice.viewModel.AdviceViewModule

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FavoriteScreen(
    adviceViewModel: AdviceViewModule = hiltViewModel(),
    navController: NavController,
) {
    val favorites by adviceViewModel.favorite.collectAsState()

    LaunchedEffect(Unit) {
        adviceViewModel.loadFavoriteAdvices()
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Favorite Advices",
                        fontSize = 25.sp
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Blue,
                    titleContentColor = Color.White
                ),
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate(Route.AdviceScreen.route) {
                            popUpTo(
                                route = Route.AdviceScreen.route,
                            ) { inclusive = true }
                        }
                    }) {
                        Icon(
                            Icons.Default.Home,
                            contentDescription = ""
                        )
                    }
                }
            )
        },
    )
    { paddingValue ->
        if (favorites.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValue),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No Favorite Advice",
                    color = Color.Gray,
                    fontSize = 18.sp
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues = paddingValue),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                items(favorites) { favorite ->
                    FavoriteCard(
                        adviceEntity = favorite,
                        favoriteText = favorite.advice,
                    )
                }
            }
        }
    }
}