package com.example.advice.view.screens

import android.annotation.SuppressLint
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.advice.viewModel.AdviceViewModule
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.example.advice.model.room.advice.AdviceEntity
import com.example.advice.ui.theme.Blue
import com.example.advice.ui.theme.Blue1
import com.example.advice.ui.theme.Blue2
import com.example.advice.view.navHost.Route
import kotlinx.coroutines.delay
import com.example.advice.view.components.AlertDialog
import com.example.advice.view.uiStates.AdviceState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.derivedStateOf
import androidx.compose.ui.res.stringResource
import com.example.advice.view.components.AdviceCard

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "ConfigurationScreenWidthHeight")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdviceScreen(
    adviceViewModule: AdviceViewModule = hiltViewModel(),
    navController: NavController,
    alertDialogTitleText: String = "",
    alertDialogText: String = "",
    refreshText: String = "",
    dialogMessage: String=""
) {
    val lazyListState = rememberLazyListState()
    val isScrolling by remember { derivedStateOf { lazyListState.isScrollInProgress } }

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val snackBarHostState = remember { SnackbarHostState() }

    val isLoading by adviceViewModule.isLoading.collectAsState()

    val adviceState by adviceViewModule.uiState.collectAsState()


    var openDialog by remember { mutableStateOf(false) }

    var deleteAdvice by remember { mutableStateOf<AdviceEntity?>(null) }


    val favorites by adviceViewModule.favorite.collectAsState()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .verticalScroll(rememberScrollState())
                        .width(LocalConfiguration.current.screenWidthDp.dp / 2)
                ) {
                    Spacer(Modifier.height(12.dp))
                    Text(
                        "Menu",
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.titleLarge
                    )
                    HorizontalDivider()
                    NavigationDrawerItem(
                        onClick = { navController.navigate(Route.FavoriteScreen.route) },
                        label = {
                            Text(
                                text = "favorites",
                                fontSize = 20.sp
                            )
                        },
                        selected = false,
                        badge = {
                            BadgedBox(badge = {
                                if (favorites.isNotEmpty()) {
                                    Badge {
                                        Text(text = favorites.size.toString())
                                    }
                                }
                            }) {
                                Icon(Icons.Default.Favorite, contentDescription = "")
                            }
                        }
                    )
                }
            }
        }
    ) {
        Scaffold(
            snackbarHost = {
                SnackbarHost(hostState = snackBarHostState) {
                    Snackbar(
                        containerColor = Color.DarkGray,
                        contentColor = Color.White,
                        snackbarData = it,
                    )
                }
            },
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Advice",
                            fontSize = 30.sp,
                            color = Color.Black
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Blue2),
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { if (drawerState.isClosed) drawerState.open() else drawerState.close() } }) {
                            Icon(
                                Icons.Default.Menu,
                                contentDescription = ""
                            )
                        }
                    }
                )
            },

            ) { paddingValues ->
            when (val state = adviceState) {
                is AdviceState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            color = Color.Black
                        )
                    }
                }

                is AdviceState.Error -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = state.message, fontSize = 20.sp,
                            color = Color.Red
                        )
                    }
                }

                is AdviceState.Success -> {
                    Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(paddingValues),
                            contentAlignment = Alignment.Center,
                        ) {
                            PullToRefreshBox(
                                isRefreshing = isLoading,
                                onRefresh = { adviceViewModule.loadAdvise() },

                            ) {
                                LazyColumn(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(PaddingValues(5.dp)),
                                    state = lazyListState,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    item {
                                        Text(
                                            text = refreshText,
                                            textAlign = TextAlign.Center,
                                            fontSize = 20.sp,
                                            color = Color.Black,
                                            fontStyle = FontStyle.Italic,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                    items(state.adviceList) { advice ->
                                        AdviceCard(
                                            advice = advice,
                                            onclickDelete = {

                                                deleteAdvice = advice
                                                openDialog = true
                                            },
                                            onclickAddFavorite = {
                                                adviceViewModule.addFavAdvice(advice)
                                            },
                                            dataTime = advice.dataTime,
                                            isScrolling = isScrolling
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if (openDialog) {
            AlertDialog(
                onConfirmation = {
                    deleteAdvice?.let {
                        adviceViewModule.deleteAdvice(it)
                    }
                    openDialog = false
                    scope.launch {
                        snackBarHostState.showSnackbar(
                            message = dialogMessage,
                            duration = SnackbarDuration.Short
                        )
                    }
                },
                onDismissRequest = { openDialog = false },
                dialogText = alertDialogText,
                dialogTitle = alertDialogTitleText
            )
        }
    }