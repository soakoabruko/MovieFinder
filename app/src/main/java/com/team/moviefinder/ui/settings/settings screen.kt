package com.team.moviefinder.ui.settings

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen() {

    val context = LocalContext.current
    var showBottomSheet by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Приложение позволяет искать фильмы и получать информацию о них."
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                showBottomSheet = true
            }
        ) {
            Text("О нашей команде")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {
                val intent = Intent(
                    Intent.ACTION_VIEW,
                    "https://kpfu.ru".toUri()
                )
                context.startActivity(intent)
            }
        ) {
            Text("Сайт университета")
        }
    }

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                showBottomSheet = false
            }
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                Text(
                    text = "Наша команда",
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text("@soakoo")
                Text("@Yaroslave_Sav")
                Text("@Souzn1k3")
                Text("@tipookie")

                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}