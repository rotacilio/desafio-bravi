package br.com.rotacilio.android.boredapp.ui.components

import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier

@Composable
fun <T> MyDropdownMenu(
    expanded: Boolean,
    selectedIndex: MutableState<Int>,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    items: List<T>
) {
    DropdownMenu(
        modifier = modifier,
        expanded = expanded,
        onDismissRequest = { onDismissRequest.invoke() }
    ) {
        items.forEachIndexed { index, s ->
            DropdownMenuItem(
                onClick = {
                    selectedIndex.value = index
                    onDismissRequest.invoke()
                }
            ) {
                Text(text = s.toString())
            }
        }
    }
}