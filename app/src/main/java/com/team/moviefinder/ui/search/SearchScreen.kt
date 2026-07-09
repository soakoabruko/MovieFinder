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

// работа с сотсоянием и жизненным циклом
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

// загрузка изображений (Coil)
import coil.compose.AsyncImage
import coil.request.ImageRequest
import androidx.compose.material.icons.filled.ArrowForward

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
    navController: NavController,
    viewModel: SearchViewModel = viewModel()
) {
    var query by remember { mutableStateOf("") }
    val uiState by viewModel.uiState.collectAsState()
    val colorScheme = MaterialTheme.colorScheme

    // каркас экрана (Scaffold)
    Scaffold(
        // верхняя панель
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Что ты хочешь посмотреть?",
                        fontWeight = FontWeight.SemiBold,
                        color = colorScheme.onSurface  // цвет из темы
                    )
                },
                // кнопка "Назад" для возврата на предыдущий экран
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Назад",
                            tint = colorScheme.onSurface  // цвет из темы
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorScheme.surface  // цвет из темы
                )
            )
        },
        // нижняя панель навигации
        bottomBar = {
            NavigationBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(78.dp),
                containerColor = colorScheme.surface,  // цвет из темы
                tonalElevation = 0.dp
            ) {
                // Home
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Home, contentDescription = "Главная") },
                    label = { Text("Главная") },
                    selected = false,
                    onClick = { /* Заглушка */ },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF0296E5),
                        selectedTextColor = Color(0xFF0296E5),
                        unselectedIconColor = colorScheme.onSurface.copy(alpha = 0.6f),
                        unselectedTextColor = colorScheme.onSurface.copy(alpha = 0.6f),
                        indicatorColor = Color.Transparent
                    )
                )

                // Search (активная)
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Search, contentDescription = "Поиск") },
                    label = { Text("Поиск") },
                    selected = true,
                    onClick = { /* Заглушка */ },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF0296E5),
                        selectedTextColor = Color(0xFF0296E5),
                        unselectedIconColor = colorScheme.onSurface.copy(alpha = 0.6f),
                        unselectedTextColor = colorScheme.onSurface.copy(alpha = 0.6f),
                        indicatorColor = Color.Transparent
                    )
                )

                // Settings
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Settings, contentDescription = "Настройки") },
                    label = { Text("Настройки") },
                    selected = false,
                    onClick = { /* Заглушка */ },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF0296E5),
                        selectedTextColor = Color(0xFF0296E5),
                        unselectedIconColor = colorScheme.onSurface.copy(alpha = 0.6f),
                        unselectedTextColor = colorScheme.onSurface.copy(alpha = 0.6f),
                        indicatorColor = Color.Transparent
                    )
                )
            }
        }
    ) { padding ->
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
                placeholder = {
                    Text(
                        "Поиск",
                        color = colorScheme.onSurface.copy(alpha = 0.6f)  // цвет из темы
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
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
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                // поиск при нажатии enter на клавиатуре
                keyboardActions = KeyboardActions(
                    onSearch = {
                        if (query.isNotBlank()) {
                            viewModel.searchMovies(query)
                        }
                    }
                ),
                // кнопка "Очистить"
                trailingIcon = {
                    Row {
                        if (query.isNotEmpty()) {
                            IconButton(onClick = { query = "" }) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Очистить",
                                    tint = colorScheme.onSurface.copy(alpha = 0.6f)
                                )
                            }
                        }
                        IconButton(
                            onClick = {
                                if (query.isNotBlank()) {
                                    viewModel.searchMovies(query)
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Поиск",
                                tint = Color(0xFF0296E5)
                            )
                        }
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // контент
            Box(modifier = Modifier.weight(1f)) {
                when (val state = uiState) {
                    // состояние "Загрузка"
                    is SearchUiState.Loading -> {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator()
                        }
                    }
                    // состояние "Успех"
                    is SearchUiState.Success -> {
                        val films = state.data.films
                        val total = state.data.searchFilmsCountResult
                        val isMoreAvailable = films.size < total

                        if (films.isEmpty()) {
                            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                Text("Ничего не найдено", color = colorScheme.onSurface)
                            }
                        } else {
                            Column {
                                // счётчик найденных фильмов
                                Text(
                                    text = "Найдено: $total фильмов",
                                    fontSize = 14.sp,
                                    color = colorScheme.onSurface.copy(alpha = 0.6f),
                                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 4.dp)
                                )
                                // список фильмов
                                LazyColumn(
                                    modifier = Modifier.fillMaxSize(),
                                    verticalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    items(films) { movie ->
                                        MovieCard(movie = movie) {
                                            println("Клик по фильму: ${movie.nameRu ?: movie.nameEn}")
                                        }
                                    }

                                    // кнопка "Загрузить ещё"
                                    if (isMoreAvailable) {
                                        item {
                                            Button(
                                                onClick = { viewModel.loadMore() },
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(horizontal = 24.dp, vertical = 8.dp),
                                                colors = ButtonDefaults.buttonColors(
                                                    containerColor = Color(0xFF0296E5)
                                                )
                                            ) {
                                                Text("Загрузить ещё")
                                            }
                                        }
                                    }

                                    // сообщение о конце списка
                                    if (!isMoreAvailable && films.size > 0) {
                                        item {
                                            Text(
                                                text = "Все фильмы загружены",
                                                fontSize = 14.sp,
                                                color = colorScheme.onSurface.copy(alpha = 0.5f),
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(16.dp)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                    // состояние "Ошибка"
                    is SearchUiState.Error -> {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text("Ошибка: ${state.message}", color = colorScheme.error)
                        }
                    }
                    // состояние "Пустой результат"
                    is SearchUiState.Empty -> {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text("Ничего не найдено", color = colorScheme.onSurface)
                        }
                    }
                    // состояние "Ожидание ввода"
                    is SearchUiState.Idle -> {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text("Введите название фильма", color = colorScheme.onSurface.copy(alpha = 0.6f))
                        }
                    }
                }
            }
        }
    }
}


// карточка фильма (MovieCard)

/**
 * Карточка с информацией о фильме
 *
 * Отображает:
 * - Постер фильма
 * - Название (русское и оригинальное)
 * - Рейтинг (со звездочкой)
 * - Стрелку для перехода к деталям
 *
 * @param movie Данные фильма из API
 * @param onClick Колбэк при клике на карточку
 */
@Composable
fun MovieCard(
    movie: MovieSearchItemResponse,
    onClick: () -> Unit
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
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // постер
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(movie.posterUrl)
                    .crossfade(true)
                    .diskCacheKey(movie.posterUrl)
                    .build(),
                contentDescription = displayTitle,
                modifier = Modifier
                    .size(70.dp)
                    .clip(RoundedCornerShape(8.dp))
            )

            Spacer(modifier = Modifier.width(12.dp))

            // информация
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = displayTitle,
                    fontWeight = FontWeight.Bold,
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    maxLines = 1,
                    overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis,
                    color = colorScheme.onSurface  // цвет из темы
                )

                if (!movie.nameEn.isNullOrBlank() && movie.nameRu != null) {
                    Text(
                        text = movie.nameEn,
                        fontSize = MaterialTheme.typography.bodySmall.fontSize,
                        color = colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                // рейтинг
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = Color(0xFFFFC107)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = movie.rating ?: "нет рейтинга",
                        fontSize = MaterialTheme.typography.bodySmall.fontSize,
                        color = colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }
            }

            // стрелка вправо
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = "Подробнее",
                tint = colorScheme.onSurface.copy(alpha = 0.4f),
                modifier = Modifier.size(24.dp)
            )
        }
    }
}