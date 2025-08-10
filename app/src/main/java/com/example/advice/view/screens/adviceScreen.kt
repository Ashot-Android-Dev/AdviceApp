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

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "ConfigurationScreenWidthHeight")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdviceScreen(
    adviceViewModule: AdviceViewModule = hiltViewModel(),
    navController: NavController,
    alertDialogTitleText: String = "",
    alertDialogText: String = "",
    refreshText: String = "",
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val adviceList by adviceViewModule.advice.collectAsState()
    val snackBarHostState = remember { SnackbarHostState() }

    val isLoading by adviceViewModule.isLoading.collectAsState()

    val adviceState by adviceViewModule.uiState.collectAsState()

    var openDialog by remember { mutableStateOf(false) }
    var deleteAdvice by remember { mutableStateOf<AdviceEntity?>(null) }
    val lazyListState = rememberLazyListState()
    var showButton by remember { mutableStateOf(true) }

    val favorites by adviceViewModule.favorite.collectAsState()

    LaunchedEffect(lazyListState.isScrollInProgress) {
        snapshotFlow { lazyListState.isScrollInProgress }
            .collect { isScroling ->
                if (isScroling) {
                    showButton = false
                } else {
                    delay(700)
                    showButton = true
                }
            }

    }

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
            when (adviceState) {
                is AdviceState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is AdviceState.Error -> {
                    val error = (adviceState as AdviceState.Error).message
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = error, fontSize = 20.sp,
                            color = Color.Red
                        )
                    }
                }

                is AdviceState.Success -> {
                    val advices = (adviceState as AdviceState.Success).adviceList

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues),
                        contentAlignment = Alignment.Center,
                    ) {
                        PullToRefreshBox(
                            isRefreshing = isLoading,
                            onRefresh = { adviceViewModule.loadAdvise() }
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
                                items(advices) { advice ->
                                    AdviceCard(
                                        advice = advice,
                                        onclickDelete = {

                                            deleteAdvice = advice
                                            openDialog = true
                                        },
                                        onclickAddFavorite = {
                                            adviceViewModule.addFavAdvice(advice)
                                        },
                                        dataTime = advice.dataTime
                                    )
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
                                message = "Deleted your note",
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
    }
}

@Composable
fun AdviceCard(
    advice: AdviceEntity,
    dataTime: String,
    onclickDelete: (AdviceEntity) -> Unit,
    onclickAddFavorite: (AdviceEntity) -> Unit,

    ) {
    var expanded by remember { mutableStateOf(false) }
    val gradient = Brush.verticalGradient(listOf(Blue, Blue, Blue1))
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth(0.95f)
            .padding(top = 10.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { expanded = !expanded }
            .animateContentSize(),
        shape = RoundedCornerShape(
            topStart = 30.dp, topEnd = 8.dp,
            bottomStart = 8.dp, bottomEnd = 30.dp
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ) {
        Column(
            modifier = Modifier
                .background(brush = gradient)
                .padding(12.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = dataTime,
                fontSize = 20.sp,
                color = Color.White

            )
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                color = Color.White
            )
            Text(
                text = if (expanded) advice.advice else advice.advice.take(20) + "...",
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 25.sp,
                fontStyle = FontStyle.Italic,
                color = Color.White,
                letterSpacing = 2.sp,
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Absolute.SpaceBetween
            ) {

                IconButton(onClick = { onclickAddFavorite(advice) }) {
                    Icon(
                        imageVector = if (advice.isFavorite) Icons.Filled.Favorite
                        else Icons.Filled.FavoriteBorder,
                        tint = if (advice.isFavorite) Color.Black else Color.Black,
                        contentDescription = ""
                    )
                }
                IconButton(onClick = { onclickDelete(advice) }) {
                    Icon(Icons.Filled.Delete, contentDescription = "")
                }
            }
        }
    }
}


@Composable
fun FavoriteCard(
    modifier: Modifier = Modifier,
    favoriteText: String,
    adviceEntity: AdviceEntity,
) {
    var expanded by remember { mutableStateOf(false) }
    val gradient = Brush.verticalGradient(listOf(Blue, Blue, Blue1))

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth(0.95f)
            .padding(top = 10.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { expanded = !expanded }
            .animateContentSize(),
        shape = RoundedCornerShape(
            topStart = 30.dp, topEnd = 8.dp,
            bottomStart = 8.dp, bottomEnd = 30.dp
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ) {
        Column(
            modifier = Modifier
                .background(brush = gradient)
                .padding(12.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = if (expanded) adviceEntity.advice else adviceEntity.advice.take(20) + "...",
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 25.sp,
                fontStyle = FontStyle.Italic,
                color = Color.White,
                letterSpacing = 2.sp,
            )
        }
    }
}