package br.com.rotacilio.android.boredapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import br.com.rotacilio.android.boredapp.R
import br.com.rotacilio.android.boredapp.db.entities.ActivityEntity
import br.com.rotacilio.android.boredapp.enums.ActivityStatus
import br.com.rotacilio.android.boredapp.utils.UiState
import br.com.rotacilio.android.boredapp.viewmodel.PendingActivitiesViewModel

@Composable
fun PendingActivitiesScreen(
    onBack: () -> Unit
) {
    val viewModel = hiltViewModel<PendingActivitiesViewModel>()
    val uiState = viewModel.uiState.collectAsState()
    val selectedActivity = remember { mutableStateOf<ActivityEntity?>(null) }

    if (selectedActivity.value != null) {
        ActivityDetailsDialog(
            activity = selectedActivity,
            startActivity = {
                viewModel.startActivity(selectedActivity.value!!)
                selectedActivity.value = null
            },
            finishActivity = {
                viewModel.finishActivity(selectedActivity.value!!)
                selectedActivity.value = null
            }
        )
    }

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
                    text = stringResource(id = R.string.pending_activities),
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
                is PendingActivitiesViewModel.UiStateVC.PendingActivitiesWasLoaded -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 16.dp, end = 16.dp)
                    ) {
                        items(state.data) { activity ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight()
                                    .padding(top = 8.dp)
                                    .clickable {
                                        selectedActivity.value = activity
                                    },
                                elevation = 8.dp,
                                shape = MaterialTheme.shapes.medium.copy(CornerSize(8.dp)),
                                backgroundColor = Color.White
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Text(
                                        text = activity.activity,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = Color.Black,
                                        style = MaterialTheme.typography.caption,
                                        maxLines = 1
                                    )
                                    Text(
                                        modifier = Modifier.padding(top = 6.dp),
                                        text = activity.statusLabel,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Normal,
                                        color = Color.Black,
                                        maxLines = 1
                                    )
                                    Text(
                                        modifier = Modifier.padding(top = 6.dp),
                                        text = activity.tempoDecorrido,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Thin,
                                        color = Color.Black,
                                        maxLines = 1
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ActivityDetailsDialog(
    activity: MutableState<ActivityEntity?>,
    startActivity: () -> Unit,
    finishActivity: () -> Unit
) {
    Dialog(
        onDismissRequest = { activity.value = null }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(Color.White)
                .padding(start = 24.dp, end = 24.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
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
                    Text(
                        text = String.format(
                            stringResource(id = R.string.description),
                            activity.value?.activity
                        )
                    )
                    Text(
                        text = String.format(
                            stringResource(id = R.string.type),
                            activity.value?.type
                        )
                    )
                    Text(
                        text = String.format(
                            stringResource(id = R.string.participants),
                            activity.value?.participants
                        )
                    )
                    Text(
                        text = String.format(
                            stringResource(id = R.string.price),
                            activity.value?.price
                        )
                    )
                    Text(
                        text = String.format(
                            stringResource(id = R.string.accessibility),
                            activity.value?.accessibility
                        )
                    )
                }
                Row(modifier = Modifier.fillMaxWidth()) {
                    Button(
                        modifier = Modifier.weight(1f),
                        onClick = {
                            when (activity.value?.status) {
                                ActivityStatus.PENDING -> startActivity.invoke()
                                ActivityStatus.IN_PROCESS -> finishActivity.invoke()
                                else -> Unit
                            }
                        }
                    ) {
                        Text(
                            text = stringResource(
                                id = when (activity.value?.status) {
                                    ActivityStatus.IN_PROCESS -> R.string.finish
                                    else -> R.string.start
                                },
                            )
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Button(
                        modifier = Modifier.weight(1f),
                        onClick = { activity.value = null }
                    ) {
                        Text(text = stringResource(id = R.string.cancel))
                    }
                }
            }
        }
    }
}