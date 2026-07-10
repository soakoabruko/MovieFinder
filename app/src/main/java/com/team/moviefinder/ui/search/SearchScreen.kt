package com.team.moviefinder.ui.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import com.team.moviefinder.data.models.MovieSearchItemResponse

// иконки material design
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*

// работа с состоянием и жизненным циклом
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

// текст и шрифты
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// навигация
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

// загрузка изображений
import coil.compose.AsyncImage
import coil.request.ImageRequest
import androidx.compose.material.icons.filled.ArrowForward
import com.team.moviefinder.ui.theme.LightBlue

/**
 * Экран поиска фильмов
 *
 * Архитектура: MVVM (Model-View-ViewModel)
 * - View: SearchScreen (UI)
 * - ViewModel: SearchViewModel (логика и состояния)
 * - Model: MovieSearchResponse (данные из API)
 *
 * Использует Material 3 дизайн с поддержкой светлой и темной темы
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    // navController: NavController,
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
                        color = colorScheme.onSurface,  // цвет из темы
                        fontWeight = FontWeight.SemiBold,
                    )
                },
                // кнопка "Назад" для возврата на предыдущий экран
                navigationIcon = {
                    IconButton(onClick = { /* navController.navigateUp() */ }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Назад",
                            tint = colorScheme.onSurface,  // цвет из темы
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorScheme.surface,  // цвет из темы
                ),
            )
        },
        bottomBar = {
            NavigationBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(78.dp),
                containerColor = colorScheme.surface,  // цвет из темы
                tonalElevation = 0.dp,
                content = {
                    NavigationBarItem(
                        selected = false,
                        onClick = {},
                        icon = {
                            Icon(
                                imageVector = Icons.Filled.Home,
                                contentDescription = "Главная",
                            )
                        },
                        label = {
                            Text("Главная")
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = LightBlue,
                            selectedTextColor = LightBlue,
                            unselectedIconColor = colorScheme.onSurface.copy(alpha = 0.6f),
                            unselectedTextColor = colorScheme.onSurface.copy(alpha = 0.6f),
                            indicatorColor = Color.Transparent
                        )
                    )

                    NavigationBarItem(
                        selected = true,
                        onClick = {},
                        icon = {
                            Icon(
                                imageVector = Icons.Filled.Search,
                                contentDescription = "Поиск"
                            )
                        },
                        label = {
                            Text("Поиск")
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = LightBlue,
                            selectedTextColor = LightBlue,
                            unselectedIconColor = colorScheme.onSurface.copy(alpha = 0.6f),
                            unselectedTextColor = colorScheme.onSurface.copy(alpha = 0.6f),
                            indicatorColor = Color.Transparent
                        )
                    )

                    NavigationBarItem(
                        selected = false,
                        onClick = {},
                        icon = {
                            Icon(
                                imageVector = Icons.Filled.Settings,
                                contentDescription = "Настройки"
                            )
                        },
                        label = {
                            Text("Настройки")
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = LightBlue,
                            selectedTextColor = LightBlue,
                            unselectedIconColor = colorScheme.onSurface.copy(alpha = 0.6f),
                            unselectedTextColor = colorScheme.onSurface.copy(alpha = 0.6f),
                            indicatorColor = Color.Transparent
                        )
                    )
                }
            )
        },
        content = { padding: PaddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(vertical = 16.dp)
            ) {
                // поле ввода
                OutlinedTextField(
                    value = query,
                    onValueChange = { query = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    placeholder = {
                        Text(
                            text = "Поиск",
                            color = colorScheme.onSurface.copy(alpha = 0.6f),  // цвет из темы
                        )
                    },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    // поиск при нажатии enter на клавиатуре
                    keyboardActions = KeyboardActions(onSearch = { vm.searchMovies(query) }),
                    singleLine = true,
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = colorScheme.surfaceVariant,   // цвет из темы
                        unfocusedContainerColor = colorScheme.surfaceVariant, // цвет из темы
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        focusedPlaceholderColor = colorScheme.onSurface.copy(alpha = 0.6f),
                        unfocusedPlaceholderColor = colorScheme.onSurface.copy(alpha = 0.6f),
                        focusedTextColor = colorScheme.onSurface,  // цвет из темы
                        unfocusedTextColor = colorScheme.onSurface, // цвет из темы
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
                        // состояние "Загрузка"
                        is SearchUiState.Loading -> {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center,
                            ) {
                                CircularProgressIndicator()
                            }
                        }

                        // состояние "Успех"
                        is SearchUiState.Success -> {
                            val total = currentState.totalFilms
                            val films = currentState.allFilms

                            val isMoreAvailable = films.size < total

                            Column {
                                // кол-во найденных фильмов
                                Text(
                                    text = "Найдено: $total фильмов",
                                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 4.dp),
                                    color = colorScheme.onSurface.copy(alpha = 0.6f),
                                    fontSize = 14.sp,
                                )
                                // список фильмов
                                LazyColumn(
                                    modifier = Modifier.fillMaxSize(),
                                    verticalArrangement = Arrangement.spacedBy(12.dp),
                                ) {
                                    items(films) { movie ->
                                        MovieCard(
                                            movie = movie,
                                            onClick = {},
                                        )
                                    }

                                    // кнопка "Загрузить ещё"
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

                                    // конец
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

                        // состояние "Ошибка"
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

                        // состояние "Пустой результат"
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

                        // состояние "Ожидание ввода"
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

/**
 * Карточка с информацией о фильме
 *
 * Отображает:
 * - Постер фильма
 * - Название (русское и английское)
 * - Рейтинг (со звездочкой)
 * - Стрелку для перехода к деталям
 *
 * @param movie Данные фильма из API
 * @param onClick Колбэк при клике на карточку
 */
@Composable
fun MovieCard(
    movie: MovieSearchItemResponse,
    onClick: () -> Unit,
) {
    val displayTitle = movie.nameRu ?: movie.nameEn ?: "Без названия"
    val colorScheme = MaterialTheme.colorScheme

    // карточка
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorScheme.surface  // цвет из темы
        )
    ) {
        // содержимое
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
            ,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // постер
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

            // информация
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = displayTitle,
                    color = colorScheme.onSurface,  // цвет из темы
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    fontWeight = FontWeight.Bold,
                    overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis,
                    maxLines = 1,
                )

                if (!movie.nameEn.isNullOrBlank() && !movie.nameRu.isNullOrBlank()) {
                    Text(
                        text = movie.nameEn,
                        color = colorScheme.onSurface.copy(alpha = 0.6f),
                        fontSize = MaterialTheme.typography.bodySmall.fontSize,
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                // рейтинг
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
                        fontSize = MaterialTheme.typography.bodySmall.fontSize,
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