package com.team.moviefinder.ui.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.team.moviefinder.data.models.MovieSearchItemResponse
import com.team.moviefinder.ui.navigation.BottomNavBar
import com.team.moviefinder.ui.theme.LightBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    navController: NavController,
    onNavigateBack: () -> Unit,
    onNavigateToDetails: (Int) -> Unit,
    vm: SearchViewModel = viewModel(),
) {
    var query by remember { mutableStateOf("") }
    val uiState by vm.uiState.collectAsState()

    val colorScheme = MaterialTheme.colorScheme

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Что ты хочешь посмотреть?",
                        color = colorScheme.onSurface,
                        fontWeight = FontWeight.SemiBold,
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onNavigateBack,
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Назад",
                            tint = colorScheme.onSurface,
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorScheme.surface,
                ),
            )
        },
        bottomBar = { BottomNavBar(navController) },
        content = { padding: PaddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(vertical = 16.dp)
                ,
            ) {
                OutlinedTextField(
                    value = query,
                    onValueChange = { query = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                    ,
                    placeholder = {
                        Text(
                            text = "Поиск",
                            color = colorScheme.onSurface.copy(alpha = 0.6f),
                        )
                    },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(onSearch = { vm.searchMovies(query) }),
                    singleLine = true,
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = colorScheme.surfaceVariant,
                        unfocusedContainerColor = colorScheme.surfaceVariant,

                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,

                        focusedPlaceholderColor = colorScheme.onSurface.copy(alpha = 0.6f),
                        unfocusedPlaceholderColor = colorScheme.onSurface.copy(alpha = 0.6f),

                        focusedTextColor = colorScheme.onSurface,
                        unfocusedTextColor = colorScheme.onSurface,
                    ),
                    trailingIcon = {
                        Row {
                            if (query.isNotEmpty()) {
                                IconButton(onClick = { query = "" }) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "Очистить",
                                        tint = colorScheme.onSurface.copy(alpha = 0.6f),
                                    )
                                }
                            }

                            IconButton(onClick = { vm.searchMovies(query) }) {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = "Поиск",
                                    tint = LightBlue,
                                )
                            }
                        }
                    },
                )

                Spacer(modifier = Modifier.height(16.dp))

                Box(modifier = Modifier.weight(1f)) {
                    when (val currentState = uiState) {
                        is SearchUiState.Loading -> {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center,
                            ) {
                                CircularProgressIndicator()
                            }
                        }

                        is SearchUiState.Success -> {
                            val total = currentState.totalFilms
                            val films = currentState.allFilms

                            val isMoreAvailable = films.size < total

                            Column {
                                Text(
                                    text = "Найдено: $total фильмов",
                                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 4.dp),
                                    color = colorScheme.onSurface.copy(alpha = 0.6f),
                                    fontSize = 14.sp,
                                )

                                LazyColumn(
                                    modifier = Modifier.fillMaxSize(),
                                    verticalArrangement = Arrangement.spacedBy(12.dp),
                                ) {
                                    items(films) { movie ->
                                        MovieCard(
                                            movie = movie,
                                            navigateToDetails = { onNavigateToDetails(movie.filmId) },
                                        )
                                    }

                                    if (isMoreAvailable) {
                                        item {
                                            Button(
                                                onClick = { vm.loadMore() },
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(horizontal = 24.dp, vertical = 8.dp)
                                                ,
                                                colors = ButtonDefaults.buttonColors(
                                                    containerColor = LightBlue,
                                                ),
                                            ) {
                                                Text("Загрузить ещё")
                                            }
                                        }
                                    }

                                    if (!isMoreAvailable) {
                                        item {
                                            Text(
                                                text = "Все фильмы загружены",
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(16.dp)
                                                ,
                                                color = colorScheme.onSurface.copy(alpha = 0.5f),
                                                fontSize = 14.sp,
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        is SearchUiState.Error -> {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center,
                            ) {
                                Text(
                                    text = currentState.message,
                                    color = colorScheme.error,
                                )
                            }
                        }

                        is SearchUiState.Empty -> {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center,
                            ) {
                                Text(
                                    text = "Ничего не найдено",
                                    color = colorScheme.onSurface,
                                )
                            }
                        }

                        is SearchUiState.Idle -> {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center,
                            ) {
                                Text(
                                    text = "Введите название фильма",
                                    color = colorScheme.onSurface,
                                )
                            }
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun MovieCard(
    movie: MovieSearchItemResponse,
    navigateToDetails: () -> Unit,
) {
    val displayTitle = movie.nameRu ?: movie.nameEn ?: "Без названия"

    val colorScheme = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .clickable { navigateToDetails() }
        ,
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
            ,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(movie.posterUrl)
                    .crossfade(true)
                    .diskCacheKey(movie.posterUrl)
                    .build()
                ,
                contentDescription = displayTitle,
                modifier = Modifier
                    .size(70.dp)
                    .clip(RoundedCornerShape(8.dp))
                ,
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = displayTitle,
                    color = colorScheme.onSurface,
                    fontSize = typography.titleMedium.fontSize,
                    fontWeight = FontWeight.Bold,
                    overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis,
                    maxLines = 1,
                )

                if (!movie.nameEn.isNullOrBlank() && !movie.nameRu.isNullOrBlank()) {
                    Text(
                        text = movie.nameEn,
                        color = colorScheme.onSurface.copy(alpha = 0.6f),
                        fontSize = typography.bodySmall.fontSize,
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Рейтинг",
                        modifier = Modifier.size(16.dp),
                        tint = Color(0xFFFFC107),
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = if (movie.rating == "null") "Нет рейтинга" else movie.rating,
                        color = colorScheme.onSurface.copy(alpha = 0.6f),
                        fontSize = typography.bodySmall.fontSize,
                    )
                }
            }

            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = "Подробнее",
                modifier = Modifier.size(24.dp),
                tint = colorScheme.onSurface.copy(alpha = 0.4f),
            )
        }
    }
}