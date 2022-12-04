package br.com.rotacilio.android.boredapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.rotacilio.android.boredapp.db.entities.ActivityEntity
import br.com.rotacilio.android.boredapp.R

@Composable
fun CardActivity(
    activity: ActivityEntity,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = 8.dp)
            .clickable { onClick.invoke() },
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
                text = String.format(stringResource(id = R.string.elapsed_time_format), activity.tempoDecorrido),
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Black,
                maxLines = 1
            )
        }
    }
}