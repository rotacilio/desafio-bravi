package br.com.rotacilio.android.boredapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import br.com.rotacilio.android.boredapp.R
import br.com.rotacilio.android.boredapp.utils.UiState
import br.com.rotacilio.android.boredapp.viewmodel.HomeViewModel

@Composable
fun HomeScreen() {
    val viewModel = hiltViewModel<HomeViewModel>()
    val uiState = viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        when (val state = uiState.value) {
            is UiState.Loading -> {
                Text(text = "Carregando...")
            }
            is UiState.Error -> {
                Text(text = "Error: ${state.code} - ${state.message}")
            }
            is HomeViewModel.UiStateVC.ActivityWasLoaded -> {
                Text(text = state.activity.toString())
            }
            else -> {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 24.dp, end = 24.dp),
                    text = stringResource(id = R.string.choose_activity_message),
                    style = MaterialTheme.typography.h5,
                    textAlign = TextAlign.Center
                )

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 24.dp, end = 24.dp, top = 24.dp),
                    onClick = { viewModel.getRandomActivity() },
                ) {
                    Text(
                        text = stringResource(id = R.string.choose_activity)
                    )
                }
            }
        }
    }
}

@Composable
@Preview
fun HomeScreenPreview() {
    HomeScreen()
}