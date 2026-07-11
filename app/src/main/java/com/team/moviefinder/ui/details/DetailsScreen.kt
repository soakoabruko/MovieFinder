package com.team.moviefinder.ui.details

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.team.moviefinder.R
import com.team.moviefinder.data.models.MovieDetailsResponse

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    id: Int,
    onNavigateBack: () -> Unit,
    vm: DetailsViewModel = viewModel(),
) {
    val uiState by vm.uiState.collectAsState()

    val colorScheme = MaterialTheme.colorScheme

    LaunchedEffect(id) {
        vm.loadMovie(id)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.details_title),
                        color = colorScheme.onSurface,
                        fontWeight = FontWeight.SemiBold,
                    )
                },

                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
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

        content = { padding: PaddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                ,
                contentAlignment = Alignment.Center,
            ) {
                when (val currentState = uiState) {
                    is DetailsUiState.Loading -> {
                        CircularProgressIndicator()
                    }

                    is DetailsUiState.Error -> {
                        Text(
                            text = currentState.message,
                            modifier = Modifier.padding(16.dp),
                            color = colorScheme.error,
                        )
                    }

                    is DetailsUiState.Success -> {
                        MovieDetailsContent(movie = currentState.film)
                    }
                }
            }
        },
    )
}

@Composable
fun MovieDetailsContent(movie: MovieDetailsResponse) {
    val displayTitle = movie.nameRu ?: movie.nameEn ?: stringResource(R.string.untitled_movie)

    val colorScheme = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
        ,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(movie.posterUrl)
                .crossfade(true)
                .build()
            ,
            contentDescription = displayTitle,
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
            ,
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = displayTitle,
            color = colorScheme.onSurface,
            textAlign = TextAlign.Center,
            style = typography.headlineLarge,
        )

        if (!movie.nameEn.isNullOrBlank() && !movie.nameRu.isNullOrBlank()) {
            Text(
                text = movie.nameEn,
                color = colorScheme.onSurface.copy(alpha = 0.6f),
                textAlign = TextAlign.Center,
                style = typography.headlineMedium,
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "Рейтинг",
                modifier = Modifier.size(16.dp),
                tint = Color(0xFFFFC107),
            )

            Spacer(modifier = Modifier.width(4.dp))

            Text(
                text = if (movie.rating == null) stringResource(R.string.no_rating) else movie.rating.toString(),
                color = colorScheme.onSurface.copy(alpha = 0.6f),
                style = typography.labelLarge,
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = movie.description ?: stringResource(R.string.no_description),
            style = typography.bodyLarge,
        )
    }
}
