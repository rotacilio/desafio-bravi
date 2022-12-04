package br.com.rotacilio.android.boredapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import br.com.rotacilio.android.boredapp.R
import br.com.rotacilio.android.boredapp.db.entities.ActivityEntity
import br.com.rotacilio.android.boredapp.enums.ActivityStatus

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