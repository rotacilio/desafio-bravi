package br.com.rotacilio.android.boredapp.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import br.com.rotacilio.android.boredapp.R
import br.com.rotacilio.android.boredapp.model.Activity
import br.com.rotacilio.android.boredapp.utils.UiState
import br.com.rotacilio.android.boredapp.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    onBack: () -> Unit,
    goToPendingActivities: () -> Unit,
    goToDoneActivities: () -> Unit,
) {
    val viewModel = hiltViewModel<HomeViewModel>()
    val uiState = viewModel.uiState.collectAsState()
    val activityWasSaved = viewModel.activityWasSaved.collectAsState()
    var displayResultDialog by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = activityWasSaved.value, block = {
        if (activityWasSaved.value)
            displayResultDialog = true
    })

    if (displayResultDialog) {
        AlertDialog(
            onDismissRequest = {},
            title = { Text(text = stringResource(id = R.string.result)) },
            text = { Text(text = stringResource(id = R.string.result_add_success)) },
            buttons = {
                TextButton(
                    onClick = {
                        displayResultDialog = false
                        onBack.invoke()
                    }
                ) {
                    Text(text = stringResource(id = R.string.ok))
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 24.dp, end = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when (val state = uiState.value) {
            is UiState.Loading -> {
                LoadingActivity()
            }
            is UiState.Error -> {
                Text(text = "Error: ${state.code} - ${state.message}")
            }
            is HomeViewModel.UiStateVC.ActivityWasLoaded -> {
                DisplayChosenActivity(
                    activity = state.activity,
                    onBack = onBack,
                    addActivity = { viewModel.registerActivity(state.activity) }
                )
            }
            else -> {
                ChooseActivity(viewModel)
                BottomMenu(goToPendingActivities, goToDoneActivities)
            }
        }
    }
}

@Composable
fun BottomMenu(
    goToPendingActivities: () -> Unit,
    goToDoneActivities: () -> Unit
) {
    Row(
        modifier = Modifier.padding(top = 24.dp)
    ) {
        Button(
            modifier = Modifier
                .weight(1f)
                .padding(end = 3.dp),
            onClick = { goToPendingActivities.invoke() }
        ) {
            Text(text = stringResource(id = R.string.pending_activities))
        }
        Button(
            modifier = Modifier
                .weight(1f)
                .padding(start = 3.dp),
            onClick = { goToDoneActivities.invoke() }
        ) {
            Text(text = stringResource(id = R.string.done_activities))
        }
    }
}

@Composable
fun DisplayChosenActivity(
    activity: Activity,
    onBack: () -> Unit,
    addActivity: () -> Unit
) {
    Text(text = stringResource(id = R.string.chosen_activity))
    Divider(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp)
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = 24.dp, bottom = 24.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(text = String.format(stringResource(id = R.string.description), activity.activity))
        Text(text = String.format(stringResource(id = R.string.type), activity.type))
        Text(
            text = String.format(
                stringResource(id = R.string.participants),
                activity.participants
            )
        )
        Text(text = String.format(stringResource(id = R.string.price), activity.price))
        Text(
            text = String.format(
                stringResource(id = R.string.accessibility),
                activity.accessibility
            )
        )
    }
    Row(modifier = Modifier.fillMaxWidth()) {
        Button(
            modifier = Modifier.weight(1f),
            onClick = { addActivity.invoke() }
        ) {
            Text(text = stringResource(id = R.string.add))
        }
        Spacer(modifier = Modifier.width(16.dp))
        Button(
            modifier = Modifier.weight(1f),
            onClick = { onBack.invoke() }
        ) {
            Text(text = stringResource(id = R.string.try_again))
        }
    }

    BackHandler {
        onBack.invoke()
    }
}

@Composable
fun LoadingActivity() {
    Text(
        text = stringResource(id = R.string.searching_activity),
        style = MaterialTheme.typography.h6,
        textAlign = TextAlign.Center
    )
    CircularProgressIndicator(
        modifier = Modifier
            .width(100.dp)
            .height(100.dp)
            .padding(top = 24.dp)
    )
}

@Composable
fun ChooseActivity(
    viewModel: HomeViewModel
) {
    Text(
        modifier = Modifier
            .fillMaxWidth(),
        text = stringResource(id = R.string.choose_activity_message),
        style = MaterialTheme.typography.h5,
        textAlign = TextAlign.Center
    )
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp),
        onClick = { viewModel.getRandomActivity() },
    ) {
        Text(
            text = stringResource(id = R.string.choose_activity)
        )
    }
}