package br.com.rotacilio.android.boredapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import br.com.rotacilio.android.boredapp.R
import br.com.rotacilio.android.boredapp.ui.components.CardActivity
import br.com.rotacilio.android.boredapp.utils.UiState
import br.com.rotacilio.android.boredapp.viewmodel.FinishedActivitiesViewModel

@Composable
fun FinishedActivitiesScreen(
    onBack: () -> Unit
) {
    val viewModel = hiltViewModel<FinishedActivitiesViewModel>()
    val uiState = viewModel.uiState.collectAsState()

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier.padding(start = 24.dp),
                    text = stringResource(id = R.string.finished_activities),
                    color = Color.White,
                    fontWeight = FontWeight.Medium,
                    fontSize = 24.sp
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onBack.invoke() }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24),
                    contentDescription = null,
                    tint = Color.White
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End,
    ) {
        Column(
            modifier = Modifier.padding(it)
        ) {
            when (val state = uiState.value) {
                is UiState.Loading -> {
                    Text(text = "Carregando...")
                }
                is FinishedActivitiesViewModel.UiStateVC.FinishedActivitiesWasLoaded -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 16.dp, end = 16.dp)
                    ) {
                        items(state.data) { activity ->
                            CardActivity(
                                activity = activity
                            )
                        }
                    }
                }
            }
        }
    }
}